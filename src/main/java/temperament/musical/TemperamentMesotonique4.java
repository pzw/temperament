package temperament.musical;

public class TemperamentMesotonique4 extends TemperamentBase {
	public TemperamentMesotonique4() {
	}

	@Override
	protected void initRatios() {
		ratiosFifthsCircle = new double[2 * getNbNotesGamme()];
		ratios = new double[2 * getNbNotesGamme()];
		// quintes montantes à partir du DO-1
		ratiosFifthsCircle[getDo()] = 1.0;
		ratiosFifthsCircle[getSol()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getDo()]);
		ratiosFifthsCircle[getRe()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getSol()]);
		ratiosFifthsCircle[getLa()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getRe()]);
		ratiosFifthsCircle[getMi()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getLa()]);
		ratiosFifthsCircle[getSi()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getMi()]);
		ratiosFifthsCircle[getFaDieze()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getSi()]);
		ratiosFifthsCircle[getDoDieze()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getFaDieze()]);
		ratiosFifthsCircle[getSolDieze()] = quinteMesotoniqueMontante(ratiosFifthsCircle[getDoDieze()]);

		// quintes descendantes à partir du DO-8
		ratiosFifthsCircle[getFa()] = quinteMesotoniqueDescendante(ratiosFifthsCircle[getDo()] * RATIO_OCTAVE_8);
		ratiosFifthsCircle[getSiBemol()] = quinteMesotoniqueDescendante(ratiosFifthsCircle[getFa()]);
		ratiosFifthsCircle[getMiBemol()] = quinteMesotoniqueDescendante(ratiosFifthsCircle[getSiBemol()]);

		initRatiosFromRatiosFifthsCircle();
	}

	@Override
	public String toString() {
		return "mésotonique 1/4 comma";
	}
}
