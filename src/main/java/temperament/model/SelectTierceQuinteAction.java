package temperament.model;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;

import temperament.musical.ITemperament;
import temperament.musical.TemperamentBase;

public class SelectTierceQuinteAction extends AbstractAction {
	private static final long	serialVersionUID	= 1L;
	private AppState			appState;

	public SelectTierceQuinteAction(AppState appState) {
		super("Sel 3 + 5");
		this.appState = appState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Integer> sel = appState.getSelection();
		if (sel.isEmpty())
			return;

		ITemperament t = appState.getTemperament();
		int idxNote1 = sel.get(0);
		double ratio1 = t.getNoteFrequencyRatio(idxNote1);
		double ratio2 = ratio1 * TemperamentBase.RATIO_TIERCE_MAJEURE;
		double ratio3 = ratio1 * TemperamentBase.RATIO_QUINTE;
		
		int idxNote2 = t.findNoteIndexByRatio(ratio2);
		int idxNote3 = t.findNoteIndexByRatio(ratio3);
		if (-1 != idxNote2 && -1 != idxNote3) {
			ArrayList<Integer> newSel = new ArrayList<Integer>();
			newSel.add(idxNote1);
			newSel.add(idxNote2);
			newSel.add(idxNote3);
			appState.setSelection(newSel);
		}
	}

}
