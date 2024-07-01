package com.example.pokemonrecyclerview;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonInfoActivity extends AppCompatActivity {
    private static final String TAG = "PokemonInfoActivity";

    private TextView pokemonName;
    private TextView pokemonId;
    private TextView pokemonTypeOne;
    private TextView pokemonTypeTwo;
    private TextView pokemonFlavorText;
    private ImageView pokemonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);
        pokemonName=findViewById(R.id.componentPokemonName);
        pokemonId=findViewById(R.id.componentPokemonId);
        pokemonImage=findViewById(R.id.pokemonImage);
        pokemonTypeOne=findViewById(R.id.typeOne);
        pokemonTypeTwo=findViewById(R.id.typeTwo);
        pokemonFlavorText=findViewById(R.id.flavorText);

        Bundle bundle=getIntent().getExtras();
        if (bundle != null) {
            String nameValue = bundle.getString("name");
            String idValue = bundle.getString("id");
            String imageValue = bundle.getString("image");
            String typeOne = bundle.getString("typeOne");
            String typeTwo = bundle.getString("typeTwo");

            pokemonName.setText(nameValue);
            pokemonId.setText(idValue);
            pokemonTypeOne.setText(Utils.formatName(typeOne));
            pokemonTypeTwo.setText(Utils.formatName(typeTwo));

            Glide.with(this)
                    .asBitmap()
                    .load(imageValue)
                    .into(pokemonImage);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://pokeapi.co")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            PokemonService service = retrofit.create(PokemonService.class);
            Log.d(TAG, "idValue: " +idValue);
            Call<PokemonSpecies> call = service.getPokemonSpecies(idValue);
            call.enqueue(new Callback<PokemonSpecies>() {
                @Override
                public void onResponse(Call<PokemonSpecies> call, Response<PokemonSpecies> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Response received: " + response.body());
                        List<PokemonSpecies.FlavorTextEntry> flavorTextEntries = response.body().getFlavorTextEntries();
                        if (flavorTextEntries != null && !flavorTextEntries.isEmpty()) {
                            for (PokemonSpecies.FlavorTextEntry entry : flavorTextEntries) {
                                if ("en".equals(entry.getLanguage().getName())) {
                                    String flavorText = entry.getFlavorText().replace("\n", " ");
                                    pokemonFlavorText.setText(flavorText);
                                    return;
                                }
                            }
                            pokemonFlavorText.setText("No English flavor text found");
                        } else {
                            Log.e(TAG, "No flavor text entries found");
                            pokemonFlavorText.setText("No flavor text entries found");
                        }
                    } else {
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                            Log.e(TAG, "Failed to retrieve Pokémon flavor text details. Response code: " + response.code() + ", Error: " + errorBody);
                            pokemonFlavorText.setText("Failed to retrieve Pokémon flavor text details");
                        } catch (Exception e) {
                            Log.e(TAG, "Failed to retrieve Pokémon flavor text details. Response code: " + response.code(), e);
                            pokemonFlavorText.setText("Failed to retrieve Pokémon flavor text details");
                        }
                    }
                }

                @Override
                public void onFailure(Call<PokemonSpecies> call, Throwable t) {
                    pokemonFlavorText.setText("Failed to load...\n" + t.getMessage());
                    Log.e(TAG, "Error: " + t.getMessage(), t);
                }
            });
        } else {
            Log.e(TAG, "No bundle received");
        }




    }



}

