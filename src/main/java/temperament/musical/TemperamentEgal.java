package temperament.musical;

public class TemperamentEgal extends TemperamentBase {

	public TemperamentEgal() {
		super();
		double step = Math.pow(2.0, 1.0 / NB_NOTES_STANDARD);
		ratios[0] = 1.0;
		for (int n = 1; n < NB_NOTES_STANDARD; n++) {
			ratios[n] = ratios[n - 1] * step;
		}
		// petite tricherie pour assurer l'octave
		ratios[ratios.length - 1] = 2.0;
	}

	@Override
	public double getNoteFrequencyRatio(int noteIndex) {
		return ratios[noteIndex];
	}

	@Override
	public String toString() {
		return "tempérament égal";
	}
}
