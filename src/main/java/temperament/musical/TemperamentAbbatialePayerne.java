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
		ratios = new double[2*NB_NOTES];
		// poser les quintes
		double ratioQuinte = Math.pow(5.0, 0.25);
		// attention : les quintes dont fausses de 1/4 comma
		ratios[IDX_DO] = RATIO_UNISSON;
		ratios[IDX_SOL] = dansOctave(ratios[IDX_DO] * ratioQuinte);
		ratios[IDX_RE] = dansOctave(ratios[IDX_SOL] * ratioQuinte);
		ratios[IDX_LA] = dansOctave(ratios[IDX_RE] * ratioQuinte);

		// poser les tierces par rapport aux quintes déjà placées
		// tierces relatives au do : la bémol -> do -> mi -> sol dièze
		ratios[IDX_LA_BEMOL] = dansOctave(ratios[IDX_DO] / RATIO_TIERCE_MAJEURE);
		ratios[IDX_MI] = dansOctave(ratios[IDX_DO] * RATIO_TIERCE_MAJEURE);
		ratios[IDX_SOL_DIEZE] = dansOctave(ratios[IDX_MI] * RATIO_TIERCE_MAJEURE);

		// tierces relatives au sol : mi bémol -> sol -> si -> ré dièze
		ratios[IDX_MI_BEMOL] = dansOctave(ratios[IDX_SOL] / RATIO_TIERCE_MAJEURE);
		ratios[IDX_SI] = dansOctave(ratios[IDX_SOL] * RATIO_TIERCE_MAJEURE);
		ratios[IDX_RE_DIEZE] = dansOctave(ratios[IDX_SI] * RATIO_TIERCE_MAJEURE);

		// tierces relatives au ré : si bémol -> ré -> fa dièze
		ratios[IDX_SI_BEMOL] = dansOctave(ratios[IDX_RE] / RATIO_TIERCE_MAJEURE);
		ratios[IDX_FA_DIEZE] = dansOctave(ratios[IDX_RE] * RATIO_TIERCE_MAJEURE);

		// tierces relatives au la : fa -> la -> do dièze
		ratios[IDX_FA] = dansOctave(ratios[IDX_LA] / RATIO_TIERCE_MAJEURE);
		ratios[IDX_DO_DIEZE] = dansOctave(ratios[IDX_LA] * RATIO_TIERCE_MAJEURE);
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
