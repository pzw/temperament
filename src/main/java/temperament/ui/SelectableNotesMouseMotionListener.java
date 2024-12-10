package temperament.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

import temperament.model.ISelectableNote;
import temperament.model.SelectableNotesModel;

/**
 * gestion du mouvement de la souris au-dessus d'un composant lié à un
 * SelectableNotesModel
 */
public class SelectableNotesMouseMotionListener extends MouseMotionAdapter {
	private SelectableNotesModel	model;
	private JComponent				component;

	public SelectableNotesMouseMotionListener(SelectableNotesModel model, JComponent component) {
		this.component = component;
		this.model = model;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		ISelectableNote n = model.findNote(e.getPoint());
		if (null != n) {
			component.setToolTipText(n.getTooltipText());
		} else {
			component.setToolTipText(null);
		}
	}

}
