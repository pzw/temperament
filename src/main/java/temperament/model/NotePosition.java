package temperament.model;

/**
 * position d'une note dans un cercle de notes
 */
public class NotePosition {
	private TemperamentBaseCircleModel	circleModel;
	/** position du centre du cercle de la note */
	private int							x, y;
	/** index de la note dans le tempérament */
	private int							noteIndex;
	/** rapport de fréquence de cette note par rapport au do */
	private double						frequencyRatio;
	/** position du centre du nom de la note */
	private int							xTx, yTx;
	/** indique si la note est actuellement sélectionnée */
	private boolean						selected	= false;

	public NotePosition(TemperamentBaseCircleModel parentState, int noteIndex, double frequencyRatio) {
		this.circleModel = parentState;
		this.noteIndex = noteIndex;
		this.frequencyRatio = frequencyRatio;
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
		return circleModel.getNoteName(noteIndex);
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

	/**
	 * détermine si un point est à l'intérieur du cercle qui représente une note
	 * 
	 * @param pX
	 * @param pY
	 * @return
	 */
	public boolean isInNotePosition(int pX, int pY) {
		double d = distance(pX, pY);
		return d <= circleModel.getNoteRadius();
	}

	/**
	 * retourne la bulle d'aide pour cette node
	 * 
	 * @return
	 */
	public String getTooltip() {
		StringBuilder s = new StringBuilder();
		s.append("<html><body>");
		s.append("<p>");
		s.append(getNoteName());
		s.append("<br>");
		s.append(frequencyRatio);
		s.append("</p>");
		s.append("</body></html>");
		return s.toString();
	}

	public void invertSelection() {
		selected = !selected;
	}

	public void setSelected(boolean newValue) {
		selected = newValue;
	}

	public boolean isSelected() {
		return selected;
	}

	public int getNoteIndex() {
		return noteIndex;
	}

}
