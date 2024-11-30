package temperament.musical;

public class TemperamentEgal extends TemperamentBase {

	public TemperamentEgal() {
	}

	@Override
	protected void initRatios() {
		double step = Math.pow(RATIO_OCTAVE, 1.0 / NB_NOTES_STANDARD);
		//ratios = new double[NB_NOTES_STANDARD + 1];
		ratios = new double[2*NB_NOTES_STANDARD];
		ratios[0] = RATIO_UNISSON;
		for (int n = 1; n < NB_NOTES_STANDARD; n++) {
			ratios[n] = ratios[n - 1] * step;
		}
	}

	@Override
	public String toString() {
		return "égal";
	}
}
