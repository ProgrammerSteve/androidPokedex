package com.example.pokemonrecyclerview;

import java.util.List;
import lombok.Data;

@Data
public class Pokemon {
    private int id;
    private String name;
    private List<AbilityData> abilities;
    private Sprites sprites;

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