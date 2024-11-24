package temperament.musical;

import temperament.constants.IConstants;

public class Gamme {
	private static String[] noteNames;
	private double[] frequencyRatio = new double[IConstants.NB_DEMI_TONS + 1];

	static {
		noteNames = new String[IConstants.NB_DEMI_TONS + 1];
		noteNames[IConstants.DO] = "do";
		noteNames[IConstants.DO_DIEZE] = "do#";
		noteNames[IConstants.RE] = "ré";
		noteNames[IConstants.RE_DIEZE] = "ré#";
		noteNames[IConstants.MI] = "mi";
		noteNames[IConstants.FA] = "fa";
		noteNames[IConstants.FA_DIEZE] = "fa#";
		noteNames[IConstants.SOL] = "sol";
		noteNames[IConstants.SOL_DIEZE] = "sol#";
		noteNames[IConstants.LA] = "la";
		noteNames[IConstants.LA_DIEZE] = "la#";
		noteNames[IConstants.SI] = "si";
		noteNames[IConstants.DO2] = "do";
	}

	public static String getNoteName(int noteIndex) {
		return noteNames[noteIndex];
	}

	public Gamme(Temperament temperament) {
		switch (temperament) {
		case Egal:
			initTemperamentEgal();
			break;
		case Pythagoricien:
			initTemperamentPythagoricien();
			break;
		}
	}

	private void initTemperamentEgal() {
		double step = Math.pow(2.0, 1.0 / 12.0);
		frequencyRatio[0] = 1.0;
		for (int n = 1; n < frequencyRatio.length; n++) {
			frequencyRatio[n] = frequencyRatio[n - 1] * step;
		}
		frequencyRatio[frequencyRatio.length - 1] = 2.0;
	}

	// construit une gamme pythagoricienne
	private void initTemperamentPythagoricien() {
		frequencyRatio[0] = 1.0;
		int note = 0;
		double ratio = 1.0;
		boolean doContinue = true;
		while (doContinue) {
			// passe la note suivante, par quinte
			// do => sol (7 demi-tons pour une quinte)
			// sol => ré
			// ré => la
			// etc
			note = (note + 7) % IConstants.NB_DEMI_TONS;
			ratio *= 1.5; // augmente la fréquence d'une quinte
			if (0 == note) {
				// on est revenu à la note de départ, on la stocke dans la note supplémentaire
				// on laisse un éventuel ratio > 2
				note = IConstants.NB_DEMI_TONS;
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
			frequencyRatio[note] = ratio;
		}
	}

	public void setFrequencyRatio(int idxNote, double ratio) {
		if (idxNote >= 0 && idxNote <= IConstants.NB_DEMI_TONS) {
			frequencyRatio[idxNote] = ratio;

		}
	}

	public double getFrequencyRatio(int idxNote) {
		if (idxNote >= 0 && idxNote <= IConstants.NB_DEMI_TONS) {
			return frequencyRatio[idxNote];
		}
		return 1.0;
	}
}
