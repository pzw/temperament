package temperament.model;

/**
 * représente une note que l'on peut sélectionner
 */
public interface ISelectableNote {
	/**
	 * retourne true si la note est actuellement sélectionnée
	 * 
	 * @return
	 */
	public boolean isSelected();

	/**
	 * modifie l'état de sélection de la note
	 * 
	 * @param newValue
	 */
	public void setSelected(boolean newValue);

	/**
	 * retourne l'index de la note dans le tempérament
	 * 
	 * @return
	 */
	public int getNoteIndex();

	/**
	 * détemrine si le point x,y est contenu dans la forme de la note
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean containsPoint(int x, int y);

	/**
	 * retourne le texte d'aide de cette note
	 * 
	 * @return
	 */
	public String getTooltipText();
}
