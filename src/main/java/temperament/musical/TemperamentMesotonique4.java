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
	private static final int	IDX_DO2			= 12;

	public TemperamentMesotonique4() {
	}

	@Override
	protected void initRatios() {
		super.initRatios();
		double ratioQuinte = Math.pow(5.0, 0.25);
		ratios[IDX_DO] = 1.0;
		ratios[IDX_SOL] = dansOctave(ratios[IDX_DO] * ratioQuinte);
		ratios[IDX_RE] = dansOctave(ratios[IDX_SOL] * ratioQuinte);
		ratios[IDX_LA] = dansOctave(ratios[IDX_RE] * ratioQuinte);
		ratios[IDX_MI] = dansOctave(ratios[IDX_LA] * ratioQuinte);
		ratios[IDX_SI] = dansOctave(ratios[IDX_MI] * ratioQuinte);
		ratios[IDX_FA_DIEZE] = dansOctave(ratios[IDX_SI] * ratioQuinte);
		ratios[IDX_DO_DIEZE] = dansOctave(ratios[IDX_FA_DIEZE] * ratioQuinte);
		ratios[IDX_SOL_DIEZE] = dansOctave(ratios[IDX_DO_DIEZE] * ratioQuinte);
		ratios[IDX_DO2] = RATIO_OCTAVE;
		ratios[IDX_FA] = dansOctave(ratios[IDX_DO] / ratioQuinte);
		ratios[IDX_SI_BEMOL] = dansOctave(ratios[IDX_FA] / ratioQuinte);
		ratios[IDX_MI_BEMOL] = dansOctave(ratios[IDX_SI_BEMOL] / ratioQuinte);
	}

	@Override
	public String toString() {
		return "mésotonique 1/4 comma";
	}

}
