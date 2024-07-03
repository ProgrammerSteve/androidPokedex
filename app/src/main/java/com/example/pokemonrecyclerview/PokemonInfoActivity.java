package com.example.pokemonrecyclerview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

    private CardView backCardBtn;
    private ImageButton imageBtn;
    private TextView pokemonName;
    private TextView pokemonId;
    private TextView pokemonWeight;
    private TextView pokemonHeight;
    private TextView pokemonTypeOne;
    private TextView pokemonTypeTwo;
    private TextView pokemonFlavorText;
    private ImageView pokemonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);
        pokemonName = findViewById(R.id.componentPokemonName);
        pokemonId = findViewById(R.id.componentPokemonId);
        pokemonImage = findViewById(R.id.pokemonImage);
        pokemonWeight = findViewById(R.id.pokemonWeight);
        pokemonHeight = findViewById(R.id.pokemonHeight);
        pokemonTypeOne = findViewById(R.id.typeOne);
        pokemonTypeTwo = findViewById(R.id.typeTwo);
        pokemonFlavorText = findViewById(R.id.flavorText);
        backCardBtn=findViewById(R.id.backCardBtn);
        imageBtn=findViewById(R.id.imageBtn);

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PokemonInfoActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String nameValue = bundle.getString("name");
            String idValue = bundle.getString("id");
            String imageValue = bundle.getString("image");
            String typeOne = bundle.getString("typeOne");
            String typeTwo = bundle.getString("typeTwo");

            double weight=bundle.getDouble("weight");
            double height=bundle.getDouble("height");

            pokemonWeight.setText(String.valueOf(weight)+" kg");
            pokemonHeight.setText(String.valueOf(height)+" cm");

            pokemonName.setText(nameValue);
            pokemonId.setText(idValue);
            pokemonTypeOne.setText(Utils.formatName(typeOne));

            pokemonTypeOne.setBackgroundColor(Color.parseColor(Utils.TYPE_COLOR.get(typeOne)));
            pokemonTypeOne.setTextColor(Color.WHITE);
            pokemonTypeTwo.setText(Utils.formatName(typeTwo));
            if(typeTwo!=null && !typeTwo.isEmpty()){
                pokemonTypeTwo.setBackgroundColor(Color.parseColor(Utils.TYPE_COLOR.get(typeTwo)));
                pokemonTypeTwo.setTextColor(Color.WHITE);
            }


            Glide.with(this)
                    .asBitmap()
                    .load(imageValue)
                    .into(pokemonImage);

            PokemonService service = Utils.retrofit.create(PokemonService.class);
            Call<PokemonSpecies> call = service.getPokemonSpecies(idValue);

            call.enqueue(new Callback<PokemonSpecies>() {
                @Override
                public void onResponse(Call<PokemonSpecies> call, Response<PokemonSpecies> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Log.d(TAG, "Response received: " + response.body());
                        List<PokemonSpecies.FlavorTextEntry> flavorTextEntries = response.body().getFlavor_text_entries();
                        if (flavorTextEntries != null && !flavorTextEntries.isEmpty()) {
                            for (PokemonSpecies.FlavorTextEntry entry : flavorTextEntries) {
                                if ("en".equals(entry.getLanguage().getName())) {
                                    String flavorText = entry.getFlavor_text().replace("\n", " ");
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










        }else{
            Log.e(TAG, "No bundle received");
        }










}
}

