// Part3_Pipe.java - Versió que busca automàticament
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Part3_Pipe {
    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println("Sistema operatiu detectat: " + SO.nomSO());

        // Buscar el directori que conté fitxers .java
        File directorioTreball = encontrarDirectorioConJava(new File("."));
        
        if (directorioTreball == null) {
            directorioTreball = new File(".");
        }

        System.out.println("Directori: " + directorioTreball.getAbsolutePath());

        // Procés: llistar fitxers
        ProcessBuilder pbList = new ProcessBuilder(SO.llistarFitxers());
        pbList.directory(directorioTreball);
        pbList.redirectErrorStream(true);

        Process listProcess = pbList.start();

        // Llegir la sortida del llistat
        StringBuilder output = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(listProcess.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
        }

        int exitCode = listProcess.waitFor();

        System.out.println("=== Fitxers .java trobats ===");

        // Filtrar les línies que contenen ".java"
        String[] lines = output.toString().split("\n");
        boolean trobat = false;
        
        for (String line : lines) {
            if (line.contains(".java")) {
                System.out.println(line);
                trobat = true;
            }
        }

        if (!trobat) {
            System.out.println("No s'han trobat fitxers .java");
        }

        System.out.println("Pipeline completat.");
    }
    
    private static File encontrarDirectorioConJava(File dir) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    File result = encontrarDirectorioConJava(file);
                    if (result != null) {
                        return result;
                    }
                } else if (file.getName().endsWith(".java")) {
                    return dir;
                }
            }
        }
        return null;
    }
}