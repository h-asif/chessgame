


import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.registry.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Enumeration;

public class ServerClientChess implements ServerClientRemote{
	
	RemoteChess clientChess, serverChess;
	public static ServerClientChess serverClientChess=null;
	boolean isServer=true;
	
	Registry registry1, registry2;
	
	ChessBoard board;
	
	static public ServerClientChess getInstance(boolean bit, String hostAddress){
		
		if( serverClientChess == null )
			serverClientChess = new ServerClientChess( bit, hostAddress );
		return serverClientChess;
	}
	
	public void start(){
		
		ServerClientRemote scr = null;
		if( serverClientChess !=null ){
			try{
				scr=(ServerClientRemote)UnicastRemoteObject.exportObject(serverClientChess,0);
			}catch(Exception e){
				board.started=false;
				board.txtArea.setText(board.txtArea.getText()+"\n"+"Chess server connection problem");
				//System.out.println("Chess server connection problem");
				e.printStackTrace();
			}
		}
		
		if(scr != null){
			try{
				registry1.rebind("server", scr);
			}catch(Exception e){
				board.txtArea.setText(board.txtArea.getText()+"\n"+"Chess server connection problem");
				board.started=false;
				e.printStackTrace();
			}
		}
	}
	
	private ServerClientChess(boolean server, String hostAddress){
		if(server){
			try{
				
				board = ChessBoard.getInstance("white");
				serverChess=(RemoteChess)UnicastRemoteObject.exportObject(board,0);
				registry1 = LocateRegistry.getRegistry();
				registry1.rebind("serverChess", serverChess);
				getIPAddress();
				board.setVisible(true);

			}catch(Exception e){
				board.started=false;
				board.txtArea.setText(board.txtArea.getText()+""+"Chess server connection problem");
				//System.out.println("Chess server connection problem");
				e.printStackTrace();
			}
		}
		else{
			
			try{
				isServer = false;
				board = ChessBoard.getInstance("black");
				clientChess=(RemoteChess)UnicastRemoteObject.exportObject(board,0);
				registry1 = LocateRegistry.getRegistry();
				registry1.rebind("clientChess", clientChess);
				registry2 = LocateRegistry.getRegistry(hostAddress); 
				serverChess=(RemoteChess)registry2.lookup("serverChess");
				ServerClientRemote scr = (ServerClientRemote)registry2.lookup("server");
				scr.readClientRegistry( getIPAddress() );
				board.setVisible(true);
				board.txtArea.setText(board.txtArea.getText()+"\n"+"Connection established succesfully\n\nwhite's turn");
				
			}catch(Exception e){
				board.txtArea.setText(board.txtArea.getText()+"\n"+"Chess client connection problem");
				board.started=false;
				//System.out.println("Chess client connection problem");
				e.printStackTrace();
			}
		}
		
		/*board.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		board.pack();
		board.setResizable(true);
		board.setLocationRelativeTo( null );*/
		
	}
	
    @Override
	public void readClientRegistry(String clientAddress ){
		try{
			registry2 = LocateRegistry.getRegistry( clientAddress );	
			clientChess=(RemoteChess)registry2.lookup("clientChess");
			board.chessPlayer="white";
			board.txtArea.setText(board.txtArea.getText()+"\n"+"connection established\nA player has Joined the game\n\nwhite's turn");
			//System.out.println("connection established");
		}catch(Exception e){
			board.txtArea.setText(board.txtArea.getText()+"\n"+"connection problem");
			e.printStackTrace();
		}
		
	}
	
	private String getIPAddress(){
		 String address = null;
		 String[] str= new String[12];
		 Enumeration<NetworkInterface> ifcs=null;
		try {
			ifcs = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if( ifcs == null )
			return null;
		 while(ifcs.hasMoreElements()){
		   NetworkInterface ifc = ifcs.nextElement();
		   System.out.println("Adresses of: "+ifc.getDisplayName());
		   Enumeration<InetAddress> adresses = ifc.getInetAddresses();
		   
                   int i=0;
                   while(adresses.hasMoreElements()){
		     System.out.println(adresses.nextElement().getHostAddress());
		    //board.txtArea.setText(board.txtArea.getText()+"\nYour external IP address: "+adresses.nextElement().getHostAddress());
                      str[i] = adresses.nextElement().getHostAddress();
                       board.txtArea.setText(board.txtArea.getText()+"\nYour external IP address: "+str[i]);
                      i++;
                   }
                   
		 }

		 
		try {
		    InetAddress addr = InetAddress.getLocalHost();
		    // Get IP Address
		    address = addr.getHostAddress();
		    board.txtArea.setText(board.txtArea.getText()+"\nYour IP address: "+address);
		//    System.out.println( address );
		    return str[0];

		    // Get hostname
		  //  String hostname = addr.getHostName();
		} catch (UnknownHostException e) {
			return str[0];
		}
	}
	
	
}
