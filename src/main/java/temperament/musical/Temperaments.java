package temperament.musical;

import java.util.ArrayList;
import java.util.List;

import temperament.musical.json.TemperamentJson;

public class Temperaments {
	private static Temperaments		_instance	= null;
	private ArrayList<ITemperament>	temperaments;

	public static synchronized Temperaments getInstance() {
		if (null == _instance) {
			_instance = new Temperaments();
		}
		return _instance;
	}

	private Temperaments() {
		temperaments = new ArrayList<ITemperament>();
		temperaments.add(new TemperamentEgal());
		temperaments.add(new TemperamentPythagore());
		temperaments.add(new TemperamentMesotonique());
		temperaments.add(new TemperamentJson("assets/test.json"));
	}

	public List<ITemperament> getTemperaments() {
		return temperaments;
	}
}
