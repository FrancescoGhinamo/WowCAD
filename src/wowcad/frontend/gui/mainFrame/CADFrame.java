package wowcad.frontend.gui.mainFrame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

import wowcad.frontend.gui.drawer.DrawingRegion;

/**
 * Application main frame
 * @author franc
 *
 */
public class CADFrame extends JFrame implements ActionListener, MouseListener {


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
	private DrawingRegion drawingRegion;


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
	}

	private void initComponents() {
		setBackground(Color.BLACK);
		setForeground(Color.BLACK);
		this.getContentPane().setBackground(Color.BLACK);
		this.setJMenuBar(initMenuBar());
		this.setLayout(new BorderLayout());
		this.add(initRibbon(), BorderLayout.NORTH);

		drawingRegion = new DrawingRegion();
		drawingRegion.addMouseListener(this);

		this.add(drawingRegion, BorderLayout.CENTER);

		this.add(initCmdBar(), BorderLayout.SOUTH);

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
		itemNew = new JMenuItem("New");
		itemNew.addActionListener(this);
		itemOpen = new JMenuItem("Open");
		itemOpen.addActionListener(this);
		itemSave = new JMenuItem("Save");
		itemSave.addActionListener(this);
		itemSaveAs = new JMenuItem("Save as");
		itemSaveAs.addActionListener(this);
		itemClose = new JMenuItem("Close");
		itemClose.addActionListener(this);
		itemExport = new JMenuItem("Export");
		itemExport.addActionListener(this);
		itemExit = new JMenuItem("Exit");
		itemExit.addActionListener(this);

		mnuFile.add(itemNew);
		mnuFile.add(itemOpen);
		mnuFile.add(itemSave);
		mnuFile.add(itemSaveAs);
		mnuFile.add(itemClose);
		mnuFile.add(itemExport);
		mnuFile.add(itemExit);

		return mnuFile;
	}

	private JMenu initInsertMenu() {
		JMenu mnuInsert = new JMenu("Insert");

		itemPoint = new JMenuItem("Point");
		itemPoint.addActionListener(this);
		itemSegment = new JMenuItem("Segment");
		itemSegment.addActionListener(this);
		itemPolyline = new JMenuItem("Polyline");
		itemPolyline.addActionListener(this);
		itemPolygon = new JMenuItem("Polygon");
		itemPolygon.addActionListener(this);
		itemEllipse = new JMenuItem("Ellipse");
		itemEllipse.addActionListener(this);
		itemCircle = new JMenuItem("Circle");
		itemCircle.addActionListener(this);

		mnuInsert.add(itemPoint);
		mnuInsert.add(itemSegment);
		mnuInsert.add(itemPolyline);
		mnuInsert.add(itemPolygon);
		mnuInsert.add(itemEllipse);
		mnuInsert.add(itemCircle);

		return mnuInsert;
	}

	private JMenu initEditMenu() {
		JMenu mnuEdit = new JMenu("Edit");

		itemTranslate = new JMenuItem("Translate");
		itemTranslate.addActionListener(this);
		itemRotate = new JMenuItem("Rotate");
		itemRotate.addActionListener(this);
		itemScale = new JMenuItem("Scale");
		itemScale.addActionListener(this);

		mnuEdit.add(itemTranslate);
		mnuEdit.add(itemRotate);
		mnuEdit.add(itemScale);

		return mnuEdit;
	}

	private JMenu initHelpMenu() {
		JMenu mnuHelp = new JMenu("Help");

		itemHelp = new JMenuItem("Help");
		itemHelp.addActionListener(this);

		mnuHelp.add(itemHelp);

		return mnuHelp;
	}



	private JPanel initRibbon() {
		JPanel ribbon = new JPanel(new FlowLayout(FlowLayout.LEFT));
		ribbon.setBackground(Color.BLACK);
		btnPoint = new JButton("Poi");
		btnPoint.setBackground(Color.BLACK);
		btnPoint.setForeground(Color.WHITE);
		btnPoint.addActionListener(this);

		btnSegment = new JButton("Segm");
		btnSegment.setBackground(Color.BLACK);
		btnSegment.setForeground(Color.WHITE);
		btnSegment.addActionListener(this);

		btnPolyline = new JButton("PolLn");
		btnPolyline.setBackground(Color.BLACK);
		btnPolyline.setForeground(Color.WHITE);
		btnPolyline.addActionListener(this);

		btnPolygon = new JButton("PolGon");
		btnPolygon.setBackground(Color.BLACK);
		btnPolygon.setForeground(Color.WHITE);
		btnPolygon.addActionListener(this);

		btnEllipse = new JButton("Ell");
		btnEllipse.setBackground(Color.BLACK);
		btnEllipse.setForeground(Color.WHITE);
		btnEllipse.addActionListener(this);

		btnCircle = new JButton("Cir");
		btnCircle.setBackground(Color.BLACK);
		btnCircle.setForeground(Color.WHITE);
		btnCircle.addActionListener(this);

		btnTranslate = new JButton("Tans");
		btnTranslate.setBackground(Color.BLACK);
		btnTranslate.setForeground(Color.WHITE);
		btnTranslate.addActionListener(this);

		btnRotate = new JButton("Rot");
		btnRotate.setBackground(Color.BLACK);
		btnRotate.setForeground(Color.WHITE);
		btnRotate.addActionListener(this);

		btnScale = new JButton("Scal");
		btnScale.setBackground(Color.BLACK);
		btnScale.setForeground(Color.WHITE);
		btnScale.addActionListener(this);

		btnExport = new JButton("Exp");
		btnExport.setBackground(Color.BLACK);
		btnExport.setForeground(Color.WHITE);
		btnExport.addActionListener(this);


		ribbon.add(btnPoint);
		ribbon.add(btnSegment);
		ribbon.add(btnPolyline);
		ribbon.add(btnPolygon);
		ribbon.add(btnEllipse);
		ribbon.add(btnCircle);
		ribbon.add(btnTranslate);
		ribbon.add(btnRotate);
		ribbon.add(btnScale);
		ribbon.add(btnExport);
		return ribbon;
	}


	private JPanel initCmdBar() {
		JPanel cmdBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
		cmdBar.setBackground(Color.BLACK);

		txtCommand = new JTextField(70);
		txtCommand.setBackground(Color.BLACK);
		txtCommand.setForeground(Color.WHITE);

		btnExe = new JButton("Exe");
		btnExe.setBackground(Color.BLACK);
		btnExe.setForeground(Color.WHITE);
		btnExe.addActionListener(this);

		btnHelp = new JButton("?");
		btnHelp.setBackground(Color.BLACK);
		btnHelp.setForeground(Color.WHITE);
		btnHelp.addActionListener(this);

		cmdBar.add(txtCommand);
		cmdBar.add(btnExe);
		cmdBar.add(btnHelp);

		return cmdBar;
	}

	@Override
	public void actionPerformed(ActionEvent aE) {
		if(aE.getSource().equals(itemNew)) {

		}
		else if(aE.getSource().equals(itemOpen)) {

		}
		else if(aE.getSource().equals(itemSave)) {

		}
		else if(aE.getSource().equals(itemSaveAs)) {

		}
		else if(aE.getSource().equals(itemClose)) {

		}
		else if(aE.getSource().equals(itemExport) || aE.getSource().equals(btnExport)) {

		}
		else if(aE.getSource().equals(itemExit)) {

		}
		else if(aE.getSource().equals(itemPoint) || aE.getSource().equals(btnPoint)) {

		}
		else if(aE.getSource().equals(itemSegment) || aE.getSource().equals(btnSegment)) {

		}
		else if(aE.getSource().equals(itemPolyline) || aE.getSource().equals(btnPolyline)) {

		}
		else if(aE.getSource().equals(itemPolygon) || aE.getSource().equals(btnPolygon)) {

		}
		else if(aE.getSource().equals(itemEllipse) || aE.getSource().equals(btnEllipse)) {

		}
		else if(aE.getSource().equals(itemCircle) || aE.getSource().equals(btnCircle)) {

		}
		else if(aE.getSource().equals(itemTranslate) || aE.getSource().equals(btnTranslate)) {

		}
		else if(aE.getSource().equals(itemRotate) || aE.getSource().equals(btnRotate)) {

		}
		else if(aE.getSource().equals(itemScale) || aE.getSource().equals(btnScale)) {

		}
		else if(aE.getSource().equals(btnExe)) {

		}
		else if(aE.getSource().equals(btnHelp)) {
			//Command help
		}


	}



	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}



	public static void main(String[] args) {
		new CADFrame().setVisible(true);

	}

}
