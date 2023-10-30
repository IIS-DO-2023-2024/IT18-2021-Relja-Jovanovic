package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

import geometry.Donut;
import geometry.Point;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgDonut extends JDialog {
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	private JTextField txtInnerRadius;
	
	private Donut donut = null;
	private Color edgeColor = null, innerColor = null;

	public DlgDonut() {
		setResizable(false);
		setTitle("Relja Jovanovic IT-18/2021");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 300, 210);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel pnlCenter = new JPanel();
			getContentPane().add(pnlCenter, BorderLayout.CENTER);
			pnlCenter.setLayout(new GridLayout(5, 2, 0, 0));
			{
				JLabel lblX = new JLabel("X coordinate", SwingConstants.CENTER);
				pnlCenter.add(lblX);
			}
			{
				txtX = new JTextField();
				pnlCenter.add(txtX);
				txtX.setColumns(10);
			}
			{
				JLabel lblY = new JLabel("Y coordinate");
				lblY.setHorizontalAlignment(SwingConstants.CENTER);
				pnlCenter.add(lblY);
			}
			{
				txtY = new JTextField();
				pnlCenter.add(txtY);
				txtY.setColumns(10);
			}
			{
				JLabel lblRadius = new JLabel("Radius");
				lblRadius.setHorizontalAlignment(SwingConstants.CENTER);
				pnlCenter.add(lblRadius);
			}
			{
				txtRadius = new JTextField();
				pnlCenter.add(txtRadius);
				txtRadius.setColumns(10);
			}
			{
				JLabel lblInnerRadius = new JLabel("Inner radius");
				lblInnerRadius.setHorizontalAlignment(SwingConstants.CENTER);
				pnlCenter.add(lblInnerRadius);
			}
			{
				txtInnerRadius = new JTextField();
				pnlCenter.add(txtInnerRadius);
				txtInnerRadius.setColumns(10);
			}
			{
				JButton btnEdgeColor = new JButton("Edge color");
				btnEdgeColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnEdgeColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						edgeColor = JColorChooser.showDialog(null, "Choose edge color", edgeColor);
						if (edgeColor == null) edgeColor = Color.BLACK;
					}
				});
				pnlCenter.add(btnEdgeColor);
			}
			{
				JButton btnInnerColor = new JButton("Inner Color");
				btnInnerColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnInnerColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						innerColor = JColorChooser.showDialog(null, "Choose inner color", innerColor);
						if (innerColor == null) innerColor = Color.WHITE;
					}
				});
				pnlCenter.add(btnInnerColor);
			}
		}
		{
			JPanel pnlSouth = new JPanel();
			getContentPane().add(pnlSouth, BorderLayout.SOUTH);
			pnlSouth.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							int newX = Integer.parseInt(txtX.getText());
							int newY = Integer.parseInt(txtY.getText());
							int newRadius = Integer.parseInt(txtRadius.getText());
							int newInnerRadius = Integer.parseInt(txtInnerRadius.getText());

							if(newX < 0 || newY < 0 || newRadius < 1 || newInnerRadius < 1 || newInnerRadius >= newRadius) {
								JOptionPane.showMessageDialog(null,  "You entered wrong value!!", "Error!", JOptionPane.ERROR_MESSAGE);
								return;
							}
							donut = new Donut(new Point(newX, newY), newRadius, newInnerRadius,false, edgeColor, innerColor);
							dispose();
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null,  "You entered wrong data type!", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				pnlSouth.add(btnOk);
			}
			{
				JButton btnExit = new JButton("Exit");
				btnExit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				pnlSouth.add(btnExit);
			}
		}
	}

	public Donut getDonut() {
		return donut;
	}
	
	public void setPoint(Point point) {
		txtX.setText("" + point.getX());
		txtY.setText("" + point.getY());
	}
	
	public void setColors(Color edgeColor, Color innerColor) {
		this.edgeColor = edgeColor;
		this.innerColor = innerColor;
	}
	
	public void setDonut(Donut donut) {
		txtX.setText("" + donut.getCenter().getX());
		txtY.setText("" + donut.getCenter().getY());
		txtRadius.setText("" + donut.getRadius());
		txtInnerRadius.setText("" + donut.getInnerRadius());
		edgeColor = donut.getColor();
		innerColor = donut.getInnerColor();
	}
}
