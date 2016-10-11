package chess;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import pieces.King;
import pieces.Piece;
import pieces.PlayerPieces;

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

public class ChessControl extends Container {
	private static final long serialVersionUID = 1L;
	private static final String INACTIVE_GAME_IMAGE = "clash.jpg";

	private PlayerPieces whitePieces;
	private PlayerPieces blackPieces;
	private Cell currentCell, previousCell;
	private Color activePlayerColor = Color.white;
	private Cell boardState[][];
	private ArrayList<Cell> destinationlist = new ArrayList<Cell>();
	private JPanel board = new JPanel(new GridLayout(8, 8));
	private JPanel inactivePanel;
	private JSplitPane splitPane;
	private BufferedImage image;
	private StatComposite statComp;

	public ChessControl() {
		init();
	}

	private void init() {
		initializePieces();
		initializeBoard();
		initializeCells();
		createStatisticsComposite();
		createInactivePannel();
		setInactivePannelVisible();
	}

	private void initializePieces() {
		whitePieces = new PlayerPieces(Color.WHITE);
		blackPieces = new PlayerPieces(Color.BLACK);
	}

	private void initializeBoard() {
		board = new JPanel(new GridLayout(8, 8));
		board.setMinimumSize(new Dimension(800, 700));
		board.setBorder(BorderFactory.createLoweredBevelBorder());
		board.setMinimumSize(new Dimension(800, 700));
	}
	
	private void createStatisticsComposite() {
		statComp = new StatComposite();
		statComp.addGameStartListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				activateBoard();
			}
		});
	}

	private void createInactivePannel() {
		// The Left Layout When Game is inactive
		inactivePanel = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				try {
					image = ImageIO.read(this.getClass().getResource(INACTIVE_GAME_IMAGE));
				} catch (IOException ex) {
					System.out.println("not found");
				}

				g.drawImage(image, 0, 0, null);
			}
		};
	}
	
	private void setInactivePannelVisible() {
		inactivePanel.setMinimumSize(new Dimension(800, 700));
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inactivePanel, statComp);

		this.setBackground(Color.BLACK);
		this.setLayout(new BorderLayout());
		this.add(splitPane);
		this.revalidate();
	}
	
	private void activateBoard() {
		splitPane.remove(inactivePanel);
		splitPane.add(board);

		statComp.addTimerExpireListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				changeTurn();
			}
		});
	}

	private void initializeCells() {
		Cell cell;
		// Defining all the Cells
		boardState = new Cell[8][8];
		for (int row = 0; row < 8; row++) {
			for (int column = 0; column < 8; column++) {
				Piece piece = getPieceForLocationAtStart(row, column);

				cell = new Cell(row, column, piece);
				cell.addMouseListener(new CellSelectionListener());
				board.add(cell);
				boardState[row][column] = cell;
			}
		}
	}
	
	private Piece getPieceForLocationAtStart(int row, int column) {
		Piece piece = whitePieces.getPieceForLocationAtStart(row, column);
		if (piece != null)
			return piece;
		
		return blackPieces.getPieceForLocationAtStart(row, column);
	}

	// A function to change the chance from White Player to Black Player or vice
	// verse
	// It is made public because it is to be accessed in the Time Class
	private void changeTurn() {
		boolean isGameEnded = false;
		if (boardState[getKing(activePlayerColor).getx()][getKing(activePlayerColor).gety()].ischeck()) {
			activePlayerColor = getInactivePlayer();
			endGame();
			isGameEnded = true;
		}
		if (destinationlist.isEmpty() == false)
			clearDestinations(destinationlist);
		if (previousCell != null)
			previousCell.deselect();
		previousCell = null;
		activePlayerColor = getInactivePlayer();
		if (!isGameEnded) {
			statComp.updateChance();
		}
	}
	
	private Color getInactivePlayer() {
		if (activePlayerColor.equals(Color.WHITE))
			return Color.BLACK;
			
		return Color.WHITE;
	}

	// A function to retrieve the Black King or White King
	private King getKing(Color color) {
		if (color.equals(Color.WHITE))
			return whitePieces.getKing();
		else
			return blackPieces.getKing();
	}

	// A function to clean the highlights of possible destination cells
	private void clearDestinations(ArrayList<Cell> destList) // Function to
																// clear the
																// last move's
																// destinations
	{
		ListIterator<Cell> it = destList.listIterator();
		while (it.hasNext())
			it.next().clearPossibleDestination();
	}

	// A function that indicates the possible moves by highlighting the Cells
	private void highlightDestinations(ArrayList<Cell> destList) {
		ListIterator<Cell> it = destList.listIterator();
		while (it.hasNext())
			it.next().setPossibleDestination();
	}

	// Function to check if the king will be in danger if the given move is made
	private boolean willKingBeInDanger(Cell fromCell, Cell toCell) {
		Cell newBoardState[][] = cloneBoardState();

		if (newBoardState[toCell.getx()][toCell.gety()].getpiece() != null)
			newBoardState[toCell.getx()][toCell.gety()].removePiece();

		newBoardState[toCell.getx()][toCell.gety()].setPiece(newBoardState[fromCell.getx()][fromCell.gety()].getpiece());
		if (newBoardState[toCell.getx()][toCell.gety()].getpiece() instanceof King) {
			((King) (newBoardState[toCell.getx()][toCell.gety()].getpiece())).setx(toCell.getx());
			((King) (newBoardState[toCell.getx()][toCell.gety()].getpiece())).sety(toCell.gety());
		}
		newBoardState[fromCell.getx()][fromCell.gety()].removePiece();
		return ((King) (newBoardState[getKing(activePlayerColor).getx()][getKing(
				activePlayerColor).gety()].getpiece()))
				.isInDanger(newBoardState);
	}
	
	private Cell[][] cloneBoardState() {
		Cell newBoardState[][] = new Cell[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++) {
				try {
					newBoardState[i][j] = new Cell(boardState[i][j]);
				} catch (CloneNotSupportedException e) {
					e.printStackTrace();
				}
			}
		
		return newBoardState;
	}

	// A function to eliminate the possible moves that will put the King in
	// danger
	private ArrayList<Cell> filterKingDangerDestinations(ArrayList<Cell> destList,
			Cell fromCell) {
		ArrayList<Cell> newlist = new ArrayList<Cell>();
		Cell newBoardState[][];
		ListIterator<Cell> it = destList.listIterator();
		int x, y;
		while (it.hasNext()) {
			newBoardState = cloneBoardState();

			Cell tempc = it.next();
			if (newBoardState[tempc.getx()][tempc.gety()].getpiece() != null)
				newBoardState[tempc.getx()][tempc.gety()].removePiece();
			newBoardState[tempc.getx()][tempc.gety()]
					.setPiece(newBoardState[fromCell.getx()][fromCell.gety()].getpiece());
			x = getKing(activePlayerColor).getx();
			y = getKing(activePlayerColor).gety();
			if (newBoardState[fromCell.getx()][fromCell.gety()].getpiece() instanceof King) {
				((King) (newBoardState[tempc.getx()][tempc.gety()].getpiece()))
						.setx(tempc.getx());
				((King) (newBoardState[tempc.getx()][tempc.gety()].getpiece()))
						.sety(tempc.gety());
				x = tempc.getx();
				y = tempc.gety();
			}
			newBoardState[fromCell.getx()][fromCell.gety()].removePiece();
			if (!(((King) (newBoardState[x][y].getpiece()))
					.isInDanger(newBoardState)))
				newlist.add(tempc);
		}
		
		return newlist;
	}

	// A Function to filter the possible moves when the king of the current
	// player is under Check
	private ArrayList<Cell> inCheckFilterDestinations(ArrayList<Cell> destList,
			Cell fromcell, Color color) {
		ArrayList<Cell> newlist = new ArrayList<Cell>();
		Cell newBoardState[][];
		ListIterator<Cell> it = destList.listIterator();
		int x, y;
		while (it.hasNext()) {
			newBoardState = cloneBoardState();
			Cell tempc = it.next();
			if (newBoardState[tempc.getx()][tempc.gety()].getpiece() != null)
				newBoardState[tempc.getx()][tempc.gety()].removePiece();
			newBoardState[tempc.getx()][tempc.gety()]
					.setPiece(newBoardState[fromcell.getx()][fromcell.gety()].getpiece());
			x = getKing(color).getx();
			y = getKing(color).gety();
			if (newBoardState[tempc.getx()][tempc.gety()].getpiece() instanceof King) {
				((King) (newBoardState[tempc.getx()][tempc.gety()].getpiece()))
						.setx(tempc.getx());
				((King) (newBoardState[tempc.getx()][tempc.gety()].getpiece()))
						.sety(tempc.gety());
				x = tempc.getx();
				y = tempc.gety();
			}
			newBoardState[fromcell.getx()][fromcell.gety()].removePiece();
			if (!(((King) (newBoardState[x][y].getpiece()))
					.isInDanger(newBoardState)))
				newlist.add(tempc);
		}
		return newlist;
	}

	// A function to check if the King is check-mate. The Game Ends if this
	// function returns true.
	public boolean checkmate(Color color) {
		ArrayList<Cell> dlist = new ArrayList<Cell>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (boardState[i][j].getpiece() != null
						&& boardState[i][j].getpiece().getcolor() == color) {
					dlist.clear();
					dlist = boardState[i][j].getpiece().possibleMovesCells(boardState, i, j);
					dlist = inCheckFilterDestinations(dlist, boardState[i][j], color);
					if (dlist.size() != 0)
						return false;
				}
			}
		}
		
		return true;
	}

	private void endGame() {
		clearDestinations(destinationlist);
		if (previousCell != null)
			previousCell.removePiece();
		String winner = statComp.endGame(activePlayerColor);
		JOptionPane.showMessageDialog(board, "Checkmate!!!\n" + winner + " wins");

		this.removeAll();
		init();
	}

	private void handleNewCellSelection() {
		if (currentCell.getpiece() != null) {
			if (currentCell.getpiece().getcolor() != activePlayerColor)
				return;
			currentCell.select();
			previousCell = currentCell;
			destinationlist.clear();
			destinationlist = currentCell.getpiece().possibleMovesCells(boardState, currentCell.getx(), currentCell.gety());
			if (currentCell.getpiece() instanceof King)
				destinationlist = filterKingDangerDestinations(destinationlist, currentCell);
			else {
				if (boardState[getKing(activePlayerColor).getx()][getKing(activePlayerColor)
						.gety()].ischeck())
					destinationlist = new ArrayList<Cell>(
							filterKingDangerDestinations(destinationlist, currentCell));
				else if (destinationlist.isEmpty() == false
						&& willKingBeInDanger(currentCell, destinationlist.get(0)))
					destinationlist.clear();
			}
			highlightDestinations(destinationlist);
		}
	}
	
	private void handleMove() {
		if (currentCell.isPossibleDestination()) {
			if (currentCell.getpiece() != null)
				currentCell.removePiece();
			currentCell.setPiece(previousCell.getpiece());
			if (previousCell.ischeck())
				previousCell.removecheck();
			previousCell.removePiece();
			if (getKing(getInactivePlayer()).isInDanger(boardState)) {
				boardState[getKing(getInactivePlayer()).getx()][getKing(
						getInactivePlayer()).gety()].setcheck();
				if (checkmate(getKing(getInactivePlayer()).getcolor())) {
					previousCell.deselect();
					if (previousCell.getpiece() != null)
						previousCell.removePiece();
					endGame();
				}
			}
			if (getKing(activePlayerColor).isInDanger(boardState) == false)
				boardState[getKing(activePlayerColor).getx()][getKing(
						activePlayerColor).gety()].removecheck();
			if (currentCell.getpiece() instanceof King) {
				((King) currentCell.getpiece())
						.setx(currentCell.getx());
				((King) currentCell.getpiece())
						.sety(currentCell.gety());
			}
			changeTurn();
		}
		
		if (previousCell != null) {
			previousCell.deselect();
			previousCell = null;
		}
		clearDestinations(destinationlist);
		destinationlist.clear();
	}
	
	private void handleChangeSelectedPiece() {
		previousCell.deselect();
		clearDestinations(destinationlist);
		destinationlist.clear();
		currentCell.select();
		previousCell = currentCell;
		destinationlist = currentCell.getpiece().possibleMovesCells(
				boardState, currentCell.getx(), currentCell.gety());
		if (currentCell.getpiece() instanceof King)
			destinationlist = filterKingDangerDestinations(
					destinationlist, currentCell);
		else {
			if (boardState[getKing(activePlayerColor).getx()][getKing(
					activePlayerColor).gety()].ischeck())
				destinationlist = new ArrayList<Cell>(
						filterKingDangerDestinations(destinationlist,
								currentCell));
			else if (destinationlist.isEmpty() == false
					&& willKingBeInDanger(currentCell,
							destinationlist.get(0)))
				destinationlist.clear();
		}
		highlightDestinations(destinationlist);
	}
	
	private void updateKingsPosition() {
		if (currentCell.getpiece() != null && currentCell.getpiece() instanceof King) {
			((King) currentCell.getpiece()).setx(currentCell.getx());
			((King) currentCell.getpiece()).sety(currentCell.gety());
		}
	}
	
	class CellSelectionListener extends MouseAdapter {
		// These are the abstract function of the parent class. Only relevant method
		// here is the On-Click Fuction
		// which is called when the user clicks on a particular cell
		@Override
		public void mouseClicked(MouseEvent arg0) {
			currentCell = (Cell) arg0.getSource();
			if (previousCell == null) {
				handleNewCellSelection();
			}
			else if (currentCell.getx() == previousCell.getx()
					&& currentCell.gety() == previousCell.gety()) {
				currentCell.deselect();
				clearDestinations(destinationlist);
				destinationlist.clear();
				previousCell = null;
			}
			else if (currentCell.getpiece() == null
					|| previousCell.getpiece().getcolor() != currentCell
							.getpiece().getcolor()) {
				handleMove();
			}
			else if (previousCell.getpiece().getcolor() == currentCell
					.getpiece().getcolor()) {
				handleChangeSelectedPiece();
			}
			
			updateKingsPosition();
		}
	}
}