package temperament.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import temperament.model.ApplicationState;
import temperament.model.ISelectableNote;
import temperament.model.KeyboardModel;
import temperament.model.KeyboardModel.KeyboardKey;

/**
 * panneau pour montrer la sélection de notes sous forme d'un clavier de piano
 */
public class KeyboardPanel extends JComponent {
	private static final long	serialVersionUID	= 1L;
	private KeyboardModel		model;

	public KeyboardPanel(KeyboardModel model, ApplicationState appState) {
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

		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
					ISelectableNote np = model.findNote(e.getPoint());
					if (null != np) {
						List<Integer> selection = appState.getSelection();
						Integer noteIndex = np.getNoteIndex();
						if ((e.getModifiersEx() & InputEvent.CTRL_DOWN_MASK) == InputEvent.CTRL_DOWN_MASK) {
							if (selection.contains(noteIndex)) {
								selection.remove(noteIndex);
							} else {
								selection.add(noteIndex);
							}
						} else {
							// clic only
							selection.clear();
							selection.add(noteIndex);
						}
						System.out.println("selection :" + selection);
						appState.setSelection(selection);
					}
				}
			}

		});

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
	}
}
