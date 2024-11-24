package temperament.ui;

import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import temperament.model.GammeParameterBean;
import temperament.musical.ITemperament;
import temperament.musical.Temperaments;

public class GammeTopPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public GammeTopPanel(GammeParameterBean model) {
		FormLayout layout = new FormLayout("$dm,p,$rg,p:g,$dm", "$dm,p,$dm");
		CellConstraints cc = new CellConstraints();
		setLayout(layout);
		add(new JLabel("Tempérament"), cc.xy(2, 2));

		PresentationModel<GammeParameterBean> pm = new PresentationModel<GammeParameterBean>(model);

		List<ITemperament> temperaments = Temperaments.getInstance().getTemperaments();
		SelectionInList<ITemperament> selTemperament = new SelectionInList<ITemperament>(temperaments,
				pm.getModel(GammeParameterBean.TEMPERAMENT_PROPERTY));
		JComboBox<ITemperament> cbTemperament = new JComboBox<ITemperament>();
		Bindings.bind(cbTemperament, selTemperament);

		add(cbTemperament, cc.xy(4, 2));
	}
}
