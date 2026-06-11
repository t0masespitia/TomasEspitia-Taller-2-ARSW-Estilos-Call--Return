import java.net.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeServer {

    private static final int PORT = 4445;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(PORT);
        System.out.println("Servidor de hora iniciado en puerto " + PORT);
        System.out.println("Presiona Ctrl+C para detener.\n");

        byte[] buffer = new byte[256];

        while (true) {
            DatagramPacket request = new DatagramPacket(buffer, buffer.length);
            socket.receive(request);

            String currentTime = LocalTime.now().format(FORMATTER);
            byte[] response = currentTime.getBytes("UTF-8");

            DatagramPacket reply = new DatagramPacket(
                    response,
                    response.length,
                    request.getAddress(),
                    request.getPort());

            socket.send(reply);
            System.out.println("Hora enviada a " + request.getAddress().getHostAddress()
                    + ":" + request.getPort() + " -> " + currentTime);
        }
    }
}
