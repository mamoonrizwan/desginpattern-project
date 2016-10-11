package pieces;

import java.awt.Color;
import java.util.List;

import chess.Cell;

/**
 * This is the Rook class inherited from abstract Piece class
 *
 */
public class Rook extends Piece {
	private static final String WHITE_ROOK_IMAGE = "White_Rook.png";
	private static final String BLACK_ROOK_IMAGE = "Black_Rook.png";

	// Constructor
	public Rook(String i, Color c) {
		setId(i);
		setImagePath(c.equals(Color.WHITE) ? WHITE_ROOK_IMAGE
				: BLACK_ROOK_IMAGE);
		setColor(c);
	}

	// Move function defined
	@Override
	public List<Cell> possibleMovesCells(Cell state[][], int x, int y) {
		// Rook can move only horizontally or vertically
		possibleMoves.clear();

		addLeftMoves(state, x, y);
		addRightMoves(state, x, y);
		addDownwardMoves(state, x, y);
		addUpwardMoves(state, x, y);

		return possibleMoves;
	}

	private void addUpwardMoves(Cell[][] state, int x, int y) {
		int tempy;
		tempy = y + 1;
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
