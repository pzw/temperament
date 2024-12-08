package temperament.musical;

import java.util.ArrayList;
import java.util.List;

/**
 * tempéramenet mésotonique de l'Abbatiale de Payerne, avec deux notes
 * supplémentaires : le ré dièze et le la bémol
 */
public class TemperamentAbbatialePayerne extends TemperamentMesotonique4 {
	private static final int	NB_NOTES		= 14;
	private static final int	IDX_DO			= 0;
	private static final int	IDX_DO_DIEZE	= 1;
	private static final int	IDX_RE			= 2;
	private static final int	IDX_RE_DIEZE	= 3;
	private static final int	IDX_MI_BEMOL	= 4;
	private static final int	IDX_MI			= 5;
	private static final int	IDX_FA			= 6;
	private static final int	IDX_FA_DIEZE	= 7;
	private static final int	IDX_SOL			= 8;
	private static final int	IDX_SOL_DIEZE	= 9;
	private static final int	IDX_LA_BEMOL	= 10;
	private static final int	IDX_LA			= 11;
	private static final int	IDX_SI_BEMOL	= 12;
	private static final int	IDX_SI			= 13;

	@Override
	protected void initRatios() {
		super.initRatios();

		// placement des notes associées aux feintes brisées
		// ré dièze : une tierce majeure au-dessus de SI (hausse de 2 octaves pour
		// rapprocher du mi bémol)
		ratiosFifthsCircle[IDX_RE_DIEZE] = tierceMajeureMontante(ratiosFifthsCircle[Si()]) * 4.0;
		// la bémol : une tierce majeure au-dessous de DO (baisse de 2 octaves pour
		// rapprocher du sol dièze)
		ratiosFifthsCircle[IDX_LA_BEMOL] = tierceMajeureDescendante(ratiosFifthsCircle[Do()]) / 4.0;

		initRatiosFromRatiosFifthsCircle();
	}

	/**
	 * première variante pour initialiser les ratios de fréquence (donne le même
	 * résultat que la 2e variante)
	 */
	@SuppressWarnings("unused")
	private void initRatiosV1() {
		ratiosFifthsCircle = new double[2 * getNbNotesGamme()];
		ratios = new double[2 * getNbNotesGamme()];

		// processus :
		// 1 : poser les quintes do - sol - ré - la
		// 2 : poser les tierces majeures à partir du do : do-la bémol, do-mi, mi-sol
		// dièze
		// 3 : poser les tierces majeures à partir du sol : sol-mi bémol, sol-si, si-ré#
		// 4 : poser les tierces majeures à partir du ré : ré-si bémol, ré-fa dièze
		// 5 : poser les tierces majeures à partir du la : la-fa, la-do dièze

		// 1 : poser les quintes do - sol - ré - la
		// attention : les quintes dont fausses de 1/4 comma
		ratiosFifthsCircle[Do()] = MusicalKnowledge.RATIO_UNISSON;
		ratiosFifthsCircle[Sol()] = quinteMesotoniqueMontante(ratiosFifthsCircle[Do()]);
		ratiosFifthsCircle[Re()] = quinteMesotoniqueMontante(ratiosFifthsCircle[Sol()]);
		ratiosFifthsCircle[La()] = quinteMesotoniqueMontante(ratiosFifthsCircle[Re()]);

		// 2 : poser les tierces majeures à partir du do : do-la bémol, do-mi, mi-sol
		// dièze
		ratiosFifthsCircle[IDX_LA_BEMOL] = tierceMajeureDescendante(ratiosFifthsCircle[Do()]);
		ratiosFifthsCircle[Mi()] = tierceMajeureMontante(ratiosFifthsCircle[Do()]);
		ratiosFifthsCircle[SolDieze()] = tierceMajeureMontante(ratiosFifthsCircle[Mi()]);

		// 3 : poser les tierces majeures à partir du sol : sol-mi bémol, sol-si, si-ré#
		ratiosFifthsCircle[MiBemol()] = tierceMajeureDescendante(ratiosFifthsCircle[Sol()]);
		ratiosFifthsCircle[Si()] = tierceMajeureMontante(ratiosFifthsCircle[Sol()]);
		ratiosFifthsCircle[IDX_RE_DIEZE] = tierceMajeureMontante(ratiosFifthsCircle[Si()]);

		// 4 : poser les tierces majeures à partir du ré : ré-si bémol, ré-fa dièze
		ratiosFifthsCircle[SiBemol()] = tierceMajeureDescendante(ratiosFifthsCircle[Re()]);
		ratiosFifthsCircle[FaDieze()] = tierceMajeureMontante(ratiosFifthsCircle[Re()]);

		// 5 : poser les tierces majeures à partir du la : la-fa, la-do dièze
		ratiosFifthsCircle[Fa()] = tierceMajeureDescendante(ratiosFifthsCircle[La()]);
		ratiosFifthsCircle[DoDieze()] = tierceMajeureMontante(ratiosFifthsCircle[La()]);

		initRatiosFromRatiosFifthsCircle();
	}

	@Override
	protected void initNoteNames() {
		super.initNoteNames();
		names[ReDieze()] = MusicalKnowledge.NOM_RE_DIEZE;
		names[LaBemol()] = MusicalKnowledge.NOM_LA_BEMOL;
	}

	@Override
	public int getNbNotesGamme() {
		return NB_NOTES;
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

	public int ReDieze() {
		return IDX_RE_DIEZE;
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

	public int LaBemol() {
		return IDX_LA_BEMOL;
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
	public String toString() {
		return "Abbatiale Payerne";
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
		result.add(new NotesInterval(this, LaBemol(), Do()));
		result.add(new NotesInterval(this, La(), DoDieze()));
		result.add(new NotesInterval(this, SiBemol(), Re()));
		result.add(new NotesInterval(this, Si(), ReDieze()));
		return result;
	}

}
