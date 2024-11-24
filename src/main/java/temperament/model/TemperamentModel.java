package temperament.model;

/**
 * modèle global, glue entre les différents modèles
 */
public class TemperamentModel {
	private TemperamentParameterBean	parameterBean;
	private TemperamentTableModel		tableModel;
	private TemperamentCircleModel		circleModel;

	public TemperamentModel() {
		parameterBean = new TemperamentParameterBean();
		circleModel = new TemperamentCircleModel(parameterBean);
		tableModel = new TemperamentTableModel(parameterBean);
	}

	public TemperamentParameterBean getParameterBean() {
		return parameterBean;
	}

	public TemperamentCircleModel getCircleModel() {
		return circleModel;
	}

	public TemperamentTableModel getTableModel() {
		return tableModel;
	}
}
