package LeerCsv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CsvFileReader {
    public static void main(String[] args) {

        String csvFilePath = "data/pokemon.csv";

        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            // Lee todas las filas del archivo CSV
            List<String[]> allData = csvReader.readAll();

            // Itera a través de las filas y muestra cada línea
            for (String[] row : allData) {
                for (String cell : row) {
                    System.out.print(cell + " | ");
                }
                System.out.println(); // Nueva línea para la siguiente fila
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }
    }
}
