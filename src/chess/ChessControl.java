package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

/**
 * @author Ashish Kedia and Adarsh Mohata
 *
 */

/**
 * This is the Main Class of our project. All GUI Elements are declared,
 * initialized and used in this class itself. It is inherited from the JFrame
 * Class of Java's Swing Library.
 * 
 */

public class ChessControl extends Container implements MouseListener {
	private static final long serialVersionUID = 1L;

	private Rook wr01, wr02, br01, br02;
	private Knight wk01, wk02, bk01, bk02;
	private Bishop wb01, wb02, bb01, bb02;
	private Pawn wp[], bp[];
	private Queen wq, bq;
	private King wk, bk;
	private Cell c, previous;
	private int chance = 0;
	private Cell boardState[][];
	private ArrayList<Cell> destinationlist = new ArrayList<Cell>();
	private JPanel board = new JPanel(new GridLayout(8, 8));
	private JPanel temp;
	private JSplitPane split;
	public static ChessControl Mainboard;
	private boolean isGameEnded = false;
	private BufferedImage image;
	private StatComposite statComp;

	public ChessControl() {
		Mainboard = this;
		init();
	}

	private void init() {
		// variable initialization
		initializePieces();
		board = new JPanel(new GridLayout(8, 8));
		board.setMinimumSize(new Dimension(800, 700));
		board.setBorder(BorderFactory.createLoweredBevelBorder());
		
		this.setBackground(Color.black);
		this.setLayout(new BorderLayout());

		Cell cell;
		pieces.Piece P;
		// Defining all the Cells
		boardState = new Cell[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				P = null;
				if (i == 0 && j == 0)
					P = br01;
				else if (i == 0 && j == 7)
					P = br02;
				else if (i == 7 && j == 0)
					P = wr01;
				else if (i == 7 && j == 7)
					P = wr02;
				else if (i == 0 && j == 1)
					P = bk01;
				else if (i == 0 && j == 6)
					P = bk02;
				else if (i == 7 && j == 1)
					P = wk01;
				else if (i == 7 && j == 6)
					P = wk02;
				else if (i == 0 && j == 2)
					P = bb01;
				else if (i == 0 && j == 5)
					P = bb02;
				else if (i == 7 && j == 2)
					P = wb01;
				else if (i == 7 && j == 5)
					P = wb02;
				else if (i == 0 && j == 3)
					P = bk;
				else if (i == 0 && j == 4)
					P = bq;
				else if (i == 7 && j == 3)
					P = wk;
				else if (i == 7 && j == 4)
					P = wq;
				else if (i == 1)
					P = bp[j];
				else if (i == 6)
					P = wp[j];
				cell = new Cell(i, j, P);
				cell.addMouseListener(this);
				board.add(cell);
				boardState[i][j] = cell;
			}
		board.setMinimumSize(new Dimension(800, 700));

		// The Left Layout When Game is inactive
		temp = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				try {
					image = ImageIO.read(this.getClass().getResource(
							"clash.jpg"));
				} catch (IOException ex) {
					System.out.println("not found");
				}

				g.drawImage(image, 0, 0, null);
			}
		};

		temp.setMinimumSize(new Dimension(800, 700));
		statComp = new StatComposite();
		statComp.addGameStartListener(new START());
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, temp, statComp);

		this.add(split);
		this.revalidate();
	}

	private void initializePieces() {
		wr01 = new Rook("WR01", "White_Rook.png", 0);
		wr02 = new Rook("WR02", "White_Rook.png", 0);
		br01 = new Rook("BR01", "Black_Rook.png", 1);
		br02 = new Rook("BR02", "Black_Rook.png", 1);
		wk01 = new Knight("WK01", "White_Knight.png", 0);
		wk02 = new Knight("WK02", "White_Knight.png", 0);
		bk01 = new Knight("BK01", "Black_Knight.png", 1);
		bk02 = new Knight("BK02", "Black_Knight.png", 1);
		wb01 = new Bishop("WB01", "White_Bishop.png", 0);
		wb02 = new Bishop("WB02", "White_Bishop.png", 0);
		bb01 = new Bishop("BB01", "Black_Bishop.png", 1);
		bb02 = new Bishop("BB02", "Black_Bishop.png", 1);
		wq = new Queen("WQ", "White_Queen.png", 0);
		bq = new Queen("BQ", "Black_Queen.png", 1);
		wk = new King("WK", "White_King.png", 0, 7, 3);
		bk = new King("BK", "Black_King.png", 1, 0, 3);
		wp = new Pawn[8];
		bp = new Pawn[8];
		for (int i = 0; i < 8; i++) {
			wp[i] = new Pawn("WP0" + (i + 1), "White_Pawn.png", 0);
			bp[i] = new Pawn("BP0" + (i + 1), "Black_Pawn.png", 1);
		}
	}

	// A function to change the chance from White Player to Black Player or vice
	// verse
	// It is made public because it is to be accessed in the Time Class
	private void changechance() {
		if (boardState[getKing(chance).getx()][getKing(chance).gety()]
				.ischeck()) {
			chance ^= 1;
			gameend();
		}
		if (destinationlist.isEmpty() == false)
			cleandestinations(destinationlist);
		if (previous != null)
			previous.deselect();
		previous = null;
		chance ^= 1;
		if (!isGameEnded /*&& timer != null*/) {
			statComp.updateChance();
		}
	}

	// A function to retrieve the Black King or White King
	private King getKing(int color) {
		if (color == 0)
			return wk;
		else
			return bk;
	}

	// A function to clean the highlights of possible destination cells
	private void cleandestinations(ArrayList<Cell> destlist) // Function to
																// clear the
																// last move's
																// destinations
	{
		ListIterator<Cell> it = destlist.listIterator();
		while (it.hasNext())
			it.next().removepossibledestination();
	}

	// A function that indicates the possible moves by highlighting the Cells
	private void highlightdestinations(ArrayList<Cell> destlist) {
		ListIterator<Cell> it = destlist.listIterator();
		while (it.hasNext())
			it.next().setpossibledestination();
	}

	// Function to check if the king will be in danger if the given move is made
	private boolean willkingbeindanger(Cell fromcell, Cell tocell) {
		Cell newboardstate[][] = new Cell[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				try {
					newboardstate[i][j] = new Cell(boardState[i][j]);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
					System.out.println("There is a problem with cloning !!");
				}
			}

		if (newboardstate[tocell.x][tocell.y].getpiece() != null)
			newboardstate[tocell.x][tocell.y].removePiece();

		newboardstate[tocell.x][tocell.y]
				.setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
		if (newboardstate[tocell.x][tocell.y].getpiece() instanceof King) {
			((King) (newboardstate[tocell.x][tocell.y].getpiece()))
					.setx(tocell.x);
			((King) (newboardstate[tocell.x][tocell.y].getpiece()))
					.sety(tocell.y);
		}
		newboardstate[fromcell.x][fromcell.y].removePiece();
		if (((King) (newboardstate[getKing(chance).getx()][getKing(chance)
				.gety()].getpiece())).isindanger(newboardstate) == true)
			return true;
		else
			return false;
	}

	// A function to eliminate the possible moves that will put the King in
	// danger
	private ArrayList<Cell> filterdestination(ArrayList<Cell> destlist,
			Cell fromcell) {
		ArrayList<Cell> newlist = new ArrayList<Cell>();
		Cell newboardstate[][] = new Cell[8][8];
		ListIterator<Cell> it = destlist.listIterator();
		int x, y;
		while (it.hasNext()) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++) {
					try {
						newboardstate[i][j] = new Cell(boardState[i][j]);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}

			Cell tempc = it.next();
			if (newboardstate[tempc.x][tempc.y].getpiece() != null)
				newboardstate[tempc.x][tempc.y].removePiece();
			newboardstate[tempc.x][tempc.y]
					.setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
			x = getKing(chance).getx();
			y = getKing(chance).gety();
			if (newboardstate[fromcell.x][fromcell.y].getpiece() instanceof King) {
				((King) (newboardstate[tempc.x][tempc.y].getpiece()))
						.setx(tempc.x);
				((King) (newboardstate[tempc.x][tempc.y].getpiece()))
						.sety(tempc.y);
				x = tempc.x;
				y = tempc.y;
			}
			newboardstate[fromcell.x][fromcell.y].removePiece();
			if ((((King) (newboardstate[x][y].getpiece()))
					.isindanger(newboardstate) == false))
				newlist.add(tempc);
		}
		return newlist;
	}

	// A Function to filter the possible moves when the king of the current
	// player is under Check
	private ArrayList<Cell> incheckfilter(ArrayList<Cell> destlist,
			Cell fromcell, int color) {
		ArrayList<Cell> newlist = new ArrayList<Cell>();
		Cell newboardstate[][] = new Cell[8][8];
		ListIterator<Cell> it = destlist.listIterator();
		int x, y;
		while (it.hasNext()) {
			for (int i = 0; i < 8; i++)
				for (int j = 0; j < 8; j++) {
					try {
						newboardstate[i][j] = new Cell(boardState[i][j]);
					} catch (CloneNotSupportedException e) {
						e.printStackTrace();
					}
				}
			Cell tempc = it.next();
			if (newboardstate[tempc.x][tempc.y].getpiece() != null)
				newboardstate[tempc.x][tempc.y].removePiece();
			newboardstate[tempc.x][tempc.y]
					.setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
			x = getKing(color).getx();
			y = getKing(color).gety();
			if (newboardstate[tempc.x][tempc.y].getpiece() instanceof King) {
				((King) (newboardstate[tempc.x][tempc.y].getpiece()))
						.setx(tempc.x);
				((King) (newboardstate[tempc.x][tempc.y].getpiece()))
						.sety(tempc.y);
				x = tempc.x;
				y = tempc.y;
			}
			newboardstate[fromcell.x][fromcell.y].removePiece();
			if ((((King) (newboardstate[x][y].getpiece()))
					.isindanger(newboardstate) == false))
				newlist.add(tempc);
		}
		return newlist;
	}

	// A function to check if the King is check-mate. The Game Ends if this
	// function returns true.
	public boolean checkmate(int color) {
		ArrayList<Cell> dlist = new ArrayList<Cell>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardState[i][j].getpiece() != null
						&& boardState[i][j].getpiece().getcolor() == color) {
					dlist.clear();
					dlist = boardState[i][j].getpiece().move(boardState, i, j);
					dlist = incheckfilter(dlist, boardState[i][j], color);
					if (dlist.size() != 0)
						return false;
				}
			}
		}
		return true;
	}

	private void gameend() {
		cleandestinations(destinationlist);
		if (previous != null)
			previous.removePiece();
		String winner = statComp.endGame(chance);
		JOptionPane.showMessageDialog(board, "Checkmate!!!\n" + winner
				+ " wins");

		split.remove(board);
		split.add(temp);
		
		isGameEnded = true;
		this.removeAll();
		init();
	}

	// These are the abstract function of the parent class. Only relevant method
	// here is the On-Click Fuction
	// which is called when the user clicks on a particular cell
	@Override
	public void mouseClicked(MouseEvent arg0) {
		c = (Cell) arg0.getSource();
		if (previous == null) {
			if (c.getpiece() != null) {
				if (c.getpiece().getcolor() != chance)
					return;
				c.select();
				previous = c;
				destinationlist.clear();
				destinationlist = c.getpiece().move(boardState, c.x, c.y);
				if (c.getpiece() instanceof King)
					destinationlist = filterdestination(destinationlist, c);
				else {
					if (boardState[getKing(chance).getx()][getKing(chance)
							.gety()].ischeck())
						destinationlist = new ArrayList<Cell>(
								filterdestination(destinationlist, c));
					else if (destinationlist.isEmpty() == false
							&& willkingbeindanger(c, destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
		} else {
			if (c.x == previous.x && c.y == previous.y) {
				c.deselect();
				cleandestinations(destinationlist);
				destinationlist.clear();
				previous = null;
			} else if (c.getpiece() == null
					|| previous.getpiece().getcolor() != c.getpiece()
							.getcolor()) {
				if (c.ispossibledestination()) {
					if (c.getpiece() != null)
						c.removePiece();
					c.setPiece(previous.getpiece());
					if (previous.ischeck())
						previous.removecheck();
					previous.removePiece();
					if (getKing(chance ^ 1).isindanger(boardState)) {
						boardState[getKing(chance ^ 1).getx()][getKing(
								chance ^ 1).gety()].setcheck();
						if (checkmate(getKing(chance ^ 1).getcolor())) {
							previous.deselect();
							if (previous.getpiece() != null)
								previous.removePiece();
							gameend();
						}
					}
					if (getKing(chance).isindanger(boardState) == false)
						boardState[getKing(chance).getx()][getKing(chance)
								.gety()].removecheck();
					if (c.getpiece() instanceof King) {
						((King) c.getpiece()).setx(c.x);
						((King) c.getpiece()).sety(c.y);
					}
					changechance();
					if (!isGameEnded) {
//						timer.reset();
//						timer.start();
						statComp.restartTimer();
					}
				}
				if (previous != null) {
					previous.deselect();
					previous = null;
				}
				cleandestinations(destinationlist);
				destinationlist.clear();
			} else if (previous.getpiece().getcolor() == c.getpiece()
					.getcolor()) {
				previous.deselect();
				cleandestinations(destinationlist);
				destinationlist.clear();
				c.select();
				previous = c;
				destinationlist = c.getpiece().move(boardState, c.x, c.y);
				if (c.getpiece() instanceof King)
					destinationlist = filterdestination(destinationlist, c);
				else {
					if (boardState[getKing(chance).getx()][getKing(chance)
							.gety()].ischeck())
						destinationlist = new ArrayList<Cell>(
								filterdestination(destinationlist, c));
					else if (destinationlist.isEmpty() == false
							&& willkingbeindanger(c, destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
		}
		if (c.getpiece() != null && c.getpiece() instanceof King) {
			((King) c.getpiece()).setx(c.x);
			((King) c.getpiece()).sety(c.y);
		}
	}

	// Other Irrelevant abstract function. Only the Click Event is captured.
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	class START implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			split.remove(temp);
			split.add(board);

			statComp.addTimerExpireListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					changechance();
				}
			});
		}
	}
}