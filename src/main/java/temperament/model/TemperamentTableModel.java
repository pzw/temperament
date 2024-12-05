package temperament.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;

import javax.swing.table.AbstractTableModel;

import temperament.musical.ITemperament;

public class TemperamentTableModel extends AbstractTableModel {
	private static final long	serialVersionUID	= 1L;
	public static final int		COL_NOTE_NAME		= 0;
	public static final int		COL_FREQUENCY_RATIO	= 1;
	public static final int		COL_FREQUENCY		= 2;
	public static final int		COL_CENTS			= 3;
	private static final int	COL_NB				= 4;
	private NumberFormat		format;
	private NumberFormat formatCents;
	private AppState			appState;

	public TemperamentTableModel(AppState appState) {
		this.appState = appState;
		appState.addPropertyChangeListener(AppState.TEMPERAMENT_PROPERTY, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (AppState.TEMPERAMENT_PROPERTY.equals(evt.getPropertyName())) {
					fireTableDataChanged();
				}
			}
		});

		format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(5);
		
		formatCents = NumberFormat.getNumberInstance();
		formatCents.setMaximumFractionDigits(0);
		formatCents.setMinimumFractionDigits(0);
	}

	private ITemperament getTemperament() {
		return appState.getTemperament();
	}

	@Override
	public int getRowCount() {
		ITemperament t = getTemperament();
		return null == t ? 0 : t.getNbNotes();
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
			if (rowIndex < t.getNbNotes()) {
				switch (columnIndex) {
				case COL_NOTE_NAME:
					result = t.getNoteName(rowIndex);
					break;
				case COL_FREQUENCY_RATIO:
					result = format.format(t.getNoteFrequencyRatio(rowIndex));
					break;
				case COL_FREQUENCY: {
					double frequenceDo = t.getFrequenceDo(appState.getLaFrequency());
					result = format.format(frequenceDo * t.getNoteFrequencyRatio(rowIndex));
					break;
				}
				case COL_CENTS: {
					double cents = 1200.0 * Math.log(t.getNoteFrequencyRatio(rowIndex)) / Math.log(2.0);
					result = formatCents.format(cents);
					break;
				}
				}
			}
		}
		return result;
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
		case COL_CENTS:
			return "Cents";
		}
		return "";
	}
}
