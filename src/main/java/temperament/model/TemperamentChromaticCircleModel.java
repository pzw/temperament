package temperament.model;

import temperament.musical.ITemperament;

/**
 * modèle pour le positionnement des notes dans un cercle : version chromatique
 * dans une octave
 */
public class TemperamentChromaticCircleModel extends TemperamentBaseCircleModel {
	public TemperamentChromaticCircleModel(ApplicationState appState) {
		super(appState);
	}

	@Override
	protected void computeNotePosition(int noteRank, boolean octave2) {
		// trouver l'angle en fonction du rapport de fréquence
		// 1 => 0
		// 2 => 360
		ITemperament t = getTemperament();
		double log2 = Math.log(2.0);
		double fRatio = t.getNoteFrequencyRatio(noteRank);
		double log = Math.log(fRatio) / log2;
		double angleDegre = 90.0 - log * 360.0;
		double angle = angleDegre * Math.PI / 180.0;
		double cos = Math.cos(angle);
		double sin = Math.sin(angle);
		int radius = r;
		if (octave2) {
			radius += 2 * r2;
		}
		int x = (int) (cx + cos * radius);
		int y = (int) (cy - sin * radius);
		double rTx = r - 3 * r2;
		int xTx = (int) (cx + cos * rTx);
		int yTx = (int) (cy - sin * rTx);
		positions[noteRank].setGraphicPosition(x, y, xTx, yTx);
	}
}
