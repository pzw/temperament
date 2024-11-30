package temperament.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.jgoodies.binding.beans.Model;

import temperament.musical.ITemperament;
import temperament.musical.NoteWave;
import temperament.musical.Temperaments;

public class AppState extends Model {
	private static final long	serialVersionUID				= 1L;
	public static final String	TEMPERAMENT_PROPERTY			= "temperament";
	public static final String	DURATION_PROPERTY				= "duration";
	public static final String	LA_FREQUENCY_PROPERTY			= "laFrequency";
	public static final String	SELECTION_PROPERTY				= "selection";
	public static final String	WAVE_VIEW_DURATION_PROPERTY		= "waveViewDuration";
	public static final String	WAVE_SHOW_SUM_PROPERTY			= "waveShowSum";
	public static final String	WAVE_SHOW_EACH_NOTE_PROPERTY	= "waveShowEachNote";
	public static final String	FREQUENCY_RATIO_PROPERTY		= "frequencyRatio";
	public static final String	FREQUENCY_RATIO_NAME_PROPERTY	= "frequencyRatioName";

	private ITemperament		temperament						= Temperaments.getInstance().getTemperaments().get(0);
	private double				duration						= 2000;
	private double				laFrequency						= 440.0;
	private List<Integer>		selection						= new ArrayList<Integer>();
	/** durée visualisée dans WavePanel */
	private double				waveViewDuration				= 50.0;
	private boolean				waveShowSum						= true;
	private boolean				waveShowEachNote				= false;
	private double				frequencyRatio					= 0.0;
	private String				frequencyRatioName				= "";

	public void setTemperamentTableModel() {
	}

	public ITemperament getTemperament() {
		return temperament;
	}

	public void setTemperament(ITemperament newValue) {
		ITemperament oldValue = getTemperament();
		temperament = newValue;
		firePropertyChange(TEMPERAMENT_PROPERTY, oldValue, newValue.toString());
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double newValue) {
		double oldValue = getDuration();
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
		firePropertyChange(TEMPERAMENT_PROPERTY, null, getTemperament());
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

	public double getNoteFrequency(int index) {
		if (null == temperament)
			return 0.0;
		double frequenceDo = temperament.getFrequenceDo(getLaFrequency());
		double result = frequenceDo * temperament.getNoteFrequencyRatio(index);
		return result;
	}

	public NoteWave buildNoteWave(int index, double pDuration, double pVolume) {
		double f = getNoteFrequency(index);
		if (0.0 == f)
			return null;
		double fadeInDuration = pDuration / 40.0;
		double fadeOutDuration = pDuration / 20.0;
		return new NoteWave(f, pDuration, fadeInDuration, fadeOutDuration, pVolume);
	}

	public Color getSelectionColor(int idx) {
		switch (idx % 6) {
		case 0:
			return Color.green;
		case 1:
			return Color.yellow;
		case 2:
			return Color.white;
		case 3:
			return Color.cyan;
		case 4 :
			return Color.magenta;
		case 5 :
			return Color.orange;
		}
		return Color.magenta;
	}

	public double getWaveViewDuration() {
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
		firePropertyChange(WAVE_SHOW_SUM_PROPERTY, oldValue, newValue);

	}

	public boolean isWaveShowEachNote() {
		return waveShowEachNote;
	}

	public void setWaveShowEachNote(boolean newValue) {
		boolean oldValue = isWaveShowEachNote();
		waveShowEachNote = newValue;
		firePropertyChange(WAVE_SHOW_EACH_NOTE_PROPERTY, oldValue, newValue);
	}

	public double getFrequencyRatio() {
		return frequencyRatio;
	}

	public void setFrequencyRatio(double newValue) {
		double oldvalue = getFrequencyRatio();
		frequencyRatio = newValue;
		firePropertyChange(FREQUENCY_RATIO_PROPERTY, oldvalue, newValue);
	}

	public String getFrequencyRatioName() {
		return frequencyRatioName;
	}

	public void setFrequencyRatioName(String newValue) {
		String oldvalue = getFrequencyRatioName();
		frequencyRatioName = newValue;
		firePropertyChange(FREQUENCY_RATIO_NAME_PROPERTY, oldvalue, newValue);
	}

}
