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
	private static final int COL_NB = 3;
	private NumberFormat format;
	private TemperamentParameterBean parameterBean;
	
	public TemperamentTableModel(TemperamentParameterBean parameterBean) {
		this.parameterBean = parameterBean;
		parameterBean.addPropertyChangeListener(TemperamentParameterBean.TEMPERAMENT_PROPERTY, new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (TemperamentParameterBean.TEMPERAMENT_PROPERTY.equals(evt.getPropertyName())) {
					fireTableDataChanged();
				}
			}
		});
		
		format = NumberFormat.getNumberInstance();
		format.setMaximumFractionDigits(5);
		format.setMinimumFractionDigits(5);
	}
	
	private ITemperament getTemperament() {
		return parameterBean.getTemperament();
	}
	
	@Override
	public int getRowCount() {
		ITemperament t = getTemperament();
		return null == t ? 0 : t.getNbNotes()+1;
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
				switch(columnIndex) {
				case COL_NOTE_NAME:
					result = t.getNoteName(rowIndex);
					break;
				case COL_FREQUENCY_RATIO:
					result = format.format(t.getNoteFrequencyRatio(rowIndex));
					break;
				case COL_FREQUENCY:
					double frequenceDo = t.getFrequenceDo(parameterBean.getLaFrequency());
					result = format.format(frequenceDo * t.getNoteFrequencyRatio(rowIndex));
					break;
				}
			}
		}
		return result;
	}

	@Override
	public String getColumnName(int column) {
		switch(column) {
		case COL_NOTE_NAME:
			return "Note";
		case COL_FREQUENCY_RATIO:
			return "Ratio";
		case COL_FREQUENCY:
			return "Frequence [Hz]";
		}
		return "";
	}
}
