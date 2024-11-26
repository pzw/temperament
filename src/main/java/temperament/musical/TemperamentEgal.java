package temperament.musical;

public class TemperamentEgal extends TemperamentBase {

	public TemperamentEgal() {
	}

	@Override
	protected void initRatios() {
		double step = Math.pow(RATIO_OCTAVE, 1.0 / NB_NOTES_STANDARD);
		ratios = new double[NB_NOTES_STANDARD + 1];
		ratios[0] = RATIO_UNISSON;
		for (int n = 1; n < NB_NOTES_STANDARD; n++) {
			ratios[n] = ratios[n - 1] * step;
		}
		// petite tricherie pour assurer l'octave
		ratios[ratios.length - 1] = RATIO_OCTAVE;
	}

	@Override
	public double getNoteFrequencyRatio(int noteIndex) {
		return ratios[noteIndex];
	}

	@Override
	public String toString() {
		return "égal";
	}
}
