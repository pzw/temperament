package sound.temperament;

import com.jgoodies.binding.beans.Model;

public class GammeParameterBean extends Model {
	private static final long serialVersionUID = 1L;
	public static final String TEMPERAMENT_PROPERTY = "temperament";
	public static final String DURATION_PROPERTY = "duration";

	private Temperament temperament = Temperament.Egal;
	private int duration = 2000;

	public Temperament getTemperament() {
		return temperament;
	}

	public void setTemperament(Temperament newValue) {
		Temperament oldValue = getTemperament();
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
}
