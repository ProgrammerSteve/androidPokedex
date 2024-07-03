package com.example.pokemonrecyclerview;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {

    public static String formatName(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public static String formatId(String id){
        if(id==null){
            return "000";
        }
        int len=id.length();
        if(len>3){
            return id;
        }
        return "0".repeat(3-len)+id;
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final Map<String, String> TYPE_COLOR  = new HashMap<String, String>(){{
        put("normal", "#A8A77A");
        put("fire", "#EE8130");
        put("water", "#6390F0");
        put("electric", "#F7D02C");
        put("grass", "#7AC74C");
        put("ice", "#96D9D6");
        put("fighting", "#C22E28");
        put("poison", "#A33EA1");
        put("ground", "#E2BF65");
        put("flying", "#A98FF3");
        put("psychic", "#F95587");
        put("bug", "#A6B91A");
        put("rock", "#B6A136");
        put("ghost", "#735797");
        put("dragon", "#6F35FC");
        put("dark", "#705746");
        put("steel", "#B7B7CE");
        put("fairy", "#D685AD");
    }};

}
