package temperament.ui;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.AppState;

/**
 * saisie des paramètres pour l'affichage dans le cercle des quintes
 */
public class CircleDisplayParamPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public CircleDisplayParamPanel(AppState appState) {
		FormLayout layout = new FormLayout("p,$rg,p", "p");
		CellConstraints cc = new CellConstraints();
		setLayout(layout);

		PresentationModel<AppState> pm = new PresentationModel<AppState>(appState);

		int x = 1;
		int y = 1;

		JCheckBox chkDisplayMajorThirds = new JCheckBox("affiche les tierces majeures");
		Bindings.bind(chkDisplayMajorThirds, pm.getModel(AppState.DISPLAY_MAJOR_THIRDS));
		add(chkDisplayMajorThirds, cc.xy(x, y));

		x += 2;
		JCheckBox chkDisplayFifths = new JCheckBox("affiche les quintes");
		Bindings.bind(chkDisplayFifths, pm.getModel(AppState.DISPLAY_FIFTHS));
		add(chkDisplayFifths, cc.xy(x, y));
	}
}
