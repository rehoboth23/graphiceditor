import java.awt.Color;
import java.awt.Graphics;

/**
 * A rectangle-shaped Shape
 * Defined by an upper-left corner (x1,y1) and a lower-right corner (x2,y2)
 * with x1<=x2 and y1<=y2
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Fall 2012
 * @author CBK, updated Fall 2016
 */
public class Rectangle implements Shape {

	private int x1, y1, x2, y2;		// upper left and lower right
	private Color color;
	// TODO: YOUR CODE HERE
	public Rectangle(Integer x1, Integer y1, Color color){
		this.x1 = x1; this.y1 = y1; this.x2 = x1; this.y2 = y1; this.color = color;
	}
	public Rectangle(Integer x1, Integer y1, Integer x2, Integer y2, Color color){
		this.x1 = x1; this.y1 = y1; this.x2 = x2; this.y2 = y2; this.color = color;
	}

	public void setCorners(int x1, int y1, int x2, int y2) {
		// Ensure correct upper left and lower right
		this.x1 = Math.min(x1, x2);
		this.y1 = Math.min(y1, y2);
		this.x2 = Math.max(x1, x2);
		this.y2 = Math.max(y1, y2);
	}

	@Override
	public void moveBy(int dx, int dy) {
		x1 += dx; x2 += dx; y1 += dy; y2 += dy;
	}

	@Override
	public Color getColor() {
		return this.color;
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	@Override
	public boolean contains(int x, int y) {
		return this.x1 <= x && this.x2 >= x && this.y1 <= y && this.y2 >= y;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x1, y1, Math.abs(x2-x1), Math.abs(y2-y1));
	}

	public String toString() {
		return "rectangle "+x1+" "+y1+" "+x2+" "+y2+" "+color.getRGB();
	}

	public static Rectangle fromString(String information){
		String[] info = information.split("\\s");
		return new Rectangle(Integer.parseInt(info[1]), Integer.parseInt(info[2]), Integer.parseInt(info[3]), Integer.parseInt(info[4]), new Color(Integer.parseInt(info[5])));

	}
}
