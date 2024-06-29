package com.example.pokemonrecyclerview;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

public class PokemonInfoActivity extends AppCompatActivity {
    private TextView pokemonName;
    private TextView pokemonId;
    private ImageView pokemonImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_info);
        pokemonName=findViewById(R.id.componentPokemonName);
        pokemonId=findViewById(R.id.componentPokemonId);
        pokemonImage=findViewById(R.id.pokemonImage);

        Bundle bundle=getIntent().getExtras();
        if (bundle!=null){
            String nameValue=bundle.getString("name");
            String idValue=bundle.getString("id");
            String imageValue=bundle.getString("image");
            pokemonName.setText(nameValue);
            pokemonId.setText(idValue);
            Glide.with(this)
                    .asBitmap()
                    .load(imageValue)
                    .into(pokemonImage);
        }
    }



}

