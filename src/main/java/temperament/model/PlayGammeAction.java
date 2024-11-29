package temperament.model;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import temperament.musical.NoteWave;
import temperament.player.NotePlayer;

public class PlayGammeAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private AppState appState;

	public PlayGammeAction(AppState appState) {
		super("Gamme");
		this.appState = appState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int dureeNote = 400;
		double volume = 0.3;
		NoteWave gamme = appState.buildNoteWave(0, dureeNote, volume);
		System.out.println("0:" + gamme.getWaveLength());
		if (null != gamme) {
			int nbNotes = appState.getTemperament().getNbNotes();
			for (int i = 1; i <= nbNotes; i++) {
				int duree = i < nbNotes ? dureeNote : 2 * dureeNote;
				NoteWave note = appState.buildNoteWave(i, duree, volume);
				if (null != note) {
					gamme.appendNote(note);
				}
				System.out.println(i + ":" + gamme.getWaveLength());
			}
			NotePlayer player = new NotePlayer(gamme);
			player.start();
		}
	}

}