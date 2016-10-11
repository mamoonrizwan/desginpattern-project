package pieces;

import java.awt.Color;
import java.util.ArrayList;

import chess.Cell;


/**
 * This is the Bishop Class.
 * The Move Function defines the basic rules for movement of Bishop on a chess board
 * 
 *
 */
public class Bishop extends Piece{
	
	//Constructor
	public Bishop(String i, Color c)
	{
		setId(i);
		setPath(c.equals(Color.WHITE) ? "White_Bishop.png" : "Black_Bishop.png");
		setColor(c);
	}
	
	//move function defined. It returns a list of all the possible destinations of a Bishop
	//The basic principle of Bishop Movement on chess board has been implemented
	public ArrayList<Cell> possibleMovesCells(Cell state[][],int x,int y)
	{
		//Bishop can Move diagonally in all 4 direction (NW,NE,SW,SE)
		//This function defines that logic
		possibleMoves.clear();
		int tempx=x+1,tempy=y-1;
		while(tempx<8&&tempy>=0)
		{
			if(state[tempx][tempy].getpiece()==null)
			{
				possibleMoves.add(state[tempx][tempy]);
			}
			else if(state[tempx][tempy].getpiece().getcolor()==this.getcolor())
				break;
			else
			{
				possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy--;
		}
		tempx=x-1;tempy=y+1;
		while(tempx>=0&&tempy<8)
		{
			if(state[tempx][tempy].getpiece()==null)
				possibleMoves.add(state[tempx][tempy]);
			else if(state[tempx][tempy].getpiece().getcolor()==this.getcolor())
				break;
			else
			{
				possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy++;
		}
		tempx=x-1;tempy=y-1;
		while(tempx>=0&&tempy>=0)
		{
			if(state[tempx][tempy].getpiece()==null)
				possibleMoves.add(state[tempx][tempy]);
			else if(state[tempx][tempy].getpiece().getcolor()==this.getcolor())
				break;
			else
			{
				possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx--;
			tempy--;
		}
		tempx=x+1;tempy=y+1;
		while(tempx<8&&tempy<8)
		{
			if(state[tempx][tempy].getpiece()==null)
				possibleMoves.add(state[tempx][tempy]);
			else if(state[tempx][tempy].getpiece().getcolor()==this.getcolor())
				break;
			else
			{
				possibleMoves.add(state[tempx][tempy]);
				break;
			}
			tempx++;
			tempy++;
		}
		return possibleMoves;
	}
}