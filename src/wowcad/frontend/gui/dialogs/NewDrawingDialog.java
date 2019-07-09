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

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import wowcad.backend.beam.drawing.Drawing;
import wowcad.backend.beam.drawing.SaveType;

public class NewDrawingDialog extends JDialog implements ActionListener {

	
	private static final long serialVersionUID = -2188514411633480477L;
	
	private JTextField txtName;
	private JTextField txtDescription;
	private JTextField txtSaveLocation;
	private JComboBox<SaveType> cmbSaveType;
	
	private JButton btnCancel;
	private JButton btnOK;
	
	private String name;
	private String description;
	private String saveLocation;
	private SaveType saveType;
	
	private boolean ok;
	
	public NewDrawingDialog(JFrame owner, boolean modale) {
		super(owner, "New drawing", modale);
		initComponents();
		this.setBounds((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3, (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 3);
		ok = false;
	}

	private void initComponents() {
		
		this.setLayout(new GridBagLayout());
		JPanel sup = new JPanel(new GridBagLayout());
		
		JPanel cont = new JPanel(new GridLayout(4, 2, 5, 5));
		cont.add(new JLabel("Name"));
		cont.add(txtName = new JTextField(20));
		cont.add(new JLabel("Description"));
		cont.add(txtDescription = new JTextField(20));
		cont.add(new JLabel("Save location"));
		txtSaveLocation = new JTextField(20);
		txtSaveLocation.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2) {
					JFileChooser fc = new JFileChooser();
					fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					if(fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
						txtSaveLocation.setText(fc.getSelectedFile().getAbsolutePath());
					}
				}
			}
		});
		cont.add(txtSaveLocation);
		cont.add(new JLabel("Save type"));
		cmbSaveType = new JComboBox<SaveType>(SaveType.values());
		cont.add(cmbSaveType);
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
			if(!txtName.getText().equals("") && !txtSaveLocation.getText().equals("")) {
				ok = true;
				name = txtName.getText();
				description = txtDescription.getText();
				saveLocation = txtSaveLocation.getText() + "\\" + name + "." + Drawing.EXTENSION;
				saveType = (SaveType) cmbSaveType.getSelectedItem();
				dispose();
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

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public String getSaveLocation() {
		return saveLocation;
	}

	public SaveType getSaveType() {
		return saveType;
	}

	public boolean isOk() {
		return ok;
	}

	
	
}
