package temperament.model;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import temperament.musical.NoteWave;
import temperament.player.NotePlayer;

public class PlaySelectionAction extends AbstractAction {
	private static final long	serialVersionUID	= 1L;
	private AppState			appStats;

	public PlaySelectionAction(AppState appState) {
		super("Ecouter");
		this.appStats = appState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		NoteWave note = null;
		List<Integer> sel = appStats.getSelection();
		if (sel.isEmpty())
			return;

		for (Integer n : sel) {
			NoteWave wave = appStats.buildNoteWave(n);
			if (null == note) {
				note = wave;
			} else {
				note.addNote(wave);
			}
		}

		if (null != note) {
			NotePlayer player = new NotePlayer(note);
			player.start();
		}
	}

}
