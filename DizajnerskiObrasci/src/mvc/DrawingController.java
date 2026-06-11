package mvc;

import drawing.FrmDrawing; // Tvoj glavni frame
import geometry.Point;
import java.awt.event.MouseEvent;

public class DrawingController {
    
    private DrawingModel model;
    private FrmDrawing frame;

    public DrawingController(DrawingModel model, FrmDrawing frame) {
        this.model = model;
        this.frame = frame;
    }

    public void mouseClicked(MouseEvent e) {
        Point mouseClick = new Point(e.getX(), e.getY());
        
    }
}