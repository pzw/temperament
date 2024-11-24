package temperament.musical;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import temperament.constants.IConstants;

public class Note {

	private float[] wave;

	/**
	 * note à une certaine fréquence, pendant une durée
	 * 
	 * @param frequency fréquence en Herz
	 * @param duration  durée en millisecondes
	 */
	public Note(double frequency, int duration, double volume) {
		if (frequency <= 0.0)
			throw new IllegalArgumentException("Frequency <= 0 hz");

		if (duration <= 0)
			throw new IllegalArgumentException("Duration <= 0 msecs");

		if (volume > 1.0 || volume < 0.0)
			throw new IllegalArgumentException("Volume out of range 0.0 - 1.0");
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

	public void addNote(Note n) {
		int l = Math.min(getWaveLength(), n.getWaveLength());
		for (int i = 0; i < l; i++) {
			wave[i] += n.wave[i];
		}
	}

	public void subNote(Note n) {
		int l = Math.min(getWaveLength(), n.getWaveLength());
		for (int i = 0; i < l; i++) {
			wave[i] -= n.wave[i];
		}
	}

	public byte[] normalize() {
		float max = 0;
		for (int i = 0; i < wave.length; i++) {
			max = Math.max(max, Math.abs(wave[i]));
		}

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

	public void playAsync() {
		PlayThread t = new PlayThread();
		t.start();
	}
	
	private void sleeep(int millisecond) {
		try {
			Thread.sleep(millisecond);
		} catch (InterruptedException e) {
		}
	}
	private class PlayThread extends Thread {
		public PlayThread() {
		}
		
		@Override
		public void run() {
			AudioFormat af = new AudioFormat(IConstants.SAMPLE_RATE, 8, 1, true, false);
			byte[] buffer = normalize();
			SourceDataLine sdl = null;
			try {
				sdl = AudioSystem.getSourceDataLine(af);
				sdl.open(af);
				sdl.start();
				sdl.write(buffer, 0, buffer.length);
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (null != sdl) {
					sleeep(200);
					sdl.close();
				}
			}
		}
	}
}
