package OperacionesJson;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PokemonCsvExporter {
    public static void exportToCsv(List<Pokemon> pokemonList, String outputPath) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputPath))) {
            // Definimos las columnas del archivo CSV
            String[] header = {"id", "num", "name", "height", "weight"};
            writer.writeNext(header);

            // Recorremos la lista de Pokémon y escribimos sus datos en el archivo CSV
            for (Pokemon pokemon : pokemonList) {
                String[] data = {
                        String.valueOf(pokemon.getId()),
                        pokemon.getNum(),
                        pokemon.getName(),
                        pokemon.getHeight(),
                        pokemon.getWeight()
                };
                writer.writeNext(data);
            }

            System.out.println("Datos exportados exitosamente a " + outputPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String filePath = "data/pokemon.json"; // Ruta al archivo JSON
        PokemonCollectionOperations collection = new PokemonCollectionOperations(filePath);

        List<Pokemon> pokemonList = collection.getPokemonList();


        String outputPath = "data/pokemon.csv";


        exportToCsv(pokemonList, outputPath);
    }
}

