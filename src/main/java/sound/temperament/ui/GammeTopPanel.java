package sound.temperament.ui;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.Bindings;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import sound.temperament.GammeParameterBean;
import sound.temperament.Temperament;

public class GammeTopPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public GammeTopPanel(GammeParameterBean model) {
		FormLayout layout = new FormLayout("$dm,p,$rg,p:g,$dm", "$dm,p,$dm");
		CellConstraints cc = new CellConstraints();
		setLayout(layout);
		add(new JLabel("Tempérament"), cc.xy(2, 2));

		PresentationModel<GammeParameterBean> pm = new PresentationModel<GammeParameterBean>(model);

		SelectionInList<Temperament> selTemperament = new SelectionInList<Temperament>(Temperament.values(),
				pm.getModel(GammeParameterBean.TEMPERAMENT_PROPERTY));
		JComboBox<Temperament> cbTemperament = new JComboBox<Temperament>();
		Bindings.bind(cbTemperament, selTemperament);

		add(cbTemperament, cc.xy(4, 2));
	}
}
