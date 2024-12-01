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
		ratiosFifthsCircle[Do()] = ratios[Do()];
		ratiosFifthsCircle[Sol()] = ratios[Sol()];
		ratiosFifthsCircle[Re()] = ratios[Re()] * RATIO_OCTAVE_2;
		ratiosFifthsCircle[La()] = ratios[La()] * RATIO_OCTAVE_2;
		ratiosFifthsCircle[Mi()] = ratios[Mi()] * RATIO_OCTAVE_3;
		ratiosFifthsCircle[Si()] = ratios[Si()] * RATIO_OCTAVE_3;
		ratiosFifthsCircle[FaDieze()] = ratios[FaDieze()] * RATIO_OCTAVE_4;
		ratiosFifthsCircle[DoDieze()] = ratios[DoDieze()] * RATIO_OCTAVE_5;
		ratiosFifthsCircle[SolDieze()] = ratios[SolDieze()] * RATIO_OCTAVE_5;
		ratiosFifthsCircle[MiBemol()] = ratios[MiBemol()] * RATIO_OCTAVE_6;
		ratiosFifthsCircle[SiBemol()] = ratios[SiBemol()] * RATIO_OCTAVE_6;
		ratiosFifthsCircle[Fa()] = ratios[Fa()] * RATIO_OCTAVE_7;
	}

	@Override
	public String toString() {
		return "égal";
	}
}
