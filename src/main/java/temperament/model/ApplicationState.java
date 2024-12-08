package temperament.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import com.jgoodies.binding.beans.Model;

import temperament.musical.ITemperament;
import temperament.musical.MusicalKnowledge;
import temperament.musical.Temperaments;

public class ApplicationState extends Model {
	private static final long	serialVersionUID				= 1L;
	public static final String	TEMPERAMENT_PROPERTY			= "temperament";
	public static final String	LA_FREQUENCY_PROPERTY			= "laFrequency";
	public static final String	SELECTION_PROPERTY				= "selection";
	public static final String	WAVE_VIEW_DURATION_PROPERTY		= "waveViewDuration";
	public static final String	WAVE_SHOW_SUM_PROPERTY			= "waveShowSum";
	public static final String	WAVE_SHOW_EACH_NOTE_PROPERTY	= "waveShowEachNote";
	public static final String	SELECTION_DESCRIPTION_PROPERTY	= "selectionDescription";
	public static final String	DISPLAY_FIFTHS					= "displayFifths";
	public static final String	DISPLAY_MAJOR_THIRDS			= "displayMajorThirds";
	public static final String	AUTO_SELECT_MAJOR_THIRD			= "autoSelectMajorThird";
	public static final String	AUTO_SELECT_FIFTH				= "autoSelectFifth";
	private ITemperament		temperament						= Temperaments.getInstance().getTemperaments().get(0);
	private double				laFrequency						= 440.0;
	private List<Integer>		selection						= new ArrayList<Integer>();
	/** durée visualisée dans WavePanel */
	private double				waveViewDuration				= 250.0;
	private boolean				waveShowSum						= true;
	private boolean				waveShowEachNote				= false;
	private String				selectionDescription			= "";
	private boolean				displayFifths					= false;
	private boolean				displayMajorThirds				= false;
	private boolean				autoSelectMajorThird			= false;
	private boolean				autoSelectFifth					= false;

	public void setTemperamentTableModel() {
	}

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

	public double getLaFrequency() {
		return laFrequency;
	}

	public void setLaFrequency(double newValue) {
		double oldValue = getLaFrequency();
		laFrequency = newValue;
		firePropertyChange(LA_FREQUENCY_PROPERTY, oldValue, newValue);
		firePropertyChange(TEMPERAMENT_PROPERTY, null, getTemperament());
	}

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

	public double getNoteFrequency(int index) {
		if (null == temperament)
			return 0.0;
		double frequenceDo = temperament.getFrequenceDo(getLaFrequency());
		double result = frequenceDo * temperament.getNoteFrequencyRatio(index);
		return result;
	}

	public Color getSelectionColor(int idx) {
		switch (idx % 6) {
		case 0:
			return Color.green;
		case 1:
			return Color.blue;
		case 2:
			return Color.white;
		case 3:
			return Color.cyan;
		case 4:
			return Color.magenta;
		case 5:
			return Color.orange;
		}
		return Color.magenta;
	}

	public double getWaveViewDuration() {
		return waveViewDuration;
	}

	public void setWaveViewDuration(double newValue) {
		double oldvalue = getWaveViewDuration();
		waveViewDuration = newValue;
		firePropertyChange(WAVE_VIEW_DURATION_PROPERTY, oldvalue, newValue);
	}

	public boolean isWaveShowSum() {
		return waveShowSum;
	}

	public void setWaveShowSum(boolean newValue) {
		boolean oldValue = isWaveShowSum();
		waveShowSum = newValue;
		firePropertyChange(WAVE_SHOW_SUM_PROPERTY, oldValue, newValue);

	}

	public boolean isWaveShowEachNote() {
		return waveShowEachNote;
	}

	public void setWaveShowEachNote(boolean newValue) {
		boolean oldValue = isWaveShowEachNote();
		waveShowEachNote = newValue;
		firePropertyChange(WAVE_SHOW_EACH_NOTE_PROPERTY, oldValue, newValue);
	}

	public String getSelectionDescription() {
		return selectionDescription;
	}

	public void setSelectionDescription(String newValue) {
		String oldvalue = getSelectionDescription();
		selectionDescription = newValue;
		firePropertyChange(SELECTION_DESCRIPTION_PROPERTY, oldvalue, newValue);
	}

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
	public String getIntervalDescription(int idx1, int idx2) {
		StringBuilder result = new StringBuilder();
		result.append(temperament.getNoteName(idx1));
		result.append(" - ");
		result.append(temperament.getNoteName(idx2));
		result.append(" : ");
		double f1 = getNoteFrequency(idx1);
		double f2 = getNoteFrequency(idx2);
		double ratio = Math.max(f1, f2) / Math.min(f1, f2);
		String ratioName = MusicalKnowledge.getFrequencyRatioName(ratio);
		result.append(ratioName);
		return result.toString();
	}

	private void updateSelectionDescription() {
		int selSize = selection.size();
		StringBuilder descr = new StringBuilder();
		if (2 == selSize) {
			descr.append(getIntervalDescription(selection.get(0), selection.get(1)));
		} else if (3 == selSize) {
			descr.append(getIntervalDescription(selection.get(0), selection.get(1)));
			descr.append("  /  ");
			descr.append(getIntervalDescription(selection.get(1), selection.get(2)));
			descr.append("  /  ");
			descr.append(getIntervalDescription(selection.get(0), selection.get(2)));

		}
		setSelectionDescription(descr.toString());
	}
}