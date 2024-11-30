package temperament.ui;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SpinnerHelper {
	private JSpinner spinner;
	private JFormattedTextField txField;
	private JComponent component;
	
	public SpinnerHelper(double value, double minValue, double maxValue, double step) {
		SpinnerNumberModel spModel = new SpinnerNumberModel(value, minValue, maxValue, step);
		spinner = new JSpinner(spModel);
		JComponent editor = spinner.getEditor();
		if (editor instanceof JSpinner.DefaultEditor) {
			txField = ((JSpinner.DefaultEditor) editor).getTextField();
			component = spinner;
		} else {
			txField = new JFormattedTextField();
			component = txField;
		}
	}
	
	public JComponent getMainComponent() {
		return component;
	}
	
	public JFormattedTextField getTextField() {
		return txField;
	}
}
