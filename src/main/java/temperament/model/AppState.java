package temperament.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.jgoodies.binding.beans.Model;

import temperament.musical.ITemperament;
import temperament.musical.NoteWave;
import temperament.musical.Temperaments;

public class AppState extends Model {
	private static final long	serialVersionUID			= 1L;
	public static final String	TEMPERAMENT_PROPERTY		= "temperament";
	public static final String	DURATION_PROPERTY			= "duration";
	public static final String	LA_FREQUENCY_PROPERTY		= "laFrequency";
	public static final String	SELECTION_PROPERTY			= "selection";
	public static final String	WAVE_VIEW_DURATION_PROPERTY	= "waveViewDuration";
	public static final String	WAVE_SHOW_SUM				= "waveShowSum";
	public static final String	WAVE_SHOW_EACH_NOTE			= "waveShowEachNote";
	private ITemperament		temperament					= Temperaments.getInstance().getTemperaments().get(0);
	private int					duration					= 2000;
	private double				laFrequency					= 440.0;
	private List<Integer>		selection					= new ArrayList<Integer>();
	/** durée visualisée dans WavePanel */
	private double				waveViewDuration			= 500.0;
	private boolean				waveShowSum					= true;
	private boolean				waveShowEachNote			= false;

	public ITemperament getTemperament() {
		return temperament;
	}

	public void setTemperament(ITemperament newValue) {
		ITemperament oldValue = getTemperament();
		temperament = newValue;
		firePropertyChange(TEMPERAMENT_PROPERTY, oldValue, newValue.toString());
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int newValue) {
		int oldValue = getDuration();
		duration = newValue;
		firePropertyChange(DURATION_PROPERTY, oldValue, newValue);
	}

	public double getLaFrequency() {
		return laFrequency;
	}

	public void setLaFrequency(double newValue) {
		double oldValue = getLaFrequency();
		laFrequency = newValue;
		firePropertyChange(LA_FREQUENCY_PROPERTY, oldValue, newValue);
	}

	public List<Integer> getSelection() {
		return selection;
	}

	public void setSelection(List<Integer> newValue) {
		List<Integer> oldValue = getSelection();
		boolean change = false;
		if (oldValue.size() != newValue.size()) {
			change = true;
		} else {
			for (int idx = 0; idx < newValue.size() && !change; idx++) {
				change = oldValue.get(idx) != newValue.get(idx);
			}
		}
		selection = newValue;
		if (change) {
			firePropertyChange(SELECTION_PROPERTY, oldValue, newValue);
		}
	}

	public NoteWave buildNoteWave(int index) {
		if (null == temperament)
			return null;
		double frequenceDo = temperament.getFrequenceDo(getLaFrequency());
		double f = frequenceDo * temperament.getNoteFrequencyRatio(index);
		return new NoteWave(f, getDuration(), 1.0);
	}

	public Color getSelectionColor(int idx) {
		switch (idx % 4) {
		case 0:
			return Color.green;
		case 1:
			return Color.yellow;
		case 2:
			return Color.white;
		case 3:
			return Color.cyan;
		}
		return Color.magenta;
	}

	public double getWaveViewDuration() {
		System.out.println("viewDuration:" + waveViewDuration);
		return waveViewDuration;
	}

	public void setWaveViewDuration(double newValue) {
		double oldvalue = getWaveViewDuration();
		waveViewDuration = newValue;
		firePropertyChange(WAVE_VIEW_DURATION_PROPERTY, oldvalue, newValue);
	}

	public boolean isWaveShowSum() {
		return waveShowSum;
	}

	public void setWaveShowSum(boolean newValue) {
		boolean oldValue = isWaveShowSum();
		waveShowSum = newValue;
		firePropertyChange(WAVE_SHOW_SUM, oldValue, newValue);

	}

	public boolean isWaveShowEachNote() {
		return waveShowEachNote;
	}

	public void setWaveShowEachNote(boolean newValue) {
		boolean oldValue = isWaveShowEachNote();
		waveShowEachNote = newValue;
		firePropertyChange(WAVE_SHOW_EACH_NOTE, oldValue, newValue);

	}
}