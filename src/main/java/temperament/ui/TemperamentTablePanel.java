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
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

import temperament.model.ApplicationState;
import temperament.model.TemperamentTableModel;

/**
 * représentation d'un tempérament dans une JTable
 */
public class TemperamentTablePanel extends JPanel {
	private static final long	serialVersionUID				= 1L;
	private JTable				tableView;
	private boolean				tableSelectionListenerEnabled	= true;

	public TemperamentTablePanel(ApplicationState appState, TemperamentTableModel model) {
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
		tcm.getColumn(TemperamentTableModel.COL_CENTS).setCellRenderer(renderer);

		tableView.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && tableSelectionListenerEnabled) {
					appState.setSelection(getSelection());
				}

			}
		});

		appState.addPropertyChangeListener(ApplicationState.SELECTION_PROPERTY, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				tableSelectionListenerEnabled = false;
				List<Integer> sel = appState.getSelection();
				ListSelectionModel lsm = tableView.getSelectionModel();
				lsm.clearSelection();
				// selection in reverse order : lower note selected last. Better UX if user
				// activate "auto-select" and use keys to go up and down
				for (Integer s : sel.reversed()) {
					lsm.addSelectionInterval(s, s);
				}
				tableSelectionListenerEnabled = true;
			}
		});

		appState.addPropertyChangeListener(ApplicationState.TEMPERAMENT_PROPERTY, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				selectFirstNote();
			}
		});

		selectFirstNote();
	}

	private void selectFirstNote() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				tableView.getSelectionModel().setSelectionInterval(0, 0);
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
