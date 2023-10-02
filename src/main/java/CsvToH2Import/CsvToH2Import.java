package CsvToH2Import;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;


import OperacionesJson.Pokemon;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvToH2Import {
    public static void main(String[] args) {
        String csvFilePath = "data/pokemon.csv"; // Reemplaza con la ruta correcta de tu archivo CSV

        // Configura la unidad de persistencia JPA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pokemon");
        EntityManager em = emf.createEntityManager();

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            List<String[]> allData = csvReader.readAll();

            // Inicia una transacción
            em.getTransaction().begin();

            for (String[] row : allData) {
                Pokemon pokemon = new Pokemon();
                pokemon.setId(Integer.parseInt(row[0]));
                pokemon.setNum(row[1]);
                pokemon.setName(row[2]);
                pokemon.setHeight(row[3]);
                pokemon.setWeight(row[4]);

                // Persiste el objeto Pokemon en la base de datos
                em.persist(pokemon);
            }

            // Confirma la transacción
            em.getTransaction().commit();

        } catch (IOException | CsvException e) {
            e.printStackTrace();
        } finally {
            // Cierra el EntityManager y EntityManagerFactory
            em.close();
            emf.close();
        }
    }
}
