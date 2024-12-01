package temperament.musical;

public class TemperamentPythagore extends TemperamentBase {
	/** une note de plus pour le si dièze */
	private static final int NB_NOTES = NB_NOTES_STANDARD + 1;
	private static final int IDX_SI_DIEZE = NB_NOTES-1;
	
	public TemperamentPythagore() {
	}

	@Override
	protected void initRatios() {
		ratios = new double[2 * NB_NOTES];
		ratiosFifthsCircle = new double[2 * NB_NOTES];
		
		ratiosFifthsCircle[IDX_DO] = RATIO_UNISSON;
		ratiosFifthsCircle[IDX_SOL] = quinteMontante(ratiosFifthsCircle[IDX_DO]);
		ratiosFifthsCircle[IDX_RE] = quinteMontante(ratiosFifthsCircle[IDX_SOL]);
		ratiosFifthsCircle[IDX_LA] = quinteMontante(ratiosFifthsCircle[IDX_RE]);
		ratiosFifthsCircle[IDX_MI] = quinteMontante(ratiosFifthsCircle[IDX_LA]);
		ratiosFifthsCircle[IDX_SI] = quinteMontante(ratiosFifthsCircle[IDX_MI]);
		ratiosFifthsCircle[IDX_FA_DIEZE] = quinteMontante(ratiosFifthsCircle[IDX_SI]);
		ratiosFifthsCircle[IDX_DO_DIEZE] = quinteMontante(ratiosFifthsCircle[IDX_FA_DIEZE]);
		ratiosFifthsCircle[IDX_SOL_DIEZE] = quinteMontante(ratiosFifthsCircle[IDX_DO_DIEZE]);
		ratiosFifthsCircle[IDX_MI_BEMOL] = quinteMontante(ratiosFifthsCircle[IDX_SOL_DIEZE]);
		ratiosFifthsCircle[IDX_SI_BEMOL] = quinteMontante(ratiosFifthsCircle[IDX_MI_BEMOL]);
		ratiosFifthsCircle[IDX_FA] = quinteMontante(ratiosFifthsCircle[IDX_SI_BEMOL]);
		ratiosFifthsCircle[IDX_SI_DIEZE] = quinteMontante(ratiosFifthsCircle[IDX_FA]);

		// on ne doit pas toucher le si dièze
		for (int i = 0; i < NB_NOTES_STANDARD; i++) {
			ratios[i] = dansOctave(ratiosFifthsCircle[i]);
		}
		ratios[IDX_SI_DIEZE] = ratiosFifthsCircle[IDX_SI_DIEZE] / RATIO_OCTAVE_7;
	}

	@Override
	protected void initNoteNames() {
		super.initNoteNames();
		names[IDX_SI_DIEZE] = "si #";
	}

	@Override
	public String toString() {
		return "pythagoricien : 12 quintes montantes";
	}

	@Override
	public int getNbNotesGamme() {
		return NB_NOTES;
	}

	@Override
	public int getNbNotesToPlayGamme() {
		// on s'arrête au si dieze
		return NB_NOTES-1;
	}

}
