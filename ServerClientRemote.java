

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface ServerClientRemote extends Remote{

	public void readClientRegistry(String address) throws RemoteException;
}
