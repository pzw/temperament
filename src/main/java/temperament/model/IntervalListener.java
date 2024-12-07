package temperament.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class IntervalListener implements PropertyChangeListener {
	private AppState appState;

	public IntervalListener(AppState pAppState) {
		appState = pAppState;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (AppState.SELECTION_PROPERTY.equals(evt.getPropertyName())) {
			List<Integer> sel = appState.getSelection();
			int selSize = sel.size();
			StringBuilder descr = new StringBuilder();
			if (2 == selSize) {
				descr.append(appState.getIntervalDescription(sel.get(0), sel.get(1)));
			} else if (3 == selSize) {
				descr.append(appState.getIntervalDescription(sel.get(0), sel.get(1)));
				descr.append("  /  ");
				descr.append(appState.getIntervalDescription(sel.get(1), sel.get(2)));
				descr.append("  /  ");
				descr.append(appState.getIntervalDescription(sel.get(0), sel.get(2)));

			}
			appState.setSelectionDescription(descr.toString());
		}
	}

}
