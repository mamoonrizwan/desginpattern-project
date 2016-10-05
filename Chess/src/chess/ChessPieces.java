package chess;

import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ChessPieces {
	private Rook whiterook1;
	private Rook whiterook2;
	private Rook blackrook1;
	private Rook BlackRook2;
	private Knight WhiteKnight1;
	private Knight WhiteKnight2;
	private Knight BlackKnight1;
	private Knight BlackKnight2;
	private Bishop WhiteBishop1;
	private Bishop WhiteBishop2;
	private Bishop BlackBishop1;
	private Bishop BlackBishop2;
	private Pawn[] WhitePawn;
	private Pawn[] BlackPawn;
	private Queen WhiteQueen;
	private Queen BlackQueen;
	private King WhiteKing;
	private King BlackKing;

	public ChessPieces() {
	}

	public Rook getWhiterook1() {
		return whiterook1;
	}

	public void setWhiterook1(Rook whiterook1) {
		this.whiterook1 = whiterook1;
	}

	public Rook getWhiterook2() {
		return whiterook2;
	}

	public void setWhiterook2(Rook whiterook2) {
		this.whiterook2 = whiterook2;
	}

	public Rook getBlackrook1() {
		return blackrook1;
	}

	public void setBlackrook1(Rook blackrook1) {
		this.blackrook1 = blackrook1;
	}

	public Rook getBlackRook2() {
		return BlackRook2;
	}

	public void setBlackRook2(Rook blackRook2) {
		BlackRook2 = blackRook2;
	}

	public Knight getWhiteKnight1() {
		return WhiteKnight1;
	}

	public void setWhiteKnight1(Knight whiteKnight1) {
		WhiteKnight1 = whiteKnight1;
	}

	public Knight getWhiteKnight2() {
		return WhiteKnight2;
	}

	public void setWhiteKnight2(Knight whiteKnight2) {
		WhiteKnight2 = whiteKnight2;
	}

	public Knight getBlackKnight1() {
		return BlackKnight1;
	}

	public void setBlackKnight1(Knight blackKnight1) {
		BlackKnight1 = blackKnight1;
	}

	public Knight getBlackKnight2() {
		return BlackKnight2;
	}

	public void setBlackKnight2(Knight blackKnight2) {
		BlackKnight2 = blackKnight2;
	}

	public Bishop getWhiteBishop1() {
		return WhiteBishop1;
	}

	public void setWhiteBishop1(Bishop whiteBishop1) {
		WhiteBishop1 = whiteBishop1;
	}

	public Bishop getWhiteBishop2() {
		return WhiteBishop2;
	}

	public void setWhiteBishop2(Bishop whiteBishop2) {
		WhiteBishop2 = whiteBishop2;
	}

	public Bishop getBlackBishop1() {
		return BlackBishop1;
	}

	public void setBlackBishop1(Bishop blackBishop1) {
		BlackBishop1 = blackBishop1;
	}

	public Bishop getBlackBishop2() {
		return BlackBishop2;
	}

	public void setBlackBishop2(Bishop blackBishop2) {
		BlackBishop2 = blackBishop2;
	}

	public Pawn[] getWhitePawn() {
		return WhitePawn;
	}

	public void setWhitePawn(Pawn[] whitePawn) {
		WhitePawn = whitePawn;
	}

	public Pawn[] getBlackPawn() {
		return BlackPawn;
	}

	public void setBlackPawn(Pawn[] blackPawn) {
		BlackPawn = blackPawn;
	}

	public Queen getWhiteQueen() {
		return WhiteQueen;
	}

	public void setWhiteQueen(Queen whiteQueen) {
		WhiteQueen = whiteQueen;
	}

	public Queen getBlackQueen() {
		return BlackQueen;
	}

	public void setBlackQueen(Queen blackQueen) {
		BlackQueen = blackQueen;
	}

	public King getWhiteKing() {
		return WhiteKing;
	}

	public void setWhiteKing(King whiteKing) {
		WhiteKing = whiteKing;
	}

	public King getBlackKing() {
		return BlackKing;
	}

	public void setBlackKing(King blackKing) {
		BlackKing = blackKing;
	}
}