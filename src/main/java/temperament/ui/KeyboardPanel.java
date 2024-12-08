package temperament.ui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;

import temperament.model.KeyboardModel;
import temperament.model.KeyboardModel.KeyboardKey;

/**
 * panneau pour montrer la sélection de notes sous forme d'un clavier de piano
 */
public class KeyboardPanel extends JComponent {
	private static final long	serialVersionUID	= 1L;
	private KeyboardModel		model;
	private boolean				blackAndWhite		= false;
	private Color				whiteKey;
	private Color				whiteKeyPressed;
	private Color				blackKey;
	private Color				blackKeyPressed;

	public KeyboardPanel(KeyboardModel model) {
		this.model = model;
		whiteKey = blackAndWhite ? Color.white : new Color(252, 197, 109);
		whiteKeyPressed = Color.yellow;
		blackKey = blackAndWhite ? Color.black : new Color(102, 69, 17);
		blackKeyPressed = Color.green;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = getWidth();
		int h = getHeight();

		model.setPanelDimensions(w, h);
		for (KeyboardKey k : model.getKeys()) {
			drawKey(g, k);
		}
	}

	private void drawKey(Graphics g, KeyboardKey key) {
		Color fill;
		if (key.isWhiteKey()) {
			fill = key.isPressed() ? whiteKeyPressed : whiteKey;
		} else {
			fill = key.isPressed() ? blackKeyPressed : blackKey;
		}

		// intérieur de la note
		g.setColor(fill);
		g.fillPolygon(key.getPolygon());

		// contour de la note
		g.setColor(Color.black);
		g.drawPolygon(key.getPolygon());
	}
}
