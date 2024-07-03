package com.example.pokemonrecyclerview;

import java.util.List;
import lombok.Data;

@Data
public class PokemonSpecies {
    private List<FlavorTextEntry> flavor_text_entries;

    @Data
    public static class FlavorTextEntry {
        private String flavor_text;
        private Language language;
        private Version version; // Add Version class reference
    }

    @Data
    public static class Language {
        private String name;
        private String url;
    }

    @Data
    public static class Version { // Add Version class
        private String name;
        private String url;
    }
}
// "flavor_text_entries": [
//         {
//             "flavor_text": "Capable of copying\nan enemy's genetic\ncode to instantly\ftransform itself\ninto a duplicate\nof the enemy.",
//             "language": {
//                     "name": "en",
//                     "url": "https://pokeapi.co/api/v2/language/9/"
//             },
//             "version": {
//                     "name": "red",
//                     "url": "https://pokeapi.co/api/v2/version/1/"
//             }
//         }
// ]