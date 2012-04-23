
public class King extends GeneralPiece implements ChessPiece {
	static final long serialVersionUID= (long) 198.3333;
//	public boolean active = false;
	private int pieceNum=0;
	
	public King(String newPlayer, int position, int pieceNum) {
		super(newPlayer, "king", position);
		// TODO Auto-generated constructor stub
		this.pieceNum = pieceNum;
		active = true;
	}

	public int getPieceNumber(){
		return pieceNum;	
	}
	
	@Override
	public boolean validateMove(Location destinationPosition) {
		// TODO Auto-generated method stub
		
		if(!isProperPlace( destinationPosition ))
			return false;
		
		if( 
			   (Math.abs(destinationPosition.getFile()-location.getFile() ) <=1 && Math.abs(destinationPosition.getRank()-location.getRank() ) <=1 )
			){
			
			return true;
		}
		else
			return false;
	}

	public boolean makeMove(Location newLocation) {
		// TODO Auto-generated method stub
		location.setLocation(newLocation);
		return true;
	}

	@Override
	public boolean hasCheckOnOpposingKing(Location positionOfOpposingKing) {
		// TODO Auto-generated method stub
		if( player.equals( "white" ) ){
			if(validateMove( ( (GeneralPiece)ChessGame.getInstance().blackKing ).location ) )
				return true;
			else
				return false;
		}
		else{
			
			if(validateMove( ( (GeneralPiece)ChessGame.getInstance().whiteKing ).location) )
				return true;
			else
				return false;
		}
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		if(active)
			return true;
		else
			return false;
	}

	@Override
	public boolean isWhite() {
		// TODO Auto-generated method stub
		if(player.equals("white") )
			return true;
		else
			return false;
	}

	@Override
	public boolean isBlack() {
		// TODO Auto-generated method stub
		if(player.equals("black") )
			return true;
		else
			return false;
	}

	@Override
	public ChessPiece kill(ChessPiece a) {
		// TODO Auto-generated method stub
		capturedBy = a;
		active = false;
		return null;
	}

}
