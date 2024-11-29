package temperament.ui;

import java.awt.BorderLayout;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.AppState;

public class WavePanel extends JPanel {
	private static final long serialVersionUID = 1L;

	public WavePanel(AppState state) {
		super(new BorderLayout());
		add(buildNorth(state), BorderLayout.NORTH);
		add(new WaveComponent(state), BorderLayout.CENTER);
	}

	private JPanel buildNorth(AppState state) {
		FormLayout layout = new FormLayout("$dm,p,$rg,p,$rg:g,p,$rg,max(40dlu;p),$dm", "2px,p,2px");
		JPanel result = new JPanel(layout);
		PresentationModel<AppState> pm = new PresentationModel<AppState>(state);
		CellConstraints cc = new CellConstraints();
		int x = 2;
		int y = 2;
		JCheckBox chkShowSum = new JCheckBox("montre la somme");
		Bindings.bind(chkShowSum, pm.getModel(AppState.WAVE_SHOW_SUM));
		result.add(chkShowSum, cc.xy(x, y));

		x += 2;
		JCheckBox chkShowEachNote = new JCheckBox("montre chaque note");
		Bindings.bind(chkShowEachNote, pm.getModel(AppState.WAVE_SHOW_EACH_NOTE));
		result.add(chkShowEachNote, cc.xy(x, y));

		x += 2;
		result.add(new JLabel("Durée visualisée [ms]"), cc.xy(x, y));

		x += 2;
		SpinnerNumberModel spModel = new SpinnerNumberModel(state.getWaveViewDuration(), 5.0, state.getDuration(), 5.0);
		JSpinner spinner = new JSpinner(spModel);
		JComponent editor = spinner.getEditor();
		JFormattedTextField txWaveViewDuration;
		if (editor instanceof JSpinner.DefaultEditor) {
			txWaveViewDuration = ((JSpinner.DefaultEditor) editor).getTextField();
			result.add(spinner, cc.xy(x, y));
		} else {
			txWaveViewDuration = new JFormattedTextField();
			result.add(txWaveViewDuration, cc.xy(x, y));
		}
		Bindings.bind(txWaveViewDuration, pm.getModel(AppState.WAVE_VIEW_DURATION_PROPERTY));
		txWaveViewDuration.addFocusListener(new SelectAllFocusListener());

		return result;
	}
}
