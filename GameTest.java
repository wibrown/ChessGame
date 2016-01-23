import javax.swing.JLabel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Set;
import java.util.TreeSet;

import org.junit.Test;

public class GameTest {

	
	@Test
	public void testReset(){
		JLabel gameLabel = new JLabel("Running...");
		GameCourt gc = new GameCourt(gameLabel);
		gc.reset();
		Tile[][] board = gc.board();
		Set<ChessPiece> pieces = gc.pieces();
		int counter = 0;
		for(int i= 0; i<board.length; i++) {
			for(int j = 0; j<board[0].length; j++) {
				counter++;
			}
		}
		for(ChessPiece c : pieces) {
			if (c.getX() == 4 && c.getY() == 6) {
				c.move(4, 2);
			}
		}
		String piecetype = "";
		String piececolor = "";
		ChessPiece p = null;
		for(ChessPiece c : pieces) {
			if (c.getX() == 4 && c.getY() == 2) {
				piecetype = c.type();
				piececolor = c.thisPieceColor();
				p = c;
				
			}
		}
		
		boolean canDiag = false;
		Set<Tile> pawnmoves = p.possibleMoves(board, pieces);
		for(Tile t : pawnmoves) {
			if(t.getX() == 3 && t.getY() == 1) {
				canDiag = true;
			}
		}
		boolean canDiagBack = false;
		for(Tile t : pawnmoves) {
			if(t.getX() == 3 && t.getY() == 3) {
				canDiagBack = true;
			}
		}
		assertTrue("has 64 tiles", counter == 64);
		assertTrue("has 32 pieces", pieces.size() == 32);
		assertTrue("pawn at (4,2)", piecetype.equals("Pawn"));
		assertTrue("white at (4,2)", piececolor.equals("white"));
		assertTrue("pawn can attack diagonally fwd", canDiag);
		assertFalse("pawn can attack diagonally bkwd", canDiagBack);
	}
	
		

}
