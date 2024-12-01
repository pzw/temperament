package temperament.musical;

public class TemperamentAbbatialePayerne extends TemperamentBase {
	private static final int	NB_NOTES		= 14;
	private static final int	IDX_DO			= 0;
	private static final int	IDX_DO_DIEZE	= 1;
	private static final int	IDX_RE			= 2;
	private static final int	IDX_MI_BEMOL	= 3;
	private static final int	IDX_RE_DIEZE	= 4;
	private static final int	IDX_MI			= 5;
	private static final int	IDX_FA			= 6;
	private static final int	IDX_FA_DIEZE	= 7;
	private static final int	IDX_SOL			= 8;
	private static final int	IDX_LA_BEMOL	= 9;
	private static final int	IDX_SOL_DIEZE	= 10;
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
	 * première variante pour initialiser les ratios de fréquence (donne le même résultat que la 2e variante)
	 */
	private void initRatiosV1() {
		// processus : 
		// 1 : poser les quintes do - sol - ré - la
		// 2 : poser les tierces majeures à partir du do : do-la bémol, do-mi, mi-sol dièze
		// 3 : poser les tierces majeures à partir du sol : sol-mi bémol, sol-si, si-ré#
		// 4 : poser les tierces majeures à partir du ré : ré-si bémol, ré-fa dièze
		// 5 : poser les tierces majeures à partir du la : la-fa, la-do dièze
		
		ratios = new double[2*NB_NOTES];
		// 1 : poser les quintes do - sol - ré - la
		// attention : les quintes dont fausses de 1/4 comma
		ratios[IDX_DO] = RATIO_UNISSON;
		ratios[IDX_SOL] = dansOctave(ratios[IDX_DO] * RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_RE] = dansOctave(ratios[IDX_SOL] * RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_LA] = dansOctave(ratios[IDX_RE] * RATIO_QUINTE_MESOTONIQUE4);

		// 2 : poser les tierces majeures à partir du do : do-la bémol, do-mi, mi-sol dièze
		ratios[IDX_LA_BEMOL] = dansOctave(ratios[IDX_DO] / RATIO_TIERCE_MAJEURE);
		ratios[IDX_MI] = dansOctave(ratios[IDX_DO] * RATIO_TIERCE_MAJEURE);
		ratios[IDX_SOL_DIEZE] = dansOctave(ratios[IDX_MI] * RATIO_TIERCE_MAJEURE);

		// 3 : poser les tierces majeures à partir du sol : sol-mi bémol, sol-si, si-ré#
		ratios[IDX_MI_BEMOL] = dansOctave(ratios[IDX_SOL] / RATIO_TIERCE_MAJEURE);
		ratios[IDX_SI] = dansOctave(ratios[IDX_SOL] * RATIO_TIERCE_MAJEURE);
		ratios[IDX_RE_DIEZE] = dansOctave(ratios[IDX_SI] * RATIO_TIERCE_MAJEURE);

		// 4 : poser les tierces majeures à partir du ré : ré-si bémol, ré-fa dièze
		ratios[IDX_SI_BEMOL] = dansOctave(ratios[IDX_RE] / RATIO_TIERCE_MAJEURE);
		ratios[IDX_FA_DIEZE] = dansOctave(ratios[IDX_RE] * RATIO_TIERCE_MAJEURE);

		// 5 : poser les tierces majeures à partir du la : la-fa, la-do dièze
		ratios[IDX_FA] = dansOctave(ratios[IDX_LA] / RATIO_TIERCE_MAJEURE);
		ratios[IDX_DO_DIEZE] = dansOctave(ratios[IDX_LA] * RATIO_TIERCE_MAJEURE);
	}

	/**
	 * deuxième variante pour initialiser les ratios de fréquence (donne le même résultat que la 2e variante)
	 */
	protected void initRatiosV2() {
		super.initRatios();
		// processus : même notes que mésotonique au 1/4 de comma
		// placement séparé des deux notes associées aux feintes brisées
		
		// comme le tempérament mésotonique au 1/4 de comma
		ratios[IDX_DO] = 1.0;
		ratios[IDX_SOL] = dansOctave(ratios[IDX_DO] * RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_RE] = dansOctave(ratios[IDX_SOL] * RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_LA] = dansOctave(ratios[IDX_RE] * RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_MI] = dansOctave(ratios[IDX_LA] * RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_SI] = dansOctave(ratios[IDX_MI] * RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_FA_DIEZE] = dansOctave(ratios[IDX_SI] * RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_DO_DIEZE] = dansOctave(ratios[IDX_FA_DIEZE] * RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_SOL_DIEZE] = dansOctave(ratios[IDX_DO_DIEZE] * RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_FA] = dansOctave(ratios[IDX_DO] / RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_SI_BEMOL] = dansOctave(ratios[IDX_FA] / RATIO_QUINTE_MESOTONIQUE4);
		ratios[IDX_MI_BEMOL] = dansOctave(ratios[IDX_SI_BEMOL] / RATIO_QUINTE_MESOTONIQUE4);
		
		// placement des notes associées aux feintes brisées
		// ré dièze : une tierce majeure au-dessus de SI
		ratios[IDX_RE_DIEZE] = dansOctave(ratios[IDX_SI] * RATIO_TIERCE_MAJEURE);
		// la bémol : une tierce majeure au-dessous de DO
		ratios[IDX_LA_BEMOL] = dansOctave(ratios[IDX_DO] / RATIO_TIERCE_MAJEURE);
	}

	@Override
	protected void initNoteNames() {
		names = new String[2*NB_NOTES];
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

}
