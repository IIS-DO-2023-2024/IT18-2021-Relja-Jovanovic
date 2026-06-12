package command;

import geometry.Shape;
import mvc.DrawingModel;
import java.util.Collections;

public class CmdToBack implements Command {

    private DrawingModel model;
    private Shape shape;

    public CmdToBack(DrawingModel model, Shape shape) {
        this.model = model;
        this.shape = shape;
    }

    @Override
    public void execute() {
        int index = model.getShapes().indexOf(shape);
        if (index > 0 && index != -1) {
            Collections.swap(model.getShapes(), index, index - 1);
        }
    }

    @Override
    public void unexecute() {
        int index = model.getShapes().indexOf(shape);
        if (index < model.getShapes().size() - 1 && index != -1) {
            Collections.swap(model.getShapes(), index, index + 1);
        }
    }

    @Override
    public String toString() {
        return "Moved To Back -> " + shape.toString();
    }
}