package command;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdBringToBack implements Command {

    private DrawingModel model;
    private Shape shape;
    private int originalIndex = -1;

    public CmdBringToBack(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        originalIndex = model.getShapes().indexOf(shape);
        if (originalIndex != -1 && originalIndex > 0) {
            model.getShapes().remove(originalIndex);
            model.getShapes().add(0, shape); 
        }
    }

    @Override
    public void unexecute() {
        if (originalIndex != -1) {
            model.getShapes().remove(shape);
            model.getShapes().add(originalIndex, shape);
        }
    }

    @Override
    public String toString() {
        return "Brought To Back -> " + shape.toString();
    }
}