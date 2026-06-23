package command;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdRemoveShape implements Command {

    private DrawingModel model;
    private Shape shape;
    private int index;

    public CmdRemoveShape(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
         index = model.getShapes().indexOf(shape);

        model.remove(shape);
        
    }

    @Override
    public void unexecute() {
        model.getShapes().add(index, shape);


    }

    @Override
    public String toString() {
        return "Deleted -> " + shape.toString();
    }
}