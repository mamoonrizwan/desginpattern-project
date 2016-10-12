package pieces;

import chess.Cell;

public class QueenMoves {

	void addDiagonal4Moves(Queen queen, Cell[][] state, int x, int y) {
		int tempx = x + 1;
		int tempy = y + 1;
		while (tempx < 8 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null)
				queen.possibleMoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == queen
					.getcolor())
				break;
			else {
				queen.possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy++;
		}
	}

	void addDiagonal3Moves(Queen queen, Cell[][] state, int x, int y) {
		int tempx = x - 1;
		int tempy = y - 1;
		while (tempx >= 0 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null)
				queen.possibleMoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == queen
					.getcolor())
				break;
			else {
				queen.possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy--;
		}
	}

	void addDiagonal2Moves(Queen queen, Cell[][] state, int x, int y) {
		int tempx = x - 1;
		int tempy = y + 1;
		while (tempx >= 0 && tempy < 8) {
			if (state[tempx][tempy].getpiece() == null)
				queen.possibleMoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == queen
					.getcolor())
				break;
			else {
				queen.possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy++;
		}
	}

	void addDiagonal1Moves(Queen queen, Cell[][] state, int x, int y) {
		int tempx = x + 1;
		int tempy = y - 1;
		while (tempx < 8 && tempy >= 0) {
			if (state[tempx][tempy].getpiece() == null)
				queen.possibleMoves.add(state[tempx][tempy]);
			else if (state[tempx][tempy].getpiece().getcolor() == queen
					.getcolor())
				break;
			else {
				queen.possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy--;
		}
	}

	void addUpwardMoves(Queen queen, Cell[][] state, int x, int y) {
		int tempy = y + 1;
		while (tempy < 8) {
			if (state[x][tempy].getpiece() == null)
				queen.possibleMoves.add(state[x][tempy]);
			else if (state[x][tempy].getpiece().getcolor() == queen.getcolor())
				break;
			else {
				queen.possibleMoves.add(state[x][tempy]);
				break;
			}
			tempy++;
		}
	}

	void addDownwardMoves(Queen queen, Cell[][] state, int x, int y) {
		int tempy = y - 1;
		while (tempy >= 0) {
			if (state[x][tempy].getpiece() == null)
				queen.possibleMoves.add(state[x][tempy]);
			else if (state[x][tempy].getpiece().getcolor() == queen.getcolor())
				break;
			else {
				queen.possibleMoves.add(state[x][tempy]);
				break;
			}
			tempy--;
		}
	}

	void addRightMoves(Queen queen, Cell[][] state, int x, int y) {
		int tempx = x + 1;
		while (tempx < 8) {
			if (state[tempx][y].getpiece() == null)
				queen.possibleMoves.add(state[tempx][y]);
			else if (state[tempx][y].getpiece().getcolor() == queen.getcolor())
				break;
			else {
				queen.possibleMoves.add(state[tempx][y]);
				break;
			}
			tempx++;
		}
	}

	void addLeftMoves(Queen queen, Cell[][] state, int x, int y) {
		int tempx = x - 1;
		while (tempx >= 0) {
			if (state[tempx][y].getpiece() == null)
				queen.possibleMoves.add(state[tempx][y]);
			else if (state[tempx][y].getpiece().getcolor() == queen.getcolor())
				break;
			else {
				queen.possibleMoves.add(state[tempx][y]);
				break;
			}
			tempx--;
		}
	}
	
	
	
	
	

}
