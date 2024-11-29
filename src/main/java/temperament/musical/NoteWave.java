package temperament.musical;

import temperament.constants.IConstants;

public class NoteWave {

	private float[]	wave;
	private int		duration;

	/**
	 * note à une certaine fréquence, pendant une durée
	 * 
	 * @param frequency fréquence en Herz
	 * @param duration  durée en millisecondes
	 */
	public NoteWave(double frequency, int duration, double volume) {
		if (frequency <= 0.0)
			throw new IllegalArgumentException("Frequency <= 0 hz");

		if (duration <= 0)
			throw new IllegalArgumentException("Duration <= 0 msecs");

		if (volume > 1.0 || volume < 0.0)
			throw new IllegalArgumentException("Volume out of range 0.0 - 1.0");
		this.duration = duration;
		wave = WaveGenerator.generateSinus(frequency, 127.0 * volume, (double) duration,
				(int) IConstants.SAMPLE_RATE * duration / 1000);
	}

	public int getWaveLength() {
		return wave.length;
	}

	public void addNote(NoteWave n) {
		int l = Math.min(getWaveLength(), n.getWaveLength());
		for (int i = 0; i < l; i++) {
			wave[i] += n.wave[i];
		}
	}

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

	public float getMaxValue() {
		float result = 0;
		for (int i = 0; i < wave.length; i++) {
			result = Math.max(result, Math.abs(wave[i]));
		}
		return result;
	}

	public byte[] normalize() {
		float max = getMaxValue();

		int size = wave.length;
		byte[] result = new byte[size];
		for (int i = 0; i < size; i++) {
			result[i] = (byte) (wave[i] * 120.0f / max);
		}

		float frontLength = IConstants.SAMPLE_RATE / 10.0f;
		for (int i = 0; i < frontLength && i < result.length / 2; i++) {
			result[i] = (byte) (result[i] * i / frontLength);
			result[result.length - 1 - i] = (byte) (result[result.length - 1 - i] * i / frontLength);
		}
		return result;
	}

	public int getSize() {
		return wave.length;
	}

	public float getSample(int idx) {
		return idx < wave.length ? wave[idx] : 0.0f;
	}

	/**
	 * retourne la durée en ms
	 * 
	 * @return
	 */
	public int getDuration() {
		return duration;
	}
}
