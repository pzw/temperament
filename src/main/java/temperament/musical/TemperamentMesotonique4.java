package temperament.musical;

public class TemperamentMesotonique4 extends TemperamentBase {

	@Override
	protected void initRatios() {
		ratiosFifthsCircle = new double[2 * getNbNotesGamme()];
		ratios = new double[2 * getNbNotesGamme()];
		// quintes montantes à partir du DO-1
		ratiosFifthsCircle[Do()] = 1.0;
		ratiosFifthsCircle[Sol()] = quinteMesotoniqueMontante(ratiosFifthsCircle[Do()]);
		ratiosFifthsCircle[Re()] = quinteMesotoniqueMontante(ratiosFifthsCircle[Sol()]);
		ratiosFifthsCircle[La()] = quinteMesotoniqueMontante(ratiosFifthsCircle[Re()]);
		ratiosFifthsCircle[Mi()] = quinteMesotoniqueMontante(ratiosFifthsCircle[La()]);
		ratiosFifthsCircle[Si()] = quinteMesotoniqueMontante(ratiosFifthsCircle[Mi()]);
		ratiosFifthsCircle[FaDieze()] = quinteMesotoniqueMontante(ratiosFifthsCircle[Si()]);
		ratiosFifthsCircle[DoDieze()] = quinteMesotoniqueMontante(ratiosFifthsCircle[FaDieze()]);
		ratiosFifthsCircle[SolDieze()] = quinteMesotoniqueMontante(ratiosFifthsCircle[DoDieze()]);

		// quintes descendantes à partir du DO-8
		ratiosFifthsCircle[Fa()] = quinteMesotoniqueDescendante(ratiosFifthsCircle[Do()] * RATIO_OCTAVE_8);
		ratiosFifthsCircle[SiBemol()] = quinteMesotoniqueDescendante(ratiosFifthsCircle[Fa()]);
		ratiosFifthsCircle[MiBemol()] = quinteMesotoniqueDescendante(ratiosFifthsCircle[SiBemol()]);

		initRatiosFromRatiosFifthsCircle();
	}

	@Override
	public String toString() {
		return "mésotonique 1/4 comma";
	}
}

