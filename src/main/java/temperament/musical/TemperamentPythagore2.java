package temperament.musical;

public class TemperamentPythagore2 extends TemperamentBase {
	public TemperamentPythagore2() {
	}

	@Override
	protected void initRatios() {
		ratiosFifthsCircle = new double[2 * getNbNotesGamme()];
		ratios = new double[2 * getNbNotesGamme()];

		ratiosFifthsCircle[getDo()] = 1.0;
		ratiosFifthsCircle[getSol()] = quinteMontante(ratiosFifthsCircle[getDo()]);
		ratiosFifthsCircle[getRe()] = quinteMontante(ratiosFifthsCircle[getSol()]);
		ratiosFifthsCircle[getLa()] = quinteMontante(ratiosFifthsCircle[getRe()]);
		ratiosFifthsCircle[getMi()] = quinteMontante(ratiosFifthsCircle[getLa()]);
		ratiosFifthsCircle[getSi()] = quinteMontante(ratiosFifthsCircle[getMi()]);
		ratiosFifthsCircle[getFaDieze()] = quinteMontante(ratiosFifthsCircle[getSi()]);
		ratiosFifthsCircle[getDoDieze()] = quinteMontante(ratiosFifthsCircle[getFaDieze()]);
		ratiosFifthsCircle[getSolDieze()] = quinteMontante(ratiosFifthsCircle[getDoDieze()]);

		ratiosFifthsCircle[getFa()] = quinteDescendante(ratiosFifthsCircle[getDo()] * RATIO_OCTAVE_8);
		ratiosFifthsCircle[getSiBemol()] = quinteDescendante(ratiosFifthsCircle[getFa()]);
		ratiosFifthsCircle[getMiBemol()] = quinteDescendante(ratiosFifthsCircle[getSiBemol()]);

		initRatiosFromRatiosFifthsCircle();
	}

	@Override
	public String toString() {
		return "pythagoricien";
	}
}
