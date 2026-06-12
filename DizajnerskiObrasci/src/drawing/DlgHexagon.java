package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import geometry.Point;
import geometry.HexagonAdapter;

public class DlgHexagon extends JDialog {
    private JTextField txtX;
    private JTextField txtY;
    private JTextField txtRadius;
    
    private HexagonAdapter hexagonAdapter = null;
    private Color edgeColor = null, innerColor = null;

    public DlgHexagon() {
        setResizable(false);
        setTitle("Relja Jovanovic IT-18/2021");
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setBounds(100, 100, 300, 180);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(0, 0));
        {
            JPanel panel = new JPanel();
            getContentPane().add(panel, BorderLayout.CENTER);
            panel.setLayout(new GridLayout(4, 2, 0, 0));
            {
                JLabel lblX = new JLabel("Center X", SwingConstants.CENTER);
                panel.add(lblX);
            }
            {
                txtX = new JTextField();
                panel.add(txtX);
                txtX.setColumns(10);
            }
            {
                JLabel lblY = new JLabel("Center Y", SwingConstants.CENTER);
                panel.add(lblY);
            }
            {
                txtY = new JTextField();
                panel.add(txtY);
                txtY.setColumns(10);
            }
            {
                JLabel lblRadius = new JLabel("Radius", SwingConstants.CENTER);
                panel.add(lblRadius);
            }
            {
                txtRadius = new JTextField();
                panel.add(txtRadius);
                txtRadius.setColumns(10);
            }
            {
                JButton btnEdgeColor = new JButton("Edge color");
                btnEdgeColor.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        edgeColor = JColorChooser.showDialog(null, "Choose edge color", edgeColor);
                        if (edgeColor == null) edgeColor = Color.BLACK;
                    }
                });
                panel.add(btnEdgeColor);
            }
            {
                JButton btnInnerColor = new JButton("Inner color");
                btnInnerColor.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        innerColor = JColorChooser.showDialog(null, "Choose inner color", innerColor);
                        if (innerColor == null) innerColor = Color.WHITE;
                    }
                });
                panel.add(btnInnerColor);
            }
        }
        {
            JPanel panel = new JPanel();
            getContentPane().add(panel, BorderLayout.SOUTH);
            panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
            {
                JButton btnOk = new JButton("OK");
                btnOk.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        try {
                            int newX = Integer.parseInt(txtX.getText());
                            int newY = Integer.parseInt(txtY.getText());
                            int newRadius = Integer.parseInt(txtRadius.getText());

                            if(newX < 0 || newY < 0 || newRadius < 1) {
                                JOptionPane.showMessageDialog(null, "Uneli ste neispravnu vrednost!", "Greška!", JOptionPane.ERROR_MESSAGE);
                                return;
                            }
                            hexagonAdapter = new HexagonAdapter(new Point(newX, newY), newRadius, false, edgeColor, innerColor);
                            dispose();
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Uneli ste pogrešan tip podataka!", "Greška!", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                panel.add(btnOk);
            }
            {
                JButton btnExit = new JButton("Exit");
                btnExit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                panel.add(btnExit);
            }
        }
    }

    public HexagonAdapter getHexagon() {
        return hexagonAdapter;
    }
    
    public void setPoint(Point point) {
        txtX.setText("" + point.getX());
        txtY.setText("" + point.getY());
    }
    
    public void setColors(Color edgeColor, Color innerColor) {
        this.edgeColor = edgeColor;
        this.innerColor = innerColor;
    }
    
    public void setHexagon(HexagonAdapter hex) {
        txtX.setText("" + hex.getCenter().getX());
        txtY.setText("" + hex.getCenter().getY());
        txtRadius.setText("" + hex.getRadius());
        edgeColor = hex.getColor();
        innerColor = hex.getInnerColor();
    }
}