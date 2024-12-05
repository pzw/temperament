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
	public static final String		NOM_DO						= "do";
	public static final String		NOM_DO_DIEZE				= "do #";
	public static final String		NOM_RE_BEMOL				= "ré b";
	public static final String		NOM_RE						= "ré";
	public static final String		NOM_RE_DIEZE				= "ré #";
	public static final String		NOM_MI_BEMOL				= "mi b";
	public static final String		NOM_MI						= "mi";
	public static final String		NOM_MI_DIEZE				= "mi #";
	public static final String		NOM_FA						= "fa";
	public static final String		NOM_FA_DIEZE				= "fa #";
	public static final String		NOM_SOL						= "sol";
	public static final String		NOM_SOL_DIEZE				= "sol #";
	public static final String		NOM_LA_BEMOL				= "la b";
	public static final String		NOM_LA						= "la";
	public static final String		NOM_LA_DIEZE				= "la #";
	public static final String		NOM_SI_BEMOL				= "si b";
	public static final String		NOM_SI						= "si";
	public static final String		NOM_SI_DIEZE				= "si #";
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

	protected void initRatiosFifthsCircleFromRatios() {
		ratiosFifthsCircle = new double[2 * getNbNotesGamme()];
		ratiosFifthsCircle[Do()] = ratios[Do()];
		ratiosFifthsCircle[Sol()] = ratios[Sol()];
		ratiosFifthsCircle[Re()] = ratios[Re()] * RATIO_OCTAVE_2;
		ratiosFifthsCircle[La()] = ratios[La()] * RATIO_OCTAVE_2;
		ratiosFifthsCircle[Mi()] = ratios[Mi()] * RATIO_OCTAVE_3;
		ratiosFifthsCircle[Si()] = ratios[Si()] * RATIO_OCTAVE_3;
		ratiosFifthsCircle[FaDieze()] = ratios[FaDieze()] * RATIO_OCTAVE_4;
		ratiosFifthsCircle[DoDieze()] = ratios[DoDieze()] * RATIO_OCTAVE_5;
		ratiosFifthsCircle[SolDieze()] = ratios[SolDieze()] * RATIO_OCTAVE_5;
		ratiosFifthsCircle[MiBemol()] = ratios[MiBemol()] * RATIO_OCTAVE_6;
		ratiosFifthsCircle[SiBemol()] = ratios[SiBemol()] * RATIO_OCTAVE_6;
		ratiosFifthsCircle[Fa()] = ratios[Fa()] * RATIO_OCTAVE_7;
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
		names[Do()] = NOM_DO;
		names[DoDieze()] = NOM_DO_DIEZE;
		names[Re()] = NOM_RE;
		names[MiBemol()] = NOM_MI_BEMOL;
		names[Mi()] = NOM_MI;
		names[Fa()] = NOM_FA;
		names[FaDieze()] = NOM_FA_DIEZE;
		names[Sol()] = NOM_SOL;
		names[SolDieze()] = NOM_SOL_DIEZE;
		names[La()] = NOM_LA;
		names[SiBemol()] = NOM_SI_BEMOL;
		names[Si()] = NOM_SI;
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
	public static double dansOctave(double ratio) {
		if (0 == ratio)
			return 0.0;

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

	@Override
	public List<NotesInterval> getMajorThirdsIntervals() {
		ArrayList<NotesInterval> result = new ArrayList<NotesInterval>();
		result.add(new NotesInterval(this, Do(), Mi()));
		result.add(new NotesInterval(this, DoDieze(), Fa()));
		result.add(new NotesInterval(this, Re(), FaDieze()));
		result.add(new NotesInterval(this, MiBemol(), Sol()));
		result.add(new NotesInterval(this, Mi(), SolDieze()));
		result.add(new NotesInterval(this, Fa(), La()));
		result.add(new NotesInterval(this, FaDieze(), SiBemol()));
		result.add(new NotesInterval(this, Sol(), Si()));
		result.add(new NotesInterval(this, SolDieze(), Do()));
		result.add(new NotesInterval(this, La(), DoDieze()));
		result.add(new NotesInterval(this, SiBemol(), Re()));
		result.add(new NotesInterval(this, Si(), MiBemol()));

		return result;
	}

	@Override
	public String getNoteFullName(int noteIndex) {
		StringBuilder sb = new StringBuilder();
		sb.append(getNoteName(noteIndex));
		double r = getNoteFrequencyRatio(noteIndex);
		sb.append("/");
		sb.append(r >= 2.0 ? "2" : "1");
		return sb.toString();
	}

	private int findNoteIndexByFullNameExact(String fullName) {
		for (int i = 0; i < getNbNotes(); i++) {
			String n = getNoteFullName(i);
			if (n.equals(fullName))
				return i;
		}
		return -1;
	}

	@Override
	public int findNoteIndexByFullName(String fullName) {
		int result = findNoteIndexByFullNameExact(fullName);
		if (-1 == result) {
			if (fullName.startsWith(NOM_RE_DIEZE)) {
				result = findNoteIndexByFullNameExact(fullName.replace(NOM_RE_DIEZE, NOM_MI_BEMOL));
			} else if (fullName.startsWith(NOM_MI_BEMOL)) {
				result = findNoteIndexByFullNameExact(fullName.replace(NOM_MI_BEMOL, NOM_RE_DIEZE));
			} else if (fullName.startsWith(NOM_SOL_DIEZE)) {
				result = findNoteIndexByFullNameExact(fullName.replace(NOM_SOL_DIEZE, NOM_LA_BEMOL));
			} else if (fullName.startsWith(NOM_LA_BEMOL)) {
				result = findNoteIndexByFullNameExact(fullName.replace(NOM_LA_BEMOL, NOM_SOL_DIEZE));
			}
		}
		return result;
	}

	@Override
	public int findNoteIndexByRatio(double ratio) {
		int bestIndex = -1;
		double bestDiff = 1000;
		for (int i = 0; i < getNbNotes(); i++) {
			double noteRatio = getNoteFrequencyRatio(i);
			double diff = Math.abs(ratio - noteRatio);
			if (diff < bestDiff) {
				bestDiff = diff;
				bestIndex = i;
			}
		}
		return bestDiff < 0.1 ? bestIndex : -1;
	}
	
}
