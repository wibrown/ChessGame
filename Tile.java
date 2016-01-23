/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;

/**
 * A basic game object displayed as a black square, starting in the upper left
 * corner of the game court.
 * 
 */
public class Tile extends GameObj {
	public static final int SIZE = 40;
	public static final int INIT_X = 0;
	public static final int INIT_Y = 0;
	private Color TILE_COLOR;

	/**
	 * Note that, because we don't need to do anything special when constructing
	 * a Square, we simply use the superclass constructor called with the
	 * correct parameters
	 */
	public Tile(int courtWidth, int courtHeight, int initX, int initY, Color tileColor) {
		super(initX, initY, SIZE, SIZE, courtWidth,
				courtHeight);
		TILE_COLOR = tileColor;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(TILE_COLOR);
		g.fillRect(pos_x, pos_y, width, height);
	}
	
	public Color getColor() {
		return TILE_COLOR;
	}

}
