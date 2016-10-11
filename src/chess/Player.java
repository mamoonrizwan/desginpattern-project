package chess;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;


/**
 * This is the Player Class
 * It provides the functionality of keeping track of all the users
 * Objects of this class is updated and written in the Game's Data Files after every Game
 *
 */
public class Player implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static final String DATABASE_PATH = System.getProperty("user.dir")+ File.separator + "chessgamedata.dat";
	private static final String TEMP_DATABASE_PATH = System.getProperty("user.dir") + File.separator + "tempfile.dat";
	
	private String name;
	private Integer gamesPlayed;
	private Integer gamesWon;
	
	// Constructor
	public Player(String name) {
		this.name = name.trim();
		gamesPlayed = new Integer(0);
		gamesWon = new Integer(0);
	}
	
	// Name Getter
	public String name() {
		return name;
	}

	// Returns the number of games played
	public Integer gamesPlayed() {
		return gamesPlayed;
	}

	// Returns the number of games won
	public Integer gamesWon() {
		return gamesWon;
	}
	
	//Calculates the win percentage of the player
	public Integer winPercentage() {
		return new Integer((gamesWon * 100) / gamesPlayed);
	}

	// Increments the number of games played
	public void updateGamesPlayed() {
		gamesPlayed++;
	}

	// Increments the number of games won
	public void updateGamesWon() {
		gamesWon++;
	}
	
	
	public static ArrayList<Player> fetchPlayers()         //Function to fetch the list of the players
	{
		Player tempPlayer;
		ObjectInputStream input = null;
		ArrayList<Player> players = new ArrayList<Player>();
		try {
			File infile = new File(DATABASE_PATH);
			input = new ObjectInputStream(new FileInputStream(infile));
			while (true) {
				tempPlayer = (Player) input.readObject();
				players.add(tempPlayer);
			}
		}
		catch (FileNotFoundException e) {
			players.clear();
			return players;
		}
		catch (EOFException e) {
		}
		catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to read the required Game files !!");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(null,
							"Game Data File Corrupted !! Click Ok to Continue Builing New File");
		}
		catch (Exception e1) {
			e1.printStackTrace();
		}
		finally {
			if (input != null)
				try {
					input.close();
				}
				catch (IOException e) {
				}
		}
		
		return players;
	}
	
	public void updatePlayer()            //Function to update the statistics of a player
	{
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		Player tempPlayer;
		File inputfile = null;
		File outputfile = null;
		try {
			inputfile = new File(DATABASE_PATH);
			outputfile = new File(TEMP_DATABASE_PATH);
		}
		catch (SecurityException e) {
			JOptionPane.showMessageDialog(null,
					"Read-Write Permission Denied !! Program Cannot Start");
			System.exit(0);
		}
		
		boolean found;
		try {
			if (!outputfile.exists())
				outputfile.createNewFile();
			
			if (!inputfile.exists()) {
				output = new ObjectOutputStream(new java.io.FileOutputStream(
						outputfile, true));
				output.writeObject(this);
			}
			else {
				input = new ObjectInputStream(new FileInputStream(inputfile));
				output = new ObjectOutputStream(new FileOutputStream(outputfile));
				found = false;
				try {
					while (true) {
						tempPlayer = (Player) input.readObject();
						if (tempPlayer.name().equals(name())) {
							output.writeObject(this);
							found = true;
						}
						else
							output.writeObject(tempPlayer);
					}
				}
				catch (EOFException e) {
				}
				
				if (!found)
					output.writeObject(this);
			}
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(null,
							"Unable to read/write the required Game files !! Press ok to continue");
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane
					.showMessageDialog(null,
							"Game Data File Corrupted !! Click Ok to Continue Builing New File");
		}
		catch (Exception e) {

		}
		finally {
			if (input != null) {
				try {
					input.close();
				}
				catch (IOException e) {
				}
			}

			if (output != null) {
				try {
					output.close();
				}
				catch (IOException e) {
				}
			}

			inputfile.delete();

			File newf = new File(DATABASE_PATH);
			if (outputfile.renameTo(newf) == false)
				System.out.println("File Renameing Unsuccessful");
		}
	}
}
