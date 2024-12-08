package temperament.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.jgoodies.binding.beans.Model;

import temperament.Commons;
import temperament.musical.ITemperament;
import temperament.musical.MusicalKnowledge;
import temperament.musical.TemperamentAbbatialePayerne;
import temperament.musical.TemperamentEgal;
import temperament.musical.TemperamentMesotonique4;
import temperament.musical.TemperamentPythagore;
import temperament.musical.TemperamentPythagore2;
import temperament.musical.TemperamentWerckmeister1;
import temperament.musical.TemperamentWerckmeister2;
import temperament.musical.TemperamentWerckmeister3;
import temperament.musical.TemperamentWerckmeister4;
import temperament.musical.json.TemperamentJson;

/**
 * view model de l'application
 */
public class ApplicationState extends Model {
	private static final long		serialVersionUID				= 1L;
	public static final String		TEMPERAMENT_PROPERTY			= "temperament";
	public static final String		LA_FREQUENCY_PROPERTY			= "laFrequency";
	public static final String		SELECTION_PROPERTY				= "selection";
	public static final String		WAVE_VIEW_DURATION_PROPERTY		= "waveViewDuration";
	public static final String		WAVE_SHOW_SUM_PROPERTY			= "waveShowSum";
	public static final String		WAVE_SHOW_EACH_NOTE_PROPERTY	= "waveShowEachNote";
	public static final String		SELECTION_DESCRIPTION_PROPERTY	= "selectionDescription";
	public static final String		DISPLAY_FIFTHS					= "displayFifths";
	public static final String		DISPLAY_MAJOR_THIRDS			= "displayMajorThirds";
	public static final String		AUTO_SELECT_MAJOR_THIRD			= "autoSelectMajorThird";
	public static final String		AUTO_SELECT_FIFTH				= "autoSelectFifth";
	private ITemperament			temperament;
	private double					laFrequency						= 440.0;
	private List<Integer>			selection						= new ArrayList<Integer>();
	/** durée visualisée dans WavePanel */
	private double					waveViewDuration				= 240.0;
	private boolean					waveShowSum						= true;
	private boolean					waveShowEachNote				= false;
	private String					selectionDescription			= "";
	private boolean					displayFifths					= true;
	private boolean					displayMajorThirds				= false;
	private boolean					autoSelectMajorThird			= false;
	private boolean					autoSelectFifth					= false;
	private ArrayList<ITemperament>	temperaments;

	public ApplicationState() {
		temperaments = new ArrayList<ITemperament>();
		temperaments.add(new TemperamentEgal());
		temperaments.add(new TemperamentPythagore());
		temperaments.add(new TemperamentPythagore2());
		temperaments.add(new TemperamentMesotonique4());
		temperaments.add(new TemperamentAbbatialePayerne());
		temperaments.add(new TemperamentWerckmeister1());
		temperaments.add(new TemperamentWerckmeister2());
		temperaments.add(new TemperamentWerckmeister3());
		temperaments.add(new TemperamentWerckmeister4());
		temperaments.add(new TemperamentJson("assets/tierce_quinte.json"));
		temperament = temperaments.get(0);
	}

	/**
	 * retourne la liste des tempéraments connus de l'application
	 * 
	 * @return
	 */
	public List<ITemperament> getTemperaments() {
		return temperaments;
	}

	/**
	 * retourne le tempérament actuellement sélectionné
	 */
	public ITemperament getTemperament() {
		return temperament;
	}

	public void setTemperament(ITemperament newValue) {
		ITemperament oldValue = getTemperament();
		List<String> saveSelection = getSelectionAsStrings();
		// System.out.println("SaveSelection : " +saveSelection);
		temperament = newValue;
		firePropertyChange(TEMPERAMENT_PROPERTY, oldValue, newValue.toString());
		if (null != saveSelection && saveSelection.size() > 0) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					List<Integer> sel = getSelectionByFullNames(saveSelection);
					setSelection(sel);
				}
			});
		}
	}

	/**
	 * retourne la fréquence choisie pour le LA
	 * 
	 * @return
	 */
	public double getLaFrequency() {
		return laFrequency;
	}

	public void setLaFrequency(double newValue) {
		double oldValue = getLaFrequency();
		laFrequency = newValue;
		firePropertyChange(LA_FREQUENCY_PROPERTY, oldValue, newValue);
		firePropertyChange(TEMPERAMENT_PROPERTY, null, getTemperament());
	}

	/**
	 * retourne les indices des notes sélectionnées
	 * 
	 * @return
	 */
	public List<Integer> getSelection() {
		return new ArrayList<Integer>(selection);
	}

	public void setSelection(List<Integer> newValue) {
		List<Integer> oldValue = getSelection();
		List<Integer> nv = adaptSelection(newValue);

		boolean change = false;
		if (oldValue.size() != nv.size()) {
			change = true;
		} else {
			for (int idx = 0; idx < nv.size() && !change; idx++) {
				change = oldValue.get(idx) != nv.get(idx);
			}
		}
		selection = nv;
		updateSelectionDescription();
		if (change) {
			firePropertyChange(SELECTION_PROPERTY, oldValue, nv);
		}
	}

	/**
	 * retourne la fréquence d'une note selon son index
	 * 
	 * @param index
	 * @return
	 */
	public double getNoteFrequency(int index) {
		if (null == temperament)
			return 0.0;
		double frequenceDo = temperament.getFrequenceDo(getLaFrequency());
		double result = frequenceDo * temperament.getNoteFrequencyRatio(index);
		return result;
	}

	/**
	 * retourne la durée de visualisation de l'onde de la note
	 * 
	 * @return
	 */
	public double getWaveViewDuration() {
		return waveViewDuration;
	}

	public void setWaveViewDuration(double newValue) {
		double oldvalue = getWaveViewDuration();
		waveViewDuration = newValue;
		firePropertyChange(WAVE_VIEW_DURATION_PROPERTY, oldvalue, newValue);
	}

	/**
	 * retourne true si le composant de visualisation de l'onde doit montrer la
	 * somme des ondes des notes sélectionnées
	 * 
	 * @return
	 */
	public boolean isWaveShowSum() {
		return waveShowSum;
	}

	public void setWaveShowSum(boolean newValue) {
		boolean oldValue = isWaveShowSum();
		waveShowSum = newValue;
		firePropertyChange(WAVE_SHOW_SUM_PROPERTY, oldValue, newValue);

	}

	/**
	 * retourne true si le composant de visualisation de l'onde doit montrer les
	 * ondes individuelles des notes sélectionnées
	 * 
	 * @return
	 */
	public boolean isWaveShowEachNote() {
		return waveShowEachNote;
	}

	public void setWaveShowEachNote(boolean newValue) {
		boolean oldValue = isWaveShowEachNote();
		waveShowEachNote = newValue;
		firePropertyChange(WAVE_SHOW_EACH_NOTE_PROPERTY, oldValue, newValue);
	}

	/**
	 * retourne un texte descriptif des intervalles des notes actuellement
	 * sélectionnées
	 * 
	 * @return
	 */
	public String getSelectionDescription() {
		return selectionDescription;
	}

	public void setSelectionDescription(String newValue) {
		String oldvalue = getSelectionDescription();
		selectionDescription = newValue;
		firePropertyChange(SELECTION_DESCRIPTION_PROPERTY, oldvalue, newValue);
	}

	/**
	 * retourne true si l'on doit afficher les rapports de quintes
	 * 
	 * @return
	 */
	public boolean isDisplayFifths() {
		return displayFifths;
	}

	public void setDisplayFifths(boolean newValue) {
		boolean oldValue = isDisplayFifths();
		this.displayFifths = newValue;
		firePropertyChange(DISPLAY_FIFTHS, oldValue, newValue);
		if (isDisplayMajorThirds() && isDisplayFifths()) {
			setDisplayMajorThirds(false);
		}
	}

	/**
	 * retourne true si l'on doit afficher les rapports des tierces majeures
	 * 
	 * @return
	 */
	public boolean isDisplayMajorThirds() {
		return displayMajorThirds;
	}

	public void setDisplayMajorThirds(boolean newValue) {
		boolean oldValue = isDisplayMajorThirds();
		this.displayMajorThirds = newValue;
		firePropertyChange(DISPLAY_MAJOR_THIRDS, oldValue, newValue);
		if (isDisplayMajorThirds() && isDisplayFifths()) {
			setDisplayFifths(false);
		}
	}

	/**
	 * retourne true si l'on doit automatiquement sélectionnée la tierce majeure
	 * lorsqu'une note est sélectionnée par l'utilisateur
	 * 
	 * @return
	 */
	public boolean isAutoSelectMajorThird() {
		return autoSelectMajorThird;
	}

	private void redoSelection() {
		List<Integer> sel = getSelection();
		if (!sel.isEmpty()) {
			List<Integer> newSel = new ArrayList<Integer>();
			newSel.add(sel.get(0));
			setSelection(newSel);
		}
	}

	public void setAutoSelectMajorThird(boolean newValue) {
		boolean oldValue = isAutoSelectMajorThird();
		this.autoSelectMajorThird = newValue;
		firePropertyChange(AUTO_SELECT_MAJOR_THIRD, oldValue, newValue);
		redoSelection();
	}

	/**
	 * retourne true si l'on doit automatiquement sélectionnée la quinte lorsqu'une
	 * note est sélectionnée par l'utilisateur
	 * 
	 * @return
	 */
	public boolean isAutoSelectFifth() {
		return autoSelectFifth;
	}

	public void setAutoSelectFifth(boolean newValue) {
		boolean oldValue = isAutoSelectFifth();
		this.autoSelectFifth = newValue;
		firePropertyChange(AUTO_SELECT_FIFTH, oldValue, newValue);
		redoSelection();
	}

	private List<String> getSelectionAsStrings() {
		ArrayList<String> result = new ArrayList<String>();
		if (null != selection && selection.size() > 0) {
			for (int noteIndex : selection) {
				result.add(temperament.getNoteFullName(noteIndex));
			}
		}
		return result;
	}

	private List<Integer> getSelectionByFullNames(List<String> pSelection) {
		List<Integer> result = new ArrayList<Integer>();
		if (null != pSelection) {
			for (String n : pSelection) {
				int noteIndex = temperament.findNoteIndexByFullName(n);
				if (noteIndex >= 0) {
					result.add(noteIndex);
				}
			}
		}
		return result;
	}

	private ArrayList<Integer> adaptSelection(List<Integer> sel) {
		ArrayList<Integer> result = new ArrayList<Integer>(sel);
		if (sel != null && sel.size() == 1 && (autoSelectFifth || autoSelectMajorThird)) {
			// l'adaptation ne peut se faire que si la sélection ne comprend qu'une note
			int idxNote1 = sel.get(0);
			result.clear();
			result.add(idxNote1);

			double ratio1 = temperament.getNoteFrequencyRatio(idxNote1);
			if (autoSelectMajorThird) {
				double ratio3 = ratio1 * MusicalKnowledge.RATIO_TIERCE_MAJEURE;
				int idxNote3 = temperament.findNoteIndexByRatio(ratio3);
				if (idxNote3 != -1) {
					result.add(idxNote3);
				}
			}
			if (autoSelectFifth) {
				double ratio5 = ratio1 * MusicalKnowledge.RATIO_QUINTE;
				int idxNote5 = temperament.findNoteIndexByRatio(ratio5);
				if (idxNote5 != -1) {
					result.add(idxNote5);
				}
			}
		}
		return result;
	}

	/**
	 * retourne la description d'un intervalle entre deux notes du tempérament
	 * actuellement actif
	 * 
	 * @param idx1
	 * @param idx2
	 * @return
	 */
	private String getIntervalDescription(int idx1, int idx2) {
		StringBuilder result = new StringBuilder();
		result.append(temperament.getNoteName(idx1));
		result.append(" - ");
		result.append(temperament.getNoteName(idx2));
		result.append(" : ");
		double f1 = getNoteFrequency(idx1);
		double f2 = getNoteFrequency(idx2);
		double ratio = Math.max(f1, f2) / Math.min(f1, f2);
		result.append(Commons.nfFrequencyRatio.format(ratio));
		result.append(" : ");
		String ratioName = MusicalKnowledge.getFrequencyRatioName(ratio);
		result.append(ratioName);
		return result.toString();
	}

	/**
	 * mise à jour de la description des intervalles qui existent entre les 2 ou 3
	 * notes sélectionnées
	 */
	private void updateSelectionDescription() {
		int selSize = selection.size();
		StringBuilder descr = new StringBuilder();
		if (2 == selSize) {
			descr.append(getIntervalDescription(selection.get(0), selection.get(1)));
		} else if (3 == selSize) {
			descr.append(getIntervalDescription(selection.get(0), selection.get(1)));
			descr.append("\n");
			descr.append(getIntervalDescription(selection.get(1), selection.get(2)));
			descr.append("\n");
			descr.append(getIntervalDescription(selection.get(0), selection.get(2)));
		} else {
			descr.append("Sélectionner 2 ou 3 notes pour obtenir une description des intervalles");
		}
		setSelectionDescription(descr.toString());
	}
}
