package temperament.musical;

import java.util.ArrayList;
import java.util.List;

public class TemperamentAbbatialePayerne extends TemperamentBase {
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

	public TemperamentAbbatialePayerne() {
	}

	@Override
	protected void initRatios() {
		initRatiosV2();
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
		ratiosFifthsCircle[IDX_DO] = RATIO_UNISSON;
		ratiosFifthsCircle[IDX_SOL] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_DO]);
		ratiosFifthsCircle[IDX_RE] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_SOL]);
		ratiosFifthsCircle[IDX_LA] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_RE]);

		// 2 : poser les tierces majeures à partir du do : do-la bémol, do-mi, mi-sol
		// dièze
		ratiosFifthsCircle[IDX_LA_BEMOL] = tierceMajeureDescendante(ratiosFifthsCircle[IDX_DO]);
		ratiosFifthsCircle[IDX_MI] = tierceMajeureMontante(ratiosFifthsCircle[IDX_DO]);
		ratiosFifthsCircle[IDX_SOL_DIEZE] = tierceMajeureMontante(ratiosFifthsCircle[IDX_MI]);

		// 3 : poser les tierces majeures à partir du sol : sol-mi bémol, sol-si, si-ré#
		ratiosFifthsCircle[IDX_MI_BEMOL] = tierceMajeureDescendante(ratiosFifthsCircle[IDX_SOL]);
		ratiosFifthsCircle[IDX_SI] = tierceMajeureMontante(ratiosFifthsCircle[IDX_SOL]);
		ratiosFifthsCircle[IDX_RE_DIEZE] = tierceMajeureMontante(ratiosFifthsCircle[IDX_SI]);

		// 4 : poser les tierces majeures à partir du ré : ré-si bémol, ré-fa dièze
		ratiosFifthsCircle[IDX_SI_BEMOL] = tierceMajeureDescendante(ratiosFifthsCircle[IDX_RE]);
		ratiosFifthsCircle[IDX_FA_DIEZE] = tierceMajeureMontante( ratiosFifthsCircle[IDX_RE]);

		// 5 : poser les tierces majeures à partir du la : la-fa, la-do dièze
		ratiosFifthsCircle[IDX_FA] = tierceMajeureDescendante(ratiosFifthsCircle[IDX_LA]);
		ratiosFifthsCircle[IDX_DO_DIEZE] = tierceMajeureMontante(ratiosFifthsCircle[IDX_LA]);
		
		initRatiosFromRatiosFifthsCircle();
	}

	/**
	 * deuxième variante pour initialiser les ratios de fréquence (donne le même
	 * résultat que la 2e variante)
	 */
	protected void initRatiosV2() {
		ratiosFifthsCircle = new double[2 * getNbNotesGamme()];
		ratios = new double[2 * getNbNotesGamme()];
		// processus : même notes que mésotonique au 1/4 de comma
		// placement séparé des deux notes associées aux feintes brisées

		// comme le tempérament mésotonique au 1/4 de comma
		ratiosFifthsCircle[IDX_DO] = 1.0;
		ratiosFifthsCircle[IDX_SOL] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_DO]);
		ratiosFifthsCircle[IDX_RE] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_SOL]);
		ratiosFifthsCircle[IDX_LA] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_RE]);
		ratiosFifthsCircle[IDX_MI] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_LA]);
		ratiosFifthsCircle[IDX_SI] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_MI]);
		ratiosFifthsCircle[IDX_FA_DIEZE] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_SI]);
		ratiosFifthsCircle[IDX_DO_DIEZE] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_FA_DIEZE]);
		ratiosFifthsCircle[IDX_SOL_DIEZE] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_DO_DIEZE]);
		
		ratiosFifthsCircle[IDX_FA] = quinteMesotoniqueDescendante(ratiosFifthsCircle[IDX_DO] * RATIO_OCTAVE_8);
		ratiosFifthsCircle[IDX_SI_BEMOL] = quinteMesotoniqueDescendante(ratiosFifthsCircle[IDX_FA]);
		ratiosFifthsCircle[IDX_MI_BEMOL] = quinteMesotoniqueDescendante(ratiosFifthsCircle[IDX_SI_BEMOL]);

		// placement des notes associées aux feintes brisées
		// ré dièze : une tierce majeure au-dessus de SI (hausse de 2 octaves pour rapprocher du mi bémol)
		ratiosFifthsCircle[IDX_RE_DIEZE] = tierceMajeureMontante(ratiosFifthsCircle[IDX_SI]) * 4.0;
		// la bémol : une tierce majeure au-dessous de DO (baisse de 2 octaves pour rapprocher du sol dièze)
		ratiosFifthsCircle[IDX_LA_BEMOL] = tierceMajeureDescendante(ratiosFifthsCircle[IDX_DO]) / 4.0;
		
		initRatiosFromRatiosFifthsCircle();
	}

	@Override
	protected void initNoteNames() {
		names = new String[2 * NB_NOTES];
		names[IDX_DO] = "do";
		names[IDX_DO_DIEZE] = "do #";
		names[IDX_RE] = "ré";
		names[IDX_MI_BEMOL] = "mi b";
		names[IDX_RE_DIEZE] = "ré #";
		names[IDX_MI] = "mi";
		names[IDX_FA] = "fa";
		names[IDX_FA_DIEZE] = "fa #";
		names[IDX_SOL] = "sol";
		names[IDX_LA_BEMOL] = "la b";
		names[IDX_SOL_DIEZE] = "sol #";
		names[IDX_LA] = "la";
		names[IDX_SI_BEMOL] = "si b";
		names[IDX_SI] = "si";
	}

	@Override
	public int getNbNotesGamme() {
		return NB_NOTES;
	}

	@Override
	public int getIndexLa() {
		return IDX_LA;
	}

	@Override
	public String toString() {
		return "Abbatiale Payerne";
	}

	@Override
	public List<NotesInterval> getFifthsIntervals() {
		ArrayList<NotesInterval> result = new ArrayList<NotesInterval>();
		result.add(new NotesInterval(this, IDX_DO, IDX_SOL));
		result.add(new NotesInterval(this, IDX_SOL, IDX_RE));
		result.add(new NotesInterval(this, IDX_RE, IDX_LA));
		result.add(new NotesInterval(this, IDX_LA, IDX_MI));
		result.add(new NotesInterval(this, IDX_MI, IDX_SI));
		result.add(new NotesInterval(this, IDX_SI, IDX_FA_DIEZE));
		result.add(new NotesInterval(this, IDX_FA_DIEZE, IDX_DO_DIEZE));
		result.add(new NotesInterval(this, IDX_DO_DIEZE, IDX_SOL_DIEZE));
		result.add(new NotesInterval(this, IDX_SOL_DIEZE, IDX_MI_BEMOL));
		result.add(new NotesInterval(this, IDX_MI_BEMOL, IDX_SI_BEMOL));
		result.add(new NotesInterval(this, IDX_SI_BEMOL, IDX_FA));
		result.add(new NotesInterval(this, IDX_FA, IDX_DO));
		return result;
	}
}
