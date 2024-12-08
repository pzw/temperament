package temperament.player;

/**
 * onde d'une note, sous forme d'un tableau de float
 */
public class NoteWave {

	private float[] wave;

	/**
	 * génère une note
	 * 
	 * @param frequency fréquence en Herz
	 * @param duration  durée en millisecondes
	 * @param volume    amplitude de l'onde
	 */
	public NoteWave(double frequency, double duration, double volume) {
		this(frequency, duration, 0.0, 0.0, volume);
	}

	/**
	 * génère une note, avec un fade in / fade out
	 * 
	 * @param frequency    fréquence en Herz
	 * @param duration     durée en millisecondes
	 * @param fadeDuration durée pour la partie fade in / fade out
	 * @param volume       amplitude de l'onde
	 */
	public NoteWave(double frequency, double duration, double fadeDuration, double volume) {
		this(frequency, duration, fadeDuration, fadeDuration, volume);
	}

	/**
	 * génère une note, avec un fade in / fade out
	 * 
	 * @param frequency       fréquence en Herz
	 * @param duration        durée en millisecondes
	 * @param fadeInDuration  durée pour la partie fade in
	 * @param fadeOutDuration durée pour la partie fade out
	 * @param volume          amplitude de l'onde
	 */
	public NoteWave(double frequency, double duration, double fadeInDuration, double fadeOutDuration, double volume) {
		wave = WaveGenerator.generateSinus(frequency, 127.0 * volume, duration, fadeInDuration, fadeOutDuration,
				(int) (NotePlayer.SAMPLE_RATE * duration / 1000));
	}

	/**
	 * construit l'onde d'une note avec un fade in / fade out relatif à la durée
	 * totale
	 * 
	 * @param frequency
	 * @param duration
	 * @param volume
	 * @return
	 */
	public static NoteWave buildNoteWaveWithFade(double frequency, double duration, double volume) {
		if (0.0 == frequency)
			return null;
		double fadeInDuration = duration / 40.0;
		double fadeOutDuration = duration / 20.0;
		return new NoteWave(frequency, duration, fadeInDuration, fadeOutDuration, volume);
	}

	/**
	 * retourne le nombre d'échantillons de l'onde
	 * 
	 * @return
	 */
	public int getWaveLength() {
		return wave.length;
	}

	/**
	 * superpose une autre onde à la notre
	 * 
	 * @param n
	 */
	public void addNote(NoteWave n) {
		int l = Math.min(getWaveLength(), n.getWaveLength());
		for (int i = 0; i < l; i++) {
			wave[i] += n.wave[i];
		}
	}

	/**
	 * ajoute (appond) une autre note à la notre
	 * 
	 * @param n
	 */
	public void appendNote(NoteWave n) {
		int l1 = wave.length;
		int l2 = n.wave.length;

		float tmp[] = new float[l1 + l2];
		for (int i = 0; i < l1; i++) {
			tmp[i] = wave[i];
		}
		for (int i = 0; i < l2; i++) {
			tmp[l1 + i] = n.wave[i];
		}
		wave = tmp;
	}

	/**
	 * retourne l'amplitude maximale de l'onde
	 * 
	 * @return
	 */
	public float getMaxValue() {
		float result = 0;
		for (int i = 0; i < wave.length; i++) {
			result = Math.max(result, Math.abs(wave[i]));
		}
		return result;
	}

	/**
	 * conversion en échantillons de 8 bits
	 * 
	 * @return
	 */
	public byte[] convert8bits() {
		float max = getMaxValue();

		int size = wave.length;
		byte[] result = new byte[size];
		for (int i = 0; i < size; i++) {
			result[i] = (byte) (wave[i] * 127.0f / max);
		}

		return result;
	}

	/**
	 * conversion en échantillons de 16 bits
	 * 
	 * @return
	 */
	public byte[] convert16bit() {
		float max = getMaxValue();

		int size = wave.length;
		byte[] result = new byte[2 * size];
		for (int i = 0; i < size; i++) {
			short tmp = (short) (wave[i] * 32767 / max);
			result[2 * i] = (byte) (tmp % 256);
			result[2 * i + 1] = (byte) (tmp / 256);
		}

		return result;
	}

	/**
	 * retourne la valeur d'un échantillon
	 * 
	 * @param idx
	 * @return
	 */
	public float getSample(int idx) {
		return idx < wave.length ? wave[idx] : 0.0f;
	}
}
