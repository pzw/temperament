package temperament.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.ApplicationState;
import temperament.model.KeyboardModel;
import temperament.model.TemperamentChromaticCircleModel;
import temperament.model.TemperamentFifthsCircleModel;
import temperament.model.TemperamentTableModel;

/**
 * panel principal de l'application
 */
public class ApplicationPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JSplitPane split1;
	private JSplitPane split2;
	
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

		KeyboardModel keyboardModel = new KeyboardModel(appState);
		KeyboardPanel keyboardPanel = new KeyboardPanel(appState, keyboardModel);
		WavePanel wavePanel = new WavePanel(appState);

		split2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, keyboardPanel, wavePanel);
		split1 = new JSplitPane(JSplitPane.VERTICAL_SPLIT, tableAndCirclesPane, split2);
		add(split1, BorderLayout.CENTER);
	}
	
	public void initSplitPanes() {
		//System.out.println("height:" + split1.getHeight());
		int pos = 2 * split1.getHeight() / 3;
		split1.setDividerLocation(pos);
		//System.out.println("width:" + split2.getWidth());
		pos = split2.getWidth() / 4;
		split2.setDividerLocation(pos);
		split2.setOneTouchExpandable(true);
	}
}
