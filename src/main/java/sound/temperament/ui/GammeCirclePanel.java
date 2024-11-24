package sound.temperament.ui;

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

import sound.temperament.GammePanelModel;
import sound.temperament.GammeParameterBean;
import sound.temperament.IConstants;
import sound.temperament.NotePosition;
import sound.temperament.Temperament;

public class GammeCirclePanel extends JComponent {
	private static final long serialVersionUID = 1L;
	private GammePanelModel state;

	public GammeCirclePanel(GammeParameterBean parameterBean, GammePanelModel state) {
		super();
		this.state = state;
		parameterBean.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (GammeParameterBean.TEMPERAMENT_PROPERTY.equals(evt.getPropertyName())) {
					setTemperament(parameterBean.getTemperament());
				}
			}
		});
		this.setTemperament(parameterBean.getTemperament());
		this.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				NotePosition p = state.findNote(e.getPoint());
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
					NotePosition p = state.findNote(e.getPoint());
					if (null != p) {
						invertNoteSelection(p);
					}
				} else if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
					state.playSelection(parameterBean.getDuration());
				}
			}

		});
	}

	private void invertNoteSelection(NotePosition p) {
		p.invertSelection();
		repaint();
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

	public void setTemperament(Temperament temperament) {
		state.setTemperament(temperament);

		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				repaint();
			}
		});
	}

	private void drawNote(Graphics g, int noteRank, Color noteColor) {
		NotePosition np = state.getNotePosition(noteRank);
		Color borderColor = np.isSelected() ? Color.red : Color.darkGray;
		drawCircle(g, np.getCenterX(), np.getCenterY(), state.getNoteRadius(), borderColor, noteColor);
		String note = np.getNoteName();
		FontMetrics fm = g.getFontMetrics();
		Rectangle2D bounds = fm.getStringBounds(note, g);
		int x = np.getTextX() - (int) (bounds.getWidth() / 2.0);
		g.setColor(borderColor);
		g.drawString(note, x, np.getTextY());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		state.setPanelDimensions(getWidth(), getHeight());
		drawCircle(g, state.getCenterX(), state.getCenterY(), state.getCircleRadius(), Color.black, null);
		if (state.isGammeDefined()) {
			for (int n = IConstants.DO; n <= IConstants.DO2; n++) {
				Color c = n < IConstants.DO2 ? Color.blue : Color.cyan;
				drawNote(g, n, c);
			}
		}
	}
}
