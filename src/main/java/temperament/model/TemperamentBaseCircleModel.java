package temperament.model;

import java.util.ArrayList;
import java.util.List;

import temperament.musical.ITemperament;
import temperament.musical.NotesInterval;

/**
 * modèle de donnée pour une représentation des notes dans un cercle
 */
public abstract class TemperamentBaseCircleModel extends SelectableNotesModel {
	protected List<NotePosition>	positions;
	protected int					cx;
	protected int					cy;
	protected int					r;
	protected int					r2;

	public TemperamentBaseCircleModel(ApplicationState appState) {
		super(appState);
	}

	@Override
	protected List<? extends ISelectableNote> getNotes() {
		return positions;
	}

	@Override
	protected void afterPanelDimensionChanged() {
		cx = getWidth() / 2;
		cy = getHeight() / 2;
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

	public String getNoteName(int noteIndex) {
		return getTemperament().getNoteName(noteIndex);
	}

	public int getNbNotes() {
		return getTemperament().getNbNotes();
	}

	public int getNbNotesGamme() {
		return getTemperament().getNbNotesGamme();
	}

	@Override
	protected void initNotes() {
		ITemperament t = getTemperament();
		positions = new ArrayList<NotePosition>();
		for (int n = 0; n < getNbNotes(); n++) {
			double fRatio = t.getNoteFrequencyRatio(n);
			positions.add(new NotePosition(this, n, fRatio));
		}
	}

	public NotePosition getPositionLa() {
		ITemperament t = getTemperament();
		return positions.get(t.La());
	}

	public boolean isTemperamentDefined() {
		return null != getTemperament();
	}

	public List<NotesInterval> getFifthsIntervals() {
		ITemperament t = getTemperament();
		return t.getFifthsIntervals();
	}

	public List<NotesInterval> getMajorThirdsIntervals() {
		ITemperament t = getTemperament();
		return t.getMajorThirdsIntervals();
	}

	public NotePosition getNotePosition(int noteIndex) {
		return positions.get(noteIndex);
	}

	@Override
	protected void updateNotes() {
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
}
