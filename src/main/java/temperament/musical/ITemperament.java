package temperament.musical;

import java.util.List;

public interface ITemperament {
	/**
	 * retourne le nombre de notes que contient le tempérament
	 * 
	 * @return
	 */
	public int getNbNotesGamme();

	/**
	 * retourne le nombre de notes à utiliser pour jouer la gamme
	 * 
	 * @return
	 */
	public int getNbNotesToPlayGamme();

	/**
	 * retourne le rapport de fréquence d'une note du tempérament
	 * 
	 * @param noteIndex
	 * @return
	 */
	public double getNoteFrequencyRatio(int noteIndex);

	/**
	 * retourne le rapport de fréquence d'une note dans une échelle de 7 octaves,
	 * allant de 1.0 à 128.0
	 * 
	 * @param noteIndex
	 * @return
	 */
	public double getNoteFrequencyRatioInFifthsCirle(int noteIndex);

	/**
	 * retourne le nom d'une note
	 * 
	 * @param noteIndex
	 * @return
	 */
	public String getNoteName(int noteIndex);

	/**
	 * retourne l'index de la note "la" dans le tempérament
	 * 
	 * @return
	 */
	public int La();

	/**
	 * retourne la fréquence du do en fonction de la fréquence du la (do juste
	 * en-dessous du la)
	 * 
	 * @param frequenceLa
	 * @return
	 */
	public double getFrequenceDo(double frequenceLa);

	/**
	 * retourne le nombre de notes générées (comprend une éventuelle 2ème octave)
	 * 
	 * @return
	 */
	public int getNbNotes();

	/**
	 * retourne les intervalles de quintes du tempérament
	 * 
	 * @return
	 */
	public List<NotesInterval> getFifthsIntervals();

	/**
	 * retourne les intervalles de tierces majeures
	 * 
	 * @return
	 */
	public List<NotesInterval> getMajorThirdsIntervals();

	/**
	 * retourne un "nom complet" de la note, qui comprend son nom et l'octave à
	 * laquelle elle appartient.
	 * 
	 * @param noteIndex
	 * @return
	 */
	public String getNoteFullName(int noteIndex);

	/**
	 * essaie de trouver l'index d'une note selon son nom complet
	 * 
	 * @param fullName
	 * @return
	 */
	public int findNoteIndexByFullName(String fullName);

	/**
	 * recherche une note selon son rapport de fréquence
	 * @param ratio
	 * @return
	 */
	public int findNoteIndexByRatio(double ratio);
}
