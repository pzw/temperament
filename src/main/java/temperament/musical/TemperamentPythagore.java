package temperament.musical;

public class TemperamentPythagore extends TemperamentBase {
	/** une note de plus pour le si dièze */
	private static final int	NB_NOTES		= NB_NOTES_STANDARD + 1;
	private static final int	IDX_SI_DIEZE	= NB_NOTES - 1;

	public TemperamentPythagore() {
	}

	@Override
	protected void initRatios() {
		ratios = new double[2 * NB_NOTES];
		ratiosFifthsCircle = new double[2 * NB_NOTES];

		ratiosFifthsCircle[getDo()] = RATIO_UNISSON;
		ratiosFifthsCircle[getSol()] = quinteMontante(ratiosFifthsCircle[getDo()]);
		ratiosFifthsCircle[getRe()] = quinteMontante(ratiosFifthsCircle[getSol()]);
		ratiosFifthsCircle[getLa()] = quinteMontante(ratiosFifthsCircle[getRe()]);
		ratiosFifthsCircle[getMi()] = quinteMontante(ratiosFifthsCircle[getLa()]);
		ratiosFifthsCircle[getSi()] = quinteMontante(ratiosFifthsCircle[getMi()]);
		ratiosFifthsCircle[getFaDieze()] = quinteMontante(ratiosFifthsCircle[getSi()]);
		ratiosFifthsCircle[getDoDieze()] = quinteMontante(ratiosFifthsCircle[getFaDieze()]);
		ratiosFifthsCircle[getSolDieze()] = quinteMontante(ratiosFifthsCircle[getDoDieze()]);
		ratiosFifthsCircle[getMiBemol()] = quinteMontante(ratiosFifthsCircle[getSolDieze()]);
		ratiosFifthsCircle[getSiBemol()] = quinteMontante(ratiosFifthsCircle[getMiBemol()]);
		ratiosFifthsCircle[getFa()] = quinteMontante(ratiosFifthsCircle[getSiBemol()]);
		ratiosFifthsCircle[IDX_SI_DIEZE] = quinteMontante(ratiosFifthsCircle[getFa()]);

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
		return NB_NOTES - 1;
	}

}
