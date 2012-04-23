
public class Rook extends GeneralPiece implements ChessPiece {
	static final long serialVersionUID= (long) 188.3333;
	private int pieceNum=0;
//	public boolean active = false;
	
	public Rook(String newPlayer, int position, int pieceNum) {
		super(newPlayer, "rook", position);
		if( player.equals("white") )
			image = "/home/wizrad/workspace/ChessGame/bin/chessPieces/wrook.png";
		else
			image = "/home/wizrad/workspace/ChessGame/bin/chessPieces/brook.png";
		this.pieceNum = pieceNum;
	}

	public int getPieceNumber(){
		return pieceNum;	
	}
	

	@Override
	public boolean validateMove(Location destinationPosition) {

		//if(!isProperPlace( destinationPosition ))
			//return false;
		int diff=0;
		if( ( destinationPosition.getFile()-location.getFile() == 0 && destinationPosition.getRank()-location.getRank() !=0 ) ||
				(destinationPosition.getFile()-location.getFile() != 0 && destinationPosition.getRank()-location.getRank() ==0) ){
			Location loc = new Location(location);
			
			if( destinationPosition.getFile()-location.getFile() == 0)
				diff = destinationPosition.getRank()-location.getRank();
			else
				diff = destinationPosition.getFile()-location.getFile();
			for(int i=1; i < Math.abs( diff )  ; i++){
				int update = (int)(diff/Math.abs(diff))*i;
				if( destinationPosition.getFile()-location.getFile() == 0){

					loc.setLocation(location.getFile(), location.getRank()+ update);
				}	
				else{

					loc.setLocation(location.getFile()+update, location.getRank());
				}
				if( loc.getPosition() != destinationPosition.getPosition() &&
						ChessGame.getInstance().positionHolder[loc.getPosition()] == 1 ){
					return false;
				}
			}
		
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
