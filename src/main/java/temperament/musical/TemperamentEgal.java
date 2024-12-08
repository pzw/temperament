package temperament.musical;

public class TemperamentEgal extends TemperamentBase {

	@Override
	protected void initRatios() {
		double step = Math.pow(MusicalKnowledge.RATIO_OCTAVE, 1.0 / NB_NOTES_STANDARD);
		// ratios = new double[NB_NOTES_STANDARD + 1];
		ratios = new double[2 * NB_NOTES_STANDARD];
		ratios[0] = MusicalKnowledge.RATIO_UNISSON;
		for (int n = 1; n < NB_NOTES_STANDARD; n++) {
			ratios[n] = ratios[n - 1] * step;
		}

		// place artificiellement les notes dans l'échelle des quintes, sur 7 octaves
		initRatiosFifthsCircleFromRatios();
	}

	@Override
	public String toString() {
		return "égal";
	}
}
