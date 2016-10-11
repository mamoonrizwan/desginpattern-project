package pieces;

import java.awt.Color;
import java.util.List;

import chess.Cell;

/**
 * This is the Queen Class inherited from the abstract Piece class
 *
 */
public class Queen extends Piece {
	private static final String WHITE_QUEEN_IMAGE = "White_Queen.png";
	private static final String BLACK_QUEEN_IMAGE = "Black_Queen.png";

	// Constructors
	public Queen(String i, Color c) {
		setId(i);
		setImagePath(c.equals(Color.WHITE) ? WHITE_QUEEN_IMAGE
				: BLACK_QUEEN_IMAGE);
		setColor(c);
	}

	// Move Function Defined
	@Override
	public List<Cell> possibleMovesCells(Cell state[][], int x, int y) {
		// Queen has most number of possible moves
		// Queen can move any number of steps in all 8 direction
		// The possible moves of queen is a combination of Rook and Bishop
		possibleMoves.clear();

		addLeftMoves(state, x, y);
		addRightMoves(state, x, y);
		addDownwardMoves(state, x, y);
		addUpwardMoves(state, x, y);
		addDiagonal1Moves(state, x, y);
		addDiagonal2Moves(state, x, y);
		addDiagonal3Moves(state, x, y);
		addDiagonal4Moves(state, x, y);

		return possibleMoves;
	}

	private void addDiagonal4Moves(Cell[][] state, int x, int y) {
		int tempx = x + 1;
		int tempy = y + 1;
		while (tempx < 8 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null)
				possibleMoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == this
					.getcolor())
				break;
			else {
				possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy++;
		}
	}

	private void addDiagonal3Moves(Cell[][] state, int x, int y) {
		int tempx = x - 1;
		int tempy = y - 1;
		while (tempx >= 0 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null)
				possibleMoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == this
					.getcolor())
				break;
			else {
				possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy--;
		}
	}

	private void addDiagonal2Moves(Cell[][] state, int x, int y) {
		int tempx = x - 1;
		int tempy = y + 1;
		while (tempx >= 0 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null)
				possibleMoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == this
					.getcolor())
				break;
			else {
				possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy++;
		}
	}

	private void addDiagonal1Moves(Cell[][] state, int x, int y) {
		int tempx = x + 1;
		int tempy = y - 1;
		while (tempx < 8 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null)
				possibleMoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == this
					.getcolor())
				break;
			else {
				possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy--;
		}
	}

	private void addUpwardMoves(Cell[][] state, int x, int y) {
		int tempy = y + 1;
		while (tempy < 8) {
			if (state[x][tempy].getpiece() == null)
				possibleMoves.add(state[x][tempy]);
			else if (state[x][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possibleMoves.add(state[x][tempy]);
				break;
			}
			tempy++;
		}
	}

	private void addDownwardMoves(Cell[][] state, int x, int y) {
		int tempy = y - 1;
		while (tempy >= 0) {
			if (state[x][tempy].getpiece() == null)
				possibleMoves.add(state[x][tempy]);
			else if (state[x][tempy].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possibleMoves.add(state[x][tempy]);
				break;
			}
			tempy--;
		}
	}

	private void addRightMoves(Cell[][] state, int x, int y) {
		int tempx = x + 1;
		while (tempx < 8) {
			if (state[tempx][y].getpiece() == null)
				possibleMoves.add(state[tempx][y]);
			else if (state[tempx][y].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possibleMoves.add(state[tempx][y]);
				break;
			}
			tempx++;
		}
	}

	private void addLeftMoves(Cell[][] state, int x, int y) {
		int tempx = x - 1;
		while (tempx >= 0) {
			if (state[tempx][y].getpiece() == null)
				possibleMoves.add(state[tempx][y]);
			else if (state[tempx][y].getpiece().getcolor() == this.getcolor())
				break;
			else {
				possibleMoves.add(state[tempx][y]);
				break;
			}
			tempx--;
		}
	}
}