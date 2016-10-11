package pieces;

import java.awt.Color;
import java.util.List;

import chess.Cell;

/**
 * This is the Pawn Class inherited from the piece
 *
 */
public class Pawn extends Piece {
	private static final String WHITE_PAWN_IMAGE = "White_Pawn.png";
	private static final String BLACK_PAWN_IMAGE = "Black_Pawn.png";

	// COnstructors
	public Pawn(String i, Color c) {
		setId(i);
		setImagePath(c.equals(Color.WHITE) ? WHITE_PAWN_IMAGE
				: BLACK_PAWN_IMAGE);
		setColor(c);
	}

	// Move Function Overridden
	@Override
	public List<Cell> possibleMovesCells(Cell state[][], int x, int y) {
		// Pawn can move only one step except the first chance when it may move
		// 2 steps
		// It can move in a diagonal fashion only for attacking a piece of
		// opposite color
		// It cannot move backward or move forward to attact a piece

		possibleMoves.clear();
		if (getcolor() == Color.WHITE) {
			addWhitePawnMoves(state, x, y);
		}
		else {
			addBlackPawnMoves(state, x, y);
		}

		return possibleMoves;
	}

	private void addBlackPawnMoves(Cell[][] state, int x, int y) {
		if (x == 8)
			return;

		if (state[x + 1][y].getpiece() == null) {
			possibleMoves.add(state[x + 1][y]);
			if (x == 1) {
				if (state[3][y].getpiece() == null)
					possibleMoves.add(state[3][y]);
			}
		}
		if ((y > 0)
				&& (state[x + 1][y - 1].getpiece() != null)
				&& (state[x + 1][y - 1].getpiece().getcolor() != this
						.getcolor()))
			possibleMoves.add(state[x + 1][y - 1]);
		if ((y < 7)
				&& (state[x + 1][y + 1].getpiece() != null)
				&& (state[x + 1][y + 1].getpiece().getcolor() != this
						.getcolor()))
			possibleMoves.add(state[x + 1][y + 1]);
	}

	private void addWhitePawnMoves(Cell[][] state, int x, int y) {
		if (x == 0)
			return;

		if (state[x - 1][y].getpiece() == null) {
			possibleMoves.add(state[x - 1][y]);
			if (x == 6) {
				if (state[4][y].getpiece() == null)
					possibleMoves.add(state[4][y]);
			}
		}
		if ((y > 0)
				&& (state[x - 1][y - 1].getpiece() != null)
				&& (state[x - 1][y - 1].getpiece().getcolor() != this
						.getcolor()))
			possibleMoves.add(state[x - 1][y - 1]);
		if ((y < 7)
				&& (state[x - 1][y + 1].getpiece() != null)
				&& (state[x - 1][y + 1].getpiece().getcolor() != this
						.getcolor()))
			possibleMoves.add(state[x - 1][y + 1]);
	}
}
