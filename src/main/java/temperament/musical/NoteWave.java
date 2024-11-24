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
		wave = new float[(int) IConstants.SAMPLE_RATE * duration / 1000];

		double angleStep = 2.0 * Math.PI * frequency / IConstants.SAMPLE_RATE;
		for (int i = 0; i < wave.length; i++) {
			double angle = i * angleStep;
			wave[i] = (float) (Math.sin(angle) * 127.0 * volume);
		}
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

	public void subNote(NoteWave n) {
		int l = Math.min(getWaveLength(), n.getWaveLength());
		for (int i = 0; i < l; i++) {
			wave[i] -= n.wave[i];
		}
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
		return wave[idx];
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
