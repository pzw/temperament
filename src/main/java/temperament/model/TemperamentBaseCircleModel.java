package temperament.model;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import temperament.musical.ITemperament;

public abstract class TemperamentBaseCircleModel {
	protected NotePosition[]	positions;
	protected int				cx;
	protected int				cy;
	protected int				r;
	protected int				r2;
	protected AppState			appState;

	public TemperamentBaseCircleModel(AppState appState) {
		this.appState = appState;
		appState.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (AppState.TEMPERAMENT_PROPERTY.equals(evt.getPropertyName())) {
					temperamentChanged();
				} else if (AppState.SELECTION_PROPERTY.equals(evt.getPropertyName())) {
					// changement de la sélection
					setSelection(appState.getSelection());
				}
			}
		});
		initPositions();
	}

	public void setPanelDimensions(int w, int h) {
		cx = w / 2;
		cy = h / 2;
		r = cx > cy ? cy : cx;
		// la note sera représentée par un cercle de r/20
		// on placera une deuxième note pour la deuxième octave à r + r/20
		// place occupée : r + r/40 + r/20 = dimTot
		// dimTot = 40r/40 + r/40 + 2r/40 = 43r / 40
		// r = dimTot * 40 / 43
		r -= 20; // petite marge de 20 pixels
		// sécurité pour les arrondis, on multiplie par 39 au lieu de 40
		r = r * 39 / 43;
		r2 = r / 20;
		computeNotePositions();
	}

	public int getCenterX() {
		return cx;
	}

	public int getCenterY() {
		return cy;
	}

	public int getCircleRadius() {
		return r;
	}

	public int getNoteRadius() {
		return r2;
	}

	protected ITemperament getTemperament() {
		return appState.getTemperament();
	}

	private void temperamentChanged() {
		initPositions();
	}

	public String getNoteName(int noteIndex) {
		return getTemperament().getNoteName(noteIndex);
	}

	public int getNbNotes() {
		return getTemperament().getNbNotes();
	}

	public int getNbNotesGamme() {
		return getTemperament().getNbNotesGamme();
	}

	private void initPositions() {
		ITemperament t = getTemperament();
		positions = new NotePosition[t.getNbNotes()];
		for (int n = 0; n < getNbNotes(); n++) {
			double fRatio = t.getNoteFrequencyRatio(n);
			positions[n] = new NotePosition(this, n, fRatio);
		}
	}

	public NotePosition getPositionLa() {
		ITemperament t = getTemperament();
		return positions[t.getIndexLa()];
	}

	public boolean isTemperamentDefined() {
		return null != getTemperament();
	}

	public NotePosition findNote(Point p) {
		for (int i = 0; i < positions.length; i++) {
			if (positions[i].isInNotePosition(p.x, p.y)) {
				return positions[i];
			}
		}
		return null;
	}

	public void invertNodeSelectionAtPoint(Point p) {
		NotePosition note = findNote(p);
		if (null != note) {
			note.invertSelection();
			appState.setSelection(getSelection());
		}
	}

	public NotePosition getNotePosition(int noteRank) {
		return positions[noteRank];
	}

	public int getSelectionRank(int noteRank) {
		if (!positions[noteRank].isSelected()) {
			// la note n'est pas sélectionnée : pas de rang
			return -1;
		}
		int result = 0;
		int idx = 0;
		while (idx < noteRank) {
			if (positions[idx].isSelected()) {
				result++;
			}
			idx++;
		}
		return result;
	}

	private void computeNotePositions() {
		ITemperament t = getTemperament();
		if (null != t) {
			int nNotes = t.getNbNotes();
			int nNotesGamme = t.getNbNotesGamme();
			for (int n = 0; n < nNotes; n++) {
				computeNotePosition(n, n >= nNotesGamme);
			}
		}
	}

	protected abstract void computeNotePosition(int noteRank, boolean octave2);

	public List<Integer> getSelection() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int n = 0; n < positions.length; n++) {
			if (positions[n].isSelected()) {
				result.add(n);
			}
		}
		return result;
	}

	public void setSelection(List<Integer> selection) {
		for (int n = 0; n < positions.length; n++) {
			boolean newSelect = selection.contains(n);
			positions[n].setSelected(newSelect);
		}
	}
}
