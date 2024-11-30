package temperament.musical;

public class TemperamentPythagore extends TemperamentBase {
	/** une note de plus pour le si dièze */
	private static final int NB_NOTES = NB_NOTES_STANDARD+1;
	public TemperamentPythagore() {
	}

	@Override
	protected void initRatios() {
		ratios = new double[2*NB_NOTES];
		ratios[0] = RATIO_UNISSON;
		int note = 0;
		double ratio = 1.0;
		boolean doContinue = true;
		while (doContinue) {
			// passe la note suivante, par quinte
			// do => sol (7 demi-tons pour une quinte)
			// sol => ré
			// ré => la
			// etc
			note = (note + 7) % NB_NOTES_STANDARD;
			ratio *= RATIO_QUINTE; // augmente la fréquence d'une quinte
			if (0 == note) {
				// on est revenu à la note de départ, on la stocke dans la note supplémentaire
				// on laisse un éventuel ratio > 2
				note = NB_NOTES-1;
				doContinue = false;
			} else {
				ratio = dansOctave(ratio);
			}
			ratios[note] = ratio;
		}
	}

	@Override
	protected void initNoteNames() {
		super.initNoteNames();
		names[NB_NOTES-1] = "si dièze";
	}

	@Override
	public String toString() {
		return "pythagoricien : 12 quintes montantes";
	}

	@Override
	public int getNbNotesGamme() {
		return NB_NOTES;
	}
	
	
}
