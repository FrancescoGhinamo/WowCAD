package wowcad.frontend.gui.drawer;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

/**
 * Pane containing buttons to move the view of the canvas, to show or hide the grid and axis
 * @author franc
 *
 */
public class NavigationPane extends JPanel implements ActionListener {

	
	private static final long serialVersionUID = -7838436805384891720L;
	
	/**
	 * Drawing region
	 */
	private DrawingRegion owner;
	
	private JButton btnMvUp;
	private JButton btnMvDwn;
	private JButton btnMvLeft;
	private JButton btnMvRight;
	
	private JButton btnZoomIn;
	private JButton btnZoomOut;
	
	private JButton btnResetView;
	
	private JCheckBox chkAxis;
	private JCheckBox chkGrid;
	
	
	

	/**
	 * Constructor
	 * @param owner: owner drawing region
	 */
	public NavigationPane(DrawingRegion owner) {
		super();
		this.owner = owner;
		setBackground(Color.BLACK);
		initComponents();
		
	}




	private void initComponents() {
		setLayout(new FlowLayout());
		this.add(initChkPan());
		this.add(initBtnPan());
		setSize(200, 200);
		
	}
	
	private JPanel initChkPan() {
		JPanel pan = new JPanel(new FlowLayout());
		pan.setBackground(Color.BLACK);
		chkAxis = new JCheckBox("Axis");
		chkAxis.setBackground(Color.BLACK);
		chkAxis.setForeground(Color.WHITE);
		chkAxis.setSelected(true);
		chkAxis.addActionListener(this);
		
		chkGrid = new JCheckBox("Grid");
		chkGrid.setBackground(Color.BLACK);
		chkGrid.setForeground(Color.WHITE);
		chkGrid.setSelected(true);
		chkGrid.addActionListener(this);
		
		pan.add(chkAxis);
		pan.add(chkGrid);
		
		return pan;
	}
	
	private JPanel initBtnPan() {
		JPanel pan = new JPanel(new FlowLayout());
		pan.setBackground(Color.BLACK);
		btnZoomIn = new JButton("+");
		btnZoomIn.setBackground(Color.BLACK);
		btnZoomIn.setForeground(Color.WHITE);
		btnZoomIn.addActionListener(this);
		
		btnMvUp = new JButton("^");
		btnMvUp.setBackground(Color.BLACK);
		btnMvUp.setForeground(Color.WHITE);
		btnMvUp.addActionListener(this);
		
		btnZoomOut = new JButton("-");
		btnZoomOut.setBackground(Color.BLACK);
		btnZoomOut.setForeground(Color.WHITE);
		btnZoomOut.addActionListener(this);
		
		btnMvLeft = new JButton("<");
		btnMvLeft.setBackground(Color.BLACK);
		btnMvLeft.setForeground(Color.WHITE);
		btnMvLeft.addActionListener(this);
		
		btnMvDwn = new JButton("\\/");
		btnMvDwn.setBackground(Color.BLACK);
		btnMvDwn.setForeground(Color.WHITE);
		btnMvDwn.addActionListener(this);
		
		btnMvRight = new JButton(">");
		btnMvRight.setBackground(Color.BLACK);
		btnMvRight.setForeground(Color.WHITE);
		btnMvRight.addActionListener(this);
		
		btnResetView = new JButton("Reset");
		btnResetView.setBackground(Color.BLACK);
		btnResetView.setForeground(Color.WHITE);
		btnResetView.addActionListener(this);
		
		pan.add(btnZoomIn);
		pan.add(btnZoomOut);
		
		pan.add(btnMvLeft);
		pan.add(btnMvUp);		
		pan.add(btnMvDwn);
		pan.add(btnMvRight);
		
		pan.add(btnResetView);
		
		
		return pan;
	}




	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(chkAxis)) {
			if(chkAxis.isSelected()) {
				owner.sendAction(ActionCommand.SHOW_AXIS);
			}
			else {
				owner.sendAction(ActionCommand.HIDE_AXIS);
			}
		}
		else if(e.getSource().equals(chkGrid)) {
			if(chkGrid.isSelected()) {
				owner.sendAction(ActionCommand.SHOW_GRID);
			}
			else {
				owner.sendAction(ActionCommand.HIDE_GRID);
			}
		}
		else if(e.getSource().equals(btnZoomIn)) {
			owner.sendAction(ActionCommand.ZOOM_IN);
		}
		else if(e.getSource().equals(btnZoomOut)) {
			owner.sendAction(ActionCommand.ZOOM_OUT);
		}
		else if(e.getSource().equals(btnMvUp)) {
			owner.sendAction(ActionCommand.MOVE_UP);
		}
		else if(e.getSource().equals(btnMvDwn)) {
			owner.sendAction(ActionCommand.MOVE_DOWN);
		}
		else if(e.getSource().equals(btnMvLeft)) {
			owner.sendAction(ActionCommand.MOVE_LEFT);
		}
		else if(e.getSource().equals(btnMvRight)) {
			owner.sendAction(ActionCommand.MOVE_RIGHT);
		}
		else if(e.getSource().equals(btnResetView)) {
			owner.sendAction(ActionCommand.RESET_VIEW);
		}
		
	}

}
