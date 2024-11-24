package temperament.ui;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import temperament.model.TemperamentParameterBean;
import temperament.model.TemperamentTableModel;

/**
 * représentation d'un tempérament dans une JTable
 */
public class TemperamentTablePanel extends JPanel {
	private static final long	serialVersionUID				= 1L;
	private JTable				tableView;
	private boolean				tableSelectionListenerEnabled	= true;

	public TemperamentTablePanel(TemperamentParameterBean parameterBean, TemperamentTableModel model) {
		setLayout(new BorderLayout());
		tableView = new JTable(model);
		tableView.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		JScrollPane sp = new JScrollPane(tableView);
		add(sp, BorderLayout.CENTER);

		TableColumnModel tcm = tableView.getColumnModel();
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.RIGHT);
		tcm.getColumn(TemperamentTableModel.COL_FREQUENCY).setCellRenderer(renderer);
		tcm.getColumn(TemperamentTableModel.COL_FREQUENCY_RATIO).setCellRenderer(renderer);

		tableView.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && tableSelectionListenerEnabled) {
					parameterBean.setSelection(getSelection());
				}

			}
		});

		parameterBean.addPropertyChangeListener(TemperamentParameterBean.SELECTION_PROPERTY,
				new PropertyChangeListener() {

					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						tableSelectionListenerEnabled = false;
						List<Integer> sel = parameterBean.getSelection();
						ListSelectionModel lsm = tableView.getSelectionModel();
						lsm.clearSelection();
						for (Integer s : sel) {
							lsm.addSelectionInterval(s, s);
						}
						tableSelectionListenerEnabled = true;
					}
				});
	}

	public void addSelectionListener(ListSelectionListener listener) {
		tableView.getSelectionModel().addListSelectionListener(listener);
	}

	public ListSelectionModel getSelectionModel() {
		return tableView.getSelectionModel();
	}

	public List<Integer> getSelection() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		ListSelectionModel lsm = tableView.getSelectionModel();
		for (int n = 0; n < tableView.getRowCount(); n++) {
			if (lsm.isSelectedIndex(n)) {
				result.add(n);
			}
		}
		return result;
	}
}
