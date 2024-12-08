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
	protected static final int		NB_NOTES_STANDARD			= 12;
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
			ratios[i] = MusicalKnowledge.dansOctave(ratiosFifthsCircle[i]);
		}
	}

	protected void initRatiosFifthsCircleFromRatios() {
		ratiosFifthsCircle = new double[2 * getNbNotesGamme()];
		ratiosFifthsCircle[Do()] = ratios[Do()];
		ratiosFifthsCircle[Sol()] = ratios[Sol()];
		ratiosFifthsCircle[Re()] = ratios[Re()] * MusicalKnowledge.RATIO_OCTAVE_2;
		ratiosFifthsCircle[La()] = ratios[La()] * MusicalKnowledge.RATIO_OCTAVE_2;
		ratiosFifthsCircle[Mi()] = ratios[Mi()] * MusicalKnowledge.RATIO_OCTAVE_3;
		ratiosFifthsCircle[Si()] = ratios[Si()] * MusicalKnowledge.RATIO_OCTAVE_3;
		ratiosFifthsCircle[FaDieze()] = ratios[FaDieze()] * MusicalKnowledge.RATIO_OCTAVE_4;
		ratiosFifthsCircle[DoDieze()] = ratios[DoDieze()] * MusicalKnowledge.RATIO_OCTAVE_5;
		ratiosFifthsCircle[SolDieze()] = ratios[SolDieze()] * MusicalKnowledge.RATIO_OCTAVE_5;
		ratiosFifthsCircle[MiBemol()] = ratios[MiBemol()] * MusicalKnowledge.RATIO_OCTAVE_6;
		ratiosFifthsCircle[SiBemol()] = ratios[SiBemol()] * MusicalKnowledge.RATIO_OCTAVE_6;
		ratiosFifthsCircle[Fa()] = ratios[Fa()] * MusicalKnowledge.RATIO_OCTAVE_7;
	}

	protected double quinteDescendante(double pFrequency) {
		return pFrequency / MusicalKnowledge.RATIO_QUINTE;
	}

	protected double quinteMontante(double pFrequency) {
		return pFrequency * MusicalKnowledge.RATIO_QUINTE;
	}

	protected double quinteMesotoniqueDescendante(double pFrequency) {
		return pFrequency / MusicalKnowledge.RATIO_QUINTE_MESOTONIQUE4;
	}

	protected double quinteMesotoniqueMontante(double pFrequency) {
		return pFrequency * MusicalKnowledge.RATIO_QUINTE_MESOTONIQUE4;
	}

	protected double tierceMajeureMontante(double pFrequency) {
		return pFrequency * MusicalKnowledge.RATIO_TIERCE_MAJEURE;
	}

	protected double tierceMajeureDescendante(double pFrequency) {
		return pFrequency / MusicalKnowledge.RATIO_TIERCE_MAJEURE;
	}

	protected void initNoteNames() {
		names = new String[getNbNotes()];
		names[Do()] = MusicalKnowledge.NOM_DO;
		names[DoDieze()] = MusicalKnowledge.NOM_DO_DIEZE;
		names[Re()] = MusicalKnowledge.NOM_RE;
		names[MiBemol()] = MusicalKnowledge.NOM_MI_BEMOL;
		names[Mi()] = MusicalKnowledge.NOM_MI;
		names[Fa()] = MusicalKnowledge.NOM_FA;
		names[FaDieze()] = MusicalKnowledge.NOM_FA_DIEZE;
		names[Sol()] = MusicalKnowledge.NOM_SOL;
		names[SolDieze()] = MusicalKnowledge.NOM_SOL_DIEZE;
		names[La()] = MusicalKnowledge.NOM_LA;
		names[SiBemol()] = MusicalKnowledge.NOM_SI_BEMOL;
		names[Si()] = MusicalKnowledge.NOM_SI;
	}

	protected void initOctave() {
		int n = getNbNotesGamme();
		for (int i = 0; i < n; i++) {
			ratios[i + n] = ratios[i] * MusicalKnowledge.RATIO_OCTAVE;
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
			if (fullName.startsWith(MusicalKnowledge.NOM_RE_DIEZE)) {
				result = findNoteIndexByFullNameExact(fullName.replace(MusicalKnowledge.NOM_RE_DIEZE, MusicalKnowledge.NOM_MI_BEMOL));
			} else if (fullName.startsWith(MusicalKnowledge.NOM_MI_BEMOL)) {
				result = findNoteIndexByFullNameExact(fullName.replace(MusicalKnowledge.NOM_MI_BEMOL, MusicalKnowledge.NOM_RE_DIEZE));
			} else if (fullName.startsWith(MusicalKnowledge.NOM_SOL_DIEZE)) {
				result = findNoteIndexByFullNameExact(fullName.replace(MusicalKnowledge.NOM_SOL_DIEZE, MusicalKnowledge.NOM_LA_BEMOL));
			} else if (fullName.startsWith(MusicalKnowledge.NOM_LA_BEMOL)) {
				result = findNoteIndexByFullNameExact(fullName.replace(MusicalKnowledge.NOM_LA_BEMOL, MusicalKnowledge.NOM_SOL_DIEZE));
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
