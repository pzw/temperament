package temperament.ui;

import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import temperament.model.SelectTierceQuinteAction;
import temperament.musical.ITemperament;
import temperament.musical.Temperaments;

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
		// add(createOldTopPanel(appState), cc.xyw(1, 4, 3, "f,f"));
	}

	private JPanel createOldTopPanel(AppState appState) {
		FormLayout layout = new FormLayout(
				"$dm,p,$rg,max(60dlu;p),$rg,max(120dlu;p),$rg,p,$rg,max(60dlu;p),$rg,p,$rg,p,$rg,p,$dm",
				"$dm,p,$lg,p,$dm");
		CellConstraints cc = new CellConstraints();
		JPanel result = new JPanel(layout);

		PresentationModel<AppState> pm = new PresentationModel<AppState>(appState);

		int x = 2;
		int y = 2;
		result.add(new JLabel("Tempérament"), cc.xy(x, y));

		x += 2;
		List<ITemperament> temperaments = Temperaments.getInstance().getTemperaments();
		SelectionInList<ITemperament> selTemperament = new SelectionInList<ITemperament>(temperaments,
				pm.getModel(AppState.TEMPERAMENT_PROPERTY));
		JComboBox<ITemperament> cbTemperament = new JComboBox<ITemperament>();
		Bindings.bind(cbTemperament, selTemperament);
		result.add(cbTemperament, cc.xyw(x, y, 3));

		x += 4;
		result.add(new JLabel("Fréquence du la"), cc.xy(x, y));
		x += 2;
		SpinnerHelper sh = new SpinnerHelper(appState.getLaFrequency(), 200, 900, 0.5);
		Bindings.bind(sh.getTextField(), pm.getModel(AppState.LA_FREQUENCY_PROPERTY));
		sh.getTextField().addFocusListener(new SelectAllFocusListener());
		result.add(sh.getMainComponent(), cc.xy(x, y));

		// add(new JTextField(), cc.xy(x, y));

		x += 2;
		PlayGammeAction playGamme = new PlayGammeAction(appState);
		result.add(new JButton(playGamme), cc.xy(x, y));

		x += 2;
		SelectTierceQuinteAction tierceQuinte = new SelectTierceQuinteAction(appState);
		result.add(new JButton(tierceQuinte), cc.xy(x, y));

		x += 2;
		PlaySelectionAction playAction = new PlaySelectionAction(appState);
		result.add(new JButton(playAction), cc.xy(x, y));

		y += 2;
		x = 2;
		result.add(new JLabel("Rapport de fréquences"), cc.xy(x, y));
		x += 2;
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMinimumFractionDigits(6);
		JFormattedTextField txFrequencyRatio = new JFormattedTextField(nf);
		// Bindings.bind(txFrequencyRatio,
		// pm.getModel(AppState.FREQUENCY_RATIO_PROPERTY));
		txFrequencyRatio.setEditable(false);
		result.add(txFrequencyRatio, cc.xy(x, y));

		x += 2;
		JTextField txFrequencyRatioName = new JTextField();
		// Bindings.bind(txFrequencyRatioName,
		// pm.getModel(AppState.FREQUENCY_RATIO_NAME_PROPERTY));
		txFrequencyRatioName.setEditable(false);
		result.add(txFrequencyRatioName, cc.xy(x, y));

		x += 2;
		JCheckBox chkDisplayMajorThirds = new JCheckBox("affiche les tierces majeures");
		Bindings.bind(chkDisplayMajorThirds, pm.getModel(AppState.DISPLAY_MAJOR_THIRDS));
		result.add(chkDisplayMajorThirds, cc.xyw(x, y, 3));

		x += 4;
		JCheckBox chkDisplayFifths = new JCheckBox("affiche les quintes");
		Bindings.bind(chkDisplayFifths, pm.getModel(AppState.DISPLAY_FIFTHS));
		result.add(chkDisplayFifths, cc.xyw(x, y, 3));

		return result;
	}

}
