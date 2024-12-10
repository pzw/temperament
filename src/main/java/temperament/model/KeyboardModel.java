package temperament.model;

import java.awt.Color;
import java.awt.Point;
import java.awt.Polygon;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import temperament.Commons;
import temperament.musical.ITemperament;
import temperament.musical.TemperamentAbbatialePayerne;

/**
 * modèle de données pour les touches d'un clavier de piano
 */
public class KeyboardModel {
	/** dimensions pour une touche de piano standard, en mm */
	private static final double	DX1				= 24;
	private static final double	DX2				= 14;
	private static final double	DY1				= 150;
	private static final double	DY2				= 100;
	private static final double	DY3				= 50;

	private int					dx1;
	private int					dx2;
	private int					dy1;
	private int					dy2;
	private int					dy3;
	private int					width			= 0;
	private int					height			= 0;
	private ApplicationState	appState;
	private List<KeyboardKey>	keys;
	private boolean				blackAndWhite	= false;
	private Color				whiteKey;
	private Color				whiteKeyPressed;
	private Color				blackKey;
	private Color				blackKeyPressed;

	public KeyboardModel(ApplicationState appState) {
		this.appState = appState;
		whiteKey = blackAndWhite ? Color.white : new Color(252, 197, 109);
		whiteKeyPressed = Color.yellow;
		blackKey = blackAndWhite ? Color.black : new Color(102, 69, 17);
		blackKeyPressed = Color.green;

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
		initKeyboard();
	}

	private void temperamentChanged() {
		initKeyboard();
	}

	private void setSelection(List<Integer> selection) {
		if (null != keys) {
			for (int n = 0; n < keys.size(); n++) {
				KeyboardKey k = keys.get(n);
				boolean newSelect = selection.contains(n);
				k.setPressed(newSelect);
			}
		}
	}

	public void setPanelDimensions(int w, int h) {
		if (w != width || h != height) {
			width = w;
			height = h;
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

	}

	public int getSelectionRank(int noteIndex) {
		if (!keys.get(noteIndex).isPressed()) {
			// la note n'est pas sélectionnée : pas de rang
			return -1;
		}
		int result = 0;
		int idx = 0;
		while (idx < noteIndex) {
			if (keys.get(idx).isPressed()) {
				result++;
			}
			idx++;
		}
		return result;
	}

	private void initKeyboard() {
		keys = new ArrayList<KeyboardKey>();
		initOctave(0);
		initOctave(1);
		updateKeyPositions();
	}

	private void updateKeyPositions() {
		if (null != keys) {
			for (KeyboardKey k : keys) {
				k.resetPolygon(dx1);
			}
		}

	}

	private void initOctave(int octave) {
		int xStart = octave * 7;
		int idx = keys.size();

		ITemperament t = appState.getTemperament();
		boolean feintesBrisees = null != t && TemperamentAbbatialePayerne.ABBATIALE_PAYERNE.equals(t.toString());
		keys.add(new KeyboardKey(KeyType.DoFa, xStart + 0, idx++));
		keys.add(new KeyboardKey(KeyType.NoireComplete, xStart + 1, idx++));
		keys.add(new KeyboardKey(KeyType.ReSolLa, xStart + 1, idx++));
		if (feintesBrisees) {
			keys.add(new KeyboardKey(KeyType.BriseeArriere, xStart + 2, idx++));
			keys.add(new KeyboardKey(KeyType.BriseeAvant, xStart + 2, idx++));
		} else {
			keys.add(new KeyboardKey(KeyType.NoireComplete, xStart + 2, idx++));
		}
		keys.add(new KeyboardKey(KeyType.MiSi, xStart + 2, idx++));
		keys.add(new KeyboardKey(KeyType.DoFa, xStart + 3, idx++));
		keys.add(new KeyboardKey(KeyType.NoireComplete, xStart + 4, idx++));
		keys.add(new KeyboardKey(KeyType.ReSolLa, xStart + 4, idx++));
		if (feintesBrisees) {
			keys.add(new KeyboardKey(KeyType.BriseeArriere, xStart + 5, idx++));
			keys.add(new KeyboardKey(KeyType.BriseeAvant, xStart + 5, idx++));
		} else {
			keys.add(new KeyboardKey(KeyType.NoireComplete, xStart + 5, idx++));
		}
		keys.add(new KeyboardKey(KeyType.ReSolLa, xStart + 5, idx++));
		keys.add(new KeyboardKey(KeyType.NoireComplete, xStart + 6, idx++));
		keys.add(new KeyboardKey(KeyType.MiSi, xStart + 6, idx++));
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
		private boolean	pressed;
		/** index de la note dans le tempérament */
		private int		noteIndex;
		private KeyType	keyType;
		private int		notePosition;

		public KeyboardKey(KeyType keyType, int notePosition, int noteIndex) {
			this.keyType = keyType;
			this.notePosition = notePosition;
			this.noteIndex = noteIndex;
			this.pressed = false;
			this.polygon = null;
		}

		public Polygon getPolygon() {
			return polygon;
		}

		public void resetPolygon(int dx) {
			switch (keyType) {
			case DoFa:
				this.polygon = createPolygonTypeDoFa(notePosition * dx);
				break;
			case ReSolLa:
				this.polygon = createPolygonTypeReSolLa(notePosition * dx);
				break;
			case MiSi:
				this.polygon = createPolygonTypeMiSi(notePosition * dx);
				break;
			case NoireComplete:
				this.polygon = createPolygonTypeNoireComplete(notePosition * dx);
				break;
			case BriseeArriere:
				this.polygon = createPolygonTypeNoireBriseeArriere(notePosition * dx);
				break;
			case BriseeAvant:
				this.polygon = createPolygonTypeNoireBriseeAvant(notePosition * dx);
				break;

			}
		}

		public boolean isWhiteKey() {
			return keyType == KeyType.DoFa || keyType == KeyType.MiSi || keyType == KeyType.ReSolLa;
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

		public Color getFillColor() {
			Color result;
			if (isPressed()) {
				int selRank = KeyboardModel.this.getSelectionRank(noteIndex);
				result = Commons.getSelectionColor(selRank);
			} else {
				if (isWhiteKey()) {
					result = whiteKey;
				} else {
					result = blackKey;
				}
			}
			return result;
		}

		private Polygon createPolygonTypeDoFa(int xStart) {
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

		private Polygon createPolygonTypeReSolLa(int xStart) {
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

		private Polygon createPolygonTypeMiSi(int xStart) {
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

		private Polygon createPolygonTypeNoireComplete(int xStart) {
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

		private Polygon createPolygonTypeNoireBriseeAvant(int xStart) {
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

		private Polygon createPolygonTypeNoireBriseeArriere(int xStart) {
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

	private enum KeyType {
		DoFa, ReSolLa, MiSi, NoireComplete, BriseeArriere, BriseeAvant
	}
}
