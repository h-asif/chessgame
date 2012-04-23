
import javax.swing.JFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author wizrad
 */
public class Game {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
		ChessBoard board = ChessBoard.getInstance(null);
		board.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE );
		board.pack();
		board.setResizable(true);
		board.setLocationRelativeTo( null );
		board.setVisible(true);

    }
}
