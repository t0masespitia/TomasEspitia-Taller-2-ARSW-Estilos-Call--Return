import java.net.*;
import java.io.*;

public class TrigServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(35001);
            System.out.println("Servidor escuchando en puerto 35001...");
        } catch (IOException e) {
            System.err.println("No se pudo escuchar en el puerto 35001.");
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

        String currentFun = "cos";
        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            if (inputLine.startsWith("fun:")) {
                String requested = inputLine.substring(4).trim().toLowerCase();
                if (requested.equals("sin") || requested.equals("cos") || requested.equals("tan")) {
                    currentFun = requested;
                    out.println("Funcion cambiada a: " + currentFun);
                    System.out.println("Funcion cambiada a: " + currentFun);
                } else {
                    out.println("Error: funcion no reconocida. Use sin, cos o tan.");
                }
            } else {
                try {
                    double number = Double.parseDouble(inputLine);
                    double result = switch (currentFun) {
                        case "sin" -> Math.sin(number);
                        case "cos" -> Math.cos(number);
                        case "tan" -> Math.tan(number);
                        default    -> Math.cos(number);
                    };
                    System.out.println("[" + currentFun + "] " + number + " -> " + result);
                    out.println(result);
                } catch (NumberFormatException e) {
                    out.println("Error: no es un numero valido ni un comando fun: valido.");
                }
            }
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}
