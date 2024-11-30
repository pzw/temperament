package temperament.ui;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.AppState;
import temperament.model.PlayGammeAction;
import temperament.model.PlaySelectionAction;
import temperament.musical.ITemperament;
import temperament.musical.Temperaments;

public class TemperamentTopPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public TemperamentTopPanel(AppState appState) {
		FormLayout layout = new FormLayout("$dm,p,$rg,max(60dlu;p),$rg,max(60dlu;p),$rg,p,$rg,max(60dlu;p),$dm",
				"$dm,p,$lg,p,$dm");
		CellConstraints cc = new CellConstraints();
		setLayout(layout);

		PresentationModel<AppState> pm = new PresentationModel<AppState>(appState);

		int x = 2;
		int y = 2;
		add(new JLabel("Tempérament"), cc.xy(x, y));

		x += 2;
		List<ITemperament> temperaments = Temperaments.getInstance().getTemperaments();
		SelectionInList<ITemperament> selTemperament = new SelectionInList<ITemperament>(temperaments,
				pm.getModel(AppState.TEMPERAMENT_PROPERTY));
		JComboBox<ITemperament> cbTemperament = new JComboBox<ITemperament>();
		Bindings.bind(cbTemperament, selTemperament);
		add(cbTemperament, cc.xyw(x, y, 3));

		x += 4;
		add(new JLabel("Fréquence du la"), cc.xy(x, y));
		x += 2;
		SpinnerHelper sh = new SpinnerHelper(appState.getLaFrequency(), 430, 450, 0.5);
		Bindings.bind(sh.getTextField(), pm.getModel(AppState.LA_FREQUENCY_PROPERTY));
		sh.getTextField().addFocusListener(new SelectAllFocusListener());
		add(sh.getMainComponent(), cc.xy(x, y));

		add(new JTextField(), cc.xy(x, y));

		y += 2;
		x = 2;
		add(new JLabel("Rapport de fréquences"), cc.xy(x, y));
		x += 2;
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(6);
		JFormattedTextField txFrequencyRatio = new JFormattedTextField(nf);
		Bindings.bind(txFrequencyRatio, pm.getModel(AppState.FREQUENCY_RATIO_PROPERTY));
		txFrequencyRatio.setEditable(false);
		add(txFrequencyRatio, cc.xy(x, y));

		x += 2;
		JTextField txFrequencyRatioName = new JTextField();
		Bindings.bind(txFrequencyRatioName, pm.getModel(AppState.FREQUENCY_RATIO_NAME_PROPERTY));
		txFrequencyRatioName.setEditable(false);
		add(txFrequencyRatioName, cc.xy(x, y));

		x += 2;
		PlaySelectionAction playAction = new PlaySelectionAction(appState);
		add(new JButton(playAction), cc.xy(x, y));

		x += 2;
		PlayGammeAction playGamme = new PlayGammeAction(appState);
		add(new JButton(playGamme), cc.xy(x, y));
	}

}
