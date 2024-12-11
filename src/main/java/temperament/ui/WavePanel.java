package temperament.ui;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.ApplicationState;

public class WavePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public WavePanel(ApplicationState state) {
		super(new BorderLayout());
		add(buildNorth(state), BorderLayout.NORTH);
		add(new WaveComponent(state), BorderLayout.CENTER);
	}

	private JPanel buildNorth(ApplicationState state) {
		FormLayout layout = new FormLayout("$dm,p,$rg,p,$rg:g,p,$rg,max(40dlu;p),$rg,p,$dm", "2px,p,2px");
		JPanel result = new JPanel(layout);
		PresentationModel<ApplicationState> pm = new PresentationModel<ApplicationState>(state);
		CellConstraints cc = new CellConstraints();
		int x = 2;
		int y = 2;
		JCheckBox chkShowSum = new JCheckBox("montre la somme");
		Bindings.bind(chkShowSum, pm.getModel(ApplicationState.WAVE_SHOW_SUM_PROPERTY));
		result.add(chkShowSum, cc.xy(x, y));

		x += 2;
		JCheckBox chkShowEachNote = new JCheckBox("montre chaque note");
		Bindings.bind(chkShowEachNote, pm.getModel(ApplicationState.WAVE_SHOW_EACH_NOTE_PROPERTY));
		result.add(chkShowEachNote, cc.xy(x, y));

		x += 2;
		result.add(new JLabel("Durée visualisée [ms]"), cc.xy(x, y));

		x += 2;
		SpinnerHelper sh = new SpinnerHelper(state.getWaveViewDuration(), 20.0, 2000.0, 20.0);
		Bindings.bind(sh.getTextField(), pm.getModel(ApplicationState.WAVE_VIEW_DURATION_PROPERTY));
		sh.getTextField().addFocusListener(new SelectAllFocusListener());
		result.add(sh.getMainComponent(), cc.xy(x, y));

		x += 2;
		JCheckBox darkBackground = new JCheckBox("fond sombre");
		Bindings.bind(darkBackground, pm.getModel(ApplicationState.DARK_BACKGROUND_PROPERTY));
		result.add(darkBackground, cc.xy(x, y));

		return result;
	}
}
