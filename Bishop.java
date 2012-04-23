
public class Bishop extends GeneralPiece implements ChessPiece {
	static final long serialVersionUID= (long) 197.3333;
	private int pieceNum=0;
//	public boolean active = false;
	public Bishop(String newPlayer, int position, int pieceNum ) {
		super(newPlayer, "bishop", position);
		if( player.equals("white") )
			image = "/home/wizrad/workspace/ChessGame/bin/chessPieces/wbishop.png";
		else
			image = "/home/wizrad/workspace/ChessGame/bin/chessPieces/bbishop.png";
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
		Location loc = new Location( location );
		if( Math.abs( loc.getFile()-destinationPosition.getFile() ) == Math.abs( loc.getRank()-destinationPosition.getRank() ) ){
			int file=0; int rank=0;
			while(loc.getPosition() != destinationPosition.getPosition() ){
				if( destinationPosition.getFile() - loc.getFile() > 0)
					file++;
				else
					file--;
				if( destinationPosition.getRank() - loc.getRank() > 0)
					rank++;
				else
					rank--;
				loc.setLocation( location.getFile()+file, location.getRank()+rank );
				
				if( loc.getPosition()!=destinationPosition.getPosition() && 
						ChessGame.getInstance().positionHolder[loc.getPosition()] == 1 ){
					return false;
				}
			}
		}	
		else{
			return false;
		}
			return true;
		
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
		if( isWhite() ){
			if(validateMove( ( (GeneralPiece)ChessGame.getInstance().blackKing ).location ) ){
			
				return true;
			}
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
