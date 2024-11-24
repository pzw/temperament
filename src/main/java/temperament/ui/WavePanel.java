package temperament.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JComponent;

import temperament.model.AppState;
import temperament.musical.NoteWave;

public class WavePanel extends JComponent {
	private static final long	serialVersionUID	= 1L;
	private AppState			appState;

	public WavePanel(AppState appState) {
		this.appState = appState;
		appState.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				repaint();
			}
		});
	}

	private void paintNote(Graphics g, int noteIndex, Color color) {
		NoteWave wave = appState.buildNoteWave(noteIndex);
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

		int viewDuration = appState.getDuration() / 10;
		int totSamples = wave.getSize();
		int stepX = 4;
		int nSteps = w / stepX;
		int stepSample = totSamples / 40 / nSteps;
		System.out.println("totSamples:" + totSamples + ", nSteps:" + nSteps + ",stepSample:" + stepSample);
		int xPrec = 0;
		int yPrec = h2 - (int) (wave.getSample(0) * scaleY);
		g.setColor(color);
		for (int i = 0; i < nSteps; i++) {
			int xCur = 2 * i * stepX;
			int yCur = h2 - (int) (wave.getSample(i * stepSample) * scaleY);
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
			int idx = 0;
			for (Integer n : sel) {
				paintNote(g, n, appState.getSelectionColor(idx++));
			}
		}
	}

}
