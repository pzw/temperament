package temperament.ui;

import java.awt.Graphics;
import java.util.List;

import javax.swing.JComponent;

import temperament.model.TemperamentParameterBean;
import temperament.musical.NoteWave;

public class WavePanel extends JComponent {
	private static final long serialVersionUID = 1L;
	private TemperamentParameterBean parameterBean;

	public WavePanel(TemperamentParameterBean parameterBean) {
		this.parameterBean = parameterBean;
	}

	private void paintNote(Graphics g, int noteIndex) {
		NoteWave wave = parameterBean.buildNoteWave(noteIndex);
		float max = wave.getMaxValue();
		int w = getWidth();
		int h = getHeight();
		int h2 = h/2;
		float scaleY = 0;
		if (0 != max) { 
			scaleY = h2 / max;
		}
		
		int viewDuration = parameterBean.getDuration() / 10;
		int totSamples = wave.getSize();
		int stepX = 2;
		int nSteps = w / stepX;
		int stepSample = totSamples / 10 / nSteps;
		for (int i = 0; i < nSteps; i++) {
			int x = i * stepX;
			int y = h2 + (int) (wave.getSample(i * stepSample) * scaleY);
		}
	}
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		int w = getWidth();
		int h = getHeight();
		int cy = h / 2;
		g.drawLine(0, cy, w, cy);
		List<Integer> sel = parameterBean.getSelection();
		if (!sel.isEmpty()) {
			for (Integer n : sel) {
				paintNote(g, n);
			}
		}
	}

}
