package temperament.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.actions.PlaySelectionAction;
import temperament.model.ApplicationState;

/**
 * panneau qui présente des outils relatifs à la sélection courante
 */
public class SelectionToolsPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public SelectionToolsPanel(ApplicationState appState) {
		setBorder(BorderFactory.createTitledBorder("Outils relatifs à la sélection"));
		
		FormLayout layout = new FormLayout("$dm,p,$rg,p,1px:g,$rg,p,$dm", "$dm,p,$lg,p,$dm");
		CellConstraints cc = new CellConstraints();
		setLayout(layout);

		PresentationModel<ApplicationState> pm = new PresentationModel<ApplicationState>(appState);

		int x = 2;
		int y = 2;

		JCheckBox chkAutoSelectMajorThird = new JCheckBox("sélectionne la tierce majeure");
		Bindings.bind(chkAutoSelectMajorThird, pm.getModel(ApplicationState.AUTO_SELECT_MAJOR_THIRD));
		add(chkAutoSelectMajorThird, cc.xy(x, y));

		x += 2;
		JCheckBox chkAutoSelectFifth = new JCheckBox("sélectionne la quinte");
		Bindings.bind(chkAutoSelectFifth, pm.getModel(ApplicationState.AUTO_SELECT_FIFTH));
		add(chkAutoSelectFifth, cc.xy(x, y));

		x = 2;
		y += 2;
		JTextField txDescription = new JTextField();
		Bindings.bind(txDescription, pm.getModel(ApplicationState.SELECTION_DESCRIPTION_PROPERTY));
		txDescription.setEditable(false);
		add(txDescription, cc.xyw(x, y, 4));

		PlaySelectionAction playAction = new PlaySelectionAction(appState);
		add(new JButton(playAction), cc.xywh(7, 2, 1, 3));
	}
}
