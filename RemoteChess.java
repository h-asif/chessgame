

import java.rmi.Remote;
import java.rmi.RemoteException;



public interface RemoteChess extends Remote {
	public void makeMove(int currPosition, int newPosition, String pieceBeingMovedLabelName, 
			String pieceBeingKilledLabelName, int permotion ) throws RemoteException;
			
	public void startLooking() throws RemoteException;
}
