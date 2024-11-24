package temperament.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import temperament.model.TemperamentCircleModel;
import temperament.model.TemperamentParameterBean;
import temperament.model.TemperamentTableModel;

public class TemperamentPanel extends JPanel {
	private static final long			serialVersionUID	= 1L;
	private TemperamentParameterBean	parameterBean		= new TemperamentParameterBean();

	public TemperamentPanel() {
		super();
		setLayout(new BorderLayout());
		add(new TemperamentTopPanel(parameterBean), BorderLayout.NORTH);
		TemperamentCircleModel circleModel = new TemperamentCircleModel(parameterBean);
		TemperamentCircleView circleView = new TemperamentCircleView(parameterBean, circleModel);
		TemperamentTableModel tableModel = new TemperamentTableModel(parameterBean);
		TemperamentTablePanel tableView = new TemperamentTablePanel(parameterBean, tableModel);
		JSplitPane splitTableCircle = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tableView, circleView);

		WavePanel wavePanel = new WavePanel(parameterBean);
		JSplitPane splitWave = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitTableCircle, wavePanel);
		add(splitWave, BorderLayout.CENTER);
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				splitTableCircle.setDividerLocation(0.33);
				splitWave.setDividerLocation(0.5);
			}
		});
	}
}
