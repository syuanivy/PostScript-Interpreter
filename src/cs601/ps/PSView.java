package cs601.ps;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;

/** The interpreter uses this Canvas to paint() the entire scene. */
public class PSView extends Canvas {
	public static Dimension imageSize = new Dimension(600, 300);
	protected PSInterpreter interpreter;

	public PSView(PSInterpreter interpreter) {
		this.interpreter = interpreter;
	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		interpreter.init(); // reset state of interpreter
		GraphicsState gstate = interpreter.gstate;

		AffineTransform saveXform = g2.getTransform();

		// draw any lines we have found, translated so that 0,0 is the lower left
		// and coordinates grow up and to the right (opposite of Java)
		AffineTransform flipVertically = new AffineTransform();
		flipVertically.scale(gstate.scaleX, -gstate.scaleY);
		flipVertically.translate(0, -gstate.maxY);
		g2.transform(flipVertically);

		for (GeneralPath p : gstate.paths) {
			g2.draw(p);
		}

		g2.setTransform(saveXform); // restore

		// draw text now with anti-aliasing on
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
											   RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setRenderingHints(rh);

		// scale coordinates of text strings according to any scale they
		// executed in postscript
		AffineTransform scale = new AffineTransform();
		scale.scale(gstate.scaleX, gstate.scaleY);
		g2.transform(scale);

		double maxY = gstate.maxY; // use to flip y coordinates.
		for (GraphicsState.Text text : gstate.strings) {
			Font f = new Font(text.fontName, Font.PLAIN, text.fontSize);
			g2.setFont(f);
//			System.out.println(text.s + "@" + text.x + "," + text.y +"("+(maxY-text.y)+")");
			g2.drawString(text.s, (int)text.x, (int)(maxY-text.y));
		}

		g2.setTransform(saveXform); // restore
	}

	@Override
	public Dimension getPreferredSize() {
		// set the preferred size of the view to be the extent
		// of the drawn image with 10 pixels buffer on width and height
		GraphicsState gstate = interpreter.gstate;
		return new Dimension(
			(int) (gstate.maxX * gstate.scaleX) + 10,
			(int) (gstate.maxY * gstate.scaleY) + 10
		);
	}
}
