import java.io.Serializable;

public class GeneralPiece implements Serializable{
	static final long serialVersionUID= (long) 187.3333;
	protected String player;
	protected String type;
	public Location location;
	public ChessPiece capturedBy = null;
	public boolean active= false;
	public String image=null;
	//ChessBoard board = ChessBoard.getInstance();
	public  GeneralPiece(String newPlayer, String newType, int position){
	
		player = newPlayer;
		type = newType;
		location = new Location( position );
	
	}
	
	public void  setPlayer(String str){
		player = str;
	}
	
	public void  setPieceType(String str){
		type = str;
	}
	
	public String  getPlayer(){
		return player ;
	}
	
	public String  getPieceType(){
		return type;
	}
	
	public Location getLocation(){
		
		return location;
	}
	
	public boolean isProperPlace(Location destinationPosition){
		/*ChessGame game = ChessGame.getInstance();
		ChessBoard board = ChessBoard.getInstance("");
		if( game.positionHolder[destinationPosition.getPosition()]==1 ){
			if( 
					( game.player==0 && board.getComponent(destinationPosition.getPosition()).getName().charAt(0)=='b')||
					( game.player==1 && board.getComponent(destinationPosition.getPosition()).getName().charAt(0)=='w')
			)
				return true;
			else
				return false;
		}
		else
			return true;*/return true;
	}
	
}