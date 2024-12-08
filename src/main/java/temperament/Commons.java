package temperament;

import java.awt.Color;
import java.text.NumberFormat;

/**
 * quelques utilitaires communs
 */
public class Commons {
	public static NumberFormat	nfInterval;
	public static NumberFormat	nfCents;
	public static NumberFormat nfFrequency;
	public static NumberFormat nfFrequencyRatio;
	
	static {
		nfInterval = NumberFormat.getNumberInstance();
		nfInterval.setMaximumFractionDigits(3);

		nfCents = NumberFormat.getNumberInstance();
		nfCents.setMaximumFractionDigits(0);
		nfCents.setMinimumFractionDigits(0);
		
		nfFrequency = NumberFormat.getNumberInstance();
		nfFrequency.setMaximumFractionDigits(5);
		nfFrequency.setMinimumFractionDigits(5);

		nfFrequencyRatio = NumberFormat.getNumberInstance();
		nfFrequencyRatio.setMaximumFractionDigits(5);
		nfFrequencyRatio.setMinimumFractionDigits(5);
	}

	/**
	 * retourne la couleur à utiliser pour montrer une note sélectionnée
	 * 
	 * @param idx
	 * @return
	 */
	public static Color getSelectionColor(int idx) {
		switch (idx % 6) {
		case 0:
			return Color.green;
		case 1:
			return Color.blue;
		case 2:
			return Color.white;
		case 3:
			return Color.cyan;
		case 4:
			return Color.magenta;
		case 5:
			return Color.orange;
		}
		return Color.magenta;
	}

}
