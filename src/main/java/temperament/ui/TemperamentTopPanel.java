package temperament.ui;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.PlaySelectionAction;
import temperament.model.AppState;
import temperament.musical.ITemperament;
import temperament.musical.Temperaments;

public class TemperamentTopPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public TemperamentTopPanel(AppState appState) {
		FormLayout layout = new FormLayout("$dm,p,$rg,p:g,$rg,p,$dm", "$dm,p,$dm");
		CellConstraints cc = new CellConstraints();
		setLayout(layout);
		add(new JLabel("Tempérament"), cc.xy(2, 2));

		PresentationModel<AppState> pm = new PresentationModel<AppState>(appState);

		List<ITemperament> temperaments = Temperaments.getInstance().getTemperaments();
		SelectionInList<ITemperament> selTemperament = new SelectionInList<ITemperament>(temperaments,
				pm.getModel(AppState.TEMPERAMENT_PROPERTY));
		JComboBox<ITemperament> cbTemperament = new JComboBox<ITemperament>();
		Bindings.bind(cbTemperament, selTemperament);

		add(cbTemperament, cc.xy(4, 2));

		PlaySelectionAction playAction = new PlaySelectionAction(appState);
		add(new JButton(playAction), cc.xy(6, 2));
	}
}
