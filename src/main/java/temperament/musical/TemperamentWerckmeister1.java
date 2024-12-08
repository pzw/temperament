package temperament.musical;

public class TemperamentWerckmeister1 extends TemperamentBase {

	@Override
	protected void initRatios() {
		double root4_2 = Math.pow(2.0, 1.0 / 4.0);
		double root4_8 = Math.pow(8.0, 1.0 / 4.0);
		double root2_2 = Math.pow(2.0, 1.0 / 2.0);

		ratios = new double[2 * NB_NOTES_STANDARD];
		ratios[Do()] = MusicalKnowledge.RATIO_UNISSON;
		ratios[DoDieze()] = 256.0 / 243.0;
		ratios[Re()] = 64.0 / 81.0 * root2_2;
		ratios[MiBemol()] = 32.0 / 27.0;
		ratios[Mi()] = 256.0 / 243.0 * root4_2;
		ratios[Fa()] = 4.0 / 3.0;
		ratios[FaDieze()] = 1024.0 / 729.0;
		ratios[Sol()] = 8.0 / 9.0 * root4_8;
		ratios[SolDieze()] = 128.0 / 81;
		ratios[La()] = 1024.0 / 729.0 * root4_2;
		ratios[SiBemol()] = 16.0 / 9.0;
		ratios[Si()] = 128.0 / 81 * root4_2;

		// place artificiellement les notes dans l'échelle des quintes, sur 7 octaves
		initRatiosFifthsCircleFromRatios();
	}

	@Override
	public String toString() {
		return "Werckmeister I (III)";
	}
}
