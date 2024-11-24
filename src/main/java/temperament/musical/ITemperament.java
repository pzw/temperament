package temperament.musical;

public interface ITemperament {
	/**
	 * retourne le nombre de notes que contient le tempérament
	 * 
	 * @return
	 */
	public int getNbNotes();

	/**
	 * retourne le rapport de fréquence d'une note du tempérament
	 * @param noteIndex
	 * @return
	 */
	public double getNoteFrequencyRatio(int noteIndex);
	
	/**
	 * retourne le nom d'une note
	 * @param noteIndex
	 * @return
	 */
	public String getNoteName(int noteIndex);
	
	/**
	 * retourne l'index de la note "la" dans le tempérament
	 * @return
	 */
	public int getIndexLa();
}
