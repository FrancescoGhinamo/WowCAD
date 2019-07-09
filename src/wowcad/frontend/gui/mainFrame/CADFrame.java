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
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import wowcad.backend.beam.cadManager.CadManager;
import wowcad.backend.beam.cadManager.CommandKeys;
import wowcad.backend.beam.cadManager.exceptions.UnsavedException;
import wowcad.backend.beam.drawing.Drawing;
import wowcad.frontend.gui.dialogs.ExportDialog;
import wowcad.frontend.gui.dialogs.NewDrawingDialog;
import wowcad.frontend.gui.dialogs.PointDialog;
import wowcad.frontend.gui.drawer.DrawingRegion;

/**
 * Application main frame
 * @author franc
 *
 */
public class CADFrame extends JFrame implements ActionListener, MouseListener, DialogCallback {


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
	private JMenuItem itemRemove;

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
		
		/*dato che tramite i click si vanno a definire i punti che serviranno al dialog per generare l'oggetto grafico
		 *conviene fare che il listener sia il dialog corrispondente (in modo da ottenere i punti gia' nella classe del dialog)
		 *se scelgo questa soluzione devo ricordarmi di aggiungere il dialog come mouse listener della drawing region
		 *e poi toglierlo dai listener dopo che il dispose del dialog
		 *il dialog dovra restituire una stringa che è il comando che verrà eseguito
		 */
		 
//		drawingRegion.addMouseListener(this);

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
		itemRemove = new JMenuItem("Remove");
		itemRemove.addActionListener(this);

		mnuEdit.add(itemTranslate);
		mnuEdit.add(itemRotate);
		mnuEdit.add(itemScale);
		mnuEdit.addSeparator();
		mnuEdit.add(itemRemove);

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
	
	
	private JFileChooser initJFileChooser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileFilter(new FileNameExtensionFilter("WowCAD drawing", Drawing.EXTENSION));
		return fc;
	}
	
	private void showException(Exception e) {
		JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	
	/**
	 * File menu functions
	 */
	public void performNew() {
		NewDrawingDialog nD = new NewDrawingDialog(this, true);
		nD.setVisible(true); 
		if(nD.isOk()) {
			try {
				drawingRegion.createDrawing(nD.getName(), nD.getDescription(), nD.getSaveLocation(), nD.getSaveType(), false);
				
			} catch (UnsavedException e) {
				int res = JOptionPane.showConfirmDialog(this, e.getMessage() + "\nCreate drawing anyway?", "WowCAD", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(res == JOptionPane.YES_OPTION) {
					try {
						drawingRegion.createDrawing(nD.getName(), nD.getDescription(), nD.getSaveLocation(), nD.getSaveType(), true);
					} catch (UnsavedException ex) {
						
					}
				}
			}
		}
	}

	public void performOpen() {
		JFileChooser fc = initJFileChooser();
		if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			try {
				drawingRegion.openDrawing(fc.getSelectedFile().getAbsolutePath(), false);
				
			} catch (UnsavedException e) {
				int res = JOptionPane.showConfirmDialog(this, e.getMessage() + "\nOpen drawing anyway?", "WowCAD", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
				if(res == JOptionPane.YES_OPTION) {
					try {
						drawingRegion.openDrawing(fc.getSelectedFile().getAbsolutePath(), true);
					} catch (Exception ex) {
						showException(e);
					}
				}
			} catch (Exception e) {
				showException(e);
			}
		}
	}
	
	public void performSave() {
		String cmd = CommandKeys.SAVE;
		try {
			drawingRegion.applyCommand(cmd);
		} catch (Exception e) {
			e.printStackTrace();
			showException(e);
		}
	}

	public void performSaveAs() {
		JFileChooser fc = initJFileChooser();
		if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			File _f = fc.getSelectedFile();
			if(!_f.getAbsolutePath().endsWith("." + Drawing.EXTENSION)) {
				_f = new File(_f.getAbsolutePath() + "." + Drawing.EXTENSION);
			}
			String cmd = CommandKeys.SAVE_LOC + CadManager.COMMAND_SPLITTER + _f.getAbsolutePath();
			try {
				drawingRegion.applyCommand(cmd);
				drawingRegion.applyCommand(CommandKeys.SAVE);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void performClose() {
		try {
			drawingRegion.closeDrawing(false);
			
		} catch (UnsavedException e) {
			int res = JOptionPane.showConfirmDialog(this, e.getMessage() + "\nClose drawing anyway?", "WowCAD", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if(res == JOptionPane.YES_OPTION) {
				try {
					drawingRegion.closeDrawing(true);
				} catch (UnsavedException ex) {
					
				}
			}
		}
	}
	
	public void performExport() {
		ExportDialog exp = new ExportDialog(this, true);
		exp.setVisible(true);
		if(exp.isOk()) {
			try {
				drawingRegion.applyCommand(exp.getCommand());
			} catch (Exception e) {
				showException(e);
			}
		}
	}
	
	public void performExit() {
		System.exit(0);
	}
	
	
	/**
	 * Insert menu functions
	 */
	@Override
	public void parseCommand(String cmd) {
		try {
			drawingRegion.applyCommand(cmd);
			drawingRegion.getDrawingCanvas().setcRec(null);
		} catch (Exception e) {
			showException(e);
		}
		
	}
	
	public void performInsertPoint() {
		PointDialog pD = new PointDialog(this, this);
		pD.setVisible(true);
		drawingRegion.getDrawingCanvas().setcRec(pD);
	}
	
	@Override
	public void actionPerformed(ActionEvent aE) {
		if(aE.getSource().equals(itemNew)) {
			performNew();
		}
		else if(aE.getSource().equals(itemOpen)) {
			performOpen();
		}
		else if(aE.getSource().equals(itemSave)) {
			performSave();
		}
		else if(aE.getSource().equals(itemSaveAs)) {
			performSaveAs();
		}
		else if(aE.getSource().equals(itemClose)) {
			performClose();
		}
		else if(aE.getSource().equals(itemExport) || aE.getSource().equals(btnExport)) {
			performExport();
		}
		else if(aE.getSource().equals(itemExit)) {
			performExit();
		}
		else if(aE.getSource().equals(itemPoint) || aE.getSource().equals(btnPoint)) {
			performInsertPoint();
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
		else if(aE.getSource().equals(itemRemove)) {
			
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
