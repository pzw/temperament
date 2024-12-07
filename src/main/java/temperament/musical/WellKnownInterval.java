package temperament.musical;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class WellKnownInterval {
	private double							ratio;
	private String							name;
	private static List<WellKnownInterval>	wellKnownIntervals;
	private static NumberFormat				nfCents;
	static {
		wellKnownIntervals = new ArrayList<WellKnownInterval>();
		wellKnownIntervals.add(new WellKnownInterval(TemperamentBase.RATIO_OCTAVE, "octave"));
		wellKnownIntervals.add(new WellKnownInterval(TemperamentBase.RATIO_SECONDE_MINEURE, "seconde mineure"));
		wellKnownIntervals.add(new WellKnownInterval(TemperamentBase.RATIO_SECONDE_MAJEURE, "seconde majeure"));
		wellKnownIntervals.add(new WellKnownInterval(TemperamentBase.RATIO_TIERCE_MINEURE, "tierce mineure"));
		wellKnownIntervals.add(new WellKnownInterval(TemperamentBase.RATIO_TIERCE_MAJEURE, "tierce majeure"));
		wellKnownIntervals.add(new WellKnownInterval(TemperamentBase.RATIO_QUARTE, "quarte"));
		wellKnownIntervals.add(new WellKnownInterval(TemperamentBase.RATIO_QUINTE, "quinte"));
		wellKnownIntervals.add(new WellKnownInterval(TemperamentBase.RATIO_SIXTE_MINEURE, "sixte mineure"));
		wellKnownIntervals.add(new WellKnownInterval(TemperamentBase.RATIO_SIXTE_MAJEURE, "sixte majeure"));
		wellKnownIntervals.add(new WellKnownInterval(TemperamentBase.RATIO_OCTAVE, "octave"));

		nfCents = NumberFormat.getNumberInstance();
		nfCents.setMaximumFractionDigits(0);

	}

	private WellKnownInterval(double ratio, String name) {
		this.ratio = ratio;
		this.name = name;
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
			double diff = Math.abs((ratio / wki.ratio) - 1.0);
			if (diff < bestDiff) {
				bestInterval = wki;
				bestDiff = diff;
			}
		}
		StringBuilder result = new StringBuilder();

		if (bestDiff < 0.03) {
			result.append(bestInterval.name);
			double r2 = ratio / bestInterval.ratio;
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
}
