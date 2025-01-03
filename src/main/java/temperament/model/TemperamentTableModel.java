package temperament.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.table.AbstractTableModel;

import temperament.Commons;
import temperament.musical.ITemperament;
import temperament.musical.MusicalKnowledge;

/**
 * modèle pour la table qui montre les notes du tempérament
 */
public class TemperamentTableModel extends AbstractTableModel {
	private static final long	serialVersionUID	= 1L;
	public static final int		COL_NOTE_NAME		= 0;
	public static final int		COL_FREQUENCY_RATIO	= 1;
	public static final int		COL_FREQUENCY		= 2;
	public static final int		COL_CENTS			= 3;
	private static final int	COL_NB				= 4;
	private ApplicationState	appState;

	public TemperamentTableModel(ApplicationState appState) {
		this.appState = appState;
		appState.addPropertyChangeListener(ApplicationState.TEMPERAMENT_PROPERTY, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (ApplicationState.TEMPERAMENT_PROPERTY.equals(evt.getPropertyName())) {
					fireTableDataChanged();
				}
			}
		});
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
					result = Commons.nfFrequencyRatio.format(t.getNoteFrequencyRatio(rowIndex));
					break;
				case COL_FREQUENCY: {
					double frequenceDo = t.getFrequenceDo(appState.getLaFrequency());
					result = Commons.nfFrequency.format(frequenceDo * t.getNoteFrequencyRatio(rowIndex));
					break;
				}
				case COL_CENTS: {
					result = Commons.nfCents.format(MusicalKnowledge.toCents(t.getNoteFrequencyRatio(rowIndex)));
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
