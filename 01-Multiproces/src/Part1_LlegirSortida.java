// Part1_LlegirSortida.java
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Part1_LlegirSortida {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Sistema operatiu detectat: " + SO.nomSO());

        // Creem ProcessBuilder amb la comanda per llistar fitxers
        ProcessBuilder pb = new ProcessBuilder(SO.llistarFitxers());
        pb.directory(new File(System.getProperty("user.dir"))); // Carpeta actual
        pb.redirectErrorStream(true); // stderr -> stdout

        // Llancem el procés
        Process process = pb.start();

        System.out.println("=== Contingut del directori ===");

        // Llegim la sortida línia a línia
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        }

        // Esperem que acabi i obtenim el codi de retorn
        int exitCode = process.waitFor();
        System.out.println("El procés ha acabat amb codi: " + exitCode);
    }
}