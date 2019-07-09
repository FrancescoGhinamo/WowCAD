package wowcad.frontend.gui.dialogs;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import wowcad.backend.beam.cadManager.CadManager;
import wowcad.backend.beam.cadManager.CommandKeys;
import wowcad.backend.beam.cadManager.KeyWords;

public class ExportDialog extends JDialog implements ActionListener {


	private static final long serialVersionUID = 8963088323725001642L;

	private JTextField txtLoc;
	private JCheckBox chkAxis;
	private JCheckBox chkGrid;
	private JTextField txtFact;

	private JButton btnCancel;
	private JButton btnOK;

	private boolean ok;

	private String command;

	public ExportDialog(JFrame owner, boolean modale) {
		super(owner, "Export", modale);
		initComponents();
		this.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3);
		ok = false;
	}

	private void initComponents() {

		this.setLayout(new GridBagLayout());
		JPanel sup = new JPanel(new GridBagLayout());

		JPanel cont = new JPanel(new GridLayout(4, 2, 5, 5));
		
		cont.add(new JLabel("Export location"));
		txtLoc = new JTextField(20);
		txtLoc.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JFileChooser fc = new JFileChooser();
					fc.setFileFilter(new FileNameExtensionFilter("JPEG picture", "jpeg"));
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if(fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
						File _f = fc.getSelectedFile();
						if(!_f.getAbsolutePath().endsWith(".jpeg")) {
							_f = new File(_f.getAbsolutePath() + ".jpeg");
						}
						txtLoc.setText(_f.getAbsolutePath());
					}
				}
			}
		});
		cont.add(txtLoc);
		cont.add(new JLabel("Additional"));
		cont.add(chkAxis = new JCheckBox("Axis"));
		cont.add(new JLabel(""));
		cont.add(chkGrid = new JCheckBox("Grid"));
		cont.add(new JLabel("1 mm is x pixel"));
		cont.add(txtFact = new JTextField(20)); 
		sup.add(cont, new GridBagConstraints());


		JPanel but = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(this);

		btnOK = new JButton("OK");
		btnOK.addActionListener(this);
		but.add(btnCancel);
		but.add(btnOK);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = gbc.gridy = 0;
		gbc.weightx = 10;
		gbc.weighty = 8;
		gbc.fill = GridBagConstraints.BOTH;

		this.add(sup, gbc);

		gbc.gridy = 1;
		gbc.weighty = 1;

		this.add(but, gbc);



	}


	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(btnOK)) {
			if(!txtLoc.getText().equals("") && !txtFact.getText().equals("")) {
				try {
					int fact = Integer.valueOf(txtFact.getText());
					String axis = "";
					if(chkAxis.isSelected()) {
						axis = KeyWords.TRUE;
					}
					else {
						axis = KeyWords.FALSE;
					}
					
					String grid = "";
					if(chkGrid.isSelected()) {
						grid = KeyWords.TRUE;
					}
					else {
						grid = KeyWords.FALSE;
					}
					ok = true;
					command = CommandKeys.EXPORT + CadManager.COMMAND_SPLITTER + txtLoc.getText() + CadManager.PARAMETER_SPLITTER + axis + CadManager.PARAMETER_SPLITTER + grid + CadManager.PARAMETER_SPLITTER + fact;
					dispose();
				}
				catch(NumberFormatException ex) {
					JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
			else {
				JOptionPane.showMessageDialog(this, "Fill in the requested fields", "Missing data", JOptionPane.WARNING_MESSAGE);
			}
		}
		else if(e.getSource().equals(btnCancel)) {
			ok = false;
			dispose();
		}

	}

	public boolean isOk() {
		return ok;
	}

	public String getCommand() {
		return command;
	}




}
