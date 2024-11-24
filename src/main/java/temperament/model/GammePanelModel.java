package temperament.model;

import java.awt.Point;

import temperament.constants.IConstants;
import temperament.musical.Gamme;
import temperament.musical.Note;
import temperament.musical.Temperament;

public class GammePanelModel {
	private NotePosition[] positions = new NotePosition[IConstants.NB_DEMI_TONS + 1];
	private Gamme gamme = null;
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

	public void setTemperament(Temperament temperament) {
		Gamme g = new Gamme(temperament);
		setGamme(g);
	}

	private void setGamme(Gamme newValue) {
		gamme = newValue;
		initPositions();
	}

	private void initPositions() {
		for (int n = IConstants.DO; n <= IConstants.DO2; n++) {
			double fRatio = gamme.getFrequencyRatio(n);
			positions[n] = new NotePosition(this, n, fRatio);
		}
	}

	public NotePosition getPositionLa() {
		return positions[9];
	}

	public boolean isGammeDefined() {
		return null != gamme;
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
		for (int n = IConstants.DO; n <= IConstants.DO2; n++) {
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
		if (null != gamme) {
			for (int n = IConstants.DO; n <= IConstants.DO2; n++) {
				computeNotePosition(n);
			}
		}
	}

	private void computeNotePosition(int noteRank) {
		// trouver l'angle en fonction du rapport de fréquence
		// 1 => 0
		// 2 => 360
		double fRatio = gamme.getFrequencyRatio(noteRank);
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
