package temperament.model;

public class NotePosition {
	private TemperamentBaseCircleModel	circleModel;
	private int							x;
	private int							y;
	private int							noteIndex;
	private double						frequencyRatio;
	private int							xTx;
	private int							yTx;
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

	public boolean isInNotePosition(int pX, int pY) {
		double d = distance(pX, pY);
		return d <= circleModel.getNoteRadius();
	}

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
