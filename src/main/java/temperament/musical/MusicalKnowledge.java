package temperament.musical;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * rassemble des notions musicales de base
 */
public class MusicalKnowledge {
	public static final double				RATIO_UNISSON				= 1.0;
	public static final double				RATIO_OCTAVE				= 2.0;
	public static final double				RATIO_OCTAVE_2				= 2.0;
	public static final double				RATIO_OCTAVE_3				= 4.0;
	public static final double				RATIO_OCTAVE_4				= 8.0;
	public static final double				RATIO_OCTAVE_5				= 16.0;
	public static final double				RATIO_OCTAVE_6				= 32.0;
	public static final double				RATIO_OCTAVE_7				= 64.0;
	public static final double				RATIO_OCTAVE_8				= 128.0;
	public static final double				RATIO_SECONDE_MINEURE		= 10.0 / 9.0;
	public static final double				RATIO_SECONDE_MAJEURE		= 9.0 / 8.0;
	public static final double				RATIO_TIERCE_MINEURE		= 6.0 / 5.0;
	public static final double				RATIO_TIERCE_MAJEURE		= 5.0 / 4.0;
	public static final double				RATIO_QUARTE				= 4.0 / 3.0;
	public static final double				RATIO_QUINTE				= 3.0 / 2.0;
	public static final double				RATIO_QUINTE_MESOTONIQUE4	= Math.pow(5.0, 0.25);
	public static final double				RATIO_SIXTE_MINEURE			= 8.0 / 5.0;
	public static final double				RATIO_SIXTE_MAJEURE			= 5.0 / 3.0;
	public static final String				NOM_DO						= "do";
	public static final String				NOM_DO_DIEZE				= "do #";
	public static final String				NOM_RE_BEMOL				= "ré b";
	public static final String				NOM_RE						= "ré";
	public static final String				NOM_RE_DIEZE				= "ré #";
	public static final String				NOM_MI_BEMOL				= "mi b";
	public static final String				NOM_MI						= "mi";
	public static final String				NOM_MI_DIEZE				= "mi #";
	public static final String				NOM_FA						= "fa";
	public static final String				NOM_FA_DIEZE				= "fa #";
	public static final String				NOM_SOL						= "sol";
	public static final String				NOM_SOL_DIEZE				= "sol #";
	public static final String				NOM_LA_BEMOL				= "la b";
	public static final String				NOM_LA						= "la";
	public static final String				NOM_LA_DIEZE				= "la #";
	public static final String				NOM_SI_BEMOL				= "si b";
	public static final String				NOM_SI						= "si";
	public static final String				NOM_SI_DIEZE				= "si #";

	private static List<WellKnownInterval>	wellKnownIntervals;
	private static NumberFormat				nfCents;

	static {
		wellKnownIntervals = new ArrayList<WellKnownInterval>();
		wellKnownIntervals.add(new WellKnownInterval(RATIO_OCTAVE, "octave"));
		wellKnownIntervals.add(new WellKnownInterval(RATIO_SECONDE_MINEURE, "seconde mineure"));
		wellKnownIntervals.add(new WellKnownInterval(RATIO_SECONDE_MAJEURE, "seconde majeure"));
		wellKnownIntervals.add(new WellKnownInterval(RATIO_TIERCE_MINEURE, "tierce mineure"));
		wellKnownIntervals.add(new WellKnownInterval(RATIO_TIERCE_MAJEURE, "tierce majeure"));
		wellKnownIntervals.add(new WellKnownInterval(RATIO_QUARTE, "quarte"));
		wellKnownIntervals.add(new WellKnownInterval(RATIO_QUINTE, "quinte"));
		wellKnownIntervals.add(new WellKnownInterval(RATIO_SIXTE_MINEURE, "sixte mineure"));
		wellKnownIntervals.add(new WellKnownInterval(RATIO_SIXTE_MAJEURE, "sixte majeure"));
		wellKnownIntervals.add(new WellKnownInterval(RATIO_OCTAVE, "octave"));

		nfCents = NumberFormat.getNumberInstance();
		nfCents.setMaximumFractionDigits(0);
	}

	/**
	 * garantir qu'un ratio est situé entre l'unisson et l'octave
	 * 
	 * @param ratio
	 * @return
	 */
	public static double dansOctave(double ratio) {
		if (0 == ratio)
			return 0.0;

		double result = ratio;
		while (result > RATIO_OCTAVE) {
			result = result / RATIO_OCTAVE;
		}
		while (result < RATIO_UNISSON) {
			result = result * RATIO_OCTAVE;
		}
		return result;
	}

	private static void appendCents(StringBuilder sb, double cents) {
		if (cents > 0) {
			sb.append("+");
		}
		sb.append(nfCents.format(cents));
		sb.append(" cents");
	}

	public static String getFrequencyRatioName(double ratio) {
		double bestDiff = 1000.0;
		WellKnownInterval bestInterval = null;
		for (WellKnownInterval wki : wellKnownIntervals) {
			double diff = Math.abs((ratio / wki.getRatio()) - 1.0);
			if (diff < bestDiff) {
				bestInterval = wki;
				bestDiff = diff;
			}
		}
		StringBuilder result = new StringBuilder();

		if (bestDiff < 0.03) {
			result.append(bestInterval.getName());
			double r2 = ratio / bestInterval.getRatio();
			if (Math.abs(r2 - 1.0) > 0.0001) {
				// on ajoute la différence en cents par rapport à l'accord juste
				result.append(" ");
				appendCents(result, toCents(r2));
			}
		} else {
			appendCents(result, toCents(ratio));
		}
		return result.toString();
	}

	/**
	 * conversion d'un ratio de fréquences en "cents"
	 * 
	 * @param frequencyRatio
	 * @return
	 */
	public static double toCents(double frequencyRatio) {
		return 1200.0 * Math.log(frequencyRatio) / Math.log(2.0);
	}

	/**
	 * détermine si un rapport de fréquence correspond à un intervalle connu
	 * 
	 * @param ratio
	 * @return
	 */
	public static boolean isWellKnownInterval(double ratio) {
		for (WellKnownInterval wki : wellKnownIntervals) {
			if (almostEqual(ratio, wki.getRatio())) {
				return true;
			}
		}
		return false;
	}

	private static boolean almostEqual(double v1, double v2) {
		return Math.abs(v1 - v2) < 0.00000001;
	}
}
