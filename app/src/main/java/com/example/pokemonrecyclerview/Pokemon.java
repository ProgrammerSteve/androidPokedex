package com.example.pokemonrecyclerview;

import java.util.List;
import lombok.Data;


// "types": [
//         {
//             "slot": 1,
//             "type": {
//                 "name": "grass",
//                 "url": "https://pokeapi.co/api/v2/type/12/"
//             }
//         },
//         {
//             "slot": 2,
//             "type": {
//                 "name": "poison",
//                 "url": "https://pokeapi.co/api/v2/type/4/"
//             }
//         }
// ],




@Data
public class Pokemon {
    private int id;
    private String name;
    private List<AbilityData> abilities;
    private Sprites sprites;
    private double weight;
    private double height;

    private List<TypeObj> types;
    @Data
    public static class TypeObj{
        private Type type;
    }
    @Data
    public static class Type{
        private String name;
    }


    @Data
    public static class AbilityData {
        private boolean is_hidden;
        private int slot;
        private Ability ability;
    }

    @Data
    public static class Ability {
        private String name;
        private String url;
    }

    @Data
    public static class Sprites{
        private String front_default;
    }
}