package temperament.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JComponent;

import temperament.model.AppState;
import temperament.musical.NoteWave;

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

	private void paintNote(Graphics g, NoteWave wave, Color color) {
		float max = wave.getMaxValue();
		int w = getWidth();
		int h = getHeight();
		int h2 = h / 2;
		int amplitude = h2;
		if (amplitude > 60) {
			amplitude -= 10;
		}

		float scaleY = 0;
		if (0 != max) {
			scaleY = amplitude / max;
		}

		double waveViewDuration = appState.getWaveViewDuration();
		double waveDuration = appState.getDuration();
		waveViewDuration = Math.min(waveViewDuration, waveDuration);
		// nombre d'échantillons montrés dans une vue de w pixel de largeur
		double nbSampleInView = ((double) wave.getSize()) * waveViewDuration / waveDuration;
		// avance dans l'échantillon pour 1 pixel
		double stepSampleDouble = nbSampleInView / w;
		int stepSample;
		int stepX;
		if (stepSampleDouble < 1.0) {
			stepX = (int) (1.0 / stepSampleDouble);
			stepSample = 1;
		} else {
			stepX = 1;
			stepSample = (int) stepSampleDouble;
		}

		int nSteps = w / stepX;
		int xPrec = 0;
		int yPrec = h2 - (int) (wave.getSample(0) * scaleY);
//		System.out.println("w:" + w + ",nbSample:" + nbSampleInView + ",stepX:" + stepX + ",stepSample:" + stepSample);
		g.setColor(color);
		for (int i = 0; i < nSteps; i++) {
			int xCur = 2 * i * stepX;
			double waveIndex = i * stepSample;
			int yCur = h2 - (int) (wave.getSample((int) waveIndex) * scaleY);
			g.drawLine(xPrec, yPrec, xCur, yCur);
			xPrec = xCur;
			yPrec = yCur;
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int w = getWidth();
		int h = getHeight();
		g.setColor(Color.black);
		g.fillRect(0, 0, w, h);
		int cy = h / 2;
		g.setColor(Color.gray);
		g.drawLine(0, cy, w, cy);
		List<Integer> sel = appState.getSelection();
		if (!sel.isEmpty()) {
			if (appState.isWaveShowEachNote()) {
				int idx = 0;
				for (Integer n : sel) {
					NoteWave wave = appState.buildNoteWave(n);
					paintNote(g, wave, appState.getSelectionColor(idx++));
				}
			}

			if (appState.isWaveShowSum()) {
				NoteWave cumul = null;
				for (Integer n : sel) {
					NoteWave wave = appState.buildNoteWave(n);
					if (null == cumul) {
						cumul = wave;
					} else {
						cumul.addNote(wave);
					}
				}
				paintNote(g, cumul, Color.red);
			}
		}
	}

}
