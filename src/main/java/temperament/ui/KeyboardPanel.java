package temperament.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import temperament.model.ApplicationState;
import temperament.model.KeyboardModel;
import temperament.model.KeyboardModel.KeyboardKey;

/**
 * panneau pour montrer la sélection de notes sous forme d'un clavier de piano
 */
public class KeyboardPanel extends JComponent {
	private static final long	serialVersionUID	= 1L;
	private KeyboardModel		model;

	public KeyboardPanel(ApplicationState appState, KeyboardModel model) {
		this.model = model;
		appState.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (ApplicationState.TEMPERAMENT_PROPERTY.equals(evt.getPropertyName())) {
					repaintLater();
				} else if (ApplicationState.SELECTION_PROPERTY.equals(evt.getPropertyName())) {
					repaint();
				}
			}
		});

		this.addMouseListener(new SelectableNotesMouseListener(appState, model));
	}

	private void repaintLater() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = getWidth();
		int h = getHeight();

		model.setPanelDimensions(w, h);
		if (null != model.getKeys()) {
			for (KeyboardKey k : model.getKeys()) {
				drawKey(g, k);
			}
		}
	}

	private void drawKey(Graphics g, KeyboardKey key) {
		// intérieur de la note
		g.setColor(key.getFillColor());
		g.fillPolygon(key.getPolygon());

		// contour de la note
		g.setColor(Color.black);
		g.drawPolygon(key.getPolygon());

		// name of the key
		if (model.getBlackKeyWidth() > 15) {
			Color txColor = Color.white;
			if (key.isWhiteKey()) {
				txColor = Color.black;
			}
			g.setColor(txColor);
			Point posTx = key.getTextPosition();
			int x = posTx.x - 1;
			int y = posTx.y - 1;

			Graphics2D g2 = (Graphics2D) g;
			AffineTransform bkp = g2.getTransform();
			g2.rotate(-Math.PI / 2.0, x, y);
			g.drawString(key.getNoteName(), x, y);
			g2.setTransform(bkp);
		}
	}
}
