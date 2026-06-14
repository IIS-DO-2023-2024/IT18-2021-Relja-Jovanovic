package mvc;

import drawing.*;
import geometry.*;
import java.awt.Color;
import java.awt.event.MouseEvent;

public class DrawingController implements observer.Subject {
    
    private DrawingModel model;
    private FrmDrawing frame;

    private boolean lineWaitingForEndPoint = false;
    private Point startPoint;
    private Color edgeColor = Color.BLACK;
    private Color innerColor = Color.WHITE;
    
    private java.util.ArrayList<String> loadedLogLines = new java.util.ArrayList<>();
    private int currentLogLineIndex = 0;
    
    private java.util.ArrayList<command.Command> undoList = new java.util.ArrayList<>();
    private java.util.ArrayList<command.Command> redoList = new java.util.ArrayList<>();
    
    private java.util.ArrayList<observer.Observer> observers = new java.util.ArrayList<>();

    public DrawingController(DrawingModel model, FrmDrawing frame) {
        this.model = model;
        this.frame = frame;
    }

    public void mouseClicked(MouseEvent e) {
        Point mouseClick = new Point(e.getX(), e.getY());
        
        if (frame.isOperationSelectSelected()) {
            boolean shapeClicked = false;

            for (int i = model.getShapes().size() - 1; i >= 0; i--) {
                if (model.get(i).contains(mouseClick.getX(), mouseClick.getY())) {
                    Shape shape = model.get(i);
                    if (shape.isSelected()) {
                        command.CmdDeselectShape cmd = new command.CmdDeselectShape(shape);
                        cmd.execute();
                        undoList.add(cmd);
                        frame.log(cmd.toString());
                    } else {
                        command.CmdSelectShape cmd = new command.CmdSelectShape(shape);
                        cmd.execute();
                        undoList.add(cmd);
                        frame.log(cmd.toString());
                    }
                    redoList.clear();
                    updateUndoRedoButtons();
                    shapeClicked = true;
                    break; 
                }
            }

            if (!shapeClicked) {
                for (int i = model.getShapes().size() - 1; i >= 0; i--) {
                    Shape shape = model.get(i);
                    if (shape.isSelected()) {
                        command.CmdDeselectShape cmd = new command.CmdDeselectShape(shape);
                        cmd.execute();
                        undoList.add(cmd);
                        redoList.clear();
                        frame.log(cmd.toString());
                        updateUndoRedoButtons();
                    }
                }
            }

            frame.getView().repaint();
            notifyObservers(); 
            return;
        }
        
        for (Shape shape : model.getShapes()) {
            shape.setSelected(false);
        }
        
        if (frame.isOperationDrawingSelected()) {
            
            if (frame.isShapePointSelected()) {
                DlgPoint dlgPoint = new DlgPoint();
                dlgPoint.setPoint(mouseClick);
                dlgPoint.setColors(edgeColor);
                dlgPoint.setVisible(true);
                if (dlgPoint.getPoint() != null) {
                    command.CmdAddShape cmd = new command.CmdAddShape(model, dlgPoint.getPoint());
                    cmd.execute();
                    undoList.add(cmd);
                    redoList.clear();
                    frame.log(cmd.toString());
                    updateUndoRedoButtons();
                }
                
            } else if (frame.isShapeLineSelected()) {
                if (lineWaitingForEndPoint) {
                    DlgLine dlgLine = new DlgLine();
                    Line line = new Line(startPoint, mouseClick);
                    dlgLine.setLine(line);
                    dlgLine.setColors(edgeColor);
                    dlgLine.setVisible(true);
                    if (dlgLine.getLine() != null) {
                        command.CmdAddShape cmd = new command.CmdAddShape(model, dlgLine.getLine());
                        cmd.execute();
                        undoList.add(cmd);
                        redoList.clear();
                        frame.log(cmd.toString());
                        updateUndoRedoButtons();
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
                    command.CmdAddShape cmd = new command.CmdAddShape(model, dlgRectangle.getRectangle());
                    cmd.execute();
                    undoList.add(cmd);
                    redoList.clear();
                    frame.log(cmd.toString());
                    updateUndoRedoButtons();
                }
                
            } else if (frame.isShapeCircleSelected()) {
                DlgCircle dlgCircle = new DlgCircle();
                dlgCircle.setPoint(mouseClick);
                dlgCircle.setColors(innerColor, edgeColor); 
                dlgCircle.setVisible(true);
                if (dlgCircle.getCircle() != null) {
                    command.CmdAddShape cmd = new command.CmdAddShape(model, dlgCircle.getCircle());
                    cmd.execute();
                    undoList.add(cmd);
                    redoList.clear();
                    frame.log(cmd.toString());
                    updateUndoRedoButtons();
                }
                
            } else if (frame.isShapeDonutSelected()) {
                DlgDonut dlgDonut = new DlgDonut();
                dlgDonut.setPoint(mouseClick);
                dlgDonut.setColors(edgeColor, innerColor);
                dlgDonut.setVisible(true);
                if (dlgDonut.getDonut() != null) {
                    command.CmdAddShape cmd = new command.CmdAddShape(model, dlgDonut.getDonut());
                    cmd.execute();
                    undoList.add(cmd);
                    redoList.clear();
                    frame.log(cmd.toString());
                    updateUndoRedoButtons();
                }
            } else if (frame.isShapeHexagonSelected()) {
                DlgHexagon dlgHex = new DlgHexagon();
                dlgHex.setPoint(mouseClick);
                dlgHex.setColors(edgeColor, innerColor);
                dlgHex.setVisible(true);
                if (dlgHex.getHexagon() != null) {
                    command.CmdAddShape cmd = new command.CmdAddShape(model, dlgHex.getHexagon());
                    cmd.execute();
                    undoList.add(cmd);
                    redoList.clear();
                    frame.log(cmd.toString());
                    updateUndoRedoButtons();
                }
            }
            
            frame.getView().repaint();
        }
        notifyObservers();
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
                command.CmdUpdateShape cmd = new command.CmdUpdateShape(model, shape, dlgPoint.getPoint());
                cmd.execute();
                undoList.add(cmd);
                redoList.clear();
                frame.log(cmd.toString());
                updateUndoRedoButtons();
                frame.getView().repaint();
            }
        } else if (shape instanceof Line) {
            DlgLine dlgLine = new DlgLine();
            dlgLine.setLine((Line)shape);
            dlgLine.setVisible(true);
            
            if(dlgLine.getLine() != null) {
                command.CmdUpdateShape cmd = new command.CmdUpdateShape(model, shape, dlgLine.getLine());
                cmd.execute();
                undoList.add(cmd);
                redoList.clear();
                frame.log(cmd.toString());
                updateUndoRedoButtons();
                frame.getView().repaint();
            }
        } else if (shape instanceof Rectangle) {
            DlgRectangle dlgRectangle = new DlgRectangle();
            dlgRectangle.setRectangle((Rectangle)shape);
            dlgRectangle.setVisible(true);
            
            if(dlgRectangle.getRectangle() != null) {
                command.CmdUpdateShape cmd = new command.CmdUpdateShape(model, shape, dlgRectangle.getRectangle());
                cmd.execute();
                undoList.add(cmd);
                redoList.clear();
                frame.log(cmd.toString());
                updateUndoRedoButtons();
                frame.getView().repaint();
            }
        } else if (shape instanceof Donut) {
            DlgDonut dlgDonut = new DlgDonut();
            dlgDonut.setDonut((Donut)shape);
            dlgDonut.setVisible(true);
            
            if(dlgDonut.getDonut() != null) {
                command.CmdUpdateShape cmd = new command.CmdUpdateShape(model, shape, dlgDonut.getDonut());
                cmd.execute();
                undoList.add(cmd);
                redoList.clear();
                frame.log(cmd.toString());
                updateUndoRedoButtons();
                frame.getView().repaint();
            }
        } else if (shape instanceof Circle) {
            DlgCircle dlgCircle = new DlgCircle();
            dlgCircle.setCircle((Circle)shape);
            dlgCircle.setVisible(true);
            
            if(dlgCircle.getCircle() != null) {
                command.CmdUpdateShape cmd = new command.CmdUpdateShape(model, shape, dlgCircle.getCircle());
                cmd.execute();
                undoList.add(cmd);
                redoList.clear();
                frame.log(cmd.toString());
                updateUndoRedoButtons();
                frame.getView().repaint();
            }
        } else if (shape instanceof geometry.HexagonAdapter) {
            DlgHexagon dlgHex = new DlgHexagon();
            dlgHex.setHexagon((geometry.HexagonAdapter) shape);
            dlgHex.setVisible(true);

            if(dlgHex.getHexagon() != null) {
                command.CmdUpdateShape cmd = new command.CmdUpdateShape(model, shape, dlgHex.getHexagon());
                cmd.execute();
                undoList.add(cmd);
                redoList.clear();
                frame.log(cmd.toString());
                updateUndoRedoButtons();
                frame.getView().repaint();
            }
        } 
        notifyObservers();
    }

    public void deleteShape() {
        if (model.getShapes().isEmpty()) return;
        
        int response = javax.swing.JOptionPane.showConfirmDialog(null, 
            "Da li zaista želite da obrišete selektovani oblik?", "Potvrda brisanja", 
            javax.swing.JOptionPane.YES_NO_OPTION, javax.swing.JOptionPane.QUESTION_MESSAGE);
            
        if (response == 0) { 
            for (int i = model.getShapes().size() - 1; i >= 0; i--) {
                Shape shape = model.get(i);
                if (shape.isSelected()) {
                    command.CmdRemoveShape cmd = new command.CmdRemoveShape(model, shape);
                    cmd.execute();
                    undoList.add(cmd);
                    frame.log(cmd.toString());
                }
            }
            redoList.clear();
            updateUndoRedoButtons();
            frame.getView().repaint(); 
        }
        notifyObservers();
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
        notifyObservers();
    }
    
    public void undo() {
        if (!undoList.isEmpty()) {
            command.Command cmd = undoList.remove(undoList.size() - 1);
            cmd.unexecute();
            redoList.add(cmd);
            frame.log("Undo -> " + cmd.toString());
            updateUndoRedoButtons();
            frame.getView().repaint();
        }
        notifyObservers();
    }

    public void redo() {
        if (!redoList.isEmpty()) {
            command.Command cmd = redoList.remove(redoList.size() - 1);
            cmd.execute();
            undoList.add(cmd);
            frame.log("Redo -> " + cmd.toString());
            updateUndoRedoButtons();
            frame.getView().repaint();
        }
        notifyObservers();
    }

    public void toFront() {
        int index = getSelectedShapeIndex();
        if (index == -1) return;
        Shape shape = model.get(index);
        command.CmdToFront cmd = new command.CmdToFront(model, shape);
        cmd.execute();
        undoList.add(cmd);
        redoList.clear();
        frame.log(cmd.toString());
        updateUndoRedoButtons();
        frame.getView().repaint();
        notifyObservers();
    }

    public void toBack() {
        int index = getSelectedShapeIndex();
        if (index == -1) return;
        Shape shape = model.get(index);
        command.CmdToBack cmd = new command.CmdToBack(model, shape);
        cmd.execute();
        undoList.add(cmd);
        redoList.clear();
        frame.log(cmd.toString());
        updateUndoRedoButtons();
        frame.getView().repaint();
        notifyObservers();
    }

    public void bringToFront() {
        int index = getSelectedShapeIndex();
        if (index == -1) return;
        Shape shape = model.get(index);
        command.CmdBringToFront cmd = new command.CmdBringToFront(model, shape);
        cmd.execute();
        undoList.add(cmd);
        redoList.clear();
        frame.log(cmd.toString());
        updateUndoRedoButtons();
        frame.getView().repaint();
        notifyObservers();
    }

    public void bringToBack() {
        int index = getSelectedShapeIndex();
        if (index == -1) return;
        Shape shape = model.get(index);
        command.CmdBringToBack cmd = new command.CmdBringToBack(model, shape);
        cmd.execute();
        undoList.add(cmd);
        redoList.clear();
        frame.log(cmd.toString());
        updateUndoRedoButtons();
        frame.getView().repaint();
        notifyObservers();
    }
    
    private void updateUndoRedoButtons() {
        frame.getBtnUndo().setEnabled(!undoList.isEmpty());
        frame.getBtnRedo().setEnabled(!redoList.isEmpty());
    }
    
    @Override
    public void addObserver(observer.Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(observer.Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        int selectedCount = 0;
        for (Shape shape : model.getShapes()) {
            if (shape.isSelected()) {
                selectedCount++;
            }
        }
        for (observer.Observer observer : observers) {
            observer.update(selectedCount);
        }
    }
    
    public void saveDrawing() {
        strategy.SaveManager manager = new strategy.SaveManager(new strategy.SaveDrawing(model));
        manager.save();
    }

    public void saveLog() {
        strategy.SaveManager manager = new strategy.SaveManager(new strategy.SaveLog(frame));
        manager.save();
    }
    
    public void openDrawing() {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Bin file", "bin"));
        fileChooser.setDialogTitle("Open drawing");

        if (fileChooser.showOpenDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
            try (java.io.ObjectInputStream ois = new java.io.ObjectInputStream(new java.io.FileInputStream(fileChooser.getSelectedFile()))) {
                
                java.util.ArrayList<Shape> loadedShapes = (java.util.ArrayList<Shape>) ois.readObject();
                model.getShapes().clear();
                model.getShapes().addAll(loadedShapes);
                
                undoList.clear();
                redoList.clear();
                updateUndoRedoButtons();
                
                frame.getView().repaint();
                notifyObservers();
                javax.swing.JOptionPane.showMessageDialog(null, "Crtež je uspešno učitan!", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Greška pri učitavanju crteža!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
    
    public void openLog() {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text file", "txt"));
        fileChooser.setDialogTitle("Open log");

        if (fileChooser.showOpenDialog(null) == javax.swing.JFileChooser.APPROVE_OPTION) {
            try {
                loadedLogLines.clear();
                java.nio.file.Files.lines(fileChooser.getSelectedFile().toPath()).forEach(line -> loadedLogLines.add(line));
                
                currentLogLineIndex = 0;
                
                model.getShapes().clear();
                undoList.clear();
                redoList.clear();
                frame.getTxtLog().setText("");
                updateUndoRedoButtons();
                frame.getView().repaint();
                
                frame.getBtnLoadNext().setEnabled(true);
                
                javax.swing.JOptionPane.showMessageDialog(null, "Log uspešno učitan! Klikćite 'Load Next' za iscrtavanje.", "Success", javax.swing.JOptionPane.INFORMATION_MESSAGE);
                
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(null, "Greška pri učitavanju loga!", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    public void readNextLogLine() {
        if (currentLogLineIndex < loadedLogLines.size()) {
            String line = loadedLogLines.get(currentLogLineIndex);
            
            parseLine(line);
            
            frame.log(line); 
            currentLogLineIndex++;
            
            if (currentLogLineIndex >= loadedLogLines.size()) {
                frame.getBtnLoadNext().setEnabled(false);
                javax.swing.JOptionPane.showMessageDialog(null, "Kraj loga!", "Info", javax.swing.JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    

    private void parseLine(String line) {
        if (line.startsWith("Undo")) {
            undo();
            return;
        } else if (line.startsWith("Redo")) {
            redo();
            return;
        }

        String[] mainParts = line.split("->");
        if (mainParts.length < 2) return;
        
        String commandName = mainParts[0].trim();
        String shapeData = mainParts[1].trim();

        if (commandName.equals("Added")) {
            Shape shape = parseShape(shapeData);
            command.CmdAddShape cmd = new command.CmdAddShape(model, shape);
            cmd.execute();
            undoList.add(cmd);
            redoList.clear();
            
        } else if (commandName.equals("Modified")) {
            String[] states = shapeData.split(" TO ");
            Shape oldShape = parseShape(states[0]);
            Shape newShape = parseShape(states[1]);
            Shape realShape = findShapeInModel(oldShape);
            
            if (realShape != null) {
                command.CmdUpdateShape cmd = new command.CmdUpdateShape(model, realShape, newShape);
                cmd.execute();
                undoList.add(cmd);
                redoList.clear();
            }
            
        } else {
            Shape shape = parseShape(shapeData);
            Shape realShape = findShapeInModel(shape);
            
            if (realShape != null) {
                command.Command cmd = null;
                switch (commandName) {
                    case "Deleted": cmd = new command.CmdRemoveShape(model, realShape); break;
                    case "Selected": cmd = new command.CmdSelectShape(realShape); break;
                    case "Deselected": cmd = new command.CmdDeselectShape(realShape); break;
                    case "Moved To Front": cmd = new command.CmdToFront(model, realShape); break;
                    case "Moved To Back": cmd = new command.CmdToBack(model, realShape); break;
                    case "Brought To Front": cmd = new command.CmdBringToFront(model, realShape); break;
                    case "Brought To Back": cmd = new command.CmdBringToBack(model, realShape); break;
                }
                if (cmd != null) {
                    cmd.execute();
                    undoList.add(cmd);
                    redoList.clear();
                }
            }
        }
        updateUndoRedoButtons();
        frame.getView().repaint();
        notifyObservers();
    }

    private Shape parseShape(String shapeStr) {
        String[] parts = shapeStr.split(":");
        String type = parts[0].trim();

        try {
            if (type.equals("Point")) {
                int x = Integer.parseInt(parts[1].split("=")[1]);
                int y = Integer.parseInt(parts[2].split("=")[1]);
                int color = Integer.parseInt(parts[3].split("=")[1]);
                return new geometry.Point(x, y, false, new Color(color));
                
            } else if (type.equals("Line")) {
                int startX = Integer.parseInt(parts[1].split("=")[1]);
                int startY = Integer.parseInt(parts[2].split("=")[1]);
                int endX = Integer.parseInt(parts[3].split("=")[1]);
                int endY = Integer.parseInt(parts[4].split("=")[1]);
                int color = Integer.parseInt(parts[5].split("=")[1]);
                return new geometry.Line(new geometry.Point(startX, startY), new geometry.Point(endX, endY), false, new Color(color));
                
            } else if (type.equals("Rectangle")) {
                int x = Integer.parseInt(parts[1].split("=")[1]);
                int y = Integer.parseInt(parts[2].split("=")[1]);
                int w = Integer.parseInt(parts[3].split("=")[1]);
                int h = Integer.parseInt(parts[4].split("=")[1]);
                int edgeC = Integer.parseInt(parts[5].split("=")[1]);
                int innerC = Integer.parseInt(parts[6].split("=")[1]);
                return new geometry.Rectangle(new geometry.Point(x, y), w, h, false, new Color(edgeC), new Color(innerC));
                
            } else if (type.equals("Circle")) {
                int x = Integer.parseInt(parts[1].split("=")[1]);
                int y = Integer.parseInt(parts[2].split("=")[1]);
                int r = Integer.parseInt(parts[3].split("=")[1]);
                int edgeC = Integer.parseInt(parts[4].split("=")[1]);
                int innerC = Integer.parseInt(parts[5].split("=")[1]);
                return new geometry.Circle(new geometry.Point(x, y), r, false, new Color(edgeC), new Color(innerC));
                
            } else if (type.equals("Donut")) {
                int x = Integer.parseInt(parts[1].split("=")[1]);
                int y = Integer.parseInt(parts[2].split("=")[1]);
                int r = Integer.parseInt(parts[3].split("=")[1]);
                int innerR = Integer.parseInt(parts[4].split("=")[1]);
                int edgeC = Integer.parseInt(parts[5].split("=")[1]);
                int innerC = Integer.parseInt(parts[6].split("=")[1]);
                return new geometry.Donut(new geometry.Point(x, y), r, innerR, false, new Color(edgeC), new Color(innerC));
                
            } else if (type.equals("Hexagon")) {
                int x = Integer.parseInt(parts[1].split("=")[1]);
                int y = Integer.parseInt(parts[2].split("=")[1]);
                int r = Integer.parseInt(parts[3].split("=")[1]);
                int edgeC = Integer.parseInt(parts[4].split("=")[1]);
                int innerC = Integer.parseInt(parts[5].split("=")[1]);
                return new geometry.HexagonAdapter(new geometry.Point(x, y), r, false, new Color(edgeC), new Color(innerC));
            }
        } catch (Exception e) {
            System.out.println("Greska pri parsiranju oblika: " + shapeStr);
        }
        return null;
    }

    private Shape findShapeInModel(Shape parsedShape) {
        for (int i = model.getShapes().size() - 1; i >= 0; i--) {
            if (model.getShapes().get(i).equals(parsedShape)) {
                return model.getShapes().get(i);
            }
        }
        return null;
    }
}