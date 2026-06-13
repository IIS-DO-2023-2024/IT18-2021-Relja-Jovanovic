package geometry;

import java.awt.Color;
import java.awt.Graphics;
import hexagon.Hexagon; 

public class HexagonAdapter extends SurfaceShape {
    
    private Hexagon hexagon;

    public HexagonAdapter(Point center, int radius) {
        this.hexagon = new Hexagon(center.getX(), center.getY(), radius);
    }

    public HexagonAdapter(Point center, int radius, boolean selected, Color edgeColor, Color innerColor) {
        this.hexagon = new Hexagon(center.getX(), center.getY(), radius);
        this.hexagon.setSelected(selected);
        this.hexagon.setBorderColor(edgeColor);
        this.hexagon.setAreaColor(innerColor);
    }


    @Override
    public void draw(Graphics g) {
        hexagon.paint(g); 
    }

    @Override
    public void fill(Graphics g) {
    }

    @Override
    public boolean contains(int x, int y) {
    	return hexagon.doesContain(x, y);   
    }

    @Override
    public HexagonAdapter clone() {
        HexagonAdapter clone = new HexagonAdapter(
            new Point(hexagon.getX(), hexagon.getY()), 
            hexagon.getR(), 
            hexagon.isSelected(), 
            hexagon.getBorderColor(), 
            hexagon.getAreaColor()
        );
        return clone;
    }

    @Override
    public void moveTo(int x, int y) {
        hexagon.setX(x);
        hexagon.setY(y);
    }

    @Override
    public void moveBy(int byX, int byY) {
        hexagon.setX(hexagon.getX() + byX);
        hexagon.setY(hexagon.getY() + byY);
    }

    @Override
    public int compareTo(Object obj) {
        if (obj instanceof HexagonAdapter) {
            return hexagon.getR() - ((HexagonAdapter) obj).getHexagon().getR();
        }
        return 0;
    }

    
    @Override
    public boolean isSelected() {
        return hexagon.isSelected();
    }

    @Override
    public void setSelected(boolean selected) {
        hexagon.setSelected(selected);
        super.setSelected(selected);
    }

    @Override
    public Color getColor() {
        return hexagon.getBorderColor();
    }

    @Override
    public void setColor(Color color) {
        hexagon.setBorderColor(color);
        super.setColor(color);
    }

    @Override
    public Color getInnerColor() {
        return hexagon.getAreaColor();
    }

    @Override
    public void setInnerColor(Color innerColor) {
        hexagon.setAreaColor(innerColor);
        super.setInnerColor(innerColor);
    }


    public Hexagon getHexagon() {
        return hexagon;
    }

    public int getRadius() {
        return hexagon.getR();
    }

    public void setRadius(int radius) {
        hexagon.setR(radius);
    }
    
    public Point getCenter() {
        return new Point(hexagon.getX(), hexagon.getY());
    }

    @Override
	public String toString() {
		return "Hexagon:x=" + getCenter().getX() + ":y=" + getCenter().getY() + 
               ":radius=" + getRadius() + 
               ":edgeColor=" + getColor().getRGB() + ":innerColor=" + getInnerColor().getRGB();
	}
}