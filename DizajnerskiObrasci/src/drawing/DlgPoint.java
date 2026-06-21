package drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;

import geometry.Point;

import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

public class DlgPoint extends JDialog {
	private JTextField txtX;
	private JTextField txtY;
	
	private Point point = null;
	private Color color = null, innerColor = null;
	private JLabel lblX;
	private JLabel lblY;
	private JButton btnEdgeColor;
	
	public DlgPoint() {
		setResizable(false);
		setTitle("Relja Jovanovic IT-18/2021");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModal(true);
		setBounds(100, 100, 300, 171);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout(0, 0));
		{
			JPanel pnlTop = new JPanel();
			getContentPane().add(pnlTop, BorderLayout.CENTER);
			{
				lblX = new JLabel("X coordinate", SwingConstants.CENTER);
			}
			{
				txtX = new JTextField();
				txtX.setColumns(10);
			}
			{
				lblY = new JLabel("Y coordinate");
				lblY.setHorizontalAlignment(SwingConstants.CENTER);
			}
			{
				txtY = new JTextField();
				txtY.setColumns(10);
			}
			{
				btnEdgeColor = new JButton("Choose a color");
				btnEdgeColor.setHorizontalAlignment(SwingConstants.CENTER);
				btnEdgeColor.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						color = JColorChooser.showDialog(null, "Choose a color", color);
						if (color == null) color = Color.BLACK;
					}
				});
			}
			GroupLayout gl_pnlTop = new GroupLayout(pnlTop);
			gl_pnlTop.setHorizontalGroup(
				gl_pnlTop.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_pnlTop.createSequentialGroup()
						.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_pnlTop.createSequentialGroup()
						.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_pnlTop.createSequentialGroup()
						.addComponent(btnEdgeColor, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
						.addGap(70))
			);
			gl_pnlTop.setVerticalGroup(
				gl_pnlTop.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_pnlTop.createSequentialGroup()
						.addGroup(gl_pnlTop.createParallelGroup(Alignment.LEADING)
							.addComponent(lblX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtX, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlTop.createParallelGroup(Alignment.LEADING)
							.addComponent(lblY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(txtY, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnEdgeColor, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(16, Short.MAX_VALUE))
			);
			pnlTop.setLayout(gl_pnlTop);
		}
		{
			JPanel pnlBottom = new JPanel();
			getContentPane().add(pnlBottom, BorderLayout.SOUTH);
			pnlBottom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			{
				JButton btnOk = new JButton("OK");
				btnOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						try {
							int newX = Integer.parseInt(txtX.getText());
							int newY = Integer.parseInt(txtY.getText());

							if(newX < 0 || newY < 0) {
								JOptionPane.showMessageDialog(null, "You entered wrong value!!", "Error!", JOptionPane.ERROR_MESSAGE);
								return;
							}
							point = new Point(newX, newY,false, color);
							dispose();
							
							
						} catch (Exception ex) {
							JOptionPane.showMessageDialog(null,  "You entered wrong data type!", "Error!", JOptionPane.ERROR_MESSAGE);
						}
					}
				});
				pnlBottom.add(btnOk);
			}
			{
				JButton btnNotOk = new JButton("Exit");
				btnNotOk.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				pnlBottom.add(btnNotOk);
			}
		}
	}

	public Point getPoint() {
		return point;
	}
	public void setColors(Color edgeColor) {
		this.color = edgeColor;
	}
	
	public void setPoint(Point point) {
		txtX.setText("" + point.getX());
		txtY.setText("" + point.getY());
		color = point.getColor();
	}
}
