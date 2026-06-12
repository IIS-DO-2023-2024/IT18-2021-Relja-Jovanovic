package drawing;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.border.TitledBorder;

import drawing.DlgCircle;
import drawing.DlgDonut;
import drawing.DlgLine;
import drawing.DlgPoint;
import drawing.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;

import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Cursor;

public class FrmDrawing extends JFrame {

	private final int OPERATION_DRAWING = 1;
	private final int OPERATION_EDIT_DELETE = 0;
	
	private int activeOperation = OPERATION_DRAWING;
	
	private PnlDrawing pnlDrawing = new PnlDrawing();
	private ButtonGroup btnsOperation = new ButtonGroup();
	private ButtonGroup btnsShapes = new ButtonGroup();
	private JToggleButton btnOperationDrawing = new JToggleButton("Drawing");
	private JToggleButton btnOperationEditOrDelete = new JToggleButton("Select");
	private JButton btnActionEdit = new JButton("Modify");
	private JButton btnActionDelete = new JButton("Delete");
	private JToggleButton btnShapePoint = new JToggleButton("Point");
	private JToggleButton btnShapeLine = new JToggleButton("Line");
	private JToggleButton btnShapeRectangle = new JToggleButton("Rectangle");
	private JToggleButton btnShapeCircle = new JToggleButton("Circle");
	private JToggleButton btnShapeDonut = new JToggleButton("Donut");
	private JButton btnColorEdge = new JButton("Edge color");
	private JButton btnColorInner = new JButton("Inner color");
	
	private Color edgeColor = Color.BLACK, innerColor = Color.WHITE;
	boolean lineWaitingForEndPoint = false;
	private Point startPoint;
	
	
	private JPanel contentPane;
	protected DrawingController controller;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
	    EventQueue.invokeLater(new Runnable() {
	        public void run() {
	            try {
	                DrawingModel model = new DrawingModel();
	                FrmDrawing frame = new FrmDrawing();
	                
	                frame.getView().setModel(model);
	                
	                DrawingController controller = new DrawingController(model, frame);
	                frame.setController(controller); 
	                
	                frame.setVisible(true);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}
	protected void setController(DrawingController controller) {
			this.controller=controller;
	}
	public PnlDrawing getView() {
		return this.pnlDrawing;
	}
	/**
	 * Create the frame.
	 */
	public FrmDrawing() {
		setTitle("Relja Jovanovic IT-18/2021");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 700);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(1100, 700));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		pnlDrawing.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (controller != null) {
                    controller.mouseClicked(e);
                }
            }
        });
		
		contentPane.add(pnlDrawing, BorderLayout.CENTER);
		
        btnActionEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.modifyShape();
                }
            }
        });
        
        btnActionDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (controller != null) {
                    controller.deleteShape();
                }
            }
        });
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		btnOperationDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperationDrawing();
			}
		});
		btnOperationDrawing.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsOperation.add(btnOperationDrawing);
		panel_2.add(btnOperationDrawing);
		
		btnOperationEditOrDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperationEditDelete();
			}
		});
		btnOperationEditOrDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsOperation.add(btnOperationEditOrDelete);
		panel_2.add(btnOperationEditOrDelete);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		
		btnActionEdit.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (controller != null) {
		            controller.modifyShape();
		        }
		    }
		});
		btnActionEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(btnActionEdit);

		btnActionDelete.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        if (controller != null) {
		            controller.deleteShape();
		        }
		    }
		});
		btnActionDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(btnActionDelete);		
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		btnShapePoint.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapePoint);
		panel_4.add(btnShapePoint);
		
		btnShapeLine.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeLine);
		panel_4.add(btnShapeLine);
		
		btnShapeRectangle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeRectangle);
		panel_4.add(btnShapeRectangle);
		
		btnShapeCircle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeCircle);
		panel_4.add(btnShapeCircle);
		
		btnShapeDonut.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeDonut);
		panel_4.add(btnShapeDonut);
		
		btnOperationDrawing.setSelected(true);
		setOperationDrawing();
		
		btnColorEdge.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        controller.chooseEdgeColor();
		    }
		});

		btnColorInner.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        controller.chooseInnerColor();
		    }
		});
		
	}
	
	private void setOperationDrawing() {
	
		activeOperation = OPERATION_DRAWING;
		
		if (controller != null) {
	        controller.deselectAll();
	    }
		
		btnActionEdit.setEnabled(false);
		btnActionDelete.setEnabled(false);
		
		btnShapePoint.setEnabled(true);
		btnShapeLine.setEnabled(true);
		btnShapeRectangle.setEnabled(true);
		btnShapeCircle.setEnabled(true);
		btnShapeDonut.setEnabled(true);
		
		btnColorEdge.setEnabled(true);
		btnColorInner.setEnabled(true);
	}
	

	private void setOperationEditDelete() {
		activeOperation = OPERATION_EDIT_DELETE;
		
		btnActionEdit.setEnabled(true);
		btnActionDelete.setEnabled(true);
		
		btnShapePoint.setEnabled(false);
		btnShapeLine.setEnabled(false);
		btnShapeRectangle.setEnabled(false);
		btnShapeCircle.setEnabled(false);
		btnShapeDonut.setEnabled(false);
		
		btnColorEdge.setEnabled(false);
		btnColorInner.setEnabled(false);
	}

	public boolean isOperationDrawingSelected() {
	    return btnOperationDrawing.isSelected();
	}

	public boolean isOperationSelectSelected() {
	    return btnOperationEditOrDelete.isSelected();
	}

	public boolean isShapePointSelected() {
	    return btnShapePoint.isSelected();
	}

	public boolean isShapeLineSelected() {
	    return btnShapeLine.isSelected();
	}

	public boolean isShapeRectangleSelected() {
	    return btnShapeRectangle.isSelected();
	}

	public boolean isShapeCircleSelected() {
	    return btnShapeCircle.isSelected();
	}

	public boolean isShapeDonutSelected() {
	    return btnShapeDonut.isSelected();
	}
}
