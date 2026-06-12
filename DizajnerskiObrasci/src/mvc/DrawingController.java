package mvc;

import drawing.*;
import geometry.*;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class DrawingController {
    
    private DrawingModel model;
    private FrmDrawing frame;

    private boolean lineWaitingForEndPoint = false;
    private Point startPoint;
    private Color edgeColor = Color.BLACK;
    private Color innerColor = Color.WHITE;

    public DrawingController(DrawingModel model, FrmDrawing frame) {
        this.model = model;
        this.frame = frame;
    }

    public void mouseClicked(MouseEvent e) {
        Point mouseClick = new Point(e.getX(), e.getY());
        
        for (Shape shape : model.getShapes()) {
            shape.setSelected(false);
        }
        
        if (frame.isOperationSelectSelected()) {
            for (int i = model.getShapes().size() - 1; i >= 0; i--) {
                if (model.get(i).contains(mouseClick.getX(), mouseClick.getY())) {
                    model.get(i).setSelected(true);
                    break; 
                }
            }
            frame.getView().repaint();
            return;
        }
        
        if (frame.isOperationDrawingSelected()) {
            
            if (frame.isShapePointSelected()) {
                DlgPoint dlgPoint = new DlgPoint();
                dlgPoint.setPoint(mouseClick);
                dlgPoint.setColors(edgeColor);
                dlgPoint.setVisible(true);
                if (dlgPoint.getPoint() != null) {
                    model.add(dlgPoint.getPoint());
                }
                
            } else if (frame.isShapeLineSelected()) {
                if (lineWaitingForEndPoint) {
                    DlgLine dlgLine = new DlgLine();
                    Line line = new Line(startPoint, mouseClick);
                    dlgLine.setLine(line);
                    dlgLine.setColors(edgeColor);
                    dlgLine.setVisible(true);
                    if (dlgLine.getLine() != null) {
                        model.add(dlgLine.getLine());
                    }
                    lineWaitingForEndPoint = false; 
                } else {
                    startPoint = mouseClick;
                    lineWaitingForEndPoint = true; 
                }
                
            } else if (frame.isShapeRectangleSelected()) {
                DlgRectangle dlgRectangle = new DlgRectangle();
                dlgRectangle.setPoint(mouseClick);
                dlgRectangle.setColors(edgeColor, innerColor);
                dlgRectangle.setVisible(true);
                if (dlgRectangle.getRectangle() != null) {
                    model.add(dlgRectangle.getRectangle());
                }
                
            } else if (frame.isShapeCircleSelected()) {
                DlgCircle dlgCircle = new DlgCircle();
                dlgCircle.setPoint(mouseClick);
                dlgCircle.setColors(innerColor, edgeColor); 
                dlgCircle.setVisible(true);
                if (dlgCircle.getCircle() != null) {
                    model.add(dlgCircle.getCircle());
                }
                
            } else if (frame.isShapeDonutSelected()) {
                DlgDonut dlgDonut = new DlgDonut();
                dlgDonut.setPoint(mouseClick);
                dlgDonut.setColors(edgeColor, innerColor);
                dlgDonut.setVisible(true);
                if (dlgDonut.getDonut() != null) {
                    model.add(dlgDonut.getDonut());
                }
            }
            
            frame.getView().repaint();
        }
    }

    public void chooseEdgeColor() {
        Color chosenColor = javax.swing.JColorChooser.showDialog(null, "Choose edge color", edgeColor);
        if (chosenColor != null) {
            edgeColor = chosenColor;
        }
    }

    public void chooseInnerColor() {
        Color chosenColor = javax.swing.JColorChooser.showDialog(null, "Choose inner color", innerColor);
        if (chosenColor != null) {
            innerColor = chosenColor;
        }
    }
    
    public void modifyShape() {
        int index = getSelectedShapeIndex();
        if (index == -1) return; 
        
        Shape shape = model.get(index);
        
        if (shape instanceof Point) {
            DlgPoint dlgPoint = new DlgPoint();
            dlgPoint.setPoint((Point)shape);
            dlgPoint.setVisible(true);
            
            if(dlgPoint.getPoint() != null) {
                model.getShapes().set(index, dlgPoint.getPoint()); // Ažuriramo model
                frame.getView().repaint();
            }
        } else if (shape instanceof Line) {
            DlgLine dlgLine = new DlgLine();
            dlgLine.setLine((Line)shape);
            dlgLine.setVisible(true);
            
            if(dlgLine.getLine() != null) {
                model.getShapes().set(index, dlgLine.getLine());
                frame.getView().repaint();
            }
        } else if (shape instanceof Rectangle) {
            DlgRectangle dlgRectangle = new DlgRectangle();
            dlgRectangle.setRectangle((Rectangle)shape);
            dlgRectangle.setVisible(true);
            
            if(dlgRectangle.getRectangle() != null) {
                model.getShapes().set(index, dlgRectangle.getRectangle());
                frame.getView().repaint();
            }
        } else if (shape instanceof Donut) {
            DlgDonut dlgDonut = new DlgDonut();
            dlgDonut.setDonut((Donut)shape);
            dlgDonut.setVisible(true);
            
            if(dlgDonut.getDonut() != null) {
                model.getShapes().set(index, dlgDonut.getDonut());
                frame.getView().repaint();
            }
        } else if (shape instanceof Circle) {
            DlgCircle dlgCircle = new DlgCircle();
            dlgCircle.setCircle((Circle)shape);
            dlgCircle.setVisible(true);
            
            if(dlgCircle.getCircle() != null) {
                model.getShapes().set(index, dlgCircle.getCircle());
                frame.getView().repaint();
            }
        } 
    }

    public void deleteShape() {
        if (model.getShapes().isEmpty()) return;
        
        int response = javax.swing.JOptionPane.showConfirmDialog(null, 
            "Da li zaista želite da obrišete selektovani oblik?", "Potvrda brisanja", 
            javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
            
        if (response == 0) { 
            model.getShapes().removeIf(shape -> shape.isSelected());
            frame.getView().repaint(); 
        }
    }

    private int getSelectedShapeIndex() {
        for (int i = model.getShapes().size() - 1; i >= 0; i--) {
            if (model.get(i).isSelected()) {
                return i;
            }
        }
        return -1;
    }
    
    public void deselectAll() {
        for (Shape shape : model.getShapes()) {
            shape.setSelected(false);
        }
        frame.getView().repaint();
    }
}