package com.example.pokemonrecyclerview;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PokemonRecViewAdapter extends RecyclerView.Adapter<PokemonRecViewAdapter.ViewHolder> {

    private List<Pokemon> pokemonList;
    private Context context;

    public PokemonRecViewAdapter(Context context) {
        this.context = context;
        this.pokemonList = new ArrayList<>();
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
        Log.d("POKEMON_LIST",pokemonList.toString());
        notifyDataSetChanged(); // Notify adapter of data change
    }

    // Method to add a single Pokemon to the list
    public void addPokemon(Pokemon pokemon) {
        pokemonList.add(pokemon);
        for(Pokemon x:pokemonList ){
            Log.d("POKEMON_ADDED",x.toString());
        }

        notifyItemInserted(pokemonList.size() - 1); // Notify adapter of item insertion
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.pokemon_list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pokemon pokemon = pokemonList.get(position);

        holder.pokemonName.setText(pokemon.getName());
        holder.pokemonId.setText(String.valueOf(pokemon.getId())); // Convert int to String

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, pokemon.getName(), Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, PokemonInfoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("name",pokemon.getName());
                bundle.putString("id",String.valueOf(pokemon.getId()));
                bundle.putString("image",pokemon.getSprites().getFront_default());
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .asBitmap()
                .load(pokemon.getSprites().getFront_default())
                .into(holder.pokemonImage);
    }
    @Override
    public int getItemCount() {
        return pokemonList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView parent;
        ImageView pokemonImage;
        TextView pokemonName, pokemonId;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent=itemView.findViewById(R.id.parent);
            pokemonImage=itemView.findViewById(R.id.pokemonImage);
            pokemonName=itemView.findViewById(R.id.pokemonName);
            pokemonId=itemView.findViewById(R.id.pokemonId);
        }
    }

}
