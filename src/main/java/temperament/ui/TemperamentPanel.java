package temperament.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.AppState;
import temperament.model.IntervalListener;
import temperament.model.TemperamentBaseCircleModel;
import temperament.model.TemperamentChromaticCircleModel;
import temperament.model.TemperamentFifthsCircleModel;
import temperament.model.TemperamentTableModel;

public class TemperamentPanel extends JPanel {
	private static final long	serialVersionUID	= 1L;
	private AppState			appState			= new AppState();

	public TemperamentPanel() {
		super();
		setLayout(new BorderLayout());
		add(new TemperamentTopPanel(appState), BorderLayout.NORTH);
		TemperamentFifthsCircleModel fifthsCircleModel = new TemperamentFifthsCircleModel(appState);
		TemperamentChromaticCircleModel chromaticCircleModel = new TemperamentChromaticCircleModel(appState);
		TemperamentCircleView fifthsCircleView = new TemperamentCircleView(appState, fifthsCircleModel);
		TemperamentCircleView chromaticCircleView = new TemperamentCircleView(appState, chromaticCircleModel);
		TemperamentTableModel tableModel = new TemperamentTableModel(appState);
		TemperamentTablePanel tableView = new TemperamentTablePanel(appState, tableModel);

		FormLayout layout = new FormLayout("p:g,10px,p:g", "p:g");
		CellConstraints cc = new CellConstraints();
		JPanel circlesPane = new JPanel(layout);
		circlesPane.add(chromaticCircleView, cc.xy(1, 1, "f,f"));
		circlesPane.add(fifthsCircleView, cc.xy(3, 1, "f,f"));
		JSplitPane splitTableCircle = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableView, circlesPane);
		appState.addPropertyChangeListener(new IntervalListener(appState));

		WavePanel wavePanel = new WavePanel(appState);
		JSplitPane splitWave = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitTableCircle, wavePanel);
		add(splitWave, BorderLayout.CENTER);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				splitTableCircle.setDividerLocation(0.33);
				splitWave.setDividerLocation(0.7);
			}
		});
	}
}
