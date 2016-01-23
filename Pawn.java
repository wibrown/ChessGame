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

public class Pawn extends ChessPiece{
	private String img_file;
	public static final int SIZE = 40;
	private String pieceColor;

	
	private BufferedImage img;
	
	
	public Pawn(int initX, int initY, String teamColor) {
		// TODO Auto-generated constructor stub
		super(320,320, initX, initY, teamColor);
		pieceColor = teamColor;
		if(teamColor.equals("black")) {
			img_file = "blackpawn.png";
		} else {
			img_file = "whitepawn.png";
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
		return "Pawn";
	}
	
	@Override
	public Set<Tile> possibleMoves(Tile[][] boardTiles, Set<ChessPiece> boardPieces){
		Set<Tile> moves = new TreeSet<Tile>();
		boolean oneForwardFree = true;
		int colorMover;
		if(this.pieceColor.equals("black")) {
			colorMover = 1;
		} else { colorMover = -1; }
				
		for(ChessPiece c : boardPieces) {
			if(c.getX() == this.getX() && c.getY() == this.getY() + colorMover) 
			{
				oneForwardFree = false;
			}
		}
		if(oneForwardFree && this.getY() + colorMover < 8 
				&& this.getY() + colorMover > -1) {
			moves.add(boardTiles[this.getX()][this.getY() + colorMover]);			
			if(this.getY() == this.initY()) {
				boolean twoForwardFree = true;
				for(ChessPiece c : boardPieces) {
					if(c.getX() == this.getX() && c.getY() == this.getY() 
							+ colorMover + colorMover) 
					{
						twoForwardFree = false;
					}
				}
				if(twoForwardFree && this.getY() + colorMover + colorMover < 8 
						&& this.getY() + colorMover + colorMover > -1) {
					moves.add(boardTiles[this.getX()][this.getY() + colorMover + colorMover]);
				}
			}
		}		
		boolean leftAttack = false;
		boolean rightAttack = false ;
		for(ChessPiece c : boardPieces) {
			if(c.getX() == this.getX() - 1 && c.getY() == this.getY() + colorMover 
					&& (this.pieceColor.equals(c.thisPieceColor()) == false)) {
				leftAttack = true;
			}
			if(c.getX() == this.getX() + 1 && c.getY() == this.getY() + colorMover 
					&& (this.pieceColor.equals(c.thisPieceColor()) == false)) {
				rightAttack = true;
			}
		}
		
		if(leftAttack) {
			moves.add(boardTiles[this.getX() - 1][this.getY() + colorMover]);
		}
		if(rightAttack) {
			moves.add(boardTiles[this.getX() + 1][this.getY() + colorMover]);
		}
			
		return moves;
	}

}
