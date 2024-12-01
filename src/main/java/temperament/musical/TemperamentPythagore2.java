package temperament.musical;

public class TemperamentPythagore2 extends TemperamentBase {
	public TemperamentPythagore2() {
	}

	@Override
	protected void initRatios() {
		ratiosFifthsCircle = new double[2 * getNbNotesGamme()];
		ratios = new double[2 * getNbNotesGamme()];

		ratiosFifthsCircle[IDX_DO] = 1.0;
		ratiosFifthsCircle[IDX_SOL] = quinteMontante(ratiosFifthsCircle[IDX_DO]);
		ratiosFifthsCircle[IDX_RE] = quinteMontante(ratiosFifthsCircle[IDX_SOL]);
		ratiosFifthsCircle[IDX_LA] = quinteMontante(ratiosFifthsCircle[IDX_RE]);
		ratiosFifthsCircle[IDX_MI] = quinteMontante(ratiosFifthsCircle[IDX_LA]);
		ratiosFifthsCircle[IDX_SI] = quinteMontante(ratiosFifthsCircle[IDX_MI]);
		ratiosFifthsCircle[IDX_FA_DIEZE] = quinteMontante(ratiosFifthsCircle[IDX_SI]);
		ratiosFifthsCircle[IDX_DO_DIEZE] = quinteMontante(ratiosFifthsCircle[IDX_FA_DIEZE]);
		ratiosFifthsCircle[IDX_SOL_DIEZE] = quinteMontante(ratiosFifthsCircle[IDX_DO_DIEZE]);
		
		ratiosFifthsCircle[IDX_FA] = quinteDescendante(ratiosFifthsCircle[IDX_DO] * RATIO_OCTAVE_8);
		ratiosFifthsCircle[IDX_SI_BEMOL] = quinteDescendante(ratiosFifthsCircle[IDX_FA]);
		ratiosFifthsCircle[IDX_MI_BEMOL] = quinteDescendante(ratiosFifthsCircle[IDX_SI_BEMOL]);

		initRatiosFromRatiosFifthsCircle();
	}

	@Override
	public String toString() {
		return "pythagoricien";
	}

}
