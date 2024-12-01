package temperament.musical;

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
		ratiosFifthsCircle[getDo()] = RATIO_UNISSON;
		ratiosFifthsCircle[getSol()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getDo()]);
		ratiosFifthsCircle[getRe()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getSol()]);
		ratiosFifthsCircle[getLa()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getRe()]);

		// 2 : poser les tierces majeures à partir du do : do-la bémol, do-mi, mi-sol
		// dièze
		ratiosFifthsCircle[IDX_LA_BEMOL] = tierceMajeureDescendante(ratiosFifthsCircle[getDo()]);
		ratiosFifthsCircle[getMi()] = tierceMajeureMontante(ratiosFifthsCircle[getDo()]);
		ratiosFifthsCircle[getSolDieze()] = tierceMajeureMontante(ratiosFifthsCircle[getMi()]);

		// 3 : poser les tierces majeures à partir du sol : sol-mi bémol, sol-si, si-ré#
		ratiosFifthsCircle[getMiBemol()] = tierceMajeureDescendante(ratiosFifthsCircle[getSol()]);
		ratiosFifthsCircle[getSi()] = tierceMajeureMontante(ratiosFifthsCircle[getSol()]);
		ratiosFifthsCircle[IDX_RE_DIEZE] = tierceMajeureMontante(ratiosFifthsCircle[getSi()]);

		// 4 : poser les tierces majeures à partir du ré : ré-si bémol, ré-fa dièze
		ratiosFifthsCircle[getSiBemol()] = tierceMajeureDescendante(ratiosFifthsCircle[getRe()]);
		ratiosFifthsCircle[getFaDieze()] = tierceMajeureMontante(ratiosFifthsCircle[getRe()]);

		// 5 : poser les tierces majeures à partir du la : la-fa, la-do dièze
		ratiosFifthsCircle[getFa()] = tierceMajeureDescendante(ratiosFifthsCircle[getLa()]);
		ratiosFifthsCircle[getDoDieze()] = tierceMajeureMontante(ratiosFifthsCircle[getLa()]);

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
		ratiosFifthsCircle[getDo()] = 1.0;
		ratiosFifthsCircle[getSol()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getDo()]);
		ratiosFifthsCircle[getRe()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getSol()]);
		ratiosFifthsCircle[getLa()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getRe()]);
		ratiosFifthsCircle[getMi()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getLa()]);
		ratiosFifthsCircle[getSi()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getMi()]);
		ratiosFifthsCircle[getFaDieze()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getSi()]);
		ratiosFifthsCircle[getDoDieze()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getFaDieze()]);
		ratiosFifthsCircle[getSolDieze()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getDoDieze()]);

		ratiosFifthsCircle[getFa()] = quinteMesotoniqueDescendante(ratiosFifthsCircle[getDo()] * RATIO_OCTAVE_8);
		ratiosFifthsCircle[getSiBemol()] = quinteMesotoniqueDescendante(ratiosFifthsCircle[getFa()]);
		ratiosFifthsCircle[getMiBemol()] = quinteMesotoniqueDescendante(ratiosFifthsCircle[getSiBemol()]);

		// placement des notes associées aux feintes brisées
		// ré dièze : une tierce majeure au-dessus de SI (hausse de 2 octaves pour
		// rapprocher du mi bémol)
		ratiosFifthsCircle[IDX_RE_DIEZE] = tierceMajeureMontante(ratiosFifthsCircle[getSi()]) * 4.0;
		// la bémol : une tierce majeure au-dessous de DO (baisse de 2 octaves pour
		// rapprocher du sol dièze)
		ratiosFifthsCircle[IDX_LA_BEMOL] = tierceMajeureDescendante(ratiosFifthsCircle[getDo()]) / 4.0;

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

	public int getDo() {
		return IDX_DO;
	}

	public int getDoDieze() {
		return IDX_DO_DIEZE;
	}

	public int getRe() {
		return IDX_RE;
	}

	public int getMiBemol() {
		return IDX_MI_BEMOL;
	}

	public int getMi() {
		return IDX_MI;
	}

	public int getFa() {
		return IDX_FA;
	}

	public int getFaDieze() {
		return IDX_FA_DIEZE;
	}

	public int getSol() {
		return IDX_SOL;
	}

	public int getSolDieze() {
		return IDX_SOL_DIEZE;
	}

	@Override
	public int getLa() {
		return IDX_LA;
	}

	public int getSiBemol() {
		return IDX_SI_BEMOL;
	}

	public int getSi() {
		return IDX_SI;
	}

	@Override
	public String toString() {
		return "Abbatiale Payerne";
	}
}
