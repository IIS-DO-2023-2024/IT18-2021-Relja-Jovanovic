package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle{

	private int innerRadius;
	
	public Donut () {
		
	}
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		//setCenter(center);
		//setRadius(radius);
		this.innerRadius = innerRadius;
	}
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center,radius,innerRadius);
		setSelected(selected);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius, selected);
		setColor(color);
	}

	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius, selected, color);
		setInnerColor(innerColor);
	}
	
	public double area() {
		return super.area() - innerRadius * innerRadius * Math.PI;
	}
	
	public boolean contains(int x, int y) {
		return (super.contains(x, y) && getCenter().distance(x,y) >= innerRadius);
	}
	
	@Override
	public boolean contains(Point p) {
		return super.contains(p) && getCenter().distance(p.getX(), p.getY()) >= innerRadius;
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Donut) {
			Donut pomocni = (Donut) obj;
			if(pomocni.getCenter().equals(getCenter()) &&
					pomocni.getRadius() == getRadius() &&
					pomocni.innerRadius == innerRadius) 
				return true;
			return false;
		}
		return false;
	}
	
	
	
	public void setInnerRadius(int innerRadius) {
		this.innerRadius = innerRadius;
	}
	public int getInnerRadius() {
		return innerRadius;
	}
	
	
	public String toString() {
		return super.toString() + " , innerRadius: " + innerRadius;
				
	}
	public void draw(Graphics g) {
		java.awt.Graphics2D g2d = (java.awt.Graphics2D) g.create();

		java.awt.geom.Ellipse2D outer = new java.awt.geom.Ellipse2D.Double(
			getCenter().getX() - getRadius(), 
			getCenter().getY() - getRadius(), 
			getRadius() * 2, 
			getRadius() * 2
		);

		java.awt.geom.Ellipse2D inner = new java.awt.geom.Ellipse2D.Double(
			getCenter().getX() - getInnerRadius(), 
			getCenter().getY() - getInnerRadius(), 
			getInnerRadius() * 2, 
			getInnerRadius() * 2
		);

		java.awt.geom.Area donutArea = new java.awt.geom.Area(outer);
		donutArea.subtract(new java.awt.geom.Area(inner));

		g2d.setColor(getInnerColor());
		g2d.fill(donutArea);

		g2d.setColor(getColor()); 
		g2d.draw(donutArea);

		if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - getRadius() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + getRadius() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - getRadius() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + getRadius() - 2, 4, 4);
			
			g.drawRect(getCenter().getX() - getInnerRadius() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() + getInnerRadius() - 2, getCenter().getY() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() - getInnerRadius() - 2, 4, 4);
			g.drawRect(getCenter().getX() - 2, getCenter().getY() + getInnerRadius() - 2, 4, 4);
		}
		
		g2d.dispose();
	}	
	
	public int compareTo(Object obj) {
		if (obj instanceof Donut) {
			return (int) (this.area() - ((Donut) obj).area());
		}
		return 0;
	}
	
	@Override
	public Donut clone() {
		return new Donut(this.getCenter().clone(), this.getRadius(), this.getInnerRadius(), this.isSelected(), this.getColor(), this.getInnerColor());
	}

}
