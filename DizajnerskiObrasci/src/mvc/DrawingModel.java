package mvc;

import geometry.Shape;
import java.util.ArrayList;

public class DrawingModel {
    
    private ArrayList<Shape> shapes = new ArrayList<>();

    public void add(Shape shape) {
        shapes.add(shape);
    }

    public void remove(Shape shape) {
        shapes.remove(shape);
    }

    public Shape get(int index) {
        return shapes.get(index);
    }

    public ArrayList<Shape> getShapes() {
        return shapes;
    }
}