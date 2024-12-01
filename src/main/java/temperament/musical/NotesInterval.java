package temperament.musical;

public class NotesInterval {
	private ITemperament	temperament;
	private int				noteLow;
	private int				noteHigh;

	public NotesInterval(ITemperament temperament, int idxLow, int idxHigh) {
		this.temperament = temperament;
		this.noteLow = idxLow;
		this.noteHigh = idxHigh;
	}

	public int getNoteIndex1() {
		return noteLow;
	}

	public int getNoteIndex2() {
		return noteHigh;
	}
	
	public double getFrequencyRatio() {
		double fLow = temperament.getNoteFrequencyRatio(noteLow);
		double fHigh = temperament.getNoteFrequencyRatio(noteHigh);
		if (fHigh < fLow) {
			fHigh = fHigh * TemperamentBase.RATIO_OCTAVE;
		}
		return fHigh / fLow;
	}
}
