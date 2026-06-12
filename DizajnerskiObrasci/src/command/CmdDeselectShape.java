package command;

import geometry.Shape;

public class CmdDeselectShape implements Command {

    private Shape shape;

    public CmdDeselectShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void execute() {
        shape.setSelected(false);
    }

    @Override
    public void unexecute() {
        shape.setSelected(true);
    }

    @Override
    public String toString() {
        return "Deselected -> " + shape.toString();
    }
}