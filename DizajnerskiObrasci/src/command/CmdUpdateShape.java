package command;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdUpdateShape implements Command {

    private DrawingModel model;
    private Shape original;
    private Shape newState;
    private Shape oldState;

    public CmdUpdateShape(DrawingModel model, Shape original, Shape newState) {
        this.model = model;
        this.original = original;
        this.newState = newState;
        this.oldState = original.clone();
    }

    @Override
    public void execute() {
        int index = model.getShapes().indexOf(original);
        if (index != -1) {
            model.getShapes().set(index, newState);
            original = newState; 
        }
    }

    @Override
    public void unexecute() {
        int index = model.getShapes().indexOf(original);
        if (index != -1) {
            model.getShapes().set(index, oldState);
            original = oldState; 
        }
    }

    @Override
    public String toString() {
        return "Modified -> " + oldState.toString() + " TO " + newState.toString();
    }
}