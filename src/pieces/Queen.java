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
	private QueenMoves chessmoves ;

	// Constructors
	public Queen(String i, Color c) {
		setId(i);
		setImagePath(c.equals(Color.WHITE) ? WHITE_QUEEN_IMAGE
				: BLACK_QUEEN_IMAGE);
		setColor(c);
		chessmoves = new QueenMoves();
	}

	// Move Function Defined
	@Override
	public List<Cell> possibleMovesCells(Cell state[][], int x, int y) {
		// Queen has most number of possible moves
		// Queen can move any number of steps in all 8 direction
		// The possible moves of queen is a combination of Rook and Bishop
		possibleMoves.clear();

		chessmoves.addLeftMoves(this, state, x, y);
		chessmoves.addRightMoves(this, state, x, y);
		chessmoves.addDownwardMoves(this, state, x, y);
		chessmoves.addUpwardMoves(this, state, x, y);
		chessmoves.addDiagonal1Moves(this, state, x, y);
		chessmoves.addDiagonal2Moves(this, state, x, y);
		chessmoves.addDiagonal3Moves(this, state, x, y);
		chessmoves.addDiagonal4Moves(this, state, x, y);

		return possibleMoves;
	}
}