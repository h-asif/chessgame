


public class Queen extends GeneralPiece implements ChessPiece {
	static final long serialVersionUID= (long) 1000000.3333;
	private int pieceNum=0;
//	public boolean active = false;
	public Queen(String newPlayer, int position, int pieceNum) {
		super(newPlayer, "queen", position);
		if( player.equals("white") )
			image = "/home/wizrad/workspace/ChessGame/bin/chessPieces/wqueen.png";
		else
			image = "/home/wizrad/workspace/ChessGame/bin/chessPieces/bqueen.png";
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
		
		Rook rook = new Rook( getPlayer(), location.getPosition(),0) ;
		Bishop bishop = new Bishop( getPlayer(), location.getPosition(),0) ;
		if( rook.validateMove(destinationPosition) || bishop.validateMove(destinationPosition) ){
			
			return true;
		}
		else
			return false;
		
	}

	@Override
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
			
			if(validateMove( ( (GeneralPiece)(ChessGame.getInstance()).whiteKing ).location) )
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
