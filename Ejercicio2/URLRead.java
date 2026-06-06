import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class URLRead {

    public static void main(String[] args) {

        try (BufferedReader consoleReader =
                new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {

            System.out.print("Ingresa la URL: ");
            String urlText = consoleReader.readLine().trim();

            if (urlText.isEmpty()) {
                System.err.println("Error: debes ingresar una URL.");
                return;
            }

            URL page = URI.create(urlText).toURL();

            Path outputFile = Paths.get("resultado.html");

            try (BufferedReader webReader =
                        new BufferedReader(
                            new InputStreamReader(page.openStream(), StandardCharsets.UTF_8));
                 Writer fileWriter =
                        Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {

                String line;
                while ((line = webReader.readLine()) != null) {
                    fileWriter.write(line);
                    fileWriter.write(System.lineSeparator());
                }

                fileWriter.flush();
            }

            System.out.println("Archivo guardado en: " + outputFile.toAbsolutePath());

            File htmlFile = outputFile.toFile();

            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(htmlFile.toURI());
                } else {
                    Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", htmlFile.getAbsolutePath()});
                }
                System.out.println("Abriendo resultado.html en el navegador...");
            } catch (IOException ex) {
                System.out.println("No se pudo abrir el navegador. Abre manualmente: " + htmlFile.getAbsolutePath());
            }

        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}