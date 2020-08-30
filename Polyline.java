import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;


/**
 * A multi-segment Shape, with straight lines connecting "joint" points -- (x1,y1) to (x2,y2) to (x3,y3) ...
 *
 * @author Chris Bailey-Kellogg, Dartmouth CS 10, Spring 2016
 * @author CBK, updated Fall 2016
 */
public class Polyline implements Shape{
	public ArrayList<Segment> segments; // list of line segments in the polyline
	Color color;

	// TODO: YOUR CODE HERE
	public Polyline(Color color){
		this.segments = new ArrayList<>();
		this.color = color;
	}

	public void addSegment(Integer x1, Integer y1, Integer x2, Integer y2){
		Segment segment = new Segment(x1, y1, x2, y2, color);
		segments.add(segment);
	}

	public void addSegment(Segment segment){
		segments.add(segment);
	}

	@Override
	public void moveBy(int dx, int dy) {
		for(Segment segment:segments){
			segment.moveBy(dx, dy);
		}
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setColor(Color color) {
		for(Segment segment:segments){
			segment.setColor(color);
		}
		this.color = color;
	}

	@Override
	public boolean contains(int x, int y) {
		for(Segment segment:segments){
			if (segment.contains(x, y)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		for(Segment segment:segments){
			segment.draw(g);
		}
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("freehand;").append(color.getRGB());
		for (Segment segment:this.segments){
			res.append(";").append(segment.toString());
		}
		return res.toString();
	}

//	public static Polyline fromString(String segments){
//		String[] list = segments.split(";");
//		Polyline polyline = new Polyline(new Color(Integer.(list[1])));
//
//		for (int i = 2; i < list.length; i++) {
//				polyline.addSegment(Segment.fromString(list[i]));
//			}
//		System.out.println(polyline);
//		return polyline;
//	}
}
