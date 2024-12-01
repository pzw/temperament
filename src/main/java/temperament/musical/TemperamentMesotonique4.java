package temperament.musical;

public class TemperamentMesotonique4 extends TemperamentBase {
	private static final int	IDX_DO			= 0;
	private static final int	IDX_DO_DIEZE	= 1;
	private static final int	IDX_RE			= 2;
	private static final int	IDX_MI_BEMOL	= 3;
	private static final int	IDX_MI			= 4;
	private static final int	IDX_FA			= 5;
	private static final int	IDX_FA_DIEZE	= 6;
	private static final int	IDX_SOL			= 7;
	private static final int	IDX_SOL_DIEZE	= 8;
	private static final int	IDX_LA			= 9;
	private static final int	IDX_SI_BEMOL	= 10;
	private static final int	IDX_SI			= 11;

	public TemperamentMesotonique4() {
	}

	@Override
	protected void initRatios() {
		super.initRatios();
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
	}

	@Override
	public String toString() {
		return "mésotonique 1/4 comma";
	}

}
