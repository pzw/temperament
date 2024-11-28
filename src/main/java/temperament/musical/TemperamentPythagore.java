package temperament.musical;

public class TemperamentPythagore extends TemperamentBase {
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

	public TemperamentPythagore() {
		super();
	}

	@Override
	protected void initRatios() {
		ratios = new double[NB_NOTES_STANDARD + 1];
		initRatiosV2();
	}

	private void initRatiosV1() {
		ratios[IDX_DO] = RATIO_UNISSON;
		ratios[IDX_SOL] = dansOctave(ratios[IDX_DO] * RATIO_QUINTE);
		ratios[IDX_RE] = dansOctave(ratios[IDX_SOL] * RATIO_QUINTE);
		ratios[IDX_LA] = dansOctave(ratios[IDX_RE] * RATIO_QUINTE);
		ratios[IDX_MI] = dansOctave(ratios[IDX_LA] * RATIO_QUINTE);
		ratios[IDX_SI] = dansOctave(ratios[IDX_MI] * RATIO_QUINTE);
		ratios[IDX_FA_DIEZE] = dansOctave(ratios[IDX_SI] * RATIO_QUINTE);
		ratios[IDX_DO_DIEZE] = dansOctave(ratios[IDX_FA_DIEZE] * RATIO_QUINTE);
		ratios[IDX_SOL_DIEZE] = dansOctave(ratios[IDX_DO_DIEZE] * RATIO_QUINTE);
		ratios[IDX_FA] = dansOctave(ratios[IDX_DO] / RATIO_QUINTE);
		ratios[IDX_SI_BEMOL] = dansOctave(ratios[IDX_FA] / RATIO_QUINTE);
		ratios[IDX_MI_BEMOL] = dansOctave(ratios[IDX_SI_BEMOL] / RATIO_QUINTE);
		ratios[IDX_DO2] = RATIO_OCTAVE;
	}

	private void initRatiosV2() {
		ratios[0] = RATIO_UNISSON;
		int note = 0;
		double ratio = 1.0;
		boolean doContinue = true;
		while (doContinue) {
			// passe la note suivante, par quinte
			// do => sol (7 demi-tons pour une quinte)
			// sol => ré
			// ré => la
			// etc
			note = (note + 7) % NB_NOTES_STANDARD;
			ratio *= RATIO_QUINTE; // augmente la fréquence d'une quinte
			if (0 == note) {
				// on est revenu à la note de départ, on la stocke dans la note supplémentaire
				// on laisse un éventuel ratio > 2
				note = NB_NOTES_STANDARD;
				doContinue = false;
			} else {
				ratio = dansOctave(ratio);
			}
			ratios[note] = ratio;
		}
	}

	@Override
	public String toString() {
		return "pythagoricien";
	}
}
