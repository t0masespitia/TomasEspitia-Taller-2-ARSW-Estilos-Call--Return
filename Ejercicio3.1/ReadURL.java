import java.net.MalformedURLException;
import java.net.URL;

public class ReadURL {
    public static void main(String[] args) {
        try {
            URL url = new URL("http://ARSW.edu.co:80/index.html?curso=arsw#seccion2");

            System.out.println("URL completa  : " + url.toString());
            System.out.println("getProtocol() : " + url.getProtocol());
            System.out.println("getAuthority(): " + url.getAuthority());
            System.out.println("getHost()     : " + url.getHost());
            System.out.println("getPort()     : " + url.getPort());
            System.out.println("getPath()     : " + url.getPath());
            System.out.println("getQuery()    : " + url.getQuery());
            System.out.println("getFile()     : " + url.getFile());
            System.out.println("getRef()      : " + url.getRef());

        } catch (MalformedURLException e) {
            System.err.println("URL malformada: " + e.getMessage());
        }
    }
}
