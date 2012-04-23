
import java.util.ArrayList;
import javax.swing.JLabel;
import java.awt.Component;

public class ChessGame {

	static final long serialVersionUID = (long)1587.3333;
	
	private static ChessGame chessGame = null;

	public int player = 0;	
	public int permotion=-1;
	public String chessPlayer=null;

	 ArrayList<GeneralPiece> white;							//stores all the players of white player
	 ArrayList<GeneralPiece>  black;						//stores all the players of black player
	
	 ChessPiece blackKing, whiteKing ;						//objects for kings of both players
	 int[] positionHolder;

	 
	
	 private ChessGame(){
		 white = new ArrayList<GeneralPiece>();
		 black = new ArrayList<GeneralPiece>();
		 positionHolder = new int[64];
	 }
	 
	 public static  ChessGame getInstance(){
		if(chessGame == null)
			chessGame = new ChessGame();
		return chessGame;
	 }
	 
	

	public boolean validateMove(String piece1Name, String piece2Name, int currPosition,  int newPosition){
		if( piece1Name == null )
			{return false;}
		boolean valid = false;
		ChessPiece pieceMoved = getPieceObj( piece1Name , currPosition);
		ChessPiece pieceKilled = getPieceObj( piece2Name, newPosition );
		Location currLoc = new Location( currPosition );
		Location newLoc = new Location( newPosition );
		
		
		if(  
			pieceKilled != null &&
			(
				( ( (GeneralPiece)pieceKilled).getPlayer().equals("white") && player==0 ) ||
				( ( (GeneralPiece)pieceKilled).getPlayer().equals("black") && player==1 ) 
			)
		)
			{return false;}
		
		if( pieceMoved == null)
			{return false;}
		
		if( pieceMoved.validateMove( newLoc ) ){
			
			if(pieceKilled !=null)
				( (GeneralPiece)pieceKilled).active=false;
			pieceMoved.makeMove(newLoc);
			positionHolder[newLoc.getPosition()]=1;
			positionHolder[currLoc.getPosition()]=0;
			
			if(isCheckToKing()){
				valid= false;
			}
			else{
				valid = true;
			}
			pieceMoved.makeMove(currLoc);
			if(pieceKilled !=null){
				( (GeneralPiece)pieceKilled).active=true;
				positionHolder[newLoc.getPosition()]=1;
			}
			else{	
				positionHolder[newLoc.getPosition()]=0;
			}
			positionHolder[currLoc.getPosition()]=1;
			return valid;
			
		}	
		else
			{return false;}
	}
		
	
	
		
	public int makeMove( String piece1Name, String piece2Name, int currPosition, int newPosition){
		if( piece1Name == null)
			return -1;
		
		ChessPiece pieceMoved = getPieceObj( piece1Name , currPosition);
		ChessPiece pieceKilled = getPieceObj( piece2Name, newPosition );
		Location currLoc = new Location( currPosition );
		Location newLoc = new Location( newPosition );
		
		pieceMoved.makeMove(newLoc);
		positionHolder[ newLoc.getPosition() ]=1;
		positionHolder[ currLoc.getPosition() ]=0;
		if(pieceKilled != null)
			pieceKilled.kill(pieceMoved);
		player = ++player%2;				//changes the player turn
		if( isCheckToKing() ){
			if(player == 0)				
				return ( (GeneralPiece)whiteKing).location.getPosition();
			else
				return ( (GeneralPiece)blackKing).location.getPosition();
		}
		else
			return -1;
	}
	

	
	@SuppressWarnings("unused")
	private  int getPosition(Component component){
		
		if( component instanceof JLabel)
			component = (Component) component.getParent();
		for(int i=0; i < 64; i++){
			
			if(ChessBoard.getInstance("").getComponent(i) == component ){
				return  i;
				
			}
		}
		return -1;
	}
	
	
	
	
	
	public ChessPiece getPieceObj( String pieceLabelName, int position ){
		
		ChessPiece obj=null;
		ArrayList<GeneralPiece> array=null;
		if(pieceLabelName == null)
			return obj;
		if(pieceLabelName.charAt(0) == 'w'){
				array = white; 				
		}
		if(pieceLabelName.charAt(0) == 'b'){
			array = black;			
		}
		
		if( array != null){
			for(int i=0; i < array.size(); i++){
				
			//	if( array.get(i).getPieceType().equals( pieceLabelName.substring(2, pieceLabelName.length() ) )){
					
					
					if(array.get(i) instanceof Pawn){
					
						if( ( (Pawn)array.get(i) ).location.getPosition() == position )
							return obj = (ChessPiece)array.get(i);
					}
					else if(array.get(i) instanceof Rook){
						if( ( (Rook)array.get(i) ).location.getPosition() == position )
							return obj = (ChessPiece)array.get(i);
						
					}
					else if(array.get(i) instanceof Knight){
						if( ( (Knight)array.get(i) ).location.getPosition() == position  )
							return obj = (ChessPiece)array.get(i);
						
					}
					else if(array.get(i) instanceof Bishop){
						if( ( (Bishop)array.get(i) ).location.getPosition() == position  )
							return obj = (ChessPiece)array.get(i);
	
					}
					else if(array.get(i) instanceof Queen){
						if( ( (Queen)array.get(i) ).location.getPosition() == position  )
							return obj = (ChessPiece)array.get(i);
						
					}
					else if(array.get(i) instanceof King){
						if( ( (King)array.get(i) ).location.getPosition() == position  )
							return obj = (ChessPiece)array.get(i);
						
					}
					
				//}
			}
			return obj;
		}
		else
			return obj;
	}
	
	

	
	public boolean isCheckToKing(){
		ArrayList<GeneralPiece> pieces;
		ChessPiece king;
		if(player == 0 ){
			pieces = black;
			king = whiteKing;
		}
		else{
			pieces = white;
			king = blackKing;
		}
		
		for(int i=0; i < pieces.size(); i++){
			ChessPiece tmp = (ChessPiece)(pieces.get(i));
			if( tmp.isActive() &&  tmp.hasCheckOnOpposingKing( ( ( (GeneralPiece)king).location ) ) ) 
				return true;
		}
		return false;
	}
	
	
	public boolean isCheckMate(){
		ArrayList<GeneralPiece> pieces;
		String str1, str2;
		
		if(player == 0 ){
			pieces = white;
			str1="ww";
			str2="bb";		
		}
		else{
			pieces = black;
			str1="bb";
			str2 = "ww";
		}
		for(int i=0; i < pieces.size(); i++){
			ChessPiece tmp = (ChessPiece)(pieces.get(i));
			for(int j=0; j< 64; j++){ 
				
				if( validateMove( str1, (positionHolder[j]==0)?null:str2, ( (GeneralPiece)tmp).location.getPosition(), j) ){
					return false;
				}
			}
		}
		return true;
	}
	
}