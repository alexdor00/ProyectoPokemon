package OperacionesJson;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class PokemonCollectionOperations {
    private List<Pokemon> pokemonList;

    public PokemonCollectionOperations(String filePath) {
        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);
            JsonArray pokemonArray = json.getAsJsonArray("pokemon");
            this.pokemonList = StreamSupport.stream(pokemonArray.spliterator(), false)
                    .map(JsonElement -> gson.fromJson(JsonElement, Pokemon.class))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getFirst10PokemonNames() {
        return pokemonList.stream()
                .limit(10)
                .map(Pokemon::getName)
                .collect(Collectors.toList());
    }

    public List<String> getLast5PokemonNames() {
        return pokemonList.stream()
                .skip(Math.max(0, pokemonList.size() - 5))
                .map(Pokemon::getName)
                .collect(Collectors.toList());
    }

    public Pokemon getPikachu() {
        return pokemonList.stream()
                .filter(pokemon -> "Pikachu".equals(pokemon.getName()))
                .findFirst()
                .orElse(null);
    }

    public Pokemon getCharmander() {
        return pokemonList.stream()
                .filter(pokemon -> "Charmander".equals(pokemon.getName()))
                .findFirst()
                .orElse(null);
    }

   /* public List<String> getFireTypePokemonNames() {
        return pokemonList.stream()
                .filter(pokemon -> pokemon.getTypes().contains("Fire"))
                .map(Pokemon::getName)
                .collect(Collectors.toList());
    }*/

    public List<String> getWaterOrElectricWeaknessPokemonNames() {
        return pokemonList.stream()
                .filter(pokemon -> pokemon.getWeaknesses().contains("Water") || pokemon.getWeaknesses().contains("Electric"))
                .map(Pokemon::getName)
                .collect(Collectors.toList());
    }

    public long getSingleWeaknessPokemonCount() {
        return pokemonList.stream()
                .filter(pokemon -> pokemon.getWeaknesses().size() == 1)
                .count();
    }

    public Pokemon getMostWeaknessesPokemon() {
        return pokemonList.stream()
                .max(Comparator.comparingInt(pokemon -> pokemon.getWeaknesses().size()))
                .orElse(null);
    }

   /* public Pokemon getLeastEvolutionsPokemon() {
        return pokemonList.stream()
                .min(Comparator.comparingInt(pokemon -> pokemon.getEvolutions().size()))
                .orElse(null);
    }*/

    /*public Pokemon getNonFireEvolutionsPokemon() {
        return pokemonList.stream()
                .filter(pokemon -> !pokemon.getEvolutions().isEmpty())
                .filter(pokemon -> pokemon.getEvolutions().stream().noneMatch(evolution -> evolution.getType().equals("Fire")))
                .findFirst()
                .orElse(null);
    }*/

    public Pokemon getHeaviestPokemon() {
        return pokemonList.stream()
                .max(Comparator.comparingDouble(pokemon -> parseWeight(pokemon.getWeight())))
                .orElse(null);
    }

    public Pokemon getTallestPokemon() {
        return pokemonList.stream()
                .max(Comparator.comparingDouble(pokemon -> parseHeight(pokemon.getHeight())))
                .orElse(null);
    }

    public Pokemon getLongestNamePokemon() {
        return pokemonList.stream()
                .max(Comparator.comparingInt(pokemon -> pokemon.getName().length()))
                .orElse(null);
    }

    public double getAverageWeight() {
        return pokemonList.stream()
                .mapToDouble(pokemon -> parseWeight(pokemon.getWeight()))
                .average()
                .orElse(0);
    }

    public double getAverageHeight() {
        return pokemonList.stream()
                .mapToDouble(pokemon -> parseHeight(pokemon.getHeight()))
                .average()
                .orElse(0);
    }

/*    public double getAverageEvolutions() {
        return pokemonList.stream()
                .mapToInt(pokemon -> pokemon.getEvolutions().size())
                .average()
                .orElse(0);
    }*/

    public double getAverageWeaknesses() {
        return pokemonList.stream()
                .mapToInt(pokemon -> pokemon.getWeaknesses().size())
                .average()
                .orElse(0);
    }

  /*  public Map<String, List<Pokemon>> groupPokemonByType() {
        return pokemonList.stream()
                .collect(Collectors.groupingBy(Pokemon::getTypes));
    }*/

    public Map<String, Long> countPokemonByWeakness() {
        return pokemonList.stream()
                .flatMap(pokemon -> pokemon.getWeaknesses().stream())
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));
    }

   /* public Map<Integer, List<Pokemon>> groupPokemonByEvolutions() {
        return pokemonList.stream()
                .collect(Collectors.groupingBy(pokemon -> pokemon.getEvolutions().size()));
    }*/

    public String getMostCommonWeakness() {
        return pokemonList.stream()
                .flatMap(pokemon -> pokemon.getWeaknesses().stream())
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private double parseWeight(String weight) {
        try {
            return Double.parseDouble(weight.split(" ")[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    private double parseHeight(String height) {
        try {
            return Double.parseDouble(height.split(" ")[0]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }
    public List<Pokemon> getPokemonList() {
        return pokemonList;
    }
    public static void main(String[] args) {
        String filePath = "data/pokemon.json";
        PokemonCollectionOperations collection = new PokemonCollectionOperations(filePath);

        System.out.println("Nombres de los 10 primeros pokemons: ");
        List<String> first10Names = collection.getFirst10PokemonNames();
        first10Names.forEach(System.out::println);


        System.out.println("Nombres de los 5 últimos pokemons: ");
        List<String> last5Names = collection.getLast5PokemonNames();
        last5Names.forEach(System.out::println);

        System.out.println("Datos de Pikachu: ");
        Pokemon pikachu = collection.getPikachu();
        System.out.println(pikachu);

        System.out.println("Datos de Charmander: ");
        Pokemon charmander = collection.getCharmander();
        System.out.println(charmander);

        System.out.println("Nombres de los pokemons con debilidad a Water o Electric: ");
        List<String> waterElectricWeakness = collection.getWaterOrElectricWeaknessPokemonNames();
        waterElectricWeakness.forEach(System.out::println);

        System.out.println("Número de pokemons con solo una debilidad: ");
        long singleWeaknessCount = collection.getSingleWeaknessPokemonCount();
        System.out.println(singleWeaknessCount);

        System.out.println("Pokemon con más debilidades: ");
        Pokemon mostWeaknesses = collection.getMostWeaknessesPokemon();
        System.out.println(mostWeaknesses);

        System.out.println("Pokemon más pesado: ");
        Pokemon heaviestPokemon = collection.getHeaviestPokemon();
        System.out.println(heaviestPokemon);

        System.out.println("Pokemon más alto: ");
        Pokemon tallestPokemon = collection.getTallestPokemon();
        System.out.println(tallestPokemon);

        System.out.println("Pokemon con el nombre más largo: ");
        Pokemon longestNamePokemon = collection.getLongestNamePokemon();
        System.out.println(longestNamePokemon);

        System.out.println("Media de peso de los pokemons: ");
        double averageWeight = collection.getAverageWeight();
        System.out.println(averageWeight);

        System.out.println("Media de altura de los pokemons: ");
        double averageHeight = collection.getAverageHeight();
        System.out.println(averageHeight);

        System.out.println("Media de debilidades de los pokemons: ");
        double averageWeaknesses = collection.getAverageWeaknesses();
        System.out.println(averageWeaknesses);

        System.out.println("Conteo de pokemons por debilidad: ");
        Map<String, Long> weaknessCount = collection.countPokemonByWeakness();
        weaknessCount.forEach((weakness, count) -> System.out.println(weakness + ": " + count));

        System.out.println("Debilidad más común: ");
        String mostCommonWeakness = collection.getMostCommonWeakness();
        System.out.println(mostCommonWeakness);


    }
}

