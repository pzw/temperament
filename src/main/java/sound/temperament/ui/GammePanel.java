package sound.temperament.ui;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import sound.temperament.GammePanelModel;
import sound.temperament.GammeParameterBean;

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
