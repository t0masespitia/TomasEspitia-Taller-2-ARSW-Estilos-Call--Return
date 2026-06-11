import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatApp {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa tu nombre: ");
        String localName = scanner.nextLine().trim();

        System.out.print("Puerto local para publicar tu objeto RMI: ");
        int localPort = Integer.parseInt(scanner.nextLine().trim());

        // Publish local ChatImpl on its own RMI registry
        ChatImpl localChat = new ChatImpl(localName);
        Registry localRegistry = LocateRegistry.createRegistry(localPort);
        localRegistry.rebind("ChatService", localChat);
        System.out.println("Objeto RMI publicado en el puerto " + localPort);

        // Connect to the remote peer
        System.out.print("IP del peer remoto: ");
        String remoteIP = scanner.nextLine().trim();

        System.out.print("Puerto del peer remoto: ");
        int remotePort = Integer.parseInt(scanner.nextLine().trim());

        ChatInterface remoteChat = null;
        System.out.println("Conectando a " + remoteIP + ":" + remotePort + "...");
        while (remoteChat == null) {
            try {
                Registry remoteRegistry = LocateRegistry.getRegistry(remoteIP, remotePort);
                remoteChat = (ChatInterface) remoteRegistry.lookup("ChatService");
                System.out.println("Conectado. Puedes empezar a chatear (escribe 'exit' para salir).");
            } catch (Exception e) {
                System.out.println("No se pudo conectar. Reintentando en 2 segundos...");
                Thread.sleep(2000);
            }
        }

        // Chat loop
        System.out.print("Tú: ");
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                System.out.println("Saliendo del chat.");
                break;
            }
            try {
                remoteChat.receiveMessage(localName, message);
            } catch (Exception e) {
                System.out.println("Error al enviar mensaje: " + e.getMessage());
            }
            System.out.print("Tú: ");
        }

        scanner.close();
        System.exit(0);
    }
}
