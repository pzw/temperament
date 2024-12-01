package temperament.musical;

public abstract class TemperamentBase implements ITemperament {
	protected static final int		IDX_DO						= 0;
	protected static final int		IDX_DO_DIEZE				= 1;
	protected static final int		IDX_RE						= 2;
	protected static final int		IDX_MI_BEMOL				= 3;
	protected static final int		IDX_MI						= 4;
	protected static final int		IDX_FA						= 5;
	protected static final int		IDX_FA_DIEZE				= 6;
	protected static final int		IDX_SOL						= 7;
	protected static final int		IDX_SOL_DIEZE				= 8;
	protected static final int		IDX_LA						= 9;
	protected static final int		IDX_SI_BEMOL				= 10;
	protected static final int		IDX_SI						= 11;
	public static final double		RATIO_UNISSON				= 1.0;
	public static final double		RATIO_OCTAVE				= 2.0;
	public static final double		RATIO_OCTAVE_2				= 2.0;
	public static final double		RATIO_OCTAVE_3				= 4.0;
	public static final double		RATIO_OCTAVE_4				= 8.0;
	public static final double		RATIO_OCTAVE_5				= 16.0;
	public static final double		RATIO_OCTAVE_6				= 32.0;
	public static final double		RATIO_OCTAVE_7				= 64.0;
	public static final double		RATIO_OCTAVE_8				= 128.0;
	public static final double		RATIO_TIERCE_MINEURE		= 6.0 / 5.0;
	public static final double		RATIO_TIERCE_MAJEURE		= 5.0 / 4.0;
	public static final double		RATIO_QUARTE				= 4.0 / 3.0;
	public static final double		RATIO_QUINTE				= 3.0 / 2.0;
	protected static final int		NB_NOTES_STANDARD			= 12;
	protected static final double	RATIO_QUINTE_MESOTONIQUE4	= Math.pow(5.0, 0.25);
	protected String[]				names;
	protected double[]				ratios;
	protected double[]				ratiosFifthsCircle;

	public TemperamentBase() {
		initRatios();
		initNoteNames();
		initOctave();
	}

	protected abstract void initRatios();

	protected void initRatiosFromRatiosFifthsCircle() {
		for (int i = 0; i < getNbNotesGamme(); i++) {
			ratios[i] = dansOctave(ratiosFifthsCircle[i]);
		}
	}

	protected double quinteDescendante(double pFrequency) {
		return pFrequency / RATIO_QUINTE;
	}

	protected double quinteMontante(double pFrequency) {
		return pFrequency * RATIO_QUINTE;
	}

	protected double quinteMesotoniqueDescendante(double pFrequency) {
		return pFrequency / RATIO_QUINTE_MESOTONIQUE4;
	}

	protected double quinteMesotoniqueMontante(double pFrequency) {
		return pFrequency * RATIO_QUINTE_MESOTONIQUE4;
	}

	protected double tierceMajeureMontante(double pFrequency) {
		return pFrequency * RATIO_TIERCE_MAJEURE;
	}

	protected double tierceMajeureDescendante(double pFrequency) {
		return pFrequency / RATIO_TIERCE_MAJEURE;
	}

	protected void initNoteNames() {
		names = new String[getNbNotes()];
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
	}

	protected void initOctave() {
		int n = getNbNotesGamme();
		for (int i = 0; i < n; i++) {
			ratios[i + n] = ratios[i] * RATIO_OCTAVE;
			ratiosFifthsCircle[i + n] = ratiosFifthsCircle[i];
			names[i + n] = names[i];
		}
	}

	@Override
	public int getNbNotes() {
		return ratios.length;
	}

	@Override
	public int getNbNotesGamme() {
		return NB_NOTES_STANDARD;
	}

	@Override
	public int getNbNotesToPlayGamme() {
		return getNbNotesGamme();
	}

	@Override
	public double getNoteFrequencyRatio(int noteIndex) {
		return ratios[noteIndex];
	}

	@Override
	public double getNoteFrequencyRatioInFifthsCirle(int noteIndex) {
		return ratiosFifthsCircle[noteIndex];
	}

	@Override
	public String getNoteName(int noteIndex) {
		if (noteIndex < names.length) {
			return names[noteIndex];
		} else {
			return "???";
		}
	}

	@Override
	public int getIndexLa() {
		return IDX_LA;
	}

	@Override
	public double getFrequenceDo(double frequenceLa) {
		return frequenceLa / getNoteFrequencyRatio(getIndexLa());
		// return 264;
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

	public static boolean almostEqual(double v1, double v2) {
		return Math.abs(v1 - v2) < 0.00000001;
	}

	public static String getFrequencyRatioName(double ratio) {
		if (almostEqual(ratio, RATIO_TIERCE_MINEURE)) {
			return "tierce mineure";
		} else if (almostEqual(ratio, RATIO_TIERCE_MAJEURE)) {
			return "tierce majeure";
		} else if (almostEqual(ratio, RATIO_QUARTE)) {
			return "quarte";
		} else if (almostEqual(ratio, RATIO_QUINTE)) {
			return "quinte";
		} else if (almostEqual(ratio, RATIO_OCTAVE)) {
			return "octave";
		} else {
			return "autre";
		}
	}
}
