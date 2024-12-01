package temperament.musical;

import java.util.ArrayList;
import java.util.List;

public abstract class TemperamentBase implements ITemperament {
	private static final int		IDX_DO						= 0;
	private static final int		IDX_DO_DIEZE				= 1;
	private static final int		IDX_RE						= 2;
	private static final int		IDX_MI_BEMOL				= 3;
	private static final int		IDX_MI						= 4;
	private static final int		IDX_FA						= 5;
	private static final int		IDX_FA_DIEZE				= 6;
	private static final int		IDX_SOL						= 7;
	private static final int		IDX_SOL_DIEZE				= 8;
	private static final int		IDX_LA						= 9;
	private static final int		IDX_SI_BEMOL				= 10;
	private static final int		IDX_SI						= 11;
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
	public double getFrequenceDo(double frequenceLa) {
		return frequenceLa / getNoteFrequencyRatio(La());
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

	public static boolean isWellKnownInterval(double ratio) {
		return almostEqual(ratio, RATIO_TIERCE_MINEURE) || almostEqual(ratio, RATIO_TIERCE_MAJEURE)
				|| almostEqual(ratio, RATIO_QUARTE) || almostEqual(ratio, RATIO_QUINTE)
				|| almostEqual(ratio, RATIO_OCTAVE);
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

	public int Do() {
		return IDX_DO;
	}

	public int DoDieze() {
		return IDX_DO_DIEZE;
	}

	public int Re() {
		return IDX_RE;
	}

	public int MiBemol() {
		return IDX_MI_BEMOL;
	}

	public int Mi() {
		return IDX_MI;
	}

	public int Fa() {
		return IDX_FA;
	}

	public int FaDieze() {
		return IDX_FA_DIEZE;
	}

	public int Sol() {
		return IDX_SOL;
	}

	public int SolDieze() {
		return IDX_SOL_DIEZE;
	}

	@Override
	public int La() {
		return IDX_LA;
	}

	public int SiBemol() {
		return IDX_SI_BEMOL;
	}

	public int Si() {
		return IDX_SI;
	}

	@Override
	public List<NotesInterval> getFifthsIntervals() {
		ArrayList<NotesInterval> result = new ArrayList<NotesInterval>();
		result.add(new NotesInterval(this, Do(), Sol()));
		result.add(new NotesInterval(this, Sol(), Re()));
		result.add(new NotesInterval(this, Re(), La()));
		result.add(new NotesInterval(this, La(), Mi()));
		result.add(new NotesInterval(this, Mi(), Si()));
		result.add(new NotesInterval(this, Si(), FaDieze()));
		result.add(new NotesInterval(this, FaDieze(), DoDieze()));
		result.add(new NotesInterval(this, DoDieze(), SolDieze()));
		result.add(new NotesInterval(this, SolDieze(), MiBemol()));
		result.add(new NotesInterval(this, MiBemol(), SiBemol()));
		result.add(new NotesInterval(this, SiBemol(), Fa()));
		result.add(new NotesInterval(this, Fa(), Do()));
		return result;
	}
}
