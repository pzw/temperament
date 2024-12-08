package temperament.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.ApplicationState;
import temperament.model.TemperamentChromaticCircleModel;
import temperament.model.TemperamentFifthsCircleModel;
import temperament.model.TemperamentTableModel;

/**
 * panel principal de l'application
 */
public class ApplicationPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public ApplicationPanel(ApplicationState appState) {
		super();
		setLayout(new BorderLayout());
		add(new TemperamentTopPanel(appState), BorderLayout.NORTH);
		TemperamentFifthsCircleModel fifthsCircleModel = new TemperamentFifthsCircleModel(appState);
		TemperamentChromaticCircleModel chromaticCircleModel = new TemperamentChromaticCircleModel(appState);
		TemperamentCircleView fifthsCircleView = new TemperamentCircleView(appState, fifthsCircleModel, true);
		TemperamentCircleView chromaticCircleView = new TemperamentCircleView(appState, chromaticCircleModel, false);
		TemperamentTableModel tableModel = new TemperamentTableModel(appState);
		TemperamentTablePanel tableView = new TemperamentTablePanel(appState, tableModel);

		FormLayout layout = new FormLayout("p,0px,p:g,0px,p:g", "p,0px,p:g");
		layout.setColumnGroups(new int[][] { { 3, 5 } });
		CellConstraints cc = new CellConstraints();
		JPanel tableAndCirclesPane = new JPanel(layout);
		tableAndCirclesPane.add(tableView, cc.xywh(1, 1, 1, 3));
		tableAndCirclesPane.add(chromaticCircleView, cc.xy(3, 3, "f,f"));
		tableAndCirclesPane.add(new CircleDisplayParamPanel(appState), cc.xy(5, 1, "center,fill"));
		tableAndCirclesPane.add(fifthsCircleView, cc.xy(5, 3, "f,f"));

		WavePanel wavePanel = new WavePanel(appState);
		JSplitPane splitWave = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableAndCirclesPane, wavePanel);
		add(splitWave, BorderLayout.CENTER);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				splitWave.setDividerLocation(0.7);
			}
		});
	}
}