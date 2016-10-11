package pieces;

import java.awt.Color;
import java.util.List;

import chess.Cell;

/**
 * This is the Bishop Class. The Move Function defines the basic rules for
 * movement of Bishop on a chess board
 * 
 *
 */
public class Bishop extends Piece {
	private static final String WHITE_BISHOP_IMAGE = "White_Bishop.png";
	private static final String BLACK_BISHOP_IMAGE = "Black_Bishop.png";

	// Constructor
	public Bishop(String i, Color c) {
		setId(i);
		setImagePath(c.equals(Color.WHITE) ? WHITE_BISHOP_IMAGE
				: BLACK_BISHOP_IMAGE);
		setColor(c);
	}

	@Override
	public List<Cell> possibleMovesCells(Cell state[][], int x, int y) {
		possibleMoves.clear();
		addNWMoves(state, x, y);
		addNEMoves(state, x, y);
		addSWMoves(state, x, y);
		addSEMoves(state, x, y);

		return possibleMoves;
	}

	private void addNWMoves(Cell state[][], int x, int y) {
		int tempx = x + 1;
		int tempy = y - 1;
		while (tempx < 8 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null) {
				possibleMoves.add(state[tempx][tempy]);
			}
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

	private void addNEMoves(Cell state[][], int x, int y) {
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

	private void addSWMoves(Cell state[][], int x, int y) {
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

	private void addSEMoves(Cell state[][], int x, int y) {
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

}