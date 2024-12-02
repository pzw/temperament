package temperament.player;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

import temperament.constants.IConstants;
import temperament.musical.NoteWave;

public class NotePlayer extends Thread {
	private NoteWave	note;
	private int			nBits;

	public NotePlayer(NoteWave note, int nBits) {
		this.note = note;
		this.nBits = nBits;
		if (nBits != 8 && nBits != 16)
			throw new IllegalArgumentException("nBits must be 8 or 16");
	}

	public NotePlayer(NoteWave note) {
		this(note, 16);
	}

	@Override
	public void run() {
		AudioFormat af = new AudioFormat(IConstants.SAMPLE_RATE, nBits, 1, true, false);
		byte[] buffer = nBits == 8 ? note.convert8bits() : note.convert16bit();
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
