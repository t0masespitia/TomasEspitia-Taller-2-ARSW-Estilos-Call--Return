import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ChatImpl extends UnicastRemoteObject implements ChatInterface {

    private final String localName;

    public ChatImpl(String localName) throws RemoteException {
        super();
        this.localName = localName;
    }

    @Override
    public void receiveMessage(String sender, String message) throws RemoteException {
        System.out.println("\n[" + sender + "]: " + message);
        System.out.print("Tú: ");
    }
}
