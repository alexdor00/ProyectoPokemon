package OperacionesJson;


import com.google.gson.Gson;
        import com.google.gson.JsonArray;
        import com.google.gson.JsonObject;

        import java.io.FileReader;
        import java.io.IOException;

public class LeerJson {
    public static void main(String[] args) {
        String filePath = "data/pokemon.json";

        try (FileReader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(reader, JsonObject.class);

            // Accede a los datos que necesites
            JsonArray pokemons = json.getAsJsonArray("pokemon");

            for (int i = 0; i < pokemons.size(); i++) {
                JsonObject pokemon = pokemons.get(i).getAsJsonObject();
                String name = pokemon.get("name").getAsString();
                String type = pokemon.get("type").toString();
                // ... Accede a otros campos según tus necesidades
                System.out.println("Nombre: " + name);
                System.out.println("Tipo: " + type);
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
