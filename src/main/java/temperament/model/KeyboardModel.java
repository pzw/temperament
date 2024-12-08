package temperament.model;

import java.awt.Point;
import java.awt.Polygon;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import temperament.musical.ITemperament;
import temperament.musical.TemperamentAbbatialePayerne;

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

	public KeyboardModel(ApplicationState appState) {
		this.appState = appState;
		appState.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (ApplicationState.TEMPERAMENT_PROPERTY.equals(evt.getPropertyName())) {
					temperamentChanged();
				} else if (ApplicationState.SELECTION_PROPERTY.equals(evt.getPropertyName())) {
					// changement de la sélection
					setSelection(appState.getSelection());
				}
			}
		});
	}

	private void temperamentChanged() {
		initKeyboard();
	}

	private void setSelection(List<Integer> selection) {
		for (int n = 0; n < keys.size(); n++) {
			KeyboardKey k = keys.get(n);
			boolean newSelect = selection.contains(n);
			k.setPressed(newSelect);
		}
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

		updateKeyPositions();
	}

	private void initKeyboard() {
		keys = new ArrayList<KeyboardKey>();
		initOctave(0);
		initOctave(1);
	}

	private void updateKeyPositions() {

	}

	private void initOctave(int octave) {
		int xStart = octave * 7;
		int idx = keys.size();

		ITemperament t = appState.getTemperament();
		boolean feintesBrisees = null != t && TemperamentAbbatialePayerne.ABBATIALE_PAYERNE.equals(t.toString());
		keys.add(new KeyboardKey(1, xStart + 0, idx++, true)); // do
		keys.add(new KeyboardKey(3, xStart + 1, idx++, false)); // do #
		keys.add(new KeyboardKey(2, xStart + 1, idx++, true)); // ré
		if (feintesBrisees) {
			keys.add(new KeyboardKey(5, xStart + 2, idx++, true)); // ré #
			keys.add(new KeyboardKey(6, xStart + 2, idx++, true)); // mi b
		} else {
			keys.add(new KeyboardKey(4, xStart + 2, idx++, true)); // ré #
		}
		keys.add(new KeyboardKey(3, xStart + 2, idx++, true)); // mi
		keys.add(new KeyboardKey(1, xStart + 3, idx++, true)); // fa
		keys.add(new KeyboardKey(4, xStart + 4, idx++, false)); // fa #
		keys.add(new KeyboardKey(2, xStart + 4, idx++, true)); // sol
		if (feintesBrisees) {
			keys.add(new KeyboardKey(5, xStart + 5, idx++, false)); // sol #
			keys.add(new KeyboardKey(6, xStart + 5, idx++, false)); // la b
		} else {
			keys.add(new KeyboardKey(4, xStart + 5, idx++, false)); // sol #
		}
		keys.add(new KeyboardKey(2, xStart + 5, idx++, true)); // la
		keys.add(new KeyboardKey(4, xStart + 6, idx++, false)); // la #
		keys.add(new KeyboardKey(3, xStart + 6, idx++, true)); // si
	}

	public List<KeyboardKey> getKeys() {
		return keys;
	}

	public KeyboardKey findNote(Point p) {
		for (int i = 0; i < keys.size(); i++) {
			KeyboardKey k = keys.get(i);
			if (k.containsPoint(p.x, p.y)) {
				return k;
			}
		}
		return null;
	}


	public class KeyboardKey {
		private Polygon	polygon;
		private boolean	whiteKey;
		private boolean	pressed;
		/** index de la note dans le tempérament */
		private int		noteIndex;
		private int		noteForm;
		private int		notePosition;

		public KeyboardKey(int noteForm, int notePosition, int noteIndex, boolean whiteKey) {
			this.noteForm = noteForm;
			this.notePosition = notePosition;
			this.noteIndex = noteIndex;
			this.whiteKey = whiteKey;
			this.pressed = false;
			this.polygon = null;
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

		public int getNoteIndex() {
			return noteIndex;
		}

		public boolean containsPoint(int x, int y) {
			return polygon.contains(x, y);
		}

		private Polygon createPolygonType1(int xStart) {
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

			return new Polygon(x, y, x.length);
		}

		private Polygon createPolygonType2(int xStart) {
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
			return new Polygon(x, y, x.length);
		}

		private Polygon createPolygonType3(int xStart) {
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

			return new Polygon(x, y, x.length);
		}

		private Polygon createPolygonType4(int xStart) {
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

			return new Polygon(x, y, x.length);
		}

		private Polygon createPolygonType5(int xStart) {
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

			return new Polygon(x, y, x.length);
		}

		private Polygon createPolygonType6(int xStart) {
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

			return new Polygon(x, y, x.length);
		}

	}
}
