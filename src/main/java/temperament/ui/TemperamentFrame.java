package temperament.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import temperament.model.ApplicationState;

public class TemperamentFrame extends JFrame {
	private static final long	serialVersionUID	= 1L;
	private ApplicationState	appState;
	private ApplicationPanel	appPanel;

	public TemperamentFrame() {
		super("Etude des tempéraments");
		setMinimumSize(new Dimension(800, 800));

		setLayout(new BorderLayout());
		appPanel = buildMainPanel();
		add(appPanel, BorderLayout.CENTER);
		pack();
	}

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (WindowEvent.WINDOW_CLOSING == e.getID()) {
			System.exit(0);
		}
		super.processWindowEvent(e);
	}

	@Override
	public void setVisible(boolean b) {
		if (b) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					setExtendedState(Frame.MAXIMIZED_BOTH);
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							appPanel.initSplitPanes();
						}
					});
				}
			});
		}
		super.setVisible(b);
	}

	private ApplicationPanel buildMainPanel() {
		appState = new ApplicationState();
		ApplicationPanel result = new ApplicationPanel(appState);
		return result;
	}
}
