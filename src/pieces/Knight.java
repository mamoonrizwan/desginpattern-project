package pieces;

import java.awt.Color;
import java.util.List;

import chess.Cell;

/**
 * This is the Knight Class inherited from the Piece abstract class
 * 
 *
 */
public class Knight extends Piece {
	private static final String WHITE_KNIGHT_IMAGE = "White_Knight.png";
	private static final String BLACK_KNIGHT_IMAGE = "Black_Knight.png";

	// Constructor
	public Knight(String i, Color c) {
		setId(i);
		setImagePath(c.equals(Color.WHITE) ? WHITE_KNIGHT_IMAGE
				: BLACK_KNIGHT_IMAGE);
		setColor(c);
	}

	// Move Function overridden
	// There are at max 8 possible moves for a knight at any point of time.
	// Knight moves only 2(1/2) steps
	@Override
	public List<Cell> possibleMovesCells(Cell state[][], int x, int y) {
		possibleMoves.clear();
		int posx[] = { x + 1, x + 1, x + 2, x + 2, x - 1, x - 1, x - 2, x - 2 };
		int posy[] = { y - 2, y + 2, y - 1, y + 1, y - 2, y + 2, y - 1, y + 1 };
		for (int i = 0; i < 8; i++)
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8))
				if ((state[posx[i]][posy[i]].getpiece() == null || state[posx[i]][posy[i]]
						.getpiece().getcolor() != this.getcolor())) {
					possibleMoves.add(state[posx[i]][posy[i]]);
				}
		return possibleMoves;
	}
}