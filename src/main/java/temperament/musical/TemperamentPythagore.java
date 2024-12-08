package temperament.musical;

/**
 * gamme de Pythagore, avec empilement des quintes : on arrive plus haut que le
 * do
 */
public class TemperamentPythagore extends TemperamentBase {
	/** une note de plus pour le si dièze */
	private static final int	NB_NOTES		= NB_NOTES_STANDARD + 1;
	private static final int	IDX_SI_DIEZE	= NB_NOTES - 1;

	@Override
	protected void initRatios() {
		ratios = new double[2 * NB_NOTES];
		ratiosFifthsCircle = new double[2 * NB_NOTES];

		ratiosFifthsCircle[Do()] = MusicalKnowledge.RATIO_UNISSON;
		ratiosFifthsCircle[Sol()] = quinteMontante(ratiosFifthsCircle[Do()]);
		ratiosFifthsCircle[Re()] = quinteMontante(ratiosFifthsCircle[Sol()]);
		ratiosFifthsCircle[La()] = quinteMontante(ratiosFifthsCircle[Re()]);
		ratiosFifthsCircle[Mi()] = quinteMontante(ratiosFifthsCircle[La()]);
		ratiosFifthsCircle[Si()] = quinteMontante(ratiosFifthsCircle[Mi()]);
		ratiosFifthsCircle[FaDieze()] = quinteMontante(ratiosFifthsCircle[Si()]);
		ratiosFifthsCircle[DoDieze()] = quinteMontante(ratiosFifthsCircle[FaDieze()]);
		ratiosFifthsCircle[SolDieze()] = quinteMontante(ratiosFifthsCircle[DoDieze()]);
		ratiosFifthsCircle[ReDieze()] = quinteMontante(ratiosFifthsCircle[SolDieze()]);
		ratiosFifthsCircle[LaDieze()] = quinteMontante(ratiosFifthsCircle[ReDieze()]);
		ratiosFifthsCircle[MiDieze()] = quinteMontante(ratiosFifthsCircle[LaDieze()]);
		ratiosFifthsCircle[SiDieze()] = quinteMontante(ratiosFifthsCircle[MiDieze()]);

		// on ne doit pas toucher le si dièze
		for (int i = 0; i < NB_NOTES_STANDARD; i++) {
			ratios[i] = MusicalKnowledge.dansOctave(ratiosFifthsCircle[i]);
		}
		ratios[SiDieze()] = ratiosFifthsCircle[SiDieze()] / MusicalKnowledge.RATIO_OCTAVE_7;
	}

	@Override
	protected void initNoteNames() {
		super.initNoteNames();
		names[ReDieze()] = "ré #";
		names[LaDieze()] = "la #";
		names[MiDieze()] = "mi #";
		names[SiDieze()] = "si #";
	}

	protected int LaDieze() {
		return SiBemol();
	}

	protected int ReDieze() {
		return MiBemol();
	}

	protected int MiDieze() {
		return Fa();
	}

	protected int SiDieze() {
		return IDX_SI_DIEZE;
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
