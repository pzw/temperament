package temperament.musical;

public class TemperamentBase implements ITemperament {
	public static final double	RATIO_UNISSON			= 1.0;
	public static final double	RATIO_OCTAVE			= 2.0;
	public static final double	RATIO_TIERCE_MINEURE	= 6.0 / 5.0;
	public static final double	RATIO_TIERCE_MAJEURE	= 5.0 / 4.0;
	public static final double	RATIO_QUARTE			= 4.0 / 3.0;
	public static final double	RATIO_QUINTE			= 3.0 / 2.0;

	protected final int			NB_NOTES_STANDARD		= 12;
	private static final int	IDX_LA					= 9;
	protected String[]			names;
	protected double[]			ratios;

	public TemperamentBase() {
		initNoteNames();
		initRatios();
	}

	protected void initRatios() {
		ratios = new double[NB_NOTES_STANDARD + 1];
		for (int i = 0; i < ratios.length; i++) {
			ratios[i] = RATIO_UNISSON;
		}
	}

	protected void initNoteNames() {
		names = new String[NB_NOTES_STANDARD + 1];
		names[0] = "do";
		names[1] = "do #";
		names[2] = "ré";
		names[3] = "mi b";
		names[4] = "mi";
		names[5] = "fa";
		names[6] = "fa #";
		names[7] = "sol";
		names[8] = "sol #";
		names[IDX_LA] = "la";
		names[10] = "si b";
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
		return IDX_LA;
	}

	@Override
	public double getFrequenceDo(double frequenceLa) {
		// return frequenceLa / getNoteFrequencyRatio(getIndexLa());
		return 264.0;
	}

	/**
	 * garantir qu'un ratio est situé entre l'unisson et l'octave
	 * 
	 * @param ratio
	 * @return
	 */
	public double dansOctave(double ratio) {
		double result = ratio;
		while (result > RATIO_OCTAVE) {
			result = result / RATIO_OCTAVE;
		}
		while (result < RATIO_UNISSON) {
			result = result * RATIO_OCTAVE;
		}
		return result;
	}
}
