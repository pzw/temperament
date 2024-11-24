package temperament.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.jgoodies.binding.beans.Model;

import temperament.musical.ITemperament;
import temperament.musical.NoteWave;
import temperament.musical.Temperaments;

public class TemperamentParameterBean extends Model {
	private static final long	serialVersionUID		= 1L;
	public static final String	TEMPERAMENT_PROPERTY	= "temperament";
	public static final String	DURATION_PROPERTY		= "duration";
	public static final String	LA_FREQUENCY_PROPERTY	= "laFrequency";
	public static final String	SELECTION_PROPERTY		= "selection";

	private ITemperament		temperament				= Temperaments.getInstance().getTemperaments().get(0);
	private int					duration				= 2000;
	private double				laFrequency				= 440.0;
	private List<Integer>		selection				= new ArrayList<Integer>();

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
			return Color.red;
		case 3:
			return Color.cyan;
		}
		return Color.white;
	}
}
