package chess;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	public static final String WINDOW_ICON_NAME = "icon.png";
	public static final String WINDOW_TITLE = "Chess";

	// Variable Declaration
	private static final int HEIGHT = 700;
	private static final int WIDTH = 1110;

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.getContentPane().add(new ChessControl());

		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle(WINDOW_TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon img = new ImageIcon(Main.class.getResource(WINDOW_ICON_NAME));
		frame.setIconImage(img.getImage());
		frame.setVisible(true);
		frame.setResizable(false);
	}

}
