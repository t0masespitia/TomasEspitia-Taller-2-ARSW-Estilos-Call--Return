import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class URLRead {

    public static void main(String[] args) {
        try (BufferedReader consoleReader =
                     new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            System.out.print("Ingresa la URL: ");
            String urlText = consoleReader.readLine().trim();

            if (urlText.isEmpty()) {
                System.err.println("Debes ingresar una URL.");
                return;
            }

            URL page = URI.create(urlText).toURL();
            Path outputFile = Path.of("resultado.html");

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(page.openStream(), StandardCharsets.UTF_8));
                 Writer writer = Files.newBufferedWriter(outputFile, StandardCharsets.UTF_8)) {

                String inputLine;

                while ((inputLine = reader.readLine()) != null) {
                    writer.write(inputLine);
                    writer.write(System.lineSeparator());
                }

                writer.flush();
                System.out.println("Archivo generado en: " + outputFile.toAbsolutePath());
            }
        } catch (MalformedURLException | IllegalArgumentException e) {
            System.err.println("URL malformada: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("No se pudo leer la pagina o escribir el archivo: " + e.getMessage());
        }
    }
}
