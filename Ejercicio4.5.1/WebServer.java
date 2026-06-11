import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class WebServer {

    private static final int PORT = 8080;
    private static final String WWW_ROOT = "www";

    private static final Map<String, String> MIME_TYPES = new HashMap<>();

    static {
        MIME_TYPES.put("html", "text/html");
        MIME_TYPES.put("htm",  "text/html");
        MIME_TYPES.put("css",  "text/css");
        MIME_TYPES.put("js",   "application/javascript");
        MIME_TYPES.put("png",  "image/png");
        MIME_TYPES.put("jpg",  "image/jpeg");
        MIME_TYPES.put("jpeg", "image/jpeg");
        MIME_TYPES.put("gif",  "image/gif");
        MIME_TYPES.put("ico",  "image/x-icon");
        MIME_TYPES.put("txt",  "text/plain");
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Servidor web iniciado en http://localhost:" + PORT);
        System.out.println("Sirviendo archivos desde: " + new File(WWW_ROOT).getAbsolutePath());
        System.out.println("Presiona Ctrl+C para detener.\n");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
            clientSocket.close();
        }
    }

    private static void handleRequest(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
        OutputStream out = clientSocket.getOutputStream();

        String requestLine = in.readLine();
        if (requestLine == null || requestLine.isEmpty()) return;

        System.out.println("Solicitud: " + requestLine);

        // Consume headers
        while (true) {
            String header = in.readLine();
            if (header == null || header.isEmpty()) break;
        }

        // Parse method and path  e.g. "GET /index.html HTTP/1.1"
        String[] parts = requestLine.split(" ");
        if (parts.length < 2 || !parts[0].equals("GET")) {
            sendError(out, 405, "Method Not Allowed");
            return;
        }

        String path = parts[1];
        // Remove query string if present
        int queryIndex = path.indexOf('?');
        if (queryIndex != -1) path = path.substring(0, queryIndex);

        // Default to index.html
        if (path.equals("/")) path = "/index.html";

        File file = new File(WWW_ROOT + path);

        if (!file.exists() || file.isDirectory()) {
            sendError(out, 404, "Not Found");
            System.out.println("404 - Archivo no encontrado: " + file.getPath());
            return;
        }

        String extension = getExtension(file.getName());
        String mimeType = MIME_TYPES.getOrDefault(extension, "application/octet-stream");

        byte[] body = Files.readAllBytes(file.toPath());

        PrintWriter header = new PrintWriter(new OutputStreamWriter(out), false);
        header.print("HTTP/1.1 200 OK\r\n");
        header.print("Content-Type: " + mimeType + "\r\n");
        header.print("Content-Length: " + body.length + "\r\n");
        header.print("Connection: close\r\n");
        header.print("\r\n");
        header.flush();

        out.write(body);
        out.flush();

        System.out.println("200 OK - " + file.getPath() + " (" + mimeType + ", " + body.length + " bytes)");
    }

    private static void sendError(OutputStream out, int code, String message) throws IOException {
        String body = "<html><body><h1>" + code + " " + message + "</h1></body></html>";
        byte[] bodyBytes = body.getBytes("UTF-8");
        PrintWriter pw = new PrintWriter(new OutputStreamWriter(out), false);
        pw.print("HTTP/1.1 " + code + " " + message + "\r\n");
        pw.print("Content-Type: text/html\r\n");
        pw.print("Content-Length: " + bodyBytes.length + "\r\n");
        pw.print("Connection: close\r\n");
        pw.print("\r\n");
        pw.flush();
        out.write(bodyBytes);
        out.flush();
    }

    private static String getExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        return (dot == -1) ? "" : filename.substring(dot + 1).toLowerCase();
    }
}
