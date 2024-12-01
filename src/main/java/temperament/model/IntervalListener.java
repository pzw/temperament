package temperament.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import temperament.musical.TemperamentBase;

public class IntervalListener implements PropertyChangeListener {
	private AppState appState;

	public IntervalListener(AppState pAppState) {
		appState = pAppState;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (AppState.SELECTION_PROPERTY.equals(evt.getPropertyName())) {
			List<Integer> sel = appState.getSelection();
			double ratio = 0.0;
			if (sel.size() == 2) {
				double f1 = appState.getNoteFrequency(sel.get(0));
				double f2 = appState.getNoteFrequency(sel.get(1));
				ratio = Math.max(f1, f2) / Math.min(f1, f2);
				appState.setFrequencyRatio(ratio);
				appState.setFrequencyRatioName(TemperamentBase.getFrequencyRatioName(ratio));
			} else {
				appState.setFrequencyRatio(0.0);
				appState.setFrequencyRatioName("choisir 2 notes");
			}
		}
	}

}
