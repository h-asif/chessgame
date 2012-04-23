import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import javax.swing.*;


class ChessBoard extends JFrame implements MouseListener,RemoteChess, Serializable{
	
	
	static final long serialVersionUID = (long)1587.3333;
	private static ChessBoard chessBoardInstance = null;
	private boolean isPieceSelected = false;								
	public String chessPlayer=null;
	JLayeredPane BoardLayeredPane;							//determines which player's turn is it 0 for white and 1 for black
	 JPanel chessBoard, colordBorderPanel, panelToPlacePiece;					
	 Container selectedPieceParent;
	 JLabel selectedPiece;
	 ChessGame chessGame=null;
	boolean kingUnderCheck = false; 
	JLabel wKing,bKing;
	Location newLocation=null;
	JTextArea  txtArea;
	JButton newGame, joineGame;
	JTextField tf;
	boolean started=false;
	boolean onServer=false;
	 //////
	private void setGUI(JPanel mainPanel, Dimension dim){
	
		//	 JFrame frame = new JFrame(); 
			JPanel panel = new JPanel( );
			int width = (int)(dim.width * 0.14);
			panel.setPreferredSize(new Dimension( width ,dim.height) );
			panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
			txtArea = new JTextArea(width, (int)(dim.height/1.5));
			txtArea.setEditable(false);
			//chatReader.scrollPane = new JScrollPane(chatReader);
			txtArea.setFont(new Font("Serif", Font.ITALIC, 16));
			txtArea.setLineWrap(true);
			txtArea.setWrapStyleWord(true);
			JScrollPane scrollPane = new JScrollPane(txtArea);
			scrollPane.setPreferredSize(new Dimension(width, (int)(dim.height/1.5)) );
			panel.add(scrollPane);
			txtArea.setText("\tWelcome to Chess Game\n" +
							"For starting a new game press \"New Game\"\n"+
							"For joining a game write IP address of you partner and press \" Joine Game \"");
			tf= new JTextField(20);
			newGame = new JButton(" New Game");
			joineGame = new JButton("Join Game");
			panel.add(newGame);
			panel.add(	joineGame);
			panel.add(tf);
			newGame.addActionListener(new ActionListener(){
				
					public void actionPerformed(ActionEvent e){
				//		try{Thread.sleep(100);}catch(Exception e1){e1.printStackTrace();};
						if(!started){
							ServerClientChess scc = ServerClientChess.getInstance(!started, "");
							if(!started)
								scc.start();
							txtArea.setText(txtArea.getText()+"\n"+"wating for the second palyer");
						//	chessPlayer = "white";
							started=true;
						}
						else
							txtArea.setText(txtArea.getText()+"\n"+"You have already started anew game");
					}
			} );
			joineGame.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e){
					if(!started){
						
						ServerClientChess.getInstance(started, tf.getText());
						tf.setText("");
						chessPlayer="black";
						started=true;
						
					}
					else
						txtArea.setText(txtArea.getText()+"\n"+"You have already Joined a game");
				}
		 } );
			tf.addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e){
					
					txtArea.setText(txtArea.getText()+"\n"+tf.getText());
					tf.setText("");
				}
		 } );
			
			panel.setVisible(true);
			mainPanel.add( (panel) );
			//frame.setVisible(true);
	}
	 
	/////
	
 	private void loadPiecsOnBoard(){

		for(int i=0; i <64; i++){
			if(i<15 || i>47)
			chessGame.positionHolder[i]=1;
			else
				chessGame.positionHolder[i]=0;
		}
		
		
		//add black rooks
		JLabel piece = new JLabel( new ImageIcon("/src/chessPieces/brook.png") );
		piece.setName("brook");
		JPanel panel = (JPanel)chessBoard.getComponent(0);
		panel.add(piece);
		chessGame.black.add(new Rook( "black", 0, 1 ) );
		piece = new JLabel( new ImageIcon("/src/chessPieces/brook.png") );
		piece.setName("brook");
		panel = (JPanel)chessBoard.getComponent(7);
		panel.add(piece);
		chessGame.black.add(new Rook( "black", 7, 2 ) );
		
		//add white rooks
		piece = new JLabel( new ImageIcon("/src/chessPieces/wrook.png") );
		piece.setName("wrook");
		panel = (JPanel)chessBoard.getComponent(56);
		panel.add(piece);
		chessGame.white.add(new Rook( "white", 56, 1 ) );
		piece = new JLabel( new ImageIcon("/src/chessPieces/wrook.png") );
		piece.setName("wrook");
		panel = (JPanel)chessBoard.getComponent(63);
		panel.add(piece);
		chessGame.white.add(new Rook( "white", 63, 2 ) );
		
		//add black knights
		piece = new JLabel( new ImageIcon("/src/chessPieces/bknight.png") );
		piece.setName("bknight");
		panel = (JPanel)chessBoard.getComponent(1);
		panel.add(piece);
		chessGame.black.add(new Knight( "black", 1, 1 ) );
		piece = new JLabel( new ImageIcon("/src/chessPieces/bknight.png") );
		piece.setName("bknight");
		panel = (JPanel)chessBoard.getComponent(6);
		panel.add(piece);
		chessGame.black.add(new Knight( "black", 6, 2 ) );
		
		//add white knights
		piece = new JLabel( new ImageIcon("/src/chessPieces/wknight.png") );
		piece.setName("wknight");
		panel = (JPanel)chessBoard.getComponent(57);
		panel.add(piece);
		chessGame.white.add(new Knight( "white", 57, 1 ) );
		piece = new JLabel( new ImageIcon("/src/chessPieces/wknight.png") );
		piece.setName("wknight");
		panel = (JPanel)chessBoard.getComponent(62);
		panel.add(piece);
		chessGame.white.add(new Knight( "white", 62, 2 ) );
		
		//add black bishops
		piece = new JLabel( new ImageIcon("/src/chessPieces/bbishop.png") );
		piece.setName("bbishop");
		panel = (JPanel)chessBoard.getComponent(2);
		panel.add(piece);
		chessGame.black.add(new Bishop( "black", 2, 1 ) );
		piece = new JLabel( new ImageIcon("/src/chessPieces/bbishop.png") );
		piece.setName("bbishop");
		panel = (JPanel)chessBoard.getComponent(5);
		panel.add(piece);
		chessGame.black.add(new Bishop( "black", 5, 2 ) );
		
		//add white bishops
		piece = new JLabel( new ImageIcon("/src/chessPieces/wbishop.png") );
		piece.setName("wbishop");
		panel = (JPanel)chessBoard.getComponent(58);
		panel.add(piece);
		chessGame.white.add(new Bishop( "white", 58, 1 ) );
		piece = new JLabel( new ImageIcon("/src/chessPieces/wbishop.png") );
		piece.setName("wbishop");
		panel = (JPanel)chessBoard.getComponent(61);
		panel.add(piece);
		chessGame.white.add(new Bishop( "white", 61, 2 ) );
		
		//add black king and queen
		piece = new JLabel( new ImageIcon("/src/chessPieces/bqueen.png") );
		piece.setName("bqueen");
		panel = (JPanel)chessBoard.getComponent(3);
		panel.add(piece);
		chessGame.black.add(new Queen( "black", 3, 1 ) );
		bKing = new JLabel( new ImageIcon("/src/chessPieces/bking.png") );
		bKing.setName("bking");
		panel = (JPanel)chessBoard.getComponent(4);
		panel.add(bKing);
		chessGame.blackKing = new King( "black", 4, 1 );
		chessGame.black.add( (GeneralPiece)chessGame.blackKing );
		
		//add white king and queen
		piece = new JLabel( new ImageIcon("/src/chessPieces/wqueen.png") );
		piece.setName("wqueen");
		panel = (JPanel)chessBoard.getComponent(59);
		panel.add(piece);
		chessGame.white.add(new Queen( "white", 59, 1 ) );
		wKing = new JLabel( new ImageIcon("/src/chessPieces/wking.png") );
		wKing.setName("wking");
		panel = (JPanel)chessBoard.getComponent(60);
		panel.add(wKing);
		chessGame.whiteKing = new King( "white", 60, 1 );
		chessGame.white.add( (GeneralPiece)chessGame.whiteKing );
		
		//add black and white pawns
		for(int i=0; i < 8; i++){
		
			piece = new JLabel( new ImageIcon("/src/chessPieces/bpawn.png") );
			String str1="bpawn";
			piece.setName(str1);
			panel = (JPanel)chessBoard.getComponent(i+8);
			panel.add(piece);
			chessGame.black.add(new Pawn( "black", i+8, i+1 ) );
			piece = new JLabel( new ImageIcon("/src/chessPieces/wpawn.png") );
			String str2="wpawn";
			piece.setName(str2);
			panel = (JPanel)chessBoard.getComponent(i+48);
			panel.add(piece);
			chessGame.white.add(new Pawn( "white", i+48, i+1 ) );
		
		}
		
	}

	
 	
 	
	private ChessBoard(String player){
		 
		chessPlayer = player;
		chessGame = ChessGame.getInstance();
		Dimension boardSize;
		Toolkit toolkit =  Toolkit.getDefaultToolkit ();
		Dimension ScreenDim = toolkit.getScreenSize();
		int borderLength = 600;
		if( ScreenDim.width > ScreenDim.height )
			borderLength = (int) (ScreenDim.height*0.85);
		else
			borderLength = (int) (ScreenDim.width*0.85);
			
		JPanel mainPanel = new JPanel(new GridLayout(1,2));
		mainPanel.setPreferredSize(ScreenDim);
		//setGUI( mainPanel,borderLength );
		boardSize = new Dimension( borderLength, borderLength );
			
		//  Use a Layered Pane for this this application
		 BoardLayeredPane = new JLayeredPane();
		 //getContentPane().add(BoardLayeredPane);
		 BoardLayeredPane.setPreferredSize(boardSize);
		 BoardLayeredPane.addMouseListener(this);
		// BoardLayeredPane.addMouseMotionListener(this);
			
		 chessBoard = new JPanel( new GridLayout(8, 8)  );
		 chessBoard.setPreferredSize( boardSize );
		 chessBoard.setBounds(0, 0, boardSize.width, boardSize.height);
		 BoardLayeredPane.add(chessBoard, JLayeredPane.DEFAULT_LAYER);

		 for(int i=0; i < 8; i++){
			for(int j =0; j < 8; j++){
			JPanel panel = new JPanel(new BorderLayout());
			panel.setBackground( (i+j) % 2 == 0 ? Color.white : Color.lightGray  );
			panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			chessBoard.add( panel );
			}		
		 }
		 loadPiecsOnBoard();
		 BoardLayeredPane.setVisible(true);
		mainPanel.add( BoardLayeredPane );
		setGUI(mainPanel, ScreenDim );
		mainPanel.setVisible(true);
		mainPanel.setPreferredSize(ScreenDim);
		getContentPane().add( mainPanel );
	}
	
	/*
	 *this function gives and maintain the singletone behaviour of the ChessBoard class 
	 */
	public static ChessBoard getInstance(String str){

		if( chessBoardInstance == null)
			chessBoardInstance = new ChessBoard( str );
		return chessBoardInstance ;
		
	}
	
	
	public void mouseClicked( MouseEvent e){

		Component c =  chessBoard.findComponentAt(e.getX(), e.getY());
		// here piece to move is selected  by the player
		if( !isPieceSelected && chosePiece( c )){
			isPieceSelected = true;
			return;
		}	
		if(  isPieceSelected ){    				//here player choice of move is evaluated and if its valid then also run
			isPieceSelected=false;
			

			String pieceBeingMovedLabelName = null;
			String pieceBeingKilledLabelName = null;
			int newPosition = getPosition( c );				//position selected by player to move the selected piece  
			if( selectedPiece != null ){
				pieceBeingMovedLabelName = selectedPiece.getName();
			}

			if( c instanceof JLabel)
				pieceBeingKilledLabelName = ( (JLabel) c).getName();
			Component compo = (Component)selectedPieceParent;
			int currPosition = getPosition( compo);
			if( chessGame.validateMove(pieceBeingMovedLabelName, pieceBeingKilledLabelName, currPosition, newPosition) ){
				makeMove(currPosition, newPosition, pieceBeingMovedLabelName,
						pieceBeingKilledLabelName, ChessGame.getInstance().permotion );
				
				ServerClientChess remote = ServerClientChess.getInstance(false,"");
				if( remote.isServer ){
					try{
					remote.clientChess.makeMove(currPosition, newPosition, pieceBeingMovedLabelName,
							pieceBeingKilledLabelName,ChessGame.getInstance().permotion );
					}catch(Exception e1){
						e1.printStackTrace();
					}
				}
				else{
					
					try{
						remote.serverChess.makeMove(currPosition, newPosition, pieceBeingMovedLabelName,
								pieceBeingKilledLabelName, ChessGame.getInstance().permotion );
					}catch(Exception e1){
						e1.printStackTrace();
					}
				}
				
			}
			else{
				colorBorder(currPosition, "black");
				isPieceSelected = false;
				if(chessGame.isCheckToKing()){
					if( chessGame.player==0 )
						colorBorder( getPosition((Component)wKing), "red" );
					else
						colorBorder( getPosition((Component)bKing), "red" );
				}
					
			}
		}
	}
		
	
	

	public void makeMove( int currPosition, int newPosition, String piece1Name, String piece2Name, int permotion){
		ChessGame.getInstance().permotion = permotion;
		if(chessGame.player == 0){
			
			colorBorder( (  (GeneralPiece)chessGame.whiteKing).location.getPosition(), "black" );
		}
		else{
			colorBorder( (  (GeneralPiece)chessGame.blackKing).location.getPosition(), "black" );
		}
		int kingPos = chessGame.makeMove(piece1Name, piece2Name, currPosition, newPosition);
	
		if(  kingPos < 0 ){
			colorBorder(kingPos, "black");
			
		}	
		else{
			colorBorder(kingPos, "red");
			if( chessGame.isCheckMate() )
				System.out.println("check mate");
			else
				System.out.println("wrong implementation");
		}
			
		JPanel panel=null;
		
		if(piece2Name != null){
			panel = (JPanel)chessBoard.getComponent(newPosition);            	//panel from which piece is to remove to kill
			panel.remove(0);
		}	
		panel = (JPanel)chessBoard.getComponent(currPosition);					//panel from which pick is picked to move
		
		JLabel piece=null;
		GeneralPiece chspis = pawnPermotion( piece1Name, newPosition);
		if(chessGame.isCheckToKing()){
			if( chessGame.player == 0 )
				colorBorder(  ( (GeneralPiece)chessGame.whiteKing).location.getPosition(), "red");
			else
				colorBorder(  ( (GeneralPiece)chessGame.blackKing).location.getPosition(), "red");
		}	
		
		if(panel .getComponentCount() != 0); 
    		piece = (JLabel)panel.getComponent(0);
		piece.setVisible(false);
		panel = (JPanel)piece.getParent();
		panel.remove(0);
		
		if( chspis != null ){
			GeneralPiece g = (GeneralPiece)chspis;
			piece = new JLabel( new ImageIcon( chspis.image) );
			Character ch = new Character( chspis.player.charAt(0));
			piece.setName(  ch.toString()+ chspis.getPieceType() ); 
			
		}
		
		panel = ( (JPanel)chessBoard.getComponent(newPosition) );
		panel.add( piece );
		panel.setVisible(true);
		piece.setVisible(true);
		colorBorder(currPosition, "blue");
		colorBorder(currPosition, "black");
		Location currLoc = new Location(currPosition);
		Location newLoc = new Location(newPosition);
		if( chessGame.player==0 ){
			txtArea.setText(txtArea.getText()+"\n"+
					"moved "+piece1Name+"from  ("+currLoc.getFile()+", "+currLoc.getRank()+" )  "+
					"to  ("+newLoc.getFile()+", "+newLoc.getRank()+" )"+
					( (piece2Name!=null)?"\n and captured "+piece2Name:"")+
					"\n\tWhite's turn");
		}
		else{
			
			txtArea.setText(txtArea.getText()+"\n"+
					"moved "+piece1Name+"from ("+currLoc.getFile()+", "+currLoc.getRank()+" )  "+
					"to  ("+newLoc.getFile()+", "+newLoc.getRank()+" )"+
					( (piece2Name!=null)?"\n and captured "+piece2Name:"")+
					"\n\tBlack's turn");
			
		}
			
	}
	
	
	
	public void colorBorder(int position, String color){
		
		if( position<0 || position>63 )
			return;
		if( color.equals("black") )
			( (JPanel)chessBoard.getComponent( position ) ).setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
		else if( color.equals("blue") )
			( (JPanel)chessBoard.getComponent( position ) ).setBorder(BorderFactory.createLineBorder(Color.BLUE, 3) );
		else if( color.equals("red") ){
			( (JPanel)chessBoard.getComponent( position ) ).setBorder(BorderFactory.createLineBorder(Color.RED, 3));
			kingUnderCheck = true;
		}
	}
	
	private  int getPosition(Component component){
		
		if( component instanceof JLabel)
			component = (Component) component.getParent();
		for(int i=0; i < 64; i++){
			
			if(chessBoard.getComponent(i) == component ){
				return  i;
				
			}
		}
		return -1;
	}
	
	
	private boolean chosePiece(Component component){
		if( chessPlayer == null )
			return false;
		if (component instanceof JLabel ){
			selectedPiece = (JLabel)component;
			selectedPieceParent = component.getParent();
			colordBorderPanel = (JPanel) selectedPieceParent;
			if(chessGame.player == 0 && chessPlayer.equals("white") && selectedPiece.getName().charAt(0) == 'w' ){
				
				colordBorderPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3) );  //color the border of the selected piece 
				return  true;		
			}
			else if( chessGame.player == 1  && chessPlayer.equals("black") && selectedPiece.getName().charAt(0) == 'b'  ){
				
				colordBorderPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3) );  //color the border of the selected piece
				return  true;
			}
			return false;

		}
		else
			return false;
		
	}
	
	private GeneralPiece pawnPermotion(String piece1Name, int newPosition){
		GeneralPiece g=null;
		GeneralPiece gnrPiece = (GeneralPiece)chessGame.getPieceObj(piece1Name, newPosition);
		ChessPiece tmp= null;
		if( gnrPiece instanceof Pawn && 
				( 
				  ( gnrPiece.getPlayer().equals("white") && gnrPiece.getLocation().getRank() == 8) ||
				  ( gnrPiece.getPlayer().equals("black") && gnrPiece.getLocation().getRank() == 1)  
				)
		){
			tmp =  chessGame.getPieceObj(piece1Name, newPosition);		
				if( tmp == null )
					return null;
				tmp = tmp.kill(tmp);
		}
		g = (GeneralPiece)tmp;
		return g;
	}
	
	
	
	public void mousePressed(MouseEvent e){}
	public void mouseReleased(MouseEvent e){}
	public void mouseEntered(MouseEvent e){}
	public void mouseExited(MouseEvent e){}
	
	boolean clientReady= false;
	public void startLooking(){
		clientReady= true;
	}
/*	public static void main(String args[]) {
	  JFrame frame = ChessBoard.getInstance("");
	  frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE );
	  frame.pack();
	  frame.setResizable(true);
	  frame.setLocationRelativeTo( null );
	  frame.setVisible(true);
	}*/

}
