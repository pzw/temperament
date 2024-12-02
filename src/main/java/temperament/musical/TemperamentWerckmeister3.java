package temperament.musical;

public class TemperamentWerckmeister3 extends TemperamentBase {
	public TemperamentWerckmeister3() {
	}

	@Override
	protected void initRatios() {
		double root4_2 = Math.pow(2.0, 1.0 / 4.0);
		double root4_8 = Math.pow(8.0, 1.0 / 4.0);
		double root2_2 = Math.pow(2.0, 1.0 / 2.0);

		ratios = new double[2 * NB_NOTES_STANDARD];
		ratios[Do()] = RATIO_UNISSON;
		ratios[DoDieze()] = 8.0 / 9.0 * root4_2;
		ratios[Re()] = 9.0 / 8.0;
		ratios[MiBemol()] = root4_2;
		ratios[Mi()] = 8.0 / 9.0 * root2_2;
		ratios[Fa()] = 9.0 / 8.0 * root4_2;
		ratios[FaDieze()] = root2_2;
		ratios[Sol()] = 3.0 / 2.0;
		ratios[SolDieze()] = 128.0 / 81;
		ratios[La()] = root4_8;
		ratios[SiBemol()] = 3.0 / root4_8;
		ratios[Si()] = 4.0 / 3.0 * root2_2;

		// place artificiellement les notes dans l'échelle des quintes, sur 7 octaves
		initRatiosFifthsCircleFromRatios();
	}

	@Override
	public String toString() {
		return "Werckmeister III (V)";
	}
}
