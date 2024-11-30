package temperament.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JComponent;

import temperament.model.AppState;
import temperament.musical.WaveGenerator;

/**
 * composant qui affiche une ou plusieurs notes
 */
public class WaveComponent extends JComponent {
	private static final long	serialVersionUID	= 1L;
	private AppState			appState;

	public WaveComponent(AppState appState) {
		this.appState = appState;

		appState.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				repaint();
			}
		});
	}

	private float getAmplitude() {
		int h = getHeight();
		int h2 = h / 2;
		int amplitude = h2;
		if (amplitude > 60) {
			amplitude -= 10;
		}
		return amplitude;
	}

	/**
	 * affiche un signal
	 * 
	 * @param g
	 * @param samples échantillons à afficher (on a un échantillon par pixel en x)
	 * @param color   couleur de la courbe
	 */
	private void paintSignal(Graphics g, float[] samples, Color color, float pStrokeWidth) {
		int w = getWidth();
		int h = getHeight();
		int h2 = h / 2;

		int xPrec = 0;
		int yPrec = h2 - (int) (samples[0]);
		Graphics2D g2d = (Graphics2D) g;
		Stroke bkp = g2d.getStroke();
		g2d.setStroke(new BasicStroke(pStrokeWidth));
		g.setColor(color);
		for (int x = 1; x < w; x++) {
			int y = h2 - (int) samples[x];
			g.drawLine(xPrec, yPrec, x, y);
			xPrec = x;
			yPrec = y;
		}
		g2d.setStroke(bkp);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		int w = getWidth();
		int h = getHeight();
		g.setColor(Color.black);
		g.fillRect(0, 0, w, h);
		int cy = h / 2;
		g.setColor(Color.gray);
		g.drawLine(0, cy, w, cy);
		List<Integer> sel = appState.getSelection();
		if (!sel.isEmpty()) {
//			double fadeInDuration = appState.getWaveViewDuration() / 20;
//			double fadeOutDuration = appState.getWaveViewDuration() / 10;
			double fadeInDuration = 0.0;
			double fadeOutDuration = 0.0;
			if (appState.isWaveShowEachNote()) {
				int idx = 0;
				for (Integer n : sel) {
					double f = appState.getNoteFrequency(n);
					float[] wave = WaveGenerator.generateSinus(f, getAmplitude(), appState.getWaveViewDuration(), fadeInDuration, fadeOutDuration, w);
					paintSignal(g, wave, appState.getSelectionColor(idx++), 1.0f);
				}
			}

			if (appState.isWaveShowSum()) {
				float[] samples = null;
				for (Integer n : sel) {
					double f = appState.getNoteFrequency(n);
					float[] wave = WaveGenerator.generateSinus(f, getAmplitude(), appState.getWaveViewDuration(), fadeInDuration, fadeOutDuration, w);
					if (null == samples) {
						samples = wave;
					} else {
						samples = WaveGenerator.sum(samples, wave);
					}
				}
				samples = WaveGenerator.amplitude(samples, getAmplitude());
				paintSignal(g, samples, Color.red, 2.0f);
			}
		}
	}

}
