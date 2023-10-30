package sort;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;

import geometry.Circle;
import geometry.Rectangle;

public class SortFrame extends JFrame {
	private JPanel contentPane;
	private ArrayList <Circle> arrayList=new ArrayList<Circle>();
	private DefaultListModel<Circle> dlm= new DefaultListModel<Circle>();
	JList lstSort = new JList();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SortFrame frame = new SortFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public SortFrame() {
		setTitle("Relja Jovanovic IT-18/2021");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 325, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel pnlCentar = new JPanel();
		contentPane.add(pnlCentar, BorderLayout.CENTER);
		
		JScrollPane scrlPnSort = new JScrollPane();
		GroupLayout gl_pnlCentar = new GroupLayout(pnlCentar);
		gl_pnlCentar.setHorizontalGroup(
			gl_pnlCentar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlCentar.createSequentialGroup()
					.addGap(53)
					.addComponent(scrlPnSort, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(63, Short.MAX_VALUE))
		);
		gl_pnlCentar.setVerticalGroup(
			gl_pnlCentar.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlCentar.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrlPnSort, GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		lstSort.setModel(dlm);
		scrlPnSort.setViewportView(lstSort);
		pnlCentar.setLayout(gl_pnlCentar);
		
		JPanel pnlSouth = new JPanel();
		contentPane.add(pnlSouth, BorderLayout.SOUTH);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SortDialog dlgSort = new SortDialog();
				dlgSort.setVisible(true);
				
				if(dlgSort.getCircle()!=null) {
					arrayList.add(dlgSort.getCircle());
					lstSort.setModel(sortCircles());
				}
			}
		});
		
		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		GroupLayout gl_pnlSouth = new GroupLayout(pnlSouth);
		gl_pnlSouth.setHorizontalGroup(
			gl_pnlSouth.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSouth.createSequentialGroup()
					.addGap(46)
					.addComponent(btnAdd)
					.addGap(54)
					.addComponent(btnClose)
					.addContainerGap(61, Short.MAX_VALUE))
		);
		gl_pnlSouth.setVerticalGroup(
			gl_pnlSouth.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlSouth.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_pnlSouth.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAdd)
						.addComponent(btnClose)))
		);
		pnlSouth.setLayout(gl_pnlSouth);
	}
	
	private DefaultListModel<Circle> sortCircles()
	{
		
		ArrayList<Circle> pomList=new ArrayList<Circle>();
		for(int i=0;i<arrayList.size();i++)
		{
			pomList.add(i,arrayList.get(i));
		}
		for(int i=1;i<=pomList.size()-1;i++) {
			for(int j=0;j<pomList.size()-1;j++) {
				if(pomList.get(j).area()>pomList.get(j+1).area()) {
					Circle pom=pomList.get(j);
					pomList.set(j, pomList.get(j+1));
					pomList.set(j+1, pom);
				}
			}
		}
		dlm.removeAllElements();
		for(Circle i:pomList) {
			dlm.addElement(i);
		}
		
		return dlm;
	}
}
