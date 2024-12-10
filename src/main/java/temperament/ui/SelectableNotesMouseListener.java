package temperament.ui;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import temperament.model.ApplicationState;
import temperament.model.ISelectableNote;
import temperament.model.SelectableNotesModel;

/**
 * gestion de la souris au-dessus d'un composant lié à un SelectableNoteModel
 */
public class SelectableNotesMouseListener extends MouseAdapter {
	private SelectableNotesModel model;
	private ApplicationState appState;

	public SelectableNotesMouseListener(ApplicationState appState, SelectableNotesModel model) {
		this.appState = appState;
		this.model = model;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
			ISelectableNote np = model.findNote(e.getPoint());
			if (null != np) {
				List<Integer> selection = appState.getSelection();
				Integer noteIndex = np.getNoteIndex();
				if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK) {
					if (selection.contains(noteIndex)) {
						selection.remove(noteIndex);
					} else {
						selection.add(noteIndex);
					}
				} else {
					// clic only
					selection.clear();
					selection.add(noteIndex);
				}
				appState.setSelection(selection);
			}
		}
	}

}
