package temperament.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.table.AbstractTableModel;

import temperament.musical.ITemperament;

public class TemperamentTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 1L;
	public static final int COL_NOTE_NAME = 0;
	public static final int COL_FREQUENCY_RATIO = 1;
	public static final int COL_FREQUENCY = 2;
	public static final int COL_OCTAVE_SELECTED = 3;
	private static final int COL_NB = 4;
	private NumberFormat format;
	private AppState appState;
	private boolean[] octave;

	public TemperamentTableModel(AppState appState) {
		this.appState = appState;
		appState.setTemperamentTableModel(this);
		appState.addPropertyChangeListener(AppState.TEMPERAMENT_PROPERTY, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (AppState.TEMPERAMENT_PROPERTY.equals(evt.getPropertyName())) {
					initSelection();
					fireTableDataChanged();
				}
			}
		});

		format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(5);

		initSelection();
	}

	private void initSelection() {
		int n = getRowCount();
		octave = new boolean[n];
	}

	private ITemperament getTemperament() {
		return appState.getTemperament();
	}

	@Override
	public int getRowCount() {
		ITemperament t = getTemperament();
		return null == t ? 0 : t.getNbNotes() + 1;
	}

	@Override
	public int getColumnCount() {
		return COL_NB;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object result = null;
		ITemperament t = getTemperament();
		if (null != t) {
			if (rowIndex <= t.getNbNotes()) {
				switch (columnIndex) {
				case COL_NOTE_NAME:
					result = t.getNoteName(rowIndex);
					break;
				case COL_FREQUENCY_RATIO:
					result = format.format(t.getNoteFrequencyRatio(rowIndex));
					break;
				case COL_FREQUENCY:
					double frequenceDo = t.getFrequenceDo(appState.getLaFrequency());
					result = format.format(frequenceDo * t.getNoteFrequencyRatio(rowIndex));
					break;
				case COL_OCTAVE_SELECTED:
					result = null == octave ? false : octave[rowIndex];
					break;
				}
			}
		}
		return result;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		ITemperament t = getTemperament();
		if (null != t) {
			if (rowIndex <= t.getNbNotes()) {
				switch (columnIndex) {
				case COL_OCTAVE_SELECTED:
					if (aValue instanceof Boolean && null != octave) {
						octave[rowIndex] = (Boolean) aValue;
					}
					break;
				default:
					break;
				}
			}
		}
	}

	@Override
	public String getColumnName(int column) {
		switch (column) {
		case COL_NOTE_NAME:
			return "Note";
		case COL_FREQUENCY_RATIO:
			return "Ratio";
		case COL_FREQUENCY:
			return "Frequence [Hz]";
		case COL_OCTAVE_SELECTED:
			return "Octave";
		}
		return "";
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (COL_OCTAVE_SELECTED == columnIndex) {
			return Boolean.class;
		}
		return String.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		switch (columnIndex) {
		case COL_OCTAVE_SELECTED:
			return true;
		}
		return false;
	}

	public boolean isOctaveSelected(int noteIndex) {
		Object o = getValueAt(noteIndex, COL_OCTAVE_SELECTED);
		if (o instanceof Boolean) {
			return (Boolean) o;
		}
		return false;
	}
}
