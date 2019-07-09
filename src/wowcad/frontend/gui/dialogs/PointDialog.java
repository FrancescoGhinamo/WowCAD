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
import javax.swing.JPanel;
import javax.swing.JTextField;

import wowcad.backend.beam.cadManager.CadManager;
import wowcad.backend.beam.cadManager.CommandKeys;
import wowcad.backend.beam.cadManager.KeyWords;
import wowcad.frontend.gui.drawer.CoordReceiver;
import wowcad.frontend.gui.mainFrame.DialogCallback;

public class PointDialog extends JDialog implements ActionListener, CoordReceiver {


	private static final long serialVersionUID = -8477877148438470478L;

	private JTextField txtName;
	private JTextField txtX;
	private JTextField txtY;


	private JButton btnCancel;
	private JButton btnOK;


	private String command;

	private DialogCallback cbk;

	public PointDialog(JFrame owner, DialogCallback cbk) {
		super(owner, "Point", false);
		initComponents();
		pack();
		this.cbk = cbk;
	}

	private void initComponents() {

		this.setLayout(new GridBagLayout());


		JPanel cont = new JPanel(new GridLayout(3, 2, 5, 5));

		cont.add(new JLabel("Name"));
		cont.add(txtName = new JTextField(20));
		cont.add(new JLabel("X"));
		cont.add(txtX = new JTextField(20));
		cont.add(new JLabel("Y"));
		cont.add(txtY = new JTextField(20));


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
			if(!txtName.getText().equals("") && !txtX.getText().equals("") && !txtY.getText().equals("")) {

				String x = txtX.getText();
				String y = txtY.getText();
				command = CommandKeys.ADD + CadManager.COMMAND_SPLITTER + KeyWords.POINT + CadManager.PARAMETER_SPLITTER + txtName.getText() + CadManager.PARAMETER_SPLITTER + x + CadManager.PARAMETER_SPLITTER + y;
				dispose();
				cbk.parseCommand(command);

			}
		}
		else if(e.getSource().equals(btnCancel)) {
			dispose();
		}

	}



	public String getCommand() {
		return command;
	}

	@Override
	public void onReceive(double x, double y) {
		txtX.setText(String.valueOf(x));
		txtY.setText(String.valueOf(y));

	}



}
