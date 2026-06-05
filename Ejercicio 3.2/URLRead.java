import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class URLRead {

    public static void main(String[] args) {
        try {
            URL page = URI.create("https://example.com/").toURL();

            try (BufferedReader reader =
                    new BufferedReader(new InputStreamReader(page.openStream(), StandardCharsets.UTF_8))) {

                String inputLine;

                while ((inputLine = reader.readLine()) != null) {
                    System.out.println(inputLine);
                }
            }
        } catch (MalformedURLException e) {
            System.err.println("URL malformada: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("No se pudo leer la pagina o escribir el archivo: " + e.getMessage());
        }
    }
}
