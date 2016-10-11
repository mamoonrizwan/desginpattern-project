package chess;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.border.TitledBorder;

public class StatComposite extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final String LABEL_TEXT_WHITE = "White";
	private static final String LABEL_TEXT_BLACK = "Black";

	private JPanel whiteDetails;
	private JPanel blackDetails;
	private JPanel whiteComboPanel;
	private JPanel blackComboPanel;
	private JPanel whitePlayerPanel, blackPlayerPanel, startOrCountDownPanel,
			sliderOrActivePlayerName, timerAndActivePlayerDetailsPannel;
	private JLabel countdownTimerLabel, moveLabel;
	private JLabel chanceLabel;
	private Time timer;
	private JComboBox<String> whiteCombo, blackCombo;

	private JSlider timeSlider;
	private Button startButton, whiteSelectButton, blackSelectButton,
			whiteNewPlayerButton, blackNewPlayerButton;

	private ArrayList<String> whitePlayerNames = new ArrayList<String>();
	private ArrayList<String> blackPlayerNames = new ArrayList<String>();
	String activePlayer;

	private Player whitePlayer = null, blackPlayer = null;
	private List<ActionListener> gameStartListeners = new ArrayList<ActionListener>();

	public int timeRemaining = 60;

	private ArrayList<Player> wplayer, bplayer;

	private boolean selected;

	public StatComposite() {
		createControl();
	}

	private void createControl() {
		timeRemaining = 60;
		activePlayer = LABEL_TEXT_WHITE;
		
		whiteDetails = new JPanel(new GridLayout(3, 3));
		blackDetails = new JPanel(new GridLayout(3, 3));
		blackComboPanel = new JPanel();
		whiteComboPanel = new JPanel();
		
		// Time Slider Details
		timeSlider = new JSlider();
		timeSlider.setMinimum(1);
		timeSlider.setMaximum(15);
		timeSlider.setValue(1);
		timeSlider.setMajorTickSpacing(2);
		timeSlider.setPaintLabels(true);
		timeSlider.setPaintTicks(true);
		

		this.setLayout(new GridLayout(3, 3));
		this.setBorder(BorderFactory.createTitledBorder(null,
				"Statistics", TitledBorder.TOP, TitledBorder.CENTER, new Font(
						"Lucida Calligraphy", Font.PLAIN, 20), Color.ORANGE));

		// Defining the Player Box in Control Panel
		whitePlayerPanel = new JPanel();
		whitePlayerPanel.setBorder(BorderFactory.createTitledBorder(null,
				"White Player", TitledBorder.TOP, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 18), Color.RED));
		whitePlayerPanel.setLayout(new BorderLayout());

		blackPlayerPanel = new JPanel();
		blackPlayerPanel.setBorder(BorderFactory.createTitledBorder(null,
				"Black Player", TitledBorder.TOP, TitledBorder.CENTER,
				new Font("times new roman", Font.BOLD, 18), Color.BLUE));
		blackPlayerPanel.setLayout(new BorderLayout());
		
		JPanel whitestats = new JPanel(new GridLayout(3, 3));
		JPanel blackstats = new JPanel(new GridLayout(3, 3));
		gatherProfiles();
		whiteCombo = new JComboBox<String>(whitePlayerNames.toArray(new String[0]));
		blackCombo = new JComboBox<String>(blackPlayerNames.toArray(new String[0]));
		whiteComboPanel.setLayout(new FlowLayout());
		blackComboPanel.setLayout(new FlowLayout());
		whiteSelectButton = new Button("Select");
		blackSelectButton = new Button("Select");
		whiteSelectButton.addActionListener(new SelectPlayerHandler(Color.WHITE));
		blackSelectButton.addActionListener(new SelectPlayerHandler(Color.BLACK));
		whiteNewPlayerButton = new Button("New Player");
		blackNewPlayerButton = new Button("New Player");
		whiteNewPlayerButton.addActionListener(new NewPlayerHandler(Color.WHITE));
		blackNewPlayerButton.addActionListener(new NewPlayerHandler(Color.BLACK));
		whiteComboPanel.add(new JScrollPane(whiteCombo));
		whiteComboPanel.add(whiteSelectButton);
		whiteComboPanel.add(whiteNewPlayerButton);
		blackComboPanel.add(new JScrollPane(blackCombo));
		blackComboPanel.add(blackSelectButton);
		blackComboPanel.add(blackNewPlayerButton);
		whitePlayerPanel.add(whiteComboPanel, BorderLayout.NORTH);
		blackPlayerPanel.add(blackComboPanel, BorderLayout.NORTH);
		whitestats.add(new JLabel("Name   :"));
		whitestats.add(new JLabel("Played :"));
		whitestats.add(new JLabel("Won    :"));
		blackstats.add(new JLabel("Name   :"));
		blackstats.add(new JLabel("Played :"));
		blackstats.add(new JLabel("Won    :"));
		whitePlayerPanel.add(whitestats, BorderLayout.WEST);
		blackPlayerPanel.add(blackstats, BorderLayout.WEST);
		this.add(whitePlayerPanel);
		this.add(blackPlayerPanel);

		sliderOrActivePlayerName = new JPanel(new FlowLayout());
		sliderOrActivePlayerName.add(timeSlider);
		JLabel setTime = new JLabel("Set Timer(in mins):");
		startButton = new Button("Start");
		startButton.setBackground(Color.black);
		startButton.setForeground(Color.white);
		startButton.addActionListener(new StartActionValidator());
		startButton.setPreferredSize(new Dimension(120, 40));
		setTime.setFont(new Font("Arial", Font.BOLD, 16));
		countdownTimerLabel = new JLabel("Time Starts now", JLabel.CENTER);
		countdownTimerLabel.setFont(new Font("SERIF", Font.BOLD, 30));
		startOrCountDownPanel = new JPanel(new FlowLayout());
		timerAndActivePlayerDetailsPannel = new JPanel(new GridLayout(3, 3));
		timerAndActivePlayerDetailsPannel.add(setTime);
		timerAndActivePlayerDetailsPannel.add(sliderOrActivePlayerName);
		startOrCountDownPanel.add(startButton);
		timerAndActivePlayerDetailsPannel.add(startOrCountDownPanel);
		this.add(timerAndActivePlayerDetailsPannel);

		this.setMinimumSize(new Dimension(285, 700));
				
	}

	public void updateChance() {
		timer.reset();
		timer.start();
		sliderOrActivePlayerName.remove(chanceLabel);
		if (activePlayer == LABEL_TEXT_WHITE)
			activePlayer = LABEL_TEXT_BLACK;
		else
			activePlayer = LABEL_TEXT_WHITE;
		chanceLabel.setText(activePlayer);
		sliderOrActivePlayerName.add(chanceLabel);
	}

	private void gatherProfiles() {
		wplayer = Player.fetchPlayers();
		Iterator<Player> witr = wplayer.iterator();
		while (witr.hasNext())
			whitePlayerNames.add(witr.next().name());

		bplayer = Player.fetchPlayers();
		Iterator<Player> bitr = bplayer.iterator();
		while (bitr.hasNext())
			blackPlayerNames.add(bitr.next().name());
	}

	public void addGameStartListener(ActionListener actionListener) {
		gameStartListeners.add(actionListener);
	}

	class StartActionValidator implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (whitePlayer == null || blackPlayer == null) {
				JOptionPane.showMessageDialog(StatComposite.this,
						"Fill in the details");
				return;
			}

			timeRemaining = timeSlider.getValue() * 60;

			whitePlayer.updateGamesPlayed();
			whitePlayer.updatePlayer();

			blackPlayer.updateGamesPlayed();
			blackPlayer.updatePlayer();

			whiteNewPlayerButton.setEnabled(false);
			blackNewPlayerButton.setEnabled(false);
			whiteSelectButton.setEnabled(false);
			blackSelectButton.setEnabled(false);

			sliderOrActivePlayerName.remove(timeSlider);
			moveLabel = new JLabel("Move:");
			moveLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
			moveLabel.setForeground(Color.red);
			sliderOrActivePlayerName.add(moveLabel);
			chanceLabel = new JLabel(activePlayer);
			chanceLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			chanceLabel.setForeground(Color.blue);
			sliderOrActivePlayerName.add(chanceLabel);
			startOrCountDownPanel.remove(startButton);
			startOrCountDownPanel.add(countdownTimerLabel);
			timer = new Time(countdownTimerLabel, timeRemaining);
			timer.start();

			for (ActionListener al : gameStartListeners) {
				al.actionPerformed(arg0);
			}
		}
	}

	public void addTimerExpireListener(ActionListener al) {
		timer.addTimerExpireListener(al);
	}

	class SelectPlayerHandler implements ActionListener {
		private Color color;

		SelectPlayerHandler(Color color) {
			this.color = color;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Player tempPlayer = null;
			String n;
			JComboBox<String> jc = (color == Color.WHITE) ? whiteCombo
					: blackCombo;
			JComboBox<String> ojc = (color == Color.WHITE) ? blackCombo
					: whiteCombo;
			ArrayList<Player> pl = (color == Color.WHITE) ? wplayer : bplayer;
			ArrayList<Player> opl = Player.fetchPlayers();
			if (opl.isEmpty())
				return;
			JPanel detailsPanel = (color == Color.WHITE) ? whiteDetails : blackDetails;
			JPanel palyerPanel = (color == Color.WHITE) ? whitePlayerPanel : blackPlayerPanel;
			if (selected)
				detailsPanel.removeAll();
			
			n = (String) jc.getSelectedItem();
			Iterator<Player> it = pl.iterator();
			Iterator<Player> oit = opl.iterator();
			while (it.hasNext()) {
				Player p = it.next();
				if (p.name().equals(n)) {
					tempPlayer = p;
					break;
				}
			}
			while (oit.hasNext()) {
				Player p = oit.next();
				if (p.name().equals(n)) {
					opl.remove(p);
					break;
				}
			}

			if (tempPlayer == null)
				return;
			if (color == Color.WHITE)
				whitePlayer = tempPlayer;
			else
				blackPlayer = tempPlayer;
			bplayer = opl;
			ojc.removeAllItems();
			for (Player s : opl)
				ojc.addItem(s.name());
			detailsPanel.add(new JLabel(" " + tempPlayer.name()));
			detailsPanel.add(new JLabel(" " + tempPlayer.gamesPlayed()));
			detailsPanel.add(new JLabel(" " + tempPlayer.gamesWon()));

			palyerPanel.revalidate();
			palyerPanel.repaint();
			palyerPanel.add(detailsPanel);
			selected = true;
		}

	}

	public String endGame(Color chance) {
		String winner;
		startOrCountDownPanel.setEnabled(false);
		timer.countdownTimer.stop();
		if (chance.equals(Color.WHITE)) {
			whitePlayer.updateGamesWon();
			whitePlayer.updatePlayer();
			winner = whitePlayer.name();
		}
		else {
			blackPlayer.updateGamesWon();
			blackPlayer.updatePlayer();
			winner = blackPlayer.name();
		}

		return winner;
	}

	class NewPlayerHandler implements ActionListener {
		private Color color;

		NewPlayerHandler(Color color) {
			this.color = color;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String name;
			JPanel j = (color == Color.WHITE) ? whitePlayerPanel
					: blackPlayerPanel;
			ArrayList<Player> players = Player.fetchPlayers();
			Iterator<Player> it = players.iterator();
			JPanel det = (color == Color.WHITE) ? whiteDetails : blackDetails;
			name = JOptionPane.showInputDialog(j, "Enter your name");

			if (name != null) {

				while (it.hasNext()) {
					if (it.next().name().equals(name)) {
						JOptionPane.showMessageDialog(j, "Player exists");
						return;
					}
				}

				if (name.length() != 0) {
					Player tem = new Player(name);
					tem.updatePlayer();
					if (color == Color.WHITE)
						whitePlayer = tem;
					else
						blackPlayer = tem;
				}
				else
					return;
			}
			else
				return;

			det.removeAll();
			det.add(new JLabel(" " + name));
			det.add(new JLabel(" 0"));
			det.add(new JLabel(" 0"));
			j.revalidate();
			j.repaint();
			j.add(det);
			selected = true;
		}
	}
}
