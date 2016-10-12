package pieces;

import java.awt.Color;

import chess.Cell;

public class KingDanger {

	boolean isInDangerFromOpponentPawns(King king, Cell[][] state) {
		if (king.getcolor().equals(Color.WHITE)) {
			if (king.kd.isInDangerFromLeftPawn(king, state))
				return true;
			if (king.kd.isInDangerFromTopPawn(king, state))
				return true;
		}
		else {
			if (king.kd.isInDangerFromBottomPawn(king, state))
				return true;
			if (king.kd.isInDangerFromRightPawn(king, state))
				return true;
		}
		return false;
	}

	boolean isInDangerFromRightPawn(King king, Cell[][] state) {
		return king.x < 7
				&& king.y < 7
				&& state[king.x + 1][king.y + 1].getpiece() != null
				&& state[king.x + 1][king.y + 1].getpiece().getcolor()
						.equals(Color.WHITE)
				&& (state[king.x + 1][king.y + 1].getpiece() instanceof Pawn);
	}

	boolean isInDangerFromBottomPawn(King king, Cell[][] state) {
		return king.x < 7
				&& king.y > 0
				&& state[king.x + 1][king.y - 1].getpiece() != null
				&& state[king.x + 1][king.y - 1].getpiece().getcolor()
						.equals(Color.WHITE)
				&& (state[king.x + 1][king.y - 1].getpiece() instanceof Pawn);
	}

	boolean isInDangerFromTopPawn(King king, Cell[][] state) {
		return king.x > 0
				&& king.y < 7
				&& state[king.x - 1][king.y + 1].getpiece() != null
				&& state[king.x - 1][king.y + 1].getpiece().getcolor()
						.equals(Color.BLACK)
				&& (state[king.x - 1][king.y + 1].getpiece() instanceof Pawn);
	}

	boolean isInDangerFromLeftPawn(King king, Cell[][] state) {
		return king.x > 0
				&& king.y > 0
				&& state[king.x - 1][king.y - 1].getpiece() != null
				&& state[king.x - 1][king.y - 1].getpiece().getcolor()
						.equals(Color.BLACK)
				&& (state[king.x - 1][king.y - 1].getpiece() instanceof Pawn);
	}

	boolean isInDangerFromOpponentKnight(King king, Cell[][] state) {
		int posx[] = { king.x + 1, king.x + 1, king.x + 2, king.x + 2, king.x - 1, king.x - 1, king.x - 2, king.x - 2 };
		int posy[] = { king.y - 2, king.y + 2, king.y - 1, king.y + 1, king.y - 2, king.y + 2, king.y - 1, king.y + 1 };
		for (int i = 0; i < 8; i++)
			if ((posx[i] >= 0 && posx[i] < 8 && posy[i] >= 0 && posy[i] < 8))
				if (state[posx[i]][posy[i]].getpiece() != null
						&& state[posx[i]][posy[i]].getpiece().getcolor() != king
								.getcolor()
						&& (state[posx[i]][posy[i]].getpiece() instanceof Knight)) {
					return true;
				}
	
		return false;
	}

	boolean isInDangerFromOpponentKing(King king, Cell[][] state) {
		int pox[] = { king.x + 1, king.x + 1, king.x + 1, king.x, king.x, king.x - 1, king.x - 1, king.x - 1 };
		int poy[] = { king.y - 1, king.y + 1, king.y, king.y + 1, king.y - 1, king.y + 1, king.y - 1, king.y };
		for (int i = 0; i < 8; i++)
			if ((pox[i] >= 0 && pox[i] < 8 && poy[i] >= 0 && poy[i] < 8))
				if (state[pox[i]][poy[i]].getpiece() != null
						&& state[pox[i]][poy[i]].getpiece().getcolor() != king
								.getcolor()
						&& (state[pox[i]][poy[i]].getpiece() instanceof King)) {
					return true;
				}
	
		return false;
	}

	boolean isInDangerFromDown(King king, Cell[][] state) {
		for (int i = king.y - 1; i >= 0; i--) {
			if (state[king.x][i].getpiece() == null)
				continue;
			else if (state[king.x][i].getpiece().getcolor() == king.getcolor())
				break;
			else {
				if ((state[king.x][i].getpiece() instanceof Rook)
						|| (state[king.x][i].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}
	
		return false;
	}

	boolean isInDangerFromUp(King king, Cell[][] state) {
		for (int i = king.y + 1; i < 8; i++) {
			if (state[king.x][i].getpiece() == null)
				continue;
			else if (state[king.x][i].getpiece().getcolor() == king.getcolor())
				break;
			else {
				if ((state[king.x][i].getpiece() instanceof Rook)
						|| (state[king.x][i].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}
		return false;
	}

	boolean isInDangerFromRight(King king, Cell[][] state) {
		for (int i = king.x - 1; i >= 0; i--) {
			if (state[i][king.y].getpiece() == null)
				continue;
			else if (state[i][king.y].getpiece().getcolor() == king.getcolor())
				break;
			else {
				if ((state[i][king.y].getpiece() instanceof Rook)
						|| (state[i][king.y].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}
	
		return false;
	}

	boolean isInDangerFromLeft(King king, Cell[][] state) {
		for (int i = king.x + 1; i < 8; i++) {
			if (state[i][king.y].getpiece() == null)
				continue;
			else if (state[i][king.y].getpiece().getcolor() == king.getcolor())
				break;
			else {
				if ((state[i][king.y].getpiece() instanceof Rook)
						|| (state[i][king.y].getpiece() instanceof Queen))
					return true;
				else
					break;
			}
		}
	
		return false;
	}

	boolean isInDangerFromDiagonal1(King king, Cell[][] state) {
		int tempx = king.x + 1, tempy = king.y - 1;
		while (tempx < 8 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx++;
				tempy--;
			}
			else if (state[tempx][tempy].getpiece().getcolor() == king
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

	boolean isInDangerFromDiagonal2(King king, Cell[][] state) {
		int tempx = king.x - 1;
		int tempy = king.y + 1;
		while (tempx >= 0 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx--;
				tempy++;
			}
			else if (state[tempx][tempy].getpiece().getcolor() == king
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

	boolean isInDangerFromDiagonal3(King king, Cell[][] state) {
		int tempx = king.x - 1;
		int tempy = king.y - 1;
		while (tempx >= 0 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx--;
				tempy--;
			}
			else if (state[tempx][tempy].getpiece().getcolor() == king
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

	boolean isInDangerFromDiagonal4(King king, Cell[][] state) {
		int tempx = king.x + 1;
		int tempy = king.y + 1;
		while (tempx < 8 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null) {
				tempx++;
				tempy++;
			}
			else if (state[tempx][tempy].getpiece().getcolor() == king
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
