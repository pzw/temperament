package temperament.model;

import com.jgoodies.binding.beans.Model;

import temperament.musical.ITemperament;
import temperament.musical.Temperaments;

public class GammeParameterBean extends Model {
	private static final long serialVersionUID = 1L;
	public static final String TEMPERAMENT_PROPERTY = "temperament";
	public static final String DURATION_PROPERTY = "duration";

	private ITemperament temperament = Temperaments.getInstance().getTemperaments().get(0);
	private int duration = 2000;

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
}
