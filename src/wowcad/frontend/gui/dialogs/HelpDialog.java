package wowcad.frontend.gui.dialogs;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class HelpDialog extends JDialog {

	private static final long serialVersionUID = -3084840682148933011L;
	
	private JTextArea txtHelp;
	private JButton btnClose;
	
	
	public HelpDialog(JFrame owner, boolean modale) {
		super(owner, "Help", modale);		
		initComponents();
		pack();
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weightx = 10;
		gbc.weightx = 17;
		gbc.insets = new Insets(5, 5, 5, 5);
		
		this.add(initTxtPanel(), gbc);
		
		gbc.gridy = 1;
		gbc.weighty = 3;
		this.add(initButtPan(), gbc);
		
		
	}
	
	private JScrollPane initTxtPanel() {
		
		txtHelp = new JTextArea(30, 40);
		txtHelp.setText("WowCAD\n"
				+ "Command help:\n"
				+ "General structure:\n"
				+ "<command name>:<param1>,<param2>,...,<paramN>\n"
				+ "\n"
				+ "To save the drawing:\n"
				+ "\tSAVE\n"
				+ "\n"
				+ "To add a shape:\n"
				+ "\tADD:<shape>,<param1>,<param2>,<param3>...\n"
				+ "\tAvailable shapes:\n"
				+ "Circle\n"
				+ "\tCIRCLE,<name>,<centerX>,<centerY>,<radius>\n"
				+ "Ellipse\n"
				+ "\tELLIPSE,<name>,<centerX>,<centerY>,<radiusX>,<radiusY>\n"
				+ "Point\n"
				+ "\tPOINT,<name>,<x>,<y>\n"
				+ "Polygon\n"
				+ "\tPOLYGON,<name>,<x1>,<y1>,<x2>,<y2>,...,<xn>,<yn>\n"
				+ "Polyline\n"
				+ "\tPOLYLINE,<name>,<x1>,<y1>,<x2>,<y2>,...,<xn>,<yn>\n"
				+ "Segment\n"
				+ "\tSEGMENT,<name>,<x1>,<y1>,<x2>,<y2>\n"
				+ "\t\t e.g: ADD:SEGMENT,3.6,2.7,-23,12\n"
				+ "\n"
				+ "To change drawing description:\n"
				+ "\tDESCRIPTION:<newDescription>\n"
				+ "\n"
				+ "To change drawing name:\n"
				+ "\tNAME:<newName>\n"
				+ "\n"
				+ "To remove a primitive:\n"
				+ "\tREMOVE:<primitiveName>\n"
				+ "\n"
				+ "To rotate a primitive:\n"
				+ "\tROTATE:<primitiveName>,<rotCenterX>,<rotCenterY>,<rotDegrees>\n"
				+ "\n"
				+ "To change drawing save location:\n"
				+ "\tSAVE_LOC:<newSaveLocation>\n"
				+ "\n"
				+ "To scale a primitive:\n"
				+ "\tSCALE:<primitiveName>,<scaleFactor>\n"
				+ "\n"
				+ "To translate a primitive:\n"
				+ "\tTRANSLATE:<primitiveName>,<deltaX>,<deltaY>\n"
				+ "\n"
				+ "To export the drawing:\n"
				+ "\tEXPORT:<expLocation>,<axis?>,<grid?>,<fact>\n"
				+ "\t ? items to be replaced with TRUE or FALSE");
		txtHelp.setEditable(false);
		
		JScrollPane scrl = new JScrollPane(txtHelp);
		
		
		return scrl;
	}

	private JPanel initButtPan() {
		JPanel pan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnClose = new JButton("Close");
		btnClose.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
			
		});
		pan.add(btnClose);
		return pan;
	}
	
}
