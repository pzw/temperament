package temperament;

import javax.swing.SwingUtilities;

import temperament.ui.TemperamentFrame;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		TemperamentFrame mainFrame = new TemperamentFrame();
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				mainFrame.setVisible(true);
			}
		});
	}
}
