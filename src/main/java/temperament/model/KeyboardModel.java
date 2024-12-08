package temperament.model;

import java.awt.Polygon;
import java.util.ArrayList;
import java.util.List;

/**
 * modèle de données pour les touches d'un clavier de piano
 */
public class KeyboardModel {
	/** dimensions pour une touche de piano standard, en mm */
	private static final double	DX1	= 24;
	private static final double	DX2	= 14;
	private static final double	DY1	= 150;
	private static final double	DY2	= 100;
	private static final double	DY3	= 50;

	private int					dx1;
	private int					dx2;
	private int					dy1;
	private int					dy2;
	private int					dy3;

	private ApplicationState	appState;
	private List<KeyboardKey>	keys;
	private boolean feintesBrisees = true;
	
	public KeyboardModel(ApplicationState appState) {
		this.appState = appState;
	}

	public void setPanelDimensions(int w, int h) {
		double scaleX = w / (14 * DX1);
		double scaleY = h / DY1;
		double scale = Math.min(scaleX, scaleY);
		dx1 = (int) (DX1 * scale);
		dx2 = (int) (DX2 * scale);
		dy1 = (int) (DY1 * scale);
		dy2 = (int) (DY2 * scale);
		dy3 = (int) (DY3 * scale);

		initKeyboard();
	}

	private void initKeyboard() {
		keys = new ArrayList<KeyboardKey>();
		initOctave(0);
		initOctave(1);
		keys.get(3).setPressed(true);
		keys.get(7).setPressed(true);
	}

	private void initOctave(int octave) {
		int xStart = octave * 7 * dx1;
		keys.add(createKeyType1(xStart + 0 * dx1)); // do
		keys.add(createKeyType4(xStart + 1 * dx1)); // do #
		keys.add(createKeyType2(xStart + 1 * dx1)); // ré
		if (feintesBrisees) {
			keys.add(createKeyType5(xStart + 2 * dx1)); // ré #
			keys.add(createKeyType6(xStart + 2 * dx1)); // mi b
		} else {
			keys.add(createKeyType4(xStart + 2 * dx1)); // ré #
		}
		keys.add(createKeyType3(xStart + 2 * dx1)); // mi
		keys.add(createKeyType1(xStart + 3 * dx1)); // fa
		keys.add(createKeyType4(xStart + 4 * dx1)); // fa #
		keys.add(createKeyType2(xStart + 4 * dx1)); // sol
		if (feintesBrisees) {
			keys.add(createKeyType5(xStart + 5 * dx1)); // sol #
			keys.add(createKeyType6(xStart + 5 * dx1)); // la b
		} else {
			keys.add(createKeyType4(xStart + 5 * dx1)); // sol #
		}
		keys.add(createKeyType2(xStart + 5 * dx1)); // la
		keys.add(createKeyType4(xStart + 6 * dx1)); // la #
		keys.add(createKeyType3(xStart + 6 * dx1)); // si
	}
	public List<KeyboardKey> getKeys() {
		return keys;
	}

	/**
	 * crée une touche pour DO - FA
	 * 
	 * @param xStart
	 * @return
	 */
	public KeyboardKey createKeyType1(int xStart) {
		int[] x = new int[6];
		int[] y = new int[6];

		x[0] = xStart;
		y[0] = 0;

		x[1] = x[0];
		y[1] = dy1;

		x[2] = x[1] + dx1;
		y[2] = y[1];

		x[3] = x[2];
		y[3] = dy2;

		x[4] = x[3] - dx2 / 2;
		y[4] = y[3];

		x[5] = x[4];
		y[5] = 0;

		return new KeyboardKey(new Polygon(x, y, x.length), true);
	}

	/**
	 * crée une touche pour RE, SOL, LA
	 * 
	 * @param xStart
	 * @return
	 */
	public KeyboardKey createKeyType2(int xStart) {
		int[] x = new int[8];
		int[] y = new int[8];

		x[0] = xStart + dx2 / 2;
		y[0] = 0;

		x[1] = x[0];
		y[1] = dy2;

		x[2] = xStart;
		y[2] = y[1];

		x[3] = x[2];
		y[3] = dy1;

		x[4] = x[3] + dx1;
		y[4] = y[3];

		x[5] = x[4];
		y[5] = dy2;

		x[6] = x[5] - dx2 / 2;
		y[6] = y[5];

		x[7] = x[6];
		y[7] = 0;
		return new KeyboardKey(new Polygon(x, y, x.length), true);
	}

	/**
	 * crée une touche pour MI - SI
	 * 
	 * @param xStart
	 * @return
	 */
	public KeyboardKey createKeyType3(int xStart) {
		int[] x = new int[6];
		int[] y = new int[6];

		x[0] = xStart + dx2 / 2;
		y[0] = 0;

		x[1] = x[0];
		y[1] = dy2;

		x[2] = xStart;
		y[2] = y[1];

		x[3] = x[2];
		y[3] = dy1;

		x[4] = x[3] + dx1;
		y[4] = y[3];

		x[5] = x[4];
		y[5] = 0;

		return new KeyboardKey(new Polygon(x, y, x.length), true);
	}

	/**
	 * crée une touche noire complète (non brisée)
	 * 
	 * @param xStart
	 * @return
	 */
	public KeyboardKey createKeyType4(int xStart) {
		int[] x = new int[4];
		int[] y = new int[4];

		x[0] = xStart - dx2 / 2;
		y[0] = 0;

		x[1] = x[0];
		y[1] = dy2;

		x[2] = x[1] + dx2;
		y[2] = y[1];

		x[3] = x[2];
		y[3] = 0;

		return new KeyboardKey(new Polygon(x, y, x.length), false);
	}

	/**
	 * crée feinte brisée (à l'avant)
	 * 
	 * @param xStart
	 * @return
	 */
	public KeyboardKey createKeyType5(int xStart) {
		int[] x = new int[4];
		int[] y = new int[4];

		x[0] = xStart - dx2 / 2;
		y[0] = dy3;

		x[1] = x[0];
		y[1] = dy2;

		x[2] = x[1] + dx2;
		y[2] = y[1];

		x[3] = x[2];
		y[3] = dy3;

		return new KeyboardKey(new Polygon(x, y, x.length), false);
	}

	/**
	 * crée feinte brisée (à l'arrière)
	 * 
	 * @param xStart
	 * @return
	 */
	public KeyboardKey createKeyType6(int xStart) {
		int[] x = new int[4];
		int[] y = new int[4];

		x[0] = xStart - dx2 / 2;
		y[0] = 0;

		x[1] = x[0];
		y[1] = dy3;

		x[2] = x[1] + dx2;
		y[2] = y[1];

		x[3] = x[2];
		y[3] = 0;

		return new KeyboardKey(new Polygon(x, y, x.length), false);
	}

	public class KeyboardKey {
		private Polygon	polygon;
		private boolean	whiteKey;
		private boolean	pressed;

		public KeyboardKey(Polygon polygon, boolean whiteKey) {
			this.polygon = polygon;
			this.whiteKey = whiteKey;
			this.pressed = false;
		}

		public Polygon getPolygon() {
			return polygon;
		}

		public boolean isWhiteKey() {
			return whiteKey;
		}

		public boolean isPressed() {
			return pressed;
		}

		public void setPressed(boolean pressed) {
			this.pressed = pressed;
		}

	}
}
