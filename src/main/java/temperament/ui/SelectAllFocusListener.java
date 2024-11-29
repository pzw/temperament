package temperament.ui;

import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 * focus listener qui sélectionne tout le texte d'un JTextField lorsqu'il reçoit
 * le focus
 */
public class SelectAllFocusListener implements FocusListener {

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
