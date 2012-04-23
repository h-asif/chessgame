import javax.swing.JOptionPane;


public class Pawn extends GeneralPiece implements ChessPiece {
	static final long serialVersionUID= (long) 2000000.3333;
	//static  PIECE_NUM=0;
	private int pieceNum=0;
//	public boolean active = false;
	private boolean firstMove = true;
	public Pawn(String newPlayer, int position, int pieceNum) {
		super(newPlayer, "pawn", position);
		this.pieceNum = pieceNum;
		
		active = true;
	}

	public int getPieceNumber(){
		return pieceNum;	
	}
	

	@Override
	public boolean validateMove( Location destinationPosition ) {
            
		int rankDiff = destinationPosition.getRank()-location.getRank();
		int fileDiff = destinationPosition.getFile()-location.getFile();
		int[] pos = ( ChessGame.getInstance()).positionHolder;
		
		if(!isProperPlace( destinationPosition ))
			return false;
		
		if(
			(firstMove && fileDiff == 0 && pos[destinationPosition.getPosition()]==0 ) &&
				( ( player.equals("white") &&  rankDiff == 2  &&  pos[8*5+destinationPosition.getFile()-1]==0 ) || 
				(player.equals("black") &&  rankDiff == -2 &&  pos[8*2+destinationPosition.getFile()-1]==0  ) ) 
		){
			firstMove = false;	
			return true;
		}
		else if( ( fileDiff == 0 &&pos[destinationPosition.getPosition()]==0 ) &&
				( ( player.equals("white") &&  rankDiff == 1) || (player.equals("black") &&  rankDiff == -1) ) ){
			firstMove = false;
			return true;
		}
		else if(
					( Math.abs( fileDiff ) == 1 && pos[destinationPosition.getPosition()]==1 ) && 
					( (player.equals("white") &&  rankDiff == 1) || (player.equals("black") &&  rankDiff == -1) )
				){
			if( pos[destinationPosition.getPosition()]==1 )
			firstMove = false;
			return true;
		}
		
		else{
			return false;
		}
		
	
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
		Pawn pawn;
		ChessPiece chess_piece=null;
		int choice=-1;
		if(a instanceof Pawn){
			pawn = (Pawn)a;
			if( pawn.player.equals(player) && pawn.getPieceNumber() == getPieceNumber() ){
				if( 
						( ChessBoard.getInstance("").chessPlayer.equals("white") && ChessGame.getInstance().player ==1 ) ||
						( ChessBoard.getInstance("").chessPlayer.equals("black") && ChessGame.getInstance().player ==0 )
					)
				{
						int msgIcon = JOptionPane.QUESTION_MESSAGE;
						String[] opts = {"Bishop", "Kight", "Rook", "Queen"};
						choice = JOptionPane.showOptionDialog (null, "Choose", "Title", 0, msgIcon, null, opts, opts[3]);
				}
				else{
					choice =ChessGame.getInstance().permotion;
				}
				
				if(choice == 0){
					chess_piece = new Bishop(player,location.getPosition(), getPieceNumber() );
				}
				else if( choice == 1 ){
					chess_piece = new Knight(player,location.getPosition(), getPieceNumber() );
				}
				else if(choice == 2 ){
					chess_piece = new Rook(player,location.getPosition(), getPieceNumber() );
				}
				else
				{
					chess_piece = new Queen(player,location.getPosition(), getPieceNumber() );
				}
				
				if( player.equals("white") ){
					ChessGame.getInstance().white.add( (GeneralPiece)chess_piece );
				}	
				else{
					ChessGame.getInstance().black.add( (GeneralPiece)chess_piece );
				}
				ChessGame.getInstance().permotion=choice;
				pawn.location.setLocation(-1);
				return chess_piece;
			}
		}
		return null;
	}

}
