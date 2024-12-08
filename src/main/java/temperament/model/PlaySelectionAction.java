package temperament.model;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;

import temperament.player.NotePlayer;
import temperament.player.NoteWave;

public class PlaySelectionAction extends AbstractAction {
	private static final long	serialVersionUID	= 1L;
	private ApplicationState			appState;

	public PlaySelectionAction(ApplicationState appState) {
		super("Ecouter");
		this.appState = appState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		NoteWave note = null;
		List<Integer> sel = appState.getSelection();
		if (sel.isEmpty())
			return;

		for (Integer n : sel) {
			double frequency = appState.getNoteFrequency(n);
			NoteWave wave = NoteWave.buildNoteWaveWithFade(frequency, 2000.0, 0.5);
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
