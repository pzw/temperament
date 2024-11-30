package temperament.player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import temperament.constants.IConstants;
import temperament.musical.NoteWave;

public class NotePlayer extends Thread {
	private NoteWave note;

	public NotePlayer(NoteWave note) {
		this.note = note;
	}

	@Override
	public void run() {
		AudioFormat af = new AudioFormat(IConstants.SAMPLE_RATE, 8, 1, true, false);
		byte[] buffer = note.normalize();
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
				// minimiser le glitch de fin de son
				try {
					sleep(500);
				} catch (InterruptedException e) {
				}
				sdl.close();
			}
		}
	}

}
