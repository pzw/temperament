package temperament.ui;

import javax.swing.JPanel;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.AppState;

public class TemperamentTopPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public TemperamentTopPanel(AppState appState) {
		FormLayout layout = new FormLayout("$dm,p,10px,p:g,$dm", "$dm,p,2px,p,$dm");
		CellConstraints cc = new CellConstraints();
		setLayout(layout);
		int x = 2;
		int y = 2;
		add(new ParameterPanel(appState), cc.xy(x, y, "f,f"));
		x += 2;
		add(new SelectionToolsPanel(appState), cc.xy(x, y, "f,f"));
	}
}
