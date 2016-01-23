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

public class King extends ChessPiece{
	private String img_file;
	public static final int SIZE = 40;
	private String pieceColor;

	
	private BufferedImage img;
	
	
	public King(int initX, int initY, String teamColor) {
		// TODO Auto-generated constructor stub
		super(320,320, initX, initY, teamColor);
		pieceColor = teamColor;
		if(teamColor.equals("black")) {
			img_file = "blackking.png";
		} else {
			img_file = "whiteking.png";
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
		return "King";
	}
	
	@Override
	public Set<Tile> possibleMoves(Tile[][] boardTiles, Set<ChessPiece> boardPieces){
		Set<Tile> moves = new TreeSet<Tile>();
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				Tile t = boardTiles[i][j];
				if((Math.abs(this.getX() - t.getX()) == 1 
						&& Math.abs(this.getY() - t.getY()) == 1) || 
						(Math.abs(this.getX() - t.getX()) == 0 
						&& Math.abs(this.getY() - t.getY()) == 1) || 
						(Math.abs(this.getX() - t.getX()) == 1 
						&& Math.abs(this.getY() - t.getY()) == 0)){
							moves.add(t);
						}
				}
			}
		for(Tile t : moves) {
			for(ChessPiece c : boardPieces) {
				if(c.getX() == t.getX() && 
						c.getY() == t.getY() && c.thisPieceColor().equals(pieceColor)){
					moves.remove(t);
				}
			}
		}
		return moves;
	}

}
