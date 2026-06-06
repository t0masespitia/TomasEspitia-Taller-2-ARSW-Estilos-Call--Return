import java.io.*;
import java.net.*;

public class SquareClient {
    public static void main(String[] args) throws IOException {
        Socket squareSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            squareSocket = new Socket("127.0.0.1", 35000);
            out = new PrintWriter(squareSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(squareSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        System.out.println("Ingresa un numero (Ctrl+D para salir):");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Cuadrado: " + in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        squareSocket.close();
    }
}
