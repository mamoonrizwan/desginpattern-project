package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * This is the Time Class. It contains all the required variables and functions
 * related to the timer on the Main GUI It uses a Timer Class
 *
 */

public class Time {
	private JLabel label;
	Timer countdownTimer;
	private final int timeLimit;
	private int Timerem;
	private List<ActionListener> timerExpireListeners = new ArrayList<ActionListener>();
	

	public Time(JLabel passedLabel, int timeLimit) {
		countdownTimer = new Timer(1000, new CountdownTimerListener());
		this.label = passedLabel;
		this.timeLimit = timeLimit;
		Timerem = timeLimit;
	}

	// A function that starts the timer
	public void start() {
		countdownTimer.start();
	}

	// A function that resets the timer
	public void reset() {
		Timerem = timeLimit;
	}

	public void addTimerExpireListener(ActionListener al) {
		timerExpireListeners.add(al);
	}

	// A function that is called after every second. It updates the timer and
	// takes other necessary actions
	class CountdownTimerListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int min, sec;
			if (Timerem > 0) {
				min = Timerem / 60;
				sec = Timerem % 60;
				label.setText(String.valueOf(min)
						+ ":"
						+ (sec >= 10 ? String.valueOf(sec) : "0"
								+ String.valueOf(sec)));
				Timerem--;
			} else {
				label.setText("Time's up!");
				reset();
				start();
				for (ActionListener al : timerExpireListeners) {
					al.actionPerformed(e);
				}
			}
		}
	}
}