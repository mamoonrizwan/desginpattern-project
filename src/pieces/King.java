package pieces;

import java.awt.Color;
import java.util.List;

import chess.Cell;

public class King extends Piece {
	private static final String WHITE_KING_IMAGE = "White_King.png";
	private static final String BLACK_KING_IMAGE = "Black_King.png";

	
	
	int x; // Extra variables for King class to keep a track of
						// king's position
	int y;

	KingDanger kd ;
	
	// King Constructor
	public King(String i, Color c) {
		setId(i);
		setImagePath(c.equals(Color.WHITE) ? WHITE_KING_IMAGE
				: BLACK_KING_IMAGE);
		setColor(c);
		kd = new KingDanger();
	}

	// general value access functions
	public void setx(int x) {
		this.x = x;
	}

	public void sety(int y) {
		this.y = y;
	}

	public int getx() {
		return x;
	}

	public int gety() {
		return y;
	}

	// Move Function for King Overridden from Pieces
	@Override
	public List<Cell> possibleMovesCells(Cell state[][], int x, int y) {
		// King can move only one step. So all the adjacent 8 cells have been
		// considered.
		possibleMoves.clear();
		int posx[] = { x, x, x + 1, x + 1, x + 1, x - 1, x - 1, x - 1 };
		int posy[] = { y - 1, y + 1, y - 1, y, y + 1, y - 1, y, y + 1 };
		for (int i = 0; i < 8; i++)
			if (posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8)
				if ((state[posx[i]][posy[i]].getpiece() == null || state[posx[i]][posy[i]]
						.getpiece().getcolor() != this.getcolor()))
					possibleMoves.add(state[posx[i]][posy[i]]);
		return possibleMoves;
	}

	// Function to check if king is under threat
	// It checks whether there is any piece of opposite color that can attack
	// king for a given board state
	public boolean isInDanger(Cell state[][]) {

		return kd.isInDangerFromLeft(this, state) || kd.isInDangerFromRight(this, state)
				|| kd.isInDangerFromUp(this, state) || kd.isInDangerFromDown(this, state)
				|| kd.isInDangerFromDiagonal1(this, state)
				|| kd.isInDangerFromDiagonal2(this, state)
				|| kd.isInDangerFromDiagonal3(this, state)
				|| kd.isInDangerFromDiagonal4(this, state)
				|| kd.isInDangerFromOpponentKnight(this, state)
				|| kd.isInDangerFromOpponentKing(this, state)
				|| kd.isInDangerFromOpponentPawns(this, state);
	}
}