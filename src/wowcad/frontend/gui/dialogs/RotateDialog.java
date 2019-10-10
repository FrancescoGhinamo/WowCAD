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
import wowcad.frontend.gui.drawer.CoordReceiver;
import wowcad.frontend.gui.mainFrame.DialogCallback;

public class RotateDialog extends JDialog implements ActionListener, CoordReceiver {

	
	private static final long serialVersionUID = 358442967476919783L;

	private JComboBox<String> cmbName;
	private JTextField txtRotCenter;
	private JTextField txtAngle;


	private JButton btnCancel;
	private JButton btnOK;


	private String command;

	private DialogCallback cbk;

	public RotateDialog(JFrame owner, DialogCallback cbk, String[] names) {
		super(owner, "Rotate", false);
		initComponents(names);
		pack();
		this.cbk = cbk;
	}

	private void initComponents(String[] names) {

		this.setLayout(new GridBagLayout());


		JPanel cont = new JPanel(new GridLayout(3, 2, 5, 5));

		cont.add(new JLabel("Name"));
		cont.add(cmbName = new JComboBox<String>(names));
		cont.add(new JLabel("Rot center"));
		cont.add(txtRotCenter = new JTextField(20));
		cont.add(new JLabel("Angle"));
		cont.add(txtAngle = new JTextField(20));
		


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
			if(!txtAngle.getText().equals("") && !txtRotCenter.getText().equals("")) {

				command = CommandKeys.ROTATE + CadManager.COMMAND_SPLITTER + (String)cmbName.getSelectedItem() + CadManager.PARAMETER_SPLITTER + txtRotCenter.getText() + CadManager.PARAMETER_SPLITTER + txtAngle.getText();
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

	@Override
	public void onReceive(double x, double y) {
		txtRotCenter.setText(String.valueOf(x) + "," + String.valueOf(y));
		
	}


}
