package temperament.musical;

public class TemperamentPythagore extends TemperamentBase {

	public TemperamentPythagore() {
		super();
		ratios[0] = 1.0;
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
			ratio *= 1.5; // augmente la fréquence d'une quinte
			if (0 == note) {
				// on est revenu à la note de départ, on la stocke dans la note supplémentaire
				// on laisse un éventuel ratio > 2
				note = NB_NOTES_STANDARD;
				doContinue = false;
				if (ratio > 2.0) {
					ratio = ratio / 2.0;
				}
			} else {
				// si l'on dépasse l'octave, on revient à l'octave inférieure
				if (ratio > 2.0) {
					ratio = ratio / 2.0;
				}
			}
			ratios[note] = ratio;
		}
	}

	@Override
	public String toString() {
		return "pythagoricien";
	}
}
