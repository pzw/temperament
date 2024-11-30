package temperament.model;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import temperament.musical.NoteWave;
import temperament.player.NotePlayer;

public class PlayGammeAction extends AbstractAction {
	private static final long	serialVersionUID	= 1L;
	private AppState			appState;

	public PlayGammeAction(AppState appState) {
		super("Gamme");
		this.appState = appState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int dureeNote = 400;
		double volume = 0.3;
		NoteWave gamme = appState.buildNoteWave(0, dureeNote, volume);
		if (null != gamme) {
			int nbNotes = appState.getTemperament().getNbNotesGamme();
			for (int i = 1; i <= nbNotes; i++) {
				int duree = i < nbNotes ? dureeNote : 2 * dureeNote;
				NoteWave note = appState.buildNoteWave(i, duree, volume);
				if (null != note) {
					gamme.appendNote(note);
				}
			}
			NotePlayer player = new NotePlayer(gamme);
			player.start();
		}
	}

}
