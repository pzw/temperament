package temperament.model;

import java.awt.Point;

import temperament.musical.ITemperament;
import temperament.musical.Note;

public class GammePanelModel {
	private NotePosition[] positions;
	private ITemperament temperament = null;
	private int cx;
	private int cy;
	private int r;
	private int r2;
	private static double log2 = Math.log(2.0);

	public void setPanelDimensions(int w, int h) {
		cx = w / 2;
		cy = h / 2;
		r = cx > cy ? cy : cx;
		r -= 10; // petite marge de 10 pixels
		r2 = r / 20;
		r -= r2;
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

	public void setTemperament(ITemperament newValue) {
		temperament = newValue;
		initPositions();
	}
	
	public String getNoteName(int noteIndex) {
		return temperament.getNoteName(noteIndex);
	}

	public int getNbNotes() {
		return temperament.getNbNotes();
	}

	private void initPositions() {
		positions = new NotePosition[temperament.getNbNotes() + 1];
		for (int n = 0; n <= getNbNotes(); n++) {
			double fRatio = temperament.getNoteFrequencyRatio(n);
			positions[n] = new NotePosition(this, n, fRatio);
		}
	}

	public NotePosition getPositionLa() {
		return positions[9];
	}

	public boolean isTemperamentDefined() {
		return null != temperament;
	}

	public NotePosition findNote(Point p) {
		for (int i = 0; i < positions.length; i++) {
			if (positions[i].isInNotePosition(p.x, p.y)) {
				return positions[i];
			}
		}
		return null;
	}

	public NotePosition getNotePosition(int noteRank) {
		return positions[noteRank];
	}

	public void playSelection(int duration) {
		Note note = null;
		for (int n = 0; n <= temperament.getNbNotes(); n++) {
			NotePosition np = getNotePosition(n);
			if (np.isSelected()) {
				if (null == note) {
					note = np.buildNote(duration);
				} else {
					note.addNote(np.buildNote(duration));
				}
			}
		}
		if (null != note) {
			note.playAsync();
		}
	}

	private void computeNotePositions() {
		if (null != temperament) {
			for (int n = 0; n <= temperament.getNbNotes(); n++) {
				computeNotePosition(n);
			}
		}
	}

	private void computeNotePosition(int noteRank) {
		// trouver l'angle en fonction du rapport de fréquence
		// 1 => 0
		// 2 => 360
		double fRatio = temperament.getNoteFrequencyRatio(noteRank);
		double log = Math.log(fRatio) / log2;
		double angleDegre = 90.0 - log * 360.0;
		double angle = angleDegre * Math.PI / 180.0;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		int x = (int) (cx + cos * r);
		int y = (int) (cy - sin * r);
		double rTx = r - 3 * r2;
		int xTx = (int) (cx + cos * rTx);
		int yTx = (int) (cy - sin * rTx);
		positions[noteRank].setGraphicPosition(x, y, xTx, yTx);
	}

}
