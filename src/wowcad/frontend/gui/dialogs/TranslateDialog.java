package wowcad.frontend.gui.dialogs;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import wowcad.backend.beam.cadManager.CadManager;
import wowcad.backend.beam.cadManager.CommandKeys;
import wowcad.frontend.gui.mainFrame.DialogCallback;

public class TranslateDialog extends JDialog implements ActionListener {

	
	private static final long serialVersionUID = 7412272482173484043L;

	private JComboBox<String> cmbName;
	private JTextField txtDeltaX;
	private JTextField txtDeltaY;


	private JButton btnCancel;
	private JButton btnOK;


	private String command;

	private DialogCallback cbk;

	public TranslateDialog(JFrame owner, DialogCallback cbk, String[] names) {
		super(owner, "Translate", false);
		initComponents(names);
		pack();
		this.cbk = cbk;
	}

	private void initComponents(String[] names) {

		this.setLayout(new GridBagLayout());


		JPanel cont = new JPanel(new GridLayout(3, 2, 5, 5));

		cont.add(new JLabel("Name"));
		cont.add(cmbName = new JComboBox<String>(names));
		cont.add(new JLabel("Delta X"));
		cont.add(txtDeltaX = new JTextField(20));
		cont.add(new JLabel("Delta Y"));
		cont.add(txtDeltaY = new JTextField(20));


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
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnOK)) {
			if(!txtDeltaX.getText().equals("") && !txtDeltaY.getText().equals("")) {

				command = CommandKeys.TRANSLATE + CadManager.COMMAND_SPLITTER + (String)cmbName.getSelectedItem() + CadManager.PARAMETER_SPLITTER + txtDeltaX.getText() + CadManager.PARAMETER_SPLITTER + txtDeltaY.getText();
				dispose();
				cbk.parseCommand(command);

			}
		}
		else if(e.getSource().equals(btnCancel)) {
			cbk.parseCommand("");
			dispose();
		}

	}



	public String getCommand() {
		return command;
	}

	

}
