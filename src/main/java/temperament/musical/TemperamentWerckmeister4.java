package temperament.musical;

public class TemperamentWerckmeister4 extends TemperamentBase {
	public TemperamentWerckmeister4() {
	}

	@Override
	protected void initRatios() {
		ratios = new double[2 * NB_NOTES_STANDARD];
		ratios[Do()] = RATIO_UNISSON;
		ratios[DoDieze()] = 98.0 / 93.0;
		// ratios[Re()] = 49.0 / 44.0;
		ratios[Re()] = 28.0 / 25.0;
		ratios[MiBemol()] = 196.0 / 165.0;
		ratios[Mi()] = 49.0 / 39.0;
		ratios[Fa()] = 4.0 / 3.0;
		ratios[FaDieze()] = 196.0 / 139.0;
		ratios[Sol()] = 196.0 / 131.0;
		ratios[SolDieze()] = 49.0 / 31.0;
		ratios[La()] = 196.0 / 117.0;
		ratios[SiBemol()] = 98.0 / 55.0;
		ratios[Si()] = 49.0 / 26.0;

		// place artificiellement les notes dans l'échelle des quintes, sur 7 octaves
		initRatiosFifthsCircleFromRatios();
	}

	@Override
	public String toString() {
		return "Werckmeister IV (VI)";
	}
}
