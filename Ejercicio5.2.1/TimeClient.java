import java.net.*;

public class TimeClient {

    private static final String SERVER_HOST = "localhost";
    private static final int    SERVER_PORT  = 4445;
    private static final int    INTERVAL_MS  = 5000;
    private static final int    TIMEOUT_MS   = 2000;

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        socket.setSoTimeout(TIMEOUT_MS);

        InetAddress serverAddress = InetAddress.getByName(SERVER_HOST);
        byte[] requestBytes = "TIME".getBytes("UTF-8");
        byte[] responseBuffer = new byte[256];

        String lastKnownTime = "(sin datos aun)";

        System.out.println("Cliente de hora iniciado. Actualizando cada "
                + (INTERVAL_MS / 1000) + " segundos...\n");

        while (true) {
            // Send request
            DatagramPacket request = new DatagramPacket(
                    requestBytes, requestBytes.length, serverAddress, SERVER_PORT);
            socket.send(request);

            // Wait for response
            DatagramPacket response = new DatagramPacket(responseBuffer, responseBuffer.length);
            try {
                socket.receive(response);
                lastKnownTime = new String(response.getData(), 0, response.getLength(), "UTF-8");
                System.out.println("[ACTUALIZADO] Hora del servidor: " + lastKnownTime);
            } catch (SocketTimeoutException e) {
                System.out.println("[SIN RESPUESTA] Servidor no disponible. "
                        + "Mostrando ultima hora conocida: " + lastKnownTime);
            }

            Thread.sleep(INTERVAL_MS);
        }
    }
}
