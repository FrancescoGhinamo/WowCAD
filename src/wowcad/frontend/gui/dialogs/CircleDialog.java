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

public class CircleDialog extends JDialog implements ActionListener, CoordReceiver {


	private static final long serialVersionUID = 8470806592789496679L;

	private JTextField txtName;
	private JTextField txtCenter;
	private JTextField txtRadius;

	private JButton btnCancel;
	private JButton btnOK;


	private String command;

	private DialogCallback cbk;

	public CircleDialog(JFrame owner, DialogCallback cbk) {
		super(owner, "Circle", false);
		initComponents();
		pack();
		this.cbk = cbk;
	}

	private void initComponents() {

		this.setLayout(new GridBagLayout());


		JPanel cont = new JPanel(new GridLayout(3, 2, 5, 5));

		cont.add(new JLabel("Name"));
		cont.add(txtName = new JTextField(20));
		cont.add(new JLabel("Center"));
		cont.add(txtCenter = new JTextField(20));
		cont.add(new JLabel("Radius"));
		cont.add(txtRadius = new JTextField(20));


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

		txtCenter.setText(x + "," + y);


	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnOK)) {
			if(!txtName.getText().equals("") && !txtCenter.getText().equals("") && !txtRadius.getText().equals("")) {
				try {

					command = CommandKeys.ADD + CadManager.COMMAND_SPLITTER + KeyWords.CIRCLE + CadManager.PARAMETER_SPLITTER + txtName.getText() + CadManager.PARAMETER_SPLITTER + txtCenter.getText() + CadManager.PARAMETER_SPLITTER + txtRadius.getText();
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
