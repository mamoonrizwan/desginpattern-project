package pieces;

import java.awt.Color;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

public class PlayerPieces {
	private Rook rook1;
	private Rook rook2;
	private Knight knight1;
	private Knight knight2;
	private Bishop bishop1;
	private Bishop bishop2;
	private Pawn[] pawns = new Pawn[8];
	private Queen queen;
	private King king;
	
	private Color color;

	public PlayerPieces(Color color) {
		if (!color.equals(Color.WHITE) && !color.equals(Color.BLACK))
			throw new IllegalArgumentException();
		
		this.color = color;
		
		String initials = color == Color.WHITE ? "W" : "B";
		rook1 = new Rook(initials + "R01", color);
		rook2 = new Rook(initials + "R02", color);
		knight1 = new Knight(initials + "K01", color);
		knight2 = new Knight(initials + "K02", color);
		bishop1 = new Bishop(initials + "B01", color);
		bishop2 = new Bishop(initials + "B02", color);
		queen = new Queen(initials + "Q", color);
		king = new King(initials + "K", color);
		for (int i = 0; i < 8; i++) {
			pawns[i] = new Pawn(initials + "P0" + (i + 1), color);
		}

		if (color.equals(Color.WHITE)) {
			king.setx(7);
			king.sety(3);
		} else {
			king.setx(0);
			king.sety(3);
		}
	}

	public Rook getRook1() {
		return rook1;
	}

	public Rook getRook2() {
		return rook2;
	}

	public Knight getKnight1() {
		return knight1;
	}

	public Knight getKnight2() {
		return knight2;
	}

	public Bishop getBishop1() {
		return bishop1;
	}

	public Bishop getBishop2() {
		return bishop2;
	}

	public Pawn[] getPawns() {
		return pawns;
	}

	public Queen getQueen() {
		return queen;
	}

	public King getKing() {
		return king;
	}
	
	public Piece getPieceForLocationAtStart(int row, int column) {
		if (color == Color.WHITE)
			return getWhitePieceForLocationAtStart(row, column);
		else
			return getBlackPieceForLocationAtStart(row, column);
	}
	
	private Piece getWhitePieceForLocationAtStart(int row, int column) {
		if (row == 7 && column == 0)
			return rook1;
		else if (row == 7 && column == 7)
			return rook2;
		else if (row == 7 && column == 1)
			return knight1;
		else if (row == 7 && column == 6)
			return knight2;
		else if (row == 7 && column == 2)
			return bishop1;
		else if (row == 7 && column == 5)
			return bishop2;
		else if (row == 7 && column == 3)
			return king;
		else if (row == 7 && column == 4)
			return queen;
		else if (row == 6)
			return pawns[column];
		
		return null;
	}
	
	private Piece getBlackPieceForLocationAtStart(int row, int column) {
		if (row == 0 && column == 0)
			return rook1;
		else if (row == 0 && column == 7)
			return rook2;
		else if (row == 0 && column == 1)
			return knight1;
		else if (row == 0 && column == 6)
			return knight2;
		else if (row == 0 && column == 2)
			return bishop1;
		else if (row == 0 && column == 5)
			return bishop2;
		else if (row == 0 && column == 3)
			return king;
		else if (row == 0 && column == 4)
			return queen;
		else if (row == 1)
			return pawns[column];
		
		return  null;
	}
}