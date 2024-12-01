package temperament.musical;

public class TemperamentEgal extends TemperamentBase {

	public TemperamentEgal() {
	}

	@Override
	protected void initRatios() {
		double step = Math.pow(RATIO_OCTAVE, 1.0 / NB_NOTES_STANDARD);
		// ratios = new double[NB_NOTES_STANDARD + 1];
		ratios = new double[2 * NB_NOTES_STANDARD];
		ratios[0] = RATIO_UNISSON;
		for (int n = 1; n < NB_NOTES_STANDARD; n++) {
			ratios[n] = ratios[n - 1] * step;
		}

		// place artificiellement les notes dans l'échelle des quintes, sur 7 octaves
		ratiosFifthsCircle = new double[2 * NB_NOTES_STANDARD];
		ratiosFifthsCircle[IDX_DO] = ratios[IDX_DO];
		ratiosFifthsCircle[IDX_SOL] = ratios[IDX_SOL];
		ratiosFifthsCircle[IDX_RE] = ratios[IDX_RE] * RATIO_OCTAVE_2;
		ratiosFifthsCircle[IDX_LA] = ratios[IDX_LA] * RATIO_OCTAVE_2;
		ratiosFifthsCircle[IDX_MI] = ratios[IDX_MI] * RATIO_OCTAVE_3;
		ratiosFifthsCircle[IDX_SI] = ratios[IDX_SI] * RATIO_OCTAVE_3;
		ratiosFifthsCircle[IDX_FA_DIEZE] = ratios[IDX_FA_DIEZE] * RATIO_OCTAVE_4;
		ratiosFifthsCircle[IDX_DO_DIEZE] = ratios[IDX_DO_DIEZE] * RATIO_OCTAVE_5;
		ratiosFifthsCircle[IDX_SOL_DIEZE] = ratios[IDX_SOL_DIEZE] * RATIO_OCTAVE_5;
		ratiosFifthsCircle[IDX_MI_BEMOL] = ratios[IDX_MI_BEMOL] * RATIO_OCTAVE_6;
		ratiosFifthsCircle[IDX_SI_BEMOL] = ratios[IDX_SI_BEMOL] * RATIO_OCTAVE_6;
		ratiosFifthsCircle[IDX_FA] = ratios[IDX_FA] * RATIO_OCTAVE_7;
	}

	@Override
	public String toString() {
		return "égal";
	}
}
