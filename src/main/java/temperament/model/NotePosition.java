package temperament.model;

import temperament.musical.Gamme;
import temperament.musical.Note;

public class NotePosition {
	private int x;
	private int y;
	private int noteRank;
	private double frequencyRatio;
	private int xTx;
	private int yTx;
	private boolean selected = false;
	private GammePanelModel parentState;

	public NotePosition(GammePanelModel parentState, int noteRank, double frequencyRatio) {
		this.noteRank = noteRank;
		this.frequencyRatio = frequencyRatio;
		this.parentState = parentState;
	}

	public void setGraphicPosition(int x, int y, int xTx, int yTx) {
		this.x = x;
		this.y = y;
		this.xTx = xTx;
		this.yTx = yTx;
	}

	public int getCenterX() {
		return x;
	}

	public int getCenterY() {
		return y;
	}

	public int getTextX() {
		return xTx;
	}

	public int getTextY() {
		return yTx;
	}

	public String getNoteName() {
		return Gamme.getNoteName(noteRank);
	}

	/**
	 * 
	 * retourne la distance entre un point (souris) et la position de la note
	 * 
	 * @param pX
	 * @param pY
	 * @return
	 */
	public double distance(int pX, int pY) {
		int dx = x - pX;
		int dy = y - pY;
		return Math.sqrt(dx * dx + dy * dy);
	}

	public boolean isInNotePosition(int pX, int pY) {
		double d = distance(pX, pY);
		return d <= parentState.getNoteRadius();
	}

	public String getTooltip() {
		StringBuilder s = new StringBuilder();
		s.append("<html><body>");
		s.append("<p>");
		s.append(Gamme.getNoteName(noteRank));
		s.append("<br>");
		s.append(frequencyRatio);
		s.append("</p>");
		s.append("</body></html>");
		return s.toString();
	}

	public Note buildNote(int duration) {
		double frequenceDo = 440.0 / parentState.getPositionLa().frequencyRatio;
		double f = frequenceDo * frequencyRatio;
		return new Note(f, duration, 1.0);
	}

	public void invertSelection() {
		selected = !selected;
	}

	public boolean isSelected() {
		return selected;
	}
}
