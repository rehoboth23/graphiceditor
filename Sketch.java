import java.awt.*;
import java.util.HashMap;

/**
 *
 */
public class Sketch {
    public HashMap<Integer, Shape> shapeMap;
    public int id;
    HashMap<Integer, String> stringMap;

    /**
     * constructor for a sketch
     */
    public Sketch(){
        shapeMap = new HashMap<>();
        id = 0;
        stringMap = new HashMap<>();
    }

    /**
     *
     * @param shapeType type of shape to add
     * @param x x coordinate
     * @param y y coordinate
     * @param r red color value
     * @param g green color value
     * @param b blue color value
     */
    public Integer addShape(Integer id, String shapeType, Integer x, Integer y, Integer r, Integer g, Integer b){
        switch (shapeType) {
            case "ellipse":
                Ellipse ellipse = new Ellipse(x, y, new Color(r, g, b));
                shapeMap.put(id, ellipse); stringMap.put(id, ellipse.toString());
                break;
            case "rectangle":
                Rectangle rectangle = new Rectangle(x, y, new Color(r, g, b));
                shapeMap.put(id, rectangle); stringMap.put(id, rectangle.toString());
                break;
            case "segment":
                Segment segment = new Segment(x, y, new Color(r, g, b));
                shapeMap.put(id, segment); stringMap.put(id, segment.toString());
                break;
            case "freehand":
                Polyline polyline = new Polyline(new Color(r, g, b));
                shapeMap.put(id, polyline); stringMap.put(id, polyline.toString());
                break;
        }

        this.id+=1;
        return this.id-1;
    }

    /**
     *
     * @param shape shape to add
     */
    public void addShape(Shape shape){
        shapeMap.put(id, shape);
    }

    /**
     *
     * @param g graphivs object to draw in
     */
    public void drawSketch(Graphics g){
        for(Shape shape:shapeMap.values()){
           shape.draw(g);
        }
    }

    /**
     *
     * @param shape shape to identify
     * @return id of shape in sketch
     */
    public Integer getShapeId(Shape shape){
        for (Integer id:shapeMap.keySet()){
            if(shapeMap.get(id) == shape){
                return id;
            }
        }
        return null;
    }

    /**
     *
     * @param id id of shape to recolor
     * @param r red value of intended color
     * @param g green value of intended color
     * @param b blue value of intended color
     */
    public void recolorShape(Integer id, Integer r, Integer g, Integer b){
        shapeMap.get(id).setColor(new Color(r, g, b));
    }

    /**
     *
     * @param shapeType shape type to edit
     * @param id id of a shape in sketch
     * @param dx draw form x position
     * @param dy draw from y position
     * @param px draw to x position
     * @param py draw to y position
     */
    public void setShapeCorners(String shapeType, int id, int dx, int dy, int px, int py){
        Shape shape = shapeMap.get(id);
        switch (shapeType) {
            case "ellipse":
                ((Ellipse) shape).setCorners(dx, dy, px, py);
                stringMap.put(id, shape.toString());
                break;
            case "rectangle":
                ((Rectangle) shape).setCorners(dx, dy, px, py);
                stringMap.put(id, shape.toString());
                break;
            case "segment":
                ((Segment) shape).setEnd(px, py);
                stringMap.put(id, shape.toString());
                break;
            case "freehand":
                ((Polyline) shape).addSegment(dx, dy, px, py);
                stringMap.put(id, shape.toString());
                break;
        }
    }

    /**
     *
     * @param id id of shape to move
     * @param mx move from x position
     * @param my move from x position
     * @param py move to x position
     * @param px move to y position
     */
    public void moveShape(Integer id, int mx, int my, int px, int py){
        shapeMap.get(id).moveBy(px-mx, py-my);
    }

    /**
     *
     * @param id id of shape to remove
     */
    public void removeShape(Integer id){
        shapeMap.remove(id);
        stringMap.remove(id);
    }

    /**
     *
     * @param point point to consider
     * @return shape at the considered point if any
     */
    public Shape ShapeAtPoint(Point point){
        for(Integer id:shapeMap.keySet()){
            if(shapeMap.get(id).contains(point.x, point.y)){
                return shapeMap.get(id);
            }
        }
        return null;
    }

    /**
     *
     * @return true if no shape in sketch; false otherwise
     */
    public boolean isEmpty(){
        return  shapeMap.isEmpty();
    }

    /**
     *
     * @return string state of sketch
     */
    public String toString(){
        StringBuilder res = new StringBuilder();
        for(Integer id:stringMap.keySet()){
            res.append("#").append(id).append("@").append(shapeMap.get(id));
        }
        return res.toString();
    }
    public void fromString(String string){
        Sketch sketch;
        if (string.contains("#")){
            String[] shapes = string.split("#");
            for(String shape:shapes){
                String[] split = shape.split("@");
                if(shape.contains("ellipse")){
                    Ellipse ellipse = Ellipse.fromString(split[1]);
                    this.shapeMap.put(Integer.parseInt(split[0]), ellipse);
                    this.id = Math.max(Integer.parseInt(split[0]), this.id)+1;
                }
                else if(shape.contains("rectangle")){
                    Rectangle rec = Rectangle.fromString(shape);
                    this.shapeMap.put(Integer.parseInt(split[0]), rec);
                    this.id = Math.max(Integer.parseInt(split[0]), this.id)+1;
                }
                else if(shape.contains("segment")){
                    Segment seg = Segment.fromString(shape);
                    this.shapeMap.put(Integer.parseInt(split[0]), seg);
                    this.id = Math.max(Integer.parseInt(split[0]), this.id)+1;
                }
            }
        }
    }
}
