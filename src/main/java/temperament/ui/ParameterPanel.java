package temperament.ui;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.actions.PlayGammeAction;
import temperament.model.ApplicationState;
import temperament.musical.ITemperament;

/**
 * panel pour sélectionner le tempérament, la fréquence du la et jouer la gamme
 */
public class ParameterPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public ParameterPanel(ApplicationState appState) {
		setBorder(BorderFactory.createTitledBorder("Paramètres"));

		FormLayout layout = new FormLayout("$dm,p,$rg,max(120dlu;p),$rg,p,$rg,max(60dlu;p),$rg,p,0px,$dm",
				"$dm,p,0px:g,$dm");
		CellConstraints cc = new CellConstraints();
		setLayout(layout);

		PresentationModel<ApplicationState> pm = new PresentationModel<ApplicationState>(appState);

		int x = 2;
		int y = 2;
		add(new JLabel("Tempérament"), cc.xy(x, y));

		x += 2;
		SelectionInList<ITemperament> selTemperament = new SelectionInList<ITemperament>(appState.getTemperaments(),
				pm.getModel(ApplicationState.TEMPERAMENT_PROPERTY));
		JComboBox<ITemperament> cbTemperament = new JComboBox<ITemperament>();
		Bindings.bind(cbTemperament, selTemperament);
		add(cbTemperament, cc.xy(x, y));

		x += 2;
		add(new JLabel("Fréquence du la"), cc.xy(x, y));

		x += 2;
		SpinnerHelper sh = new SpinnerHelper(appState.getLaFrequency(), 200, 900, 0.5);
		Bindings.bind(sh.getTextField(), pm.getModel(ApplicationState.LA_FREQUENCY_PROPERTY));
		sh.getTextField().addFocusListener(new SelectAllFocusListener());
		add(sh.getMainComponent(), cc.xy(x, y));

		x += 2;
		PlayGammeAction playGamme = new PlayGammeAction(appState);
		add(new JButton(playGamme), cc.xy(x, y));
	}

}
