package wowcad.frontend.gui.mainFrame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import wowcad.frontend.gui.drawer.DrawingRegion;

/**
 * Application main frame
 * @author franc
 *
 */
public class CADFrame extends JFrame implements ActionListener {

	
	private static final long serialVersionUID = -158826047285841843L;
	
	/**
	 * File menu
	 */
	private JMenuItem itemNew;
	private JMenuItem itemOpen;
	private JMenuItem itemSave;
	private JMenuItem itemSaveAs;
	private JMenuItem itemClose;
	private JMenuItem itemExport;
	private JMenuItem itemExit;
	
	/**
	 * Insert menu
	 */
	private JMenuItem itemPoint;
	private JMenuItem itemSegment;
	private JMenuItem itemPolyline;
	private JMenuItem itemPolygon;
	private JMenuItem itemEllipse;
	private JMenuItem itemCircle;
	
	/**
	 * Edit menu
	 */
	private JMenuItem itemTranslate;
	private JMenuItem itemRotate;
	private JMenuItem itemScale;
	
	/**
	 * Help menu
	 */
	private JMenuItem itemHelp;
	
	
	
	
	/**
	 * Commands of the ribbon
	 */
	private JButton btnPoint;
	private JButton btnSegment;
	private JButton btnPolyline;
	private JButton btnPolygon;
	private JButton btnEllipse;
	private JButton btnCircle;
	private JButton btnTranslate;
	private JButton btnRotate;
	private JButton btnScale;
	private JButton btnExport;
	
	
	/**
	 * Main region
	 */
	private JTabbedPane tabs;
	private ArrayList<DrawingRegion> drawingRegions;

	
	/**
	 * Command input
	 */
	private JTextField txtCommand;
	private JButton btnExe;
	private JButton btnHelp;
	
	public CADFrame() {
		super("WowCAD");
		setExtendedState(MAXIMIZED_BOTH);
		setMinimumSize(new Dimension((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 2 / 3, (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 2 / 3));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initComponents();
		drawingRegions = new ArrayList<DrawingRegion>();
	}

	private void initComponents() {
		setBackground(Color.BLACK);
		setForeground(Color.BLACK);
		this.getContentPane().setBackground(Color.BLACK);
		this.setJMenuBar(initMenuBar());
		
	}
	
	private JMenuBar initMenuBar() {
		JMenuBar bar = new JMenuBar();
		bar.add(initFileMenu());
		bar.add(initInsertMenu());
		bar.add(initEditMenu());
		bar.add(initHelpMenu());
		return bar;
	}
	
	private JMenu initFileMenu() {
		JMenu mnuFile = new JMenu("File");
		return mnuFile;
	}
	
	private JMenu initInsertMenu() {
		JMenu mnuInsert = new JMenu("Insert");
		return mnuInsert;
	}
	
	private JMenu initEditMenu() {
		JMenu mnuEdit = new JMenu("Edit");
		return mnuEdit;
	}
	
	private JMenu initHelpMenu() {
		JMenu mnuHelp = new JMenu("Help");
		return mnuHelp;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

	public static void main(String[] args) {
		new CADFrame().setVisible(true);

	}

}
