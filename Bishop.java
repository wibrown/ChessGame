/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

public class Bishop extends ChessPiece{
	private String img_file;
	public static final int SIZE = 40;
	private String pieceColor;

	
	private BufferedImage img;
	
	
	public Bishop(int initX, int initY, String teamColor) {
		// TODO Auto-generated constructor stub
		super(320,320, initX, initY, teamColor);
		pieceColor = teamColor;
		if(teamColor.equals("black")) {
			img_file = "blackbishop.png";
		} else {
			img_file = "whitebishop.png";
		}
		
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null);
	}
	
	@Override
	public String type(){
		return "Bishop";
	}
	
	@Override
	public Set<Tile> possibleMoves(Tile[][] boardTiles, Set<ChessPiece> boardPieces){
		Set<Tile> moves = new TreeSet<Tile>();
		boolean moveUp = true;
		for(int i = this.getY() - 1; i > -1; i--){
			boolean isGood = true;
			boolean keepUp = true;
			for(ChessPiece c : boardPieces) {				
				if(c.getX() == this.getX() && c.getY() == i 
						&& c.thisPieceColor().equals(pieceColor)) {
					isGood = false;
					keepUp = false;
				} else if (c.getX() == this.getX() && c.getY() == i) {
					keepUp = false;
				}		
			}
			if(isGood && moveUp) {
				moves.add(boardTiles[this.getX()][i]);
			}
			moveUp = keepUp;
		}
		boolean moveDn = true;
		for(int i = this.getY() + 1; i < 8; i++){
			boolean isGood = true;
			boolean keepDn = true;
			for(ChessPiece c : boardPieces) {				
				if(c.getX() == this.getX() && c.getY() == i 
						&& c.thisPieceColor().equals(pieceColor)) {
					isGood = false;
					keepDn = false;
				} else if (c.getX() == this.getX() && c.getY() == i) {
					keepDn = false;
				}		
			}
			if(isGood && moveDn) {
				moves.add(boardTiles[this.getX()][i]);
			}
			moveDn = keepDn;
		} 
		boolean moveLt = true;
		for(int i = this.getX() - 1; i > -1; i--){
			boolean isGood = true;
			boolean keepLt = true;
			for(ChessPiece c : boardPieces) {				
				if(c.getY() == this.getY() && c.getX() == i 
						&& c.thisPieceColor().equals(pieceColor)) {
					isGood = false;
					keepLt = false;
				} else if (c.getY() == this.getY() && c.getX() == i) {
					keepLt = false;
				}		
			}
			if(isGood && moveLt) {
				moves.add(boardTiles[i][this.getY()]);
			}
			moveLt = keepLt;
		}
		boolean moveRt = true;
		for(int i = this.getX() + 1; i < 8; i++){
			boolean isGood = true;
			boolean keepRt = true;
			for(ChessPiece c : boardPieces) {				
				if(c.getY() == this.getY() && c.getX() == i 
						&& c.thisPieceColor().equals(pieceColor)) {
					isGood = false;
					keepRt = false;
				} else if (c.getY() == this.getY() && c.getX() == i) {
					keepRt = false;
				}		
			}
			if(isGood && moveRt) {
				moves.add(boardTiles[i][this.getY()]);
			}
			moveRt = keepRt;
		} 

		return moves;
	}

}
