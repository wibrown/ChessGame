/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	
	private Tile[][] gameTiles;
	private Set<ChessPiece> chessPieces;	


	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)

	// Game constants
	public static final int COURT_WIDTH = 320;
	public static final int COURT_HEIGHT = 320;
	public static final int SQUARE_VELOCITY = 4;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;

	public GameCourt(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// This key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually
		// moves the square.)
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) { /*
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					square.v_x = -SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					square.v_x = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					square.v_y = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_UP)
					square.v_y = -SQUARE_VELOCITY; */
			}

			public void keyReleased(KeyEvent e) { /*
				square.v_x = 0;
				square.v_y = 0; */
			}
		});
		this.status = status;
	}
	
	
	private void fillTiles() {
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<8; j++) {
				Tile tile;
				if((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
					tile = new Tile(COURT_WIDTH, COURT_HEIGHT, i, j, Color.WHITE);
				} else {
					tile = new Tile(COURT_WIDTH, COURT_HEIGHT, i, j, Color.GRAY);
				}
				gameTiles[i][j] = tile;
			}
		}
	}
	
	public Tile[][] board(){
		return gameTiles;
	}
	
	public Set<ChessPiece> pieces(){
		return chessPieces;
	}
	
	private void addPieces() {
		for(int i = 0; i<8; i++){
		chessPieces.add(new Pawn(i,1, "black"));
		chessPieces.add(new Pawn(i,6, "white"));
		}
		chessPieces.add(new Rook(0,0, "black"));
		chessPieces.add(new Rook(7,0, "black"));
		chessPieces.add(new Rook(0,7, "white"));
		chessPieces.add(new Rook(7,7, "white"));
		chessPieces.add(new Knight(1,0, "black"));
		chessPieces.add(new Knight(6,0, "black"));
		chessPieces.add(new Knight(1,7, "white"));
		chessPieces.add(new Knight(6,7, "white"));
		chessPieces.add(new Bishop(2,0, "black"));
		chessPieces.add(new Bishop(5,0, "black"));
		chessPieces.add(new Bishop(2,7, "white"));
		chessPieces.add(new Bishop(5,7, "white"));
		chessPieces.add(new Queen(3,0, "black"));
		chessPieces.add(new King(4,0, "black"));
		chessPieces.add(new Queen(3,7, "white"));
		chessPieces.add(new King(4,7, "white"));
	}
	
	private void testMoves() {
		for(ChessPiece c : chessPieces) {
			//Scanner sc = new Scanner(InputStream is);
			//int y = 
			if(c.thisPieceColor().equals("black")) {
				c.move(c.getX(), c.getY() + 1);
			} else {
				c.move(c.getX(), c.getY() - 1);
			}
		}
	}
	

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		//test = new ChessPiece(COURT_WIDTH, COURT_HEIGHT,1,0,Color.BLACK);
		//poison = new Poison(COURT_WIDTH, COURT_HEIGHT);
		//snitch = new Circle(COURT_WIDTH, COURT_HEIGHT);
		gameTiles = new Tile[8][8];
		chessPieces = new TreeSet<ChessPiece>();
		this.fillTiles();
		this.addPieces();
		//this.testMoves();

		playing = true;
		status.setText("Running...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}

	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	
	private int getNextInt(int min, int max, Scanner sc) {
	    while (true) {
	      try {
	        int choice = Integer.parseInt(sc.next());
	        if (choice >= min && choice <= max) {
	          return choice;
	        }
	      } catch (NumberFormatException ex) {
	        // Was not a number. Ignore and prompt again.
	      }
	      System.out.println("Invalid input. Please try again!");
	    }
	  }
	
	private int turnsPassed = 0;
	Scanner sc = new Scanner(System.in);

	

	void tick() {
		if (playing) {			
			if(turnsPassed > 0) {
				if(turnsPassed % 2 == 1){
					System.out.println("It's the White Team's turn.");
					System.out.println("Select the X coordinate (0 to 7) of the piece you want to move.");
					int pieceX = getNextInt(0, 7, sc);
					System.out.println("Select the Y coordinate (0 to 7) of the piece you want to move.");
					int pieceY = getNextInt(0, 7, sc);
					Set<Tile> moves = new TreeSet<Tile>();
					String pieceType = "";
					for(ChessPiece c : chessPieces) {
						if(c.getX() == pieceX && c.getY() == pieceY 
								&& c.thisPieceColor().equals("white")) {
							moves = c.possibleMoves(gameTiles, chessPieces);
							pieceType = c.type();
						}
					}
					LinkedList<Tile> orderedMoves = new LinkedList<Tile>(moves);
					Collections.sort(orderedMoves);
					System.out.println("Piece selected: " + pieceType);
					System.out.println("Move options:");
					for(int i = 0; i< orderedMoves.size(); i++) {
						System.out.println(i + ": Move to (" + orderedMoves.get(i).getX() + 
								", " + orderedMoves.get(i).getY() + ")");
					}
					
					if(orderedMoves.size() == 0){
						System.out.println("No moves for this piece! "
								+ "Unfortunately I didn't code an undo function, so you lose your turn :(");
					} else {
					int moveChoice = getNextInt(0, orderedMoves.size() - 1, sc);
					int newX = orderedMoves.get(moveChoice).getX();
					int newY = orderedMoves.get(moveChoice).getY();

					for(ChessPiece c : chessPieces) {
						if(c.getX() == newX && c.getY() == newY && c.thisPieceColor().equals("black")) {
							System.out.println("Captured a " + c.type() + "!");
							chessPieces.remove(c);
						}
					}				
					for(ChessPiece c : chessPieces) {
						if(c.getX() == pieceX && c.getY() == pieceY && c.thisPieceColor().equals("white")) {
							c.move(newX, newY);
						}
					}
					}
				} else {System.out.println("It's the Black Team's turn.");
				System.out.println("Select the X coordinate (0 to 7) of the piece you want to move.");
				int pieceX = getNextInt(0, 7, sc);
				System.out.println("Select the Y coordinate (0 to 7) of the piece you want to move.");
				int pieceY = getNextInt(0, 7, sc);
				Set<Tile> moves = new TreeSet<Tile>();
				String pieceType = "";
				for(ChessPiece c : chessPieces) {
					if(c.getX() == pieceX && c.getY() == pieceY 
							&& c.thisPieceColor().equals("black")) {
						moves = c.possibleMoves(gameTiles, chessPieces);
						pieceType = c.type();
					}
				}
				LinkedList<Tile> orderedMoves = new LinkedList<Tile>(moves);
				Collections.sort(orderedMoves);
				System.out.println("Piece selected: " + pieceType);
				System.out.println("Move options:");
				for(int i = 0; i< orderedMoves.size(); i++) {
					System.out.println(i + ": Move to (" + orderedMoves.get(i).getX() + 
							", " + orderedMoves.get(i).getY() + ")");
				}
				
				if(orderedMoves.size() == 0){
					System.out.println("No moves for this piece! "
							+ "Unfortunately I didn't code an undo function, so you lose your turn :(");
				} else {
				int moveChoice = getNextInt(0, orderedMoves.size() - 1, sc);
				int newX = orderedMoves.get(moveChoice).getX();
				int newY = orderedMoves.get(moveChoice).getY();

				for(ChessPiece c : chessPieces) {
					if(c.getX() == newX && c.getY() == newY && c.thisPieceColor().equals("white")) {
						System.out.println("Captured a " + c.type() + "!");
						chessPieces.remove(c);
					}
				}				
				for(ChessPiece c : chessPieces) {
					if(c.getX() == pieceX && c.getY() == pieceY && c.thisPieceColor().equals("black")) {
						c.move(newX, newY);
					}
				}
				}
			
				}
			}
			turnsPassed++;
			repaint();
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for(Tile[] tt : gameTiles) {
			for(Tile t : tt) {
				t.draw(g);
			}			
		}
		
		for(ChessPiece p : chessPieces) {
			p.draw(g);
		}
		//poison.draw(g);
		//snitch.draw(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
