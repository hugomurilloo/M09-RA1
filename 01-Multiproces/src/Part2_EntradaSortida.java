// Part2_EntradaSortida.java
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Part2_EntradaSortida {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Sistema operatiu detectat: " + SO.nomSO());

        // Creem ProcessBuilder amb la comanda per ordenar
        ProcessBuilder pb = new ProcessBuilder(SO.ordenar());
        pb.redirectErrorStream(true); // stderr -> stdout

        // Llancem el procés
        Process process = pb.start();

        String[] fruites = {"plàtan", "poma", "albergínia", "cireres", "maduixa"};

        // Escrivim al stdin del procés fill (sort)
        System.out.println("Enviem al procés 'sort':");
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(process.getOutputStream(), StandardCharsets.UTF_8), true)) {
            for (String fruita : fruites) {
                System.out.println("-> " + fruita);
                writer.println(fruita);
            }
        } // En tancar el writer, enviem EOF al fill

        // Llegim la sortida del procés fill (ja ordenada)
        System.out.println("Resposta del procés 'sort' (ordenat):");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("<- " + line);
            }
        }

        int exitCode = process.waitFor();
        System.out.println("Codi de retorn: " + exitCode);
    }
}