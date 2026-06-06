import java.net.*;
import java.io.*;

public class SquareServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Servidor escuchando en puerto 35000...");
        } catch (IOException e) {
            System.err.println("No se pudo escuchar en el puerto 35000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();
            System.out.println("Cliente conectado.");
        } catch (IOException e) {
            System.err.println("Accept fallido.");
            System.exit(1);
        }

        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Numero recibido: " + inputLine);
            try {
                double number = Double.parseDouble(inputLine);
                double square = number * number;
                out.println(square);
            } catch (NumberFormatException e) {
                out.println("Error: no es un numero valido.");
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
