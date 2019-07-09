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
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import wowcad.backend.beam.cadManager.CadManager;
import wowcad.backend.beam.cadManager.CommandKeys;
import wowcad.backend.beam.cadManager.KeyWords;
import wowcad.frontend.gui.drawer.CoordReceiver;
import wowcad.frontend.gui.mainFrame.DialogCallback;

public class PolylineDialog extends JDialog implements ActionListener, CoordReceiver {


	private static final long serialVersionUID = -2079913455171960214L;


	private JTextField txtName;
	private DefaultTableModel tbl;
	private JTextField txtCoord;
	private JButton btnAdd;


	private JButton btnCancel;
	private JButton btnOK;


	private String command;

	private DialogCallback cbk;

	private String points;

	public PolylineDialog(JFrame owner, DialogCallback cbk) {
		super(owner, "Polyline", false);
		initComponents();
		pack();
		this.cbk = cbk;
		this.points = "";
	}

	private void initComponents() {

		this.setLayout(new GridBagLayout());


		JPanel cont = initInPan();	


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

	private JPanel initInPan() {
		JPanel pan = new JPanel(new GridBagLayout());

		JPanel txts = new JPanel(new GridLayout(2, 2, 5, 5));
		txts.add(new JLabel("Name"));
		txts.add(txtName = new JTextField(20));
		btnAdd = new JButton("Add point");
		btnAdd.addActionListener(this);
		txts.add(btnAdd);
		txts.add(txtCoord = new JTextField(20));


		tbl = new DefaultTableModel(new String[] {"x", "y"}, 0);
		JTable t = new JTable(tbl);
		JScrollPane scrl = new JScrollPane(t);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = 10;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		pan.add(txts, gbc);

		gbc.gridy = 1;
		gbc.weighty = 10;
		pan.add(scrl, gbc);


		return pan;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnOK)) {
			if(!txtName.getText().equals("") && !points.equals("")) {


				command = CommandKeys.ADD + CadManager.COMMAND_SPLITTER + KeyWords.POLYLINE + CadManager.PARAMETER_SPLITTER + txtName.getText() + points;
				dispose();
				cbk.parseCommand(command);

			}
		}
		else if(e.getSource().equals(btnCancel)) {
			cbk.parseCommand("");
			dispose();
		}
		else if(e.getSource().equals(btnAdd)) {
			try {
				tbl.addRow(txtCoord.getText().split(","));
				points += "," + txtCoord.getText();			
				txtCoord.setText("");
			}
			catch(Exception ex) {

			}

		}

	}

	@Override
	public void onReceive(double x, double y) {
		points += "," + String.valueOf(x) + "," + String.valueOf(y);
		tbl.addRow(new String[] {String.valueOf(x), String.valueOf(y)});

	}



}
