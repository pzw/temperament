package temperament.musical;

public class TemperamentWerckmeister2 extends TemperamentBase {

	@Override
	protected void initRatios() {
		double root3_2 = Math.pow(2.0, 1.0 / 3.0);
		double root3_4 = Math.pow(4.0, 1.0 / 3.0);

		ratios = new double[2 * NB_NOTES_STANDARD];
		ratios[Do()] = MusicalKnowledge.RATIO_UNISSON;
		ratios[DoDieze()] = 16384.0 / 19683.0 * root3_2;
		ratios[Re()] = 8.0 / 9.0 * root3_2;
		ratios[MiBemol()] = 32.0 / 27.0;
		ratios[Mi()] = 64.0 / 81.0 * root3_4;
		ratios[Fa()] = 4.0 / 3.0;
		ratios[FaDieze()] = 1024.0 / 729.0;
		ratios[Sol()] = 32.0 / 27.0 * root3_2;
		ratios[SolDieze()] = 8192.0 / 6561.0 * root3_2;
		ratios[La()] = 256.0 / 243.0 * root3_4;
		ratios[SiBemol()] = 9.0 / (4 * root3_2);
		ratios[Si()] = 4096.0 / 2187.0;

		// place artificiellement les notes dans l'échelle des quintes, sur 7 octaves
		initRatiosFifthsCircleFromRatios();
	}

	@Override
	public String toString() {
		return "Werckmeister II (IV)";
	}
}
