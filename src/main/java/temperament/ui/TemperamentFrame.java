package temperament.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;

public class TemperamentFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	public TemperamentFrame() {
		super("Etude des tempéraments");
		setMinimumSize(new Dimension(800, 800));

		setLayout(new BorderLayout());
		add(buildMainPanel(), BorderLayout.CENTER);
		pack();
		setExtendedState(Frame.MAXIMIZED_BOTH);
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (WindowEvent.WINDOW_CLOSING == e.getID()) {
			System.exit(0);
		}
		super.processWindowEvent(e);
	}

	private JComponent buildMainPanel() {
		TemperamentPanel result = new TemperamentPanel();
		return result;
	}
}
