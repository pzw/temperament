package temperament.musical;

import temperament.constants.IConstants;

public class NoteWave {

	private float[]	wave;

	/**
	 * note à une certaine fréquence, pendant une durée
	 * 
	 * @param frequency fréquence en Herz
	 * @param duration  durée en millisecondes
	 */
	public NoteWave(double frequency, double duration, double volume) {
		this(frequency, duration, 0.0, 0.0, volume);
	}
	
	public NoteWave(double frequency, double duration, double fadeDuration, double volume) {
		this(frequency, duration, fadeDuration, fadeDuration, volume);
	}

	public NoteWave(double frequency, double duration, double fadeInDuration, double fadeOutDuration, double volume) {
		wave = WaveGenerator.generateSinus(frequency, 127.0 * volume, duration, fadeInDuration, fadeOutDuration,
				(int) (IConstants.SAMPLE_RATE * duration / 1000));
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

	public byte[] convert8bits() {
		float max = getMaxValue();

		int size = wave.length;
		byte[] result = new byte[size];
		for (int i = 0; i < size; i++) {
			result[i] = (byte) (wave[i] * 127.0f / max);
		}
		
		return result;
	}
	
	public byte[] convert16bit() {
		float max = getMaxValue();

		int size = wave.length;
		byte[] result = new byte[2*size];
		for (int i = 0; i < size; i++) {
			short tmp = (short) (wave[i] * 32767 / max);
			result[2*i] = (byte) (tmp % 256);
			result[2*i+1] = (byte) (tmp / 256);
		}
		
		return result;
	}

	public int getSize() {
		return wave.length;
	}

	public float getSample(int idx) {
		return idx < wave.length ? wave[idx] : 0.0f;
	}
}
