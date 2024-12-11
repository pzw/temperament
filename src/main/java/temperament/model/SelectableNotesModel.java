package temperament.model;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import temperament.musical.ITemperament;

/**
 * modèle abstrait d'un ensemble de notes que l'on peut sélectionner
 */
public abstract class SelectableNotesModel {
	private ApplicationState	appState;
	private int					width	= 0;
	private int					height	= 0;

	public SelectableNotesModel(ApplicationState appState) {
		this.appState = appState;
		initNotes();

		appState.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (ApplicationState.TEMPERAMENT_PROPERTY.equals(evt.getPropertyName())) {
					temperamentChanged();
				} else if (ApplicationState.SELECTION_PROPERTY.equals(evt.getPropertyName())) {
					// changement de la sélection
					setSelection(appState.getSelection());
				}
			}
		});

	}

	protected abstract List<? extends ISelectableNote> getNotes();

	protected abstract void initNotes();

	protected abstract void updateNotes();

	protected abstract void afterPanelDimensionChanged();

	public ApplicationState getApplicationState() {
		return appState;
	}

	public void setPanelDimensions(int w, int h) {
		if (w != width || h != height) {
			width = w;
			height = h;
			afterPanelDimensionChanged();
			updateNotes();
		}
	}

	public List<Integer> getSelection() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (ISelectableNote n : getNotes()) {
			if (n.isSelected()) {
				result.add(n.getNoteIndex());
			}
		}
		return result;
	}

	public void setSelection(List<Integer> selection) {
		for (ISelectableNote n : getNotes()) {
			boolean newSelect = selection.contains(n.getNoteIndex());
			n.setSelected(newSelect);
		}
	}

	/**
	 * retourne le rang d'une note dans la sélection actuelle
	 * 
	 * @param noteIndex
	 * @return
	 */
	public int getSelectionRank(int noteIndex) {
		List<? extends ISelectableNote> notes = getNotes();
		if (null == notes) {
			return -1;
		}
		int idxNoteIndex = findIndexOfNote(noteIndex);
		if (idxNoteIndex == -1) {
			return -1;
		}
		if (idxNoteIndex >= notes.size()) {
			return -1;
		}
		ISelectableNote n = notes.get(idxNoteIndex);
		if (!n.isSelected()) {
			return -1;
		}
		
		int result = 0;
		int idx = 0;
		while (idx < idxNoteIndex) {
			n = notes.get(idx);
			if (null != n && n.isSelected()) {
				result++;
			}
			idx++;
		}
		return result;
	}

	/**
	 * retourne l'index (dans la collection notes) de la note qui a le 'noteIndex' recherché
	 * @param noteIndex
	 * @return
	 */
	private int findIndexOfNote(int noteIndex) {
		List<? extends ISelectableNote> notes = getNotes();
		for (int i = 0; i < notes.size(); i++) {
			ISelectableNote n = notes.get(i);
			if (n.getNoteIndex() == noteIndex) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * recherche si le graphisme d'une note contient un point (x,y)
	 * 
	 * @param p
	 * @return
	 */
	public ISelectableNote findNote(Point p) {
		for (ISelectableNote n : getNotes()) {
			if (n.containsPoint(p.x, p.y)) {
				return n;
			}
		}
		return null;
	}

	protected ITemperament getTemperament() {
		return appState.getTemperament();
	}

	protected int getWidth() {
		return width;
	}

	protected int getHeight() {
		return height;
	}

	private void temperamentChanged() {
		width = -1;
		height = -1;
		initNotes();
	}
}
