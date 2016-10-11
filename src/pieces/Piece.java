package pieces;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import chess.Cell;

/**
 * This is the Piece Class. It is an abstract class from which all the actual
 * pieces are inherited. It defines all the function common to all the pieces
 * The move() function an abstract function that has to be overridden in all the
 * inherited class It implements Cloneable interface as a copy of the piece is
 * required very often
 */
public abstract class Piece implements Cloneable {

	// Member Variables
	private Color color;
	private String id = null;
	private String path;
	protected List<Cell> possibleMoves = new ArrayList<Cell>(); // Protected
																// (access from
																// child
																// classes)

	public abstract List<Cell> possibleMovesCells(Cell pos[][], int x, int y); // Abstract
																				// Function.
																				// Must
																				// be
																				// overridden

	// Id Setter
	public void setId(String id) {
		this.id = id;
	}

	// Path Setter
	public void setImagePath(String path) {
		this.path = path;
	}

	// Color Setter
	public void setColor(Color c) {
		this.color = c;
	}

	// Path getter
	public String getImagePath() {
		return path;
	}

	// Id getter
	public String getId() {
		return id;
	}

	// Color Getter
	public Color getcolor() {
		return this.color;
	}

	// Function to return the a "shallow" copy of the object. The copy has exact
	// same variable value but different reference
	public Piece getcopy() throws CloneNotSupportedException {
		return (Piece) this.clone();
	}
}