package com.example.pokemonrecyclerview;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonService {
    @GET("/api/v2/pokemon")
    Call<PokemonListResponse> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);

    @GET("/api/v2/pokemon/{id}")
    Call<Pokemon> getPokemonDetails(@Path("id") String id);
}