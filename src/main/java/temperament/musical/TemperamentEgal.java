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
		ratiosFifthsCircle[getDo()] = ratios[getDo()];
		ratiosFifthsCircle[getSol()] = ratios[getSol()];
		ratiosFifthsCircle[getRe()] = ratios[getRe()] * RATIO_OCTAVE_2;
		ratiosFifthsCircle[getLa()] = ratios[getLa()] * RATIO_OCTAVE_2;
		ratiosFifthsCircle[getMi()] = ratios[getMi()] * RATIO_OCTAVE_3;
		ratiosFifthsCircle[getSi()] = ratios[getSi()] * RATIO_OCTAVE_3;
		ratiosFifthsCircle[getFaDieze()] = ratios[getFaDieze()] * RATIO_OCTAVE_4;
		ratiosFifthsCircle[getDoDieze()] = ratios[getDoDieze()] * RATIO_OCTAVE_5;
		ratiosFifthsCircle[getSolDieze()] = ratios[getSolDieze()] * RATIO_OCTAVE_5;
		ratiosFifthsCircle[getMiBemol()] = ratios[getMiBemol()] * RATIO_OCTAVE_6;
		ratiosFifthsCircle[getSiBemol()] = ratios[getSiBemol()] * RATIO_OCTAVE_6;
		ratiosFifthsCircle[getFa()] = ratios[getFa()] * RATIO_OCTAVE_7;
	}

	@Override
	public String toString() {
		return "égal";
	}
}
