package temperament.ui;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.AppState;
import temperament.model.PlaySelectionAction;
import temperament.musical.ITemperament;
import temperament.musical.Temperaments;

public class TemperamentTopPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public TemperamentTopPanel(AppState appState) {
		FormLayout layout = new FormLayout("$dm,p,$rg,p:g,$rg,p,$rg,max(p;50dlu),$rg,p,$rg,p,$rg,p,$dm", "$dm,p,$dm");
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
		add(cbTemperament, cc.xy(x, y));

		x += 2;
		add(new JLabel("Durée visualisée [ms]"), cc.xy(x, y));

		x += 2;
		NumberFormat fmt = NumberFormat.getNumberInstance();
		fmt.setGroupingUsed(false);
		fmt.setMaximumFractionDigits(3);
		JFormattedTextField txWaveViewDuration = new JFormattedTextField(fmt);
		Bindings.bind(txWaveViewDuration, pm.getModel(AppState.WAVE_VIEW_DURATION_PROPERTY));
		add(txWaveViewDuration, cc.xy(x, y));
		txWaveViewDuration.addFocusListener(new MyFocusListener());

		x += 2;
		PlaySelectionAction playAction = new PlaySelectionAction(appState);
		add(new JButton(playAction), cc.xy(x, y));
	}

	private class MyFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			Component c = e.getComponent();
			if (c instanceof JTextField) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						((JTextField) c).selectAll();
					}
				});
			}
		}

		@Override
		public void focusLost(FocusEvent e) {
			Component c = e.getComponent();
			if (c instanceof JTextField) {
				((JTextField) c).select(0, 0);
			}
		}
	}
}
