package temperament.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import temperament.model.ApplicationState;
import temperament.player.NotePlayer;
import temperament.player.NoteWave;

/**
 * action pour jouer toutes les notes de la gamme du tempérament courant
 */
public class PlayGammeAction extends AbstractAction {
	private static final long	serialVersionUID	= 1L;
	private ApplicationState	appState;

	public PlayGammeAction(ApplicationState appState) {
		super("Gamme");
		this.appState = appState;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int noteDuration = 400;
		double volume = 0.3;
		double frequency = appState.getNoteFrequency(0);
		NoteWave gamme = NoteWave.buildNoteWaveWithFade(frequency, noteDuration, volume);
		if (null != gamme) {
			int nbNotes = appState.getTemperament().getNbNotesToPlayGamme();
			for (int i = 1; i <= nbNotes; i++) {
				// last note longer than others
				int effectiveDuration = i < nbNotes ? noteDuration : 2 * noteDuration;
				frequency = appState.getNoteFrequency(i);
				NoteWave note = NoteWave.buildNoteWaveWithFade(frequency, effectiveDuration, volume);
				if (null != note) {
					gamme.appendNote(note);
				}
			}
			NotePlayer player = new NotePlayer(gamme);
			player.start();
		}
	}

}
