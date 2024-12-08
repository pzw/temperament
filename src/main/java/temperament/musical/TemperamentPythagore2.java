package temperament.musical;

/**
 * gamme de Pythagore, avec une quinte du loup entre le mi bémol et le sol dièze
 */
public class TemperamentPythagore2 extends TemperamentBase {

	@Override
	protected void initRatios() {
		ratiosFifthsCircle = new double[2 * getNbNotesGamme()];
		ratios = new double[2 * getNbNotesGamme()];

		ratiosFifthsCircle[Do()] = 1.0;
		ratiosFifthsCircle[Sol()] = quinteMontante(ratiosFifthsCircle[Do()]);
		ratiosFifthsCircle[Re()] = quinteMontante(ratiosFifthsCircle[Sol()]);
		ratiosFifthsCircle[La()] = quinteMontante(ratiosFifthsCircle[Re()]);
		ratiosFifthsCircle[Mi()] = quinteMontante(ratiosFifthsCircle[La()]);
		ratiosFifthsCircle[Si()] = quinteMontante(ratiosFifthsCircle[Mi()]);
		ratiosFifthsCircle[FaDieze()] = quinteMontante(ratiosFifthsCircle[Si()]);
		ratiosFifthsCircle[DoDieze()] = quinteMontante(ratiosFifthsCircle[FaDieze()]);
		ratiosFifthsCircle[SolDieze()] = quinteMontante(ratiosFifthsCircle[DoDieze()]);

		ratiosFifthsCircle[Fa()] = quinteDescendante(ratiosFifthsCircle[Do()] * MusicalKnowledge.RATIO_OCTAVE_8);
		ratiosFifthsCircle[SiBemol()] = quinteDescendante(ratiosFifthsCircle[Fa()]);
		ratiosFifthsCircle[MiBemol()] = quinteDescendante(ratiosFifthsCircle[SiBemol()]);

		initRatiosFromRatiosFifthsCircle();
	}

	@Override
	public String toString() {
		return "pythagoricien";
	}
}
