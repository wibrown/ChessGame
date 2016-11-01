import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;

/**
 * A game object displayed using an image.
 * 
 * Note that the image is read from the file when the object is constructed, and
 * that all objects created by this constructor share the same image data (i.e.
 * img is static). This important for efficiency: your program will go very
 * slowing if you try to create a new BufferedImage every time the draw method
 * is invoked.
 */
public class ChessPiece extends GameObj {
	private String img_file = "poison.png";
	public static final int SIZE = 40;
	private int INIT_X = 0;
	private int INIT_Y = 0;
	private String pieceColor;

	private static BufferedImage img;

	public ChessPiece(int courtWidth, int courtHeight, int initX, int initY, String teamColor) {
		super(initX, initY, SIZE, SIZE, courtWidth,
				courtHeight);
		pieceColor = teamColor;
		INIT_X = initX;
		INIT_Y = initY;
		
		
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
	
	public String thisPieceColor(){
		return pieceColor;
	}
	
	public void setX(int xval) {
		super.pos_x = xval * 40;
	}
	
	public void setY(int yval) {
		super.pos_y = yval * 40;
	}
	
	public void move(int newX, int newY) {
		this.setX(newX);
		this.setY(newY);
	}
	
	
	public int initX() {
		return INIT_X;
	}
	
	public int initY() {
		return INIT_Y;
	}
	
	public String type(){
		return "generic";
	}
	
	public Set<Tile> possibleMoves(Tile[][] boardTiles, Set<ChessPiece> boardPieces) {
		Set<Tile> moves = new TreeSet<Tile>();
		return moves;
		
	}
	

}
