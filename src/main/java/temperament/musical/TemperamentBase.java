package temperament.musical;

public class TemperamentBase implements ITemperament {
	protected final int NB_NOTES_STANDARD = 12;
	protected String[] names;
	protected double[] ratios;
	
	public TemperamentBase() {
		ratios = new double[NB_NOTES_STANDARD+1];
		names = new String[NB_NOTES_STANDARD+1];
		names[0] = "do";
		names[1] = "do#";
		names[2] = "ré";
		names[3] = "ré#";
		names[4] = "mi";
		names[5] = "fa";
		names[6] = "fa#";
		names[7] = "sol";
		names[8] = "sol#";
		names[9] = "la";
		names[10] = "la#";
		names[11] = "si";
		names[12] = "do";
	}

	@Override
	public int getNbNotes() {
		return NB_NOTES_STANDARD;
	}

	@Override
	public double getNoteFrequencyRatio(int noteIndex) {
		return ratios[noteIndex];
	}

	@Override
	public String getNoteName(int noteIndex) {
		return names[noteIndex];
	}

	@Override
	public int getIndexLa() {
		return 9;
	}

	@Override
	public double getFrequenceDo(double frequenceLa) {
		return frequenceLa / getNoteFrequencyRatio(getIndexLa());
	}

	
}
