package drawing;

import javax.swing.JPanel;
import geometry.Shape;
import mvc.DrawingModel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

public class PnlDrawing extends JPanel {

    private DrawingModel model; 

    public PnlDrawing() {
        setBackground(Color.WHITE);
    }

    public void setModel(DrawingModel model) {
        this.model = model;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); 
        
        if (model != null) {
            Iterator<Shape> it = model.getShapes().iterator();
            while (it.hasNext()) {
                it.next().draw(g);
            }
        }
    }
}