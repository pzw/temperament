package temperament.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import temperament.model.GammePanelModel;
import temperament.model.GammeParameterBean;

public class GammePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private GammeParameterBean parameterBean = new GammeParameterBean();
	private GammePanelModel model = new GammePanelModel();

	public GammePanel() {
		super();
		setLayout(new BorderLayout());
		add(new GammeTopPanel(parameterBean), BorderLayout.NORTH);
		add(new GammeCirclePanel(parameterBean, model), BorderLayout.CENTER);
	}
}
