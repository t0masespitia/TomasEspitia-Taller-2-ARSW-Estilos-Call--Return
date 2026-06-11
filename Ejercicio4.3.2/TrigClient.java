import java.io.*;
import java.net.*;

public class TrigClient {
    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            socket = new Socket("127.0.0.1", 35001);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Host desconocido.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("No se pudo conectar al servidor.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Ingresa un numero o 'fun:sin' / 'fun:cos' / 'fun:tan' (Ctrl+D para salir):");

        String userInput;
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            System.out.println("Respuesta: " + in.readLine());
        }

        out.close();
        in.close();
        stdIn.close();
        socket.close();
    }
}
