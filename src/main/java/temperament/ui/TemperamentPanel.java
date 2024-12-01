package temperament.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import temperament.model.AppState;
import temperament.model.IntervalListener;
import temperament.model.TemperamentCircleModel;
import temperament.model.TemperamentTableModel;

public class TemperamentPanel extends JPanel {
	private static final long	serialVersionUID	= 1L;
	private AppState			appState			= new AppState();

	public TemperamentPanel() {
		super();
		setLayout(new BorderLayout());
		add(new TemperamentTopPanel(appState), BorderLayout.NORTH);
		TemperamentCircleModel circleModel = new TemperamentCircleModel(appState);
		TemperamentChromaticCircleView circleView = new TemperamentChromaticCircleView(appState, circleModel);
		TemperamentTableModel tableModel = new TemperamentTableModel(appState);
		TemperamentTablePanel tableView = new TemperamentTablePanel(appState, tableModel);
		JSplitPane splitTableCircle = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableView, circleView);
		appState.addPropertyChangeListener(new IntervalListener(appState));
		
		WavePanel wavePanel = new WavePanel(appState);
		JSplitPane splitWave = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitTableCircle, wavePanel);
		add(splitWave, BorderLayout.CENTER);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				splitTableCircle.setDividerLocation(0.33);
				splitWave.setDividerLocation(0.5);
			}
		});
	}
}
