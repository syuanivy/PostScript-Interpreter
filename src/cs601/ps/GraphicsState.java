package cs601.ps;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.util.ArrayList;

public class GraphicsState {
	public static class Text {
		String s;
		double x;
		double y;
		public String fontName;
		public int fontSize;

		public Text(String s, double x, double y, String fontName, int fontSize) {
			this.s = s;
			this.x = x;
			this.y = y;
			this.fontName = fontName;
			this.fontSize = fontSize;
		}
	}

	public PSInterpreter interpreter;

	public double x, y; // current location
	public double scaleX = 1.0, scaleY = 1.0;
	public double maxX, maxY;
	public String fontName;
	public int fontSize;
	public java.util.List<Text> strings = new ArrayList<>();
	public java.util.List<GeneralPath> paths = new ArrayList<>();
	public GeneralPath currentPath = new GeneralPath();

	public GraphicsState(PSInterpreter interpreter) {
		this.interpreter = interpreter;
	}

	public GraphicsState(GraphicsState old) {
		this.currentPath = old.currentPath;
		this.fontName = old.fontName;
		this.fontSize = old.fontSize;
		this.paths = old.paths;
		this.scaleX = old.scaleX;
		this.scaleY = old.scaleY;
		this.x = old.x;
		this.y = old.y;
	}

	public void lineto(int x, int y) {
		currentPath.lineTo(x, y);
		moveto(x, y);
	}

	public void moveto(int x, int y) {
		this.x = x;
		this.y = y;
		currentPath.moveTo(x, y);
	}

	public void rmoveto(int dx, int dy) {
		this.x += dx;
		this.y += dy;
	}

	public void stroke() {
		paths.add(currentPath);
		currentPath = new GeneralPath();
	}

	public void show(String s) {
		strings.add(new Text(s,x,y,fontName,fontSize));
	}

	public void updateBounds() {
		Dimension d = getBounds();
		this.maxX = d.getWidth();
		this.maxY = d.getHeight();
//		System.out.printf("bounds %f, %f\n", maxX, maxY);
	}

	public Dimension getBounds() {
		double maxX = 0;
		double maxY = 0;
		for (GeneralPath p : paths) {
			maxX = Math.max(maxX, p.getBounds2D().getMaxX());
			maxY = Math.max(maxY, p.getBounds2D().getMaxY());
		}
		for (GraphicsState.Text text : strings) {
			Font font = new Font(text.fontName, Font.PLAIN, text.fontSize);
			FontMetrics metrics = interpreter.view.getFontMetrics(font);
			int height = metrics.getHeight();
			int advance = metrics.stringWidth(text.s);
			maxX = Math.max(maxX, text.x + advance);
			maxY = Math.max(maxY, text.y + height);
		}
		return new Dimension((int)maxX, (int)maxY);
	}
}
