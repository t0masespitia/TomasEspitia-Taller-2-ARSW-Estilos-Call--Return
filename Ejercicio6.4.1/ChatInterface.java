import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatInterface extends Remote {
    void receiveMessage(String sender, String message) throws RemoteException;
}
