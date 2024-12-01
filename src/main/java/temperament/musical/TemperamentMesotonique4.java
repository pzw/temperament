package temperament.musical;

public class TemperamentMesotonique4 extends TemperamentBase {

	public TemperamentMesotonique4() {
	}

	@Override
	protected void initRatios() {
		ratiosFifthsCircle = new double[2 * getNbNotesGamme()];
		ratios = new double[2 * getNbNotesGamme()];
		// quintes montantes à partir du DO-1
		ratiosFifthsCircle[IDX_DO] = 1.0;
		ratiosFifthsCircle[IDX_SOL] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_DO]);
		ratiosFifthsCircle[IDX_RE] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_SOL]);
		ratiosFifthsCircle[IDX_LA] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_RE]);
		ratiosFifthsCircle[IDX_MI] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_LA]);
		ratiosFifthsCircle[IDX_SI] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_MI]);
		ratiosFifthsCircle[IDX_FA_DIEZE] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_SI]);
		ratiosFifthsCircle[IDX_DO_DIEZE] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_FA_DIEZE]);
		ratiosFifthsCircle[IDX_SOL_DIEZE] = quinteMesotoniqueMontante(ratiosFifthsCircle[IDX_DO_DIEZE]);
		
		// quintes descendantes à partir du DO-8
		ratiosFifthsCircle[IDX_FA] = quinteMesotoniqueDescendante(ratiosFifthsCircle[IDX_DO] * RATIO_OCTAVE_8);
		ratiosFifthsCircle[IDX_SI_BEMOL] = quinteMesotoniqueDescendante(ratiosFifthsCircle[IDX_FA]);
		ratiosFifthsCircle[IDX_MI_BEMOL] = quinteMesotoniqueDescendante(ratiosFifthsCircle[IDX_SI_BEMOL]);
		
		initRatiosFromRatiosFifthsCircle();
	}

	public void initRatiosOld() {
		/*
		 * ratios[IDX_SOL] = dansOctave(ratios[IDX_DO] * RATIO_QUINTE_MESOTONIQUE4);
		 * ratios[IDX_RE] = dansOctave(ratios[IDX_SOL] * RATIO_QUINTE_MESOTONIQUE4);
		 * ratios[IDX_LA] = dansOctave(ratios[IDX_RE] * RATIO_QUINTE_MESOTONIQUE4);
		 * ratios[IDX_MI] = dansOctave(ratios[IDX_LA] * RATIO_QUINTE_MESOTONIQUE4);
		 * ratios[IDX_SI] = dansOctave(ratios[IDX_MI] * RATIO_QUINTE_MESOTONIQUE4);
		 * ratios[IDX_FA_DIEZE] = dansOctave(ratios[IDX_SI] *
		 * RATIO_QUINTE_MESOTONIQUE4); ratios[IDX_DO_DIEZE] =
		 * dansOctave(ratios[IDX_FA_DIEZE] * RATIO_QUINTE_MESOTONIQUE4);
		 * ratios[IDX_SOL_DIEZE] = dansOctave(ratios[IDX_DO_DIEZE] *
		 * RATIO_QUINTE_MESOTONIQUE4); ratios[IDX_FA] = dansOctave(ratios[IDX_DO] /
		 * RATIO_QUINTE_MESOTONIQUE4); ratios[IDX_SI_BEMOL] = dansOctave(ratios[IDX_FA]
		 * / RATIO_QUINTE_MESOTONIQUE4); ratios[IDX_MI_BEMOL] =
		 * dansOctave(ratios[IDX_SI_BEMOL] / RATIO_QUINTE_MESOTONIQUE4);
		 */
	}

	@Override
	public String toString() {
		return "mésotonique 1/4 comma";
	}

}
