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

	private JPanel whiteDetails = new JPanel(new GridLayout(3, 3));
	private JPanel blackDetails = new JPanel(new GridLayout(3, 3));
	private JPanel whiteComboPanel = new JPanel();
	private JPanel blackComboPanel = new JPanel();
	private JPanel  whitePlayerPanel, blackPlayerPanel, startOrCountDownPanel,
			sliderOrActivePlayerName, timerAndActivePlayerDetailsPannel;
	private JLabel countdownTimerLabel, mov;
	private JLabel CHNC;
	private Time timer;
	private JComboBox<String> whiteCombo, blackCombo;
	
	private JSlider timeSlider;
	private Button startButton, whiteSelectButton, blackSelectButton, whiteNewPlayerButton, blackNewPlayerButton;

	private ArrayList<String> whitePlayerNames = new ArrayList<String>();
	private ArrayList<String> blackPlayerNames = new ArrayList<String>();
	String move;
	
	private Player White=null,Black=null;
	private List<ActionListener> gameStartListeners = new ArrayList<ActionListener>(); 

	public int timeRemaining = 60;
	
	private ArrayList<Player> wplayer, bplayer;
	
	private boolean selected;
	
	public StatComposite() {
		createControl();
	}
	
	private void createControl() {
		timeRemaining = 60;
		move = "White";
		
		timeSlider = new JSlider();
		whiteDetails = new JPanel(new GridLayout(3, 3));
		blackDetails = new JPanel(new GridLayout(3, 3));
		blackComboPanel = new JPanel();
		whiteComboPanel = new JPanel();
		
		// Time Slider Details
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
		whiteSelectButton.addActionListener(new SelectHandler(0));
		blackSelectButton.addActionListener(new SelectHandler(1));
		whiteNewPlayerButton = new Button("New Player");
		blackNewPlayerButton = new Button("New Player");
		whiteNewPlayerButton.addActionListener(new NewPlayerHandler(0));
		blackNewPlayerButton.addActionListener(new NewPlayerHandler(1));
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
		startButton.addActionListener(new START());
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
		sliderOrActivePlayerName.remove(CHNC);
		if (move == "White")
			move = "Black";
		else
			move = "White";
		CHNC.setText(move);
		sliderOrActivePlayerName.add(CHNC);
	}
	
	public void restartTimer() {
		timer.reset();
		timer.start();
	}
	
	private void gatherProfiles() {
		wplayer = Player.fetch_players();
		Iterator<Player> witr = wplayer.iterator();
		while (witr.hasNext())
			whitePlayerNames.add(witr.next().name());
		

		bplayer = Player.fetch_players();
		Iterator<Player> bitr = bplayer.iterator();
		while (bitr.hasNext())
			blackPlayerNames.add(bitr.next().name());
	}
	
	public void addGameStartListener(ActionListener actionListener) {
		gameStartListeners.add(actionListener);
	}
	
	class START implements ActionListener {

		@SuppressWarnings("deprecation")
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub

			if (White == null || Black == null) {
				JOptionPane.showMessageDialog(StatComposite.this,
						"Fill in the details");
				return;
			}
			timeRemaining = timeSlider.getValue() * 60;
			
			White.updateGamesPlayed();
			White.Update_Player();
			Black.updateGamesPlayed();
			Black.Update_Player();
			whiteNewPlayerButton.disable();
			blackNewPlayerButton.disable();
			whiteSelectButton.disable();
			blackSelectButton.disable();

			sliderOrActivePlayerName.remove(timeSlider);
			mov = new JLabel("Move:");
			mov.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
			mov.setForeground(Color.red);
			sliderOrActivePlayerName.add(mov);
			CHNC = new JLabel(move);
			CHNC.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
			CHNC.setForeground(Color.blue);
			sliderOrActivePlayerName.add(CHNC);
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
	
	class SelectHandler implements ActionListener {
		private int color;

		SelectHandler(int i) {
			color = i;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Player tempPlayer = null;
			String n = (color == 0) ? "White" : "Black";
			JComboBox<String> jc = (color == 0) ? whiteCombo : blackCombo;
			JComboBox<String> ojc = (color == 0) ? blackCombo : whiteCombo;
			ArrayList<Player> pl = (color == 0) ? wplayer : bplayer;
			// ArrayList<Player> otherPlayer=(color==0)?bplayer:wplayer;
			ArrayList<Player> opl = Player.fetch_players();
			if (opl.isEmpty())
				return;
			JPanel det = (color == 0) ? whiteDetails : blackDetails;
			JPanel PL = (color == 0) ? whitePlayerPanel : blackPlayerPanel;
			if (selected == true)
				det.removeAll();
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
			if (color == 0)
				White = tempPlayer;
			else
				Black = tempPlayer;
			bplayer = opl;
			ojc.removeAllItems();
			for (Player s : opl)
				ojc.addItem(s.name());
			det.add(new JLabel(" " + tempPlayer.name()));
			det.add(new JLabel(" " + tempPlayer.gamesplayed()));
			det.add(new JLabel(" " + tempPlayer.gameswon()));

			PL.revalidate();
			PL.repaint();
			PL.add(det);
			selected = true;
		}

	}

	public String endGame(int chance) {
		String winner;
		startOrCountDownPanel.setEnabled(false);
		timer.countdownTimer.stop();
//		if (previous != null)
//			previous.removePiece();
		if (chance == 0) {
			White.updateGamesWon();
			White.Update_Player();
			winner = White.name();
		} else {
			Black.updateGamesWon();
			Black.Update_Player();
			winner = Black.name();
		}
		
		return winner;
	}
	
	class NewPlayerHandler implements ActionListener {
		private int color;

		NewPlayerHandler(int i) {
			color = i;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String n = (color == 0) ? "White" : "Black";
			JPanel j = (color == 0) ? whitePlayerPanel : blackPlayerPanel;
			ArrayList<Player> N = Player.fetch_players();
			Iterator<Player> it = N.iterator();
			JPanel det = (color == 0) ? whiteDetails : blackDetails;
			n = JOptionPane.showInputDialog(j, "Enter your name");

			if (n != null) {

				while (it.hasNext()) {
					if (it.next().name().equals(n)) {
						JOptionPane.showMessageDialog(j, "Player exists");
						return;
					}
				}

				if (n.length() != 0) {
					Player tem = new Player(n);
					tem.Update_Player();
					if (color == 0)
						White = tem;
					else
						Black = tem;
				} else
					return;
			} else
				return;
			det.removeAll();
			det.add(new JLabel(" " + n));
			det.add(new JLabel(" 0"));
			det.add(new JLabel(" 0"));
			j.revalidate();
			j.repaint();
			j.add(det);
			selected = true;
		}
	}
	
}
