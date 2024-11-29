package temperament.musical;

public class WaveGenerator {
	/**
	 * générationn d'une sinusoide
	 * @param pFrequency fréquence [Hz]
	 * @param pDuration durée à générer [ms]
	 * @param pNbSamples nombre d'échantillons désirés
	 * @return
	 */
	public static float[] generateSinus(double pFrequency, double amplitude, double pDuration, int pNbSamples) {
		float[] result = new float[pNbSamples];
		double deltaT = pDuration * pFrequency * 2.0 * Math.PI / 1000.0 / (double) pNbSamples;
		for (int i = 0; i < pNbSamples; i++) {
			double t = ((double) i) * deltaT;
			result[i] = (float) (amplitude * Math.sin(t));
		}
		return result;
	}
	
	/**
	 * retourne l'amplitude d'un signal
	 * @param pInput échantillons du signal
	 * @return
	 */
	public static float getAmplitude(float[] pInput) {
		float result = 0;
		for (int i = 0; i < pInput.length; i++) {
			result = Math.max(result, Math.abs(pInput[i]));
		}
		return result;
	}
	
	/**
	 * adapte l'amplitude d'un signal
	 * @param pInput signal
	 * @param pOutAmplitude amplitude de sortie
	 * @return signal avec l'amplitude corrigée
	 */
	public static float[] amplitude(float[] pInput, float pOutAmplitude) {
		float[] result = new float[pInput.length];
		float inAmplitude = getAmplitude(pInput);
		float gain = inAmplitude > 0.0 ? pOutAmplitude / inAmplitude : 1.0f;
		for (int i = 0; i < pInput.length; i++) {
			result[i] = pInput[i] * gain;
		}
		return result;
	}
	
	/**
	 * retourne la somme de deux signaux
	 * @param pInput1
	 * @param pInput2
	 * @return
	 */
	public static float[] sum(float[] pInput1, float[] pInput2) {
		int len = Math.min(pInput1.length, pInput2.length);
		float[] result = new float[len];
		for (int i = 0; i < len; i++) {
			result[i] = pInput1[i] + pInput2[i];
		}
		return result;
	}
}
