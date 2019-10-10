package wowcad.frontend.gui.dialogs;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import wowcad.backend.beam.cadManager.CadManager;
import wowcad.backend.beam.cadManager.CommandKeys;
import wowcad.backend.beam.cadManager.KeyWords;
import wowcad.frontend.gui.drawer.CoordReceiver;
import wowcad.frontend.gui.mainFrame.DialogCallback;

public class SegmentDialog extends JDialog implements ActionListener, CoordReceiver {

	
	private static final long serialVersionUID = -3543461104273342075L;
	
	private JTextField txtName;
	private JTextField txtp1;
	private JTextField txtp2;
	
	private JButton btnCancel;
	private JButton btnOK;

	
	private boolean serv1;

	private String command;
	
	private DialogCallback cbk;
	
	public SegmentDialog(JFrame owner, DialogCallback cbk) {
		super(owner, "Segment", false);
		initComponents();
		pack();
		this.cbk = cbk;
		this.serv1 = true;
	}

	private void initComponents() {

		this.setLayout(new GridBagLayout());
		

		JPanel cont = new JPanel(new GridLayout(3, 2, 5, 5));
		
		cont.add(new JLabel("Name"));
		cont.add(txtName = new JTextField(20));
		cont.add(new JLabel("First end"));
		cont.add(txtp1 = new JTextField(20));
		cont.add(new JLabel("Second end"));
		cont.add(txtp2 = new JTextField(20));
		
		
		JPanel but = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);

		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		but.add(btnCancel);
		but.add(btnOK);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = 10;
		gbc.weighty = 8;
		gbc.fill = GridBagConstraints.BOTH;

		this.add(cont, gbc);

		gbc.gridy = 1;
		gbc.weighty = 1;

		this.add(but, gbc);



	}

	@Override
	public void onReceive(double x, double y) {
		if(serv1) {
			txtp1.setText(x + "," + y);
		}
		else {
			txtp2.setText(x + "," + y);
		}
		serv1 = !serv1;

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnOK)) {
			if(!txtName.getText().equals("") && !txtp1.getText().equals("") && !txtp2.getText().equals("")) {
				try {
					
					command = CommandKeys.ADD + CadManager.COMMAND_SPLITTER + KeyWords.SEGMENT + CadManager.PARAMETER_SPLITTER + txtName.getText() + CadManager.PARAMETER_SPLITTER + txtp1.getText() + CadManager.PARAMETER_SPLITTER + txtp2.getText();
					dispose();
					cbk.parseCommand(command);
				}
				catch(Exception ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}
		else if(e.getSource().equals(btnCancel)) {
			cbk.parseCommand("");
			dispose();
		}

	}

}
