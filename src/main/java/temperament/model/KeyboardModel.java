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
public class KeyboardModel extends SelectableNotesModel {
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
	private List<KeyboardKey>	keys;
	private Color				whiteKey;
	private Color				blackKey;

	public KeyboardModel(ApplicationState appState) {
		super(appState);
		selectKeyboardColor();

		appState.addPropertyChangeListener(ApplicationState.TEMPERAMENT_PROPERTY, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				selectKeyboardColor();
			}
		});
	}

	private void selectKeyboardColor() {
		ITemperament t = getTemperament();
		boolean blackAndWhite = null != t && t.isModernTemperament();
		;
		whiteKey = blackAndWhite ? Color.white : new Color(252, 197, 109);
		blackKey = blackAndWhite ? Color.black : new Color(102, 69, 17);
	}

	@Override
	protected List<? extends ISelectableNote> getNotes() {
		return keys;
	}

	@Override
	protected void afterPanelDimensionChanged() {
		double scaleX = getWidth() / (14 * DX1);
		double scaleY = getHeight() / DY1;
		double scale = Math.min(scaleX, scaleY);
		dx1 = (int) (DX1 * scale);
		dx2 = (int) (DX2 * scale);
		dy1 = (int) (DY1 * scale);
		dy2 = (int) (DY2 * scale);
		dy3 = (int) (DY3 * scale);
	}

	/**
	 * retourne la largeur d'une touche "blanche"
	 * 
	 * @return
	 */
	public int getWhiteKeyWidth() {
		return dx1;
	}

	/**
	 * retourne la largeur d'une touche "noire"
	 * 
	 * @return
	 */
	public int getBlackKeyWidth() {
		return dx2;
	}

	@Override
	protected void initNotes() {
		keys = new ArrayList<KeyboardKey>();
		initOctave(0);
		initOctave(1);
		updateNotes();
	}

	@Override
	protected void updateNotes() {
		if (null != keys) {
			for (KeyboardKey k : keys) {
				k.updateKeyPosition(dx1);
			}
		}

	}

	private void initOctave(int octave) {
		int xStart = octave * 7;

		// attention à l'index de la note de départ : dans le cas du "tempérament de
		// Pythagore avec les quintes empilées, le tempérament contient une note de plus
		// que l'octave (le si#)
		ITemperament t = getTemperament();
		int idx = octave * t.getNbNotesGamme();

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
			keys.add(new KeyboardKey(KeyType.BriseeAvant, xStart + 5, idx++));
			keys.add(new KeyboardKey(KeyType.BriseeArriere, xStart + 5, idx++));
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

	public class KeyboardKey implements ISelectableNote {
		private Polygon	polygon;
		private boolean	selected;
		/** index de la note dans le tempérament */
		private int		noteIndex;
		private KeyType	keyType;
		private int		notePosition;
		private Point	textPosition;
		private int		width;

		public KeyboardKey(KeyType keyType, int notePosition, int noteIndex) {
			this.keyType = keyType;
			this.notePosition = notePosition;
			this.noteIndex = noteIndex;
			this.selected = false;
			this.polygon = null;
			this.textPosition = null;
		}

		/**
		 * retourne le polygon qui représente le contour de la touche du clavier
		 * 
		 * @return
		 */
		public Polygon getPolygon() {
			return polygon;
		}

		/**
		 * retourne la position d'affichage du nom de la note associée à la touche
		 * 
		 * @return
		 */
		public Point getTextPosition() {
			return textPosition;
		}

		@Override
		public String getTooltipText() {
			return null;
		}

		/**
		 * met à jour la position de la touche, suite à un changement de dimensions du
		 * panel qui représente le clavier
		 * 
		 * @param dx
		 */
		public void updateKeyPosition(int dx) {
			switch (keyType) {
			case DoFa:
				updateKeyPositionTypeDoFa(notePosition * dx);
				break;
			case ReSolLa:
				updateKeyPositionTypeReSolLa(notePosition * dx);
				break;
			case MiSi:
				updateKeyPositionTypeMiSi(notePosition * dx);
				break;
			case NoireComplete:
				updateKeyPositionTypeNoireComplete(notePosition * dx);
				break;
			case BriseeArriere:
				updateKeyPositionTypeNoireBriseeArriere(notePosition * dx);
				break;
			case BriseeAvant:
				updateKeyPositionTypeNoireBriseeAvant(notePosition * dx);
				break;

			}
		}

		/**
		 * retourne true si la touche est une touche blanche
		 * 
		 * @return
		 */
		public boolean isWhiteKey() {
			return keyType == KeyType.DoFa || keyType == KeyType.MiSi || keyType == KeyType.ReSolLa;
		}

		@Override
		public boolean isSelected() {
			return selected;
		}

		@Override
		public void setSelected(boolean pressed) {
			this.selected = pressed;
		}

		/**
		 * retourne l'index de la note associée à la touche, dans le tempérament actuel
		 */
		public int getNoteIndex() {
			return noteIndex;
		}

		/**
		 * détermine si la forme de la touche contient le point (x,y)
		 */
		public boolean containsPoint(int x, int y) {
			return polygon.contains(x, y);
		}

		/**
		 * retourne le nom de la note associée à la touche
		 * 
		 * @return
		 */
		public String getNoteName() {
			return getTemperament().getNoteName(noteIndex);
		}

		/**
		 * retourne la largeur de la touche, en pixels
		 * 
		 * @return
		 */
		public int getWidth() {
			return width;
		}

		public Color getFillColor() {
			Color result;
			if (isSelected()) {
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

		private void updateKeyPositionTypeDoFa(int xStart) {
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

			polygon = new Polygon(x, y, x.length);
			textPosition = new Point((x[1] + x[2]) / 2, y[2]);
			width = dx1;
		}

		private void updateKeyPositionTypeReSolLa(int xStart) {
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
			polygon = new Polygon(x, y, x.length);
			textPosition = new Point((x[3] + x[4]) / 2, y[4]);
			width = dx1;
		}

		private void updateKeyPositionTypeMiSi(int xStart) {
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

			polygon = new Polygon(x, y, x.length);
			textPosition = new Point((x[3] + x[4]) / 2, y[4]);
			width = dx1;
		}

		private void updateKeyPositionTypeNoireComplete(int xStart) {
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

			polygon = new Polygon(x, y, x.length);
			textPosition = new Point((x[1] + x[2]) / 2, y[2]);
			width = dx2;
		}

		private void updateKeyPositionTypeNoireBriseeAvant(int xStart) {
			int[] x = new int[4];
			int[] y = new int[4];

			x[0] = xStart - dx2 / 2;
			y[0] = dy3 + 1;

			x[1] = x[0];
			y[1] = dy2;

			x[2] = x[1] + dx2;
			y[2] = y[1];

			x[3] = x[2];
			y[3] = y[0];

			polygon = new Polygon(x, y, x.length);
			textPosition = new Point((x[1] + x[2]) / 2, y[2]);
			width = dx2;
		}

		private void updateKeyPositionTypeNoireBriseeArriere(int xStart) {
			int[] x = new int[4];
			int[] y = new int[4];

			x[0] = xStart - dx2 / 2;
			y[0] = 0;

			x[1] = x[0];
			y[1] = dy3 - 1;

			x[2] = x[1] + dx2;
			y[2] = y[1];

			x[3] = x[2];
			y[3] = 0;

			polygon = new Polygon(x, y, x.length);
			textPosition = new Point((x[1] + x[2]) / 2, y[2]);
			width = dx2;
		}

	}

	private enum KeyType {
		DoFa, ReSolLa, MiSi, NoireComplete, BriseeArriere, BriseeAvant
	}
}
