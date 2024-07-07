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

import android.widget.Filter;
import android.widget.Filterable;
import java.util.stream.Collectors;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class PokemonRecViewAdapter extends RecyclerView.Adapter<PokemonRecViewAdapter.ViewHolder> implements Filterable {

    private List<Pokemon> pokemonList;
    private List<Pokemon> pokemonListFull; //#######################################################
    private Context context;

    public PokemonRecViewAdapter(Context context) {
        this.context = context;
        this.pokemonList = new ArrayList<>();
        this.pokemonListFull = new ArrayList<>(); //#######################################################
    }

    public void setPokemonList(List<Pokemon> pokemonList) {
        this.pokemonList = pokemonList;
        this.pokemonListFull = new ArrayList<>(pokemonList); //#######################################################
        notifyDataSetChanged();
    }

    // Method to add a single Pokemon to the list
    public void addPokemon(Pokemon pokemon) {
        pokemonList.add(pokemon);
        pokemonListFull.add(pokemon); //#######################################################
        notifyItemInserted(pokemonList.size() - 1);// Notify adapter of item insertion
    }



    @Override
    public Filter getFilter() {
        return pokemonFilter;
    }

    private Filter pokemonFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Pokemon> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(pokemonListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                filteredList = pokemonListFull.stream()
                        .filter(pokemon -> pokemon.getName().toLowerCase().contains(filterPattern))
                        .collect(Collectors.toList());
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            pokemonList.clear();
            pokemonList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


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

        holder.pokemonName.setText(Utils.formatName(pokemon.getName()));
        holder.pokemonId.setText(Utils.formatId(String.valueOf(pokemon.getId()))); // Convert int to String

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, pokemon.getName(), Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, PokemonInfoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putDouble("weight",pokemon.getWeight()/10);
                bundle.putDouble("height",pokemon.getHeight()*10);

                bundle.putString("name",Utils.formatName(pokemon.getName()));

                bundle.putString("typeOne",pokemon.getTypes().get(0).getType().getName());
                if(pokemon.getTypes().size()==2){
                    bundle.putString("typeTwo",pokemon.getTypes().get(1).getType().getName());
                }else {
                    bundle.putString("typeTwo","");
                }

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
