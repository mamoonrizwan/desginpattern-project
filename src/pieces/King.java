package pieces;

import java.awt.Color;
import java.util.List;

import chess.Cell;

public class King extends Piece {
	private static final String WHITE_KING_IMAGE = "White_King.png";
	private static final String BLACK_KING_IMAGE = "Black_King.png";

	private int x, y; // Extra variables for King class to keep a track of
						// king's position

	// King Constructor
	public King(String i, Color c) {
		setId(i);
		setImagePath(c.equals(Color.WHITE) ? WHITE_KING_IMAGE
				: BLACK_KING_IMAGE);
		setColor(c);
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

		return isInDangerFromLeft(state) || isInDangerFromRight(state)
				|| isInDangerFromUp(state) || isInDangerFromDown(state)
				|| isInDangerFromDiagonal1(state)
				|| isInDangerFromDiagonal2(state)
				|| isInDangerFromDiagonal3(state)
				|| isInDangerFromDiagonal4(state)
				|| isInDangerFromOpponentKnight(state)
				|| isInDangerFromOpponentKing(state)
				|| isInDangerFromOpponentPawns(state);
	}

	private boolean isInDangerFromOpponentPawns(Cell[][] state) {
		if (getcolor().equals(Color.WHITE)) {
			if (isInDangerFromLeftPawn(state))
				return true;
			if (isInDangerFromTopPawn(state))
				return true;
		}
		else {
			if (isInDangerFromBottomPawn(state))
				return true;
			if (isInDangerFromRightPawn(state))
				return true;
		}
		return false;
	}

	private boolean isInDangerFromRightPawn(Cell[][] state) {
		return x < 7
				&& y < 7
				&& state[x + 1][y + 1].getpiece() != null
				&& state[x + 1][y + 1].getpiece().getcolor()
						.equals(Color.WHITE)
				&& (state[x + 1][y + 1].getpiece() instanceof Pawn);
	}

	private boolean isInDangerFromBottomPawn(Cell[][] state) {
		return x < 7
				&& y > 0
				&& state[x + 1][y - 1].getpiece() != null
				&& state[x + 1][y - 1].getpiece().getcolor()
						.equals(Color.WHITE)
				&& (state[x + 1][y - 1].getpiece() instanceof Pawn);
	}

	private boolean isInDangerFromTopPawn(Cell[][] state) {
		return x > 0
				&& y < 7
				&& state[x - 1][y + 1].getpiece() != null
				&& state[x - 1][y + 1].getpiece().getcolor()
						.equals(Color.BLACK)
				&& (state[x - 1][y + 1].getpiece() instanceof Pawn);
	}

	private boolean isInDangerFromLeftPawn(Cell[][] state) {
		return x > 0
				&& y > 0
				&& state[x - 1][y - 1].getpiece() != null
				&& state[x - 1][y - 1].getpiece().getcolor()
						.equals(Color.BLACK)
				&& (state[x - 1][y - 1].getpiece() instanceof Pawn);
	}

	private boolean isInDangerFromOpponentKnight(Cell[][] state) {
		int posx[] = { x + 1, x + 1, x + 2, x + 2, x - 1, x - 1, x - 2, x - 2 };
		int posy[] = { y - 2, y + 2, y - 1, y + 1, y - 2, y + 2, y - 1, y + 1 };
		for (int i = 0; i < 8; i++)
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8))
				if (state[posx[i]][posy[i]].getpiece() != null
						&& state[posx[i]][posy[i]].getpiece().getcolor() != this
								.getcolor()
						&& (state[posx[i]][posy[i]].getpiece() instanceof Knight)) {
					return true;
				}

		return false;
	}

	private boolean isInDangerFromOpponentKing(Cell[][] state) {
		int pox[] = { x + 1, x + 1, x + 1, x, x, x - 1, x - 1, x - 1 };
		int poy[] = { y - 1, y + 1, y, y + 1, y - 1, y + 1, y - 1, y };
		for (int i = 0; i < 8; i++)
			if ((pox[i] >= 0 && pox[i] < 8 && poy[i] >= 0 && poy[i] < 8))
				if (state[pox[i]][poy[i]].getpiece() != null
						&& state[pox[i]][poy[i]].getpiece().getcolor() != this
								.getcolor()
						&& (state[pox[i]][poy[i]].getpiece() instanceof King)) {
					return true;
				}

		return false;
	}

	private boolean isInDangerFromDown(Cell[][] state) {
		for (int i = y - 1; i >= 0; i--) {
			if (state[x][i].getpiece() == null)
				continue;
			else if (state[x][i].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if ((state[x][i].getpiece() instanceof Rook)
						|| (state[x][i].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}

		return false;
	}

	private boolean isInDangerFromUp(Cell[][] state) {
		for (int i = y + 1; i < 8; i++) {
			if (state[x][i].getpiece() == null)
				continue;
			else if (state[x][i].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if ((state[x][i].getpiece() instanceof Rook)
						|| (state[x][i].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}
		return false;
	}

	private boolean isInDangerFromRight(Cell[][] state) {
		for (int i = x - 1; i >= 0; i--) {
			if (state[i][y].getpiece() == null)
				continue;
			else if (state[i][y].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if ((state[i][y].getpiece() instanceof Rook)
						|| (state[i][y].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}

		return false;
	}

	private boolean isInDangerFromLeft(Cell[][] state) {
		for (int i = x + 1; i < 8; i++) {
			if (state[i][y].getpiece() == null)
				continue;
			else if (state[i][y].getpiece().getcolor() == this.getcolor())
				break;
			else {
				if ((state[i][y].getpiece() instanceof Rook)
						|| (state[i][y].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}

		return false;
	}

	private boolean isInDangerFromDiagonal1(Cell[][] state) {
		int tempx = x + 1, tempy = y - 1;
		while (tempx < 8 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx++;
				tempy--;
			}
			else if (state[tempx][tempy].getpiece().getcolor() == this
					.getcolor())
				break;
			else {
				if (state[tempx][tempy].getpiece() instanceof Bishop
						|| state[tempx][tempy].getpiece() instanceof Queen)
					return true;
				else
					break;
			}
		}

		return false;
	}

	private boolean isInDangerFromDiagonal2(Cell[][] state) {
		int tempx = x - 1;
		int tempy = y + 1;
		while (tempx >= 0 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx--;
				tempy++;
			}
			else if (state[tempx][tempy].getpiece().getcolor() == this
					.getcolor())
				break;
			else {
				if (state[tempx][tempy].getpiece() instanceof Bishop
						|| state[tempx][tempy].getpiece() instanceof Queen)
					return true;
				else
					break;
			}
		}

		return false;
	}

	private boolean isInDangerFromDiagonal3(Cell[][] state) {
		int tempx = x - 1;
		int tempy = y - 1;
		while (tempx >= 0 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx--;
				tempy--;
			}
			else if (state[tempx][tempy].getpiece().getcolor() == this
					.getcolor())
				break;
			else {
				if (state[tempx][tempy].getpiece() instanceof Bishop
						|| state[tempx][tempy].getpiece() instanceof Queen)
					return true;
				else
					break;
			}
		}

		return false;
	}

	private boolean isInDangerFromDiagonal4(Cell[][] state) {
		int tempx = x + 1;
		int tempy = y + 1;
		while (tempx < 8 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx++;
				tempy++;
			}
			else if (state[tempx][tempy].getpiece().getcolor() == this
					.getcolor())
				break;
			else {
				if (state[tempx][tempy].getpiece() instanceof Bishop
						|| state[tempx][tempy].getpiece() instanceof Queen)
					return true;
				else
					break;
			}
		}

		return false;
	}
}