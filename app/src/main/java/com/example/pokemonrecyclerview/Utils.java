package com.example.pokemonrecyclerview;

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
}
