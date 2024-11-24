package temperament.musical;

public interface ITemperamentBuilder {
	/**
	 * retourne le nombre de notes que contient le tempérament
	 * 
	 * @return
	 */
	public int getNbNotes();

	/**
	 * construit un tableau de rapports de fréquences. L'élément à l'index 0 vaut
	 * toujours 1.0 et correspond à la fréquence fondamentale. La valeur 2.0
	 * représente l'octave.
	 * 
	 * @return
	 */
	public double[] buildFrequenciesRatios();
}
