package com.example.pokemonrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {
    RecyclerView pokemonRecView;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView=findViewById(R.id.searchView);
        pokemonRecView = findViewById(R.id.pokemonRecycler);
        ArrayList<Pokemon> pokemonList = new ArrayList<>();

        PokemonRecViewAdapter adapter = new PokemonRecViewAdapter(this);
        adapter.setPokemonList(pokemonList);

        pokemonRecView.setAdapter(adapter);
        pokemonRecView.setLayoutManager(new GridLayoutManager(this, 1));


        PokemonService service = Utils.retrofit.create(PokemonService.class);
        Call<PokemonListResponse> call = service.getPokemonList(25, 0); // Fetch the first 20 Pokémon

        call.enqueue(new Callback<PokemonListResponse>() {
            @Override
            public void onResponse(Call<PokemonListResponse> call, Response<PokemonListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonListResponse pokemonListResponse = response.body();
                    List<PokemonListResponse.PokemonResult> results = pokemonListResponse.getResults();

                    // Now you can iterate through the results or use them as needed
                    for (PokemonListResponse.PokemonResult result : results) {
                        Log.e("PokemonListResponse", result.toString());
                        String url = result.getUrl();
                        String pokemonId = extractPokemonId(url);
                        fetchPokemonDetails(pokemonId,adapter);

                        // Process each PokemonResult object here
                    }
                } else {
                    // Handle unsuccessful response or empty body
                    Log.e("MainActivity", "PokemonListResponse is empty");
                }
            }

            @Override
            public void onFailure(Call<PokemonListResponse> call, Throwable t) {
                Log.e("MainActivity", "PokemonListResponse Failed");
                // Handle network failures here
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
    }


    private String extractPokemonId(String url) {
        // Assuming the URL is in the format "https://pokeapi.co/api/v2/pokemon/{id}/"
        String[] parts = url.split("/");
        return parts[parts.length - 1]; // The ID should be the second-to-last part in the URL
    }

    private void fetchPokemonDetails(String pokemonId, PokemonRecViewAdapter adapter) {

        PokemonService service = Utils.retrofit.create(PokemonService.class);
        Call<Pokemon> call = service.getPokemonDetails(pokemonId);

        call.enqueue(new Callback<Pokemon>() {
            @Override
            public void onResponse(Call<Pokemon> call, Response<Pokemon> response) {
                Log.d("fetchPokemonDetails",response.toString());
                if (response.isSuccessful() && response.body() != null) {
                    Pokemon fetchedPokemon = response.body();
                    adapter.addPokemon(fetchedPokemon);
                } else {
                    Log.e("MainActivity", "Failed to retrieve Pokémon details");
                }
            }

            @Override
            public void onFailure(Call<Pokemon> call, Throwable t) {
                Log.e("MainActivity", "onFailure: ", t);
            }
        });
    }
}