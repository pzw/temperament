package temperament.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import temperament.model.AppState;
import temperament.model.NotePosition;
import temperament.model.TemperamentBaseCircleModel;

public class TemperamentCircleView extends JComponent {
	private static final long			serialVersionUID	= 1L;
	private TemperamentBaseCircleModel	model;
	private AppState					appState;

	public TemperamentCircleView(AppState appState, TemperamentBaseCircleModel model) {
		super();
		this.appState = appState;
		this.model = model;
		appState.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (AppState.TEMPERAMENT_PROPERTY.equals(evt.getPropertyName())) {
					repaintLater();
				} else if (AppState.SELECTION_PROPERTY.equals(evt.getPropertyName())) {
					repaint();
				}
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				NotePosition p = model.findNote(e.getPoint());
				if (null != p) {
					setToolTipText(p.getTooltip());
				} else {
					setToolTipText(null);
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
			}
		});
		this.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 1) {
					model.invertNodeSelectionAtPoint(e.getPoint());
				}
			}

		});
	}

	private void drawCircle(Graphics g, int xc, int yc, int r, Color borderColor, Color fillColor) {
		int x1 = xc - r;
		int y1 = yc - r;
		int d = 2 * r;

		if (null != fillColor) {
			g.setColor(fillColor);
			g.fillArc(x1, y1, d, d, 0, 360);
		}
		g.setColor(borderColor);
		g.drawArc(x1, y1, d, d, 0, 360);
	}

	public void repaintLater() {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				repaint();
			}
		});
	}

	private void drawNote(Graphics g, int noteRank, Color noteColor, boolean octave2) {
		NotePosition np = model.getNotePosition(noteRank);
		Color borderColor = np.isSelected() ? Color.red : Color.darkGray;
		if (np.isSelected()) {
			int selRank = model.getSelectionRank(noteRank);
			noteColor = appState.getSelectionColor(selRank);
		}
		drawCircle(g, np.getCenterX(), np.getCenterY(), model.getNoteRadius(), borderColor, noteColor);

		if (!octave2) {
			String note = np.getNoteName();
			FontMetrics fm = g.getFontMetrics();
			Rectangle2D bounds = fm.getStringBounds(note, g);
			int x = np.getTextX() - (int) (bounds.getWidth() / 2.0);
			g.setColor(Color.darkGray);
			g.drawString(note, x, np.getTextY());
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		model.setPanelDimensions(getWidth(), getHeight());
		drawCircle(g, model.getCenterX(), model.getCenterY(), model.getCircleRadius(), Color.black, null);
		if (model.isTemperamentDefined()) {
			int nbNotesGamme = model.getNbNotesGamme();
			for (int n = 0; n < model.getNbNotes(); n++) {
				Color c = n < model.getNbNotes() ? Color.gray : Color.darkGray;
				drawNote(g, n, c, n >= nbNotesGamme);
			}
		}
	}
}
