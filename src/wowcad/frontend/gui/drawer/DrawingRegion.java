package wowcad.frontend.gui.drawer;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JPanel;

import wowcad.backend.beam.cadManager.CadManager;
import wowcad.backend.beam.cadManager.exceptions.UnsavedException;
import wowcad.backend.beam.drawing.SaveType;

/**
 * Drawing region of the CAD software:
 * contains the drawing canvas and the buttons
 * @author franc
 *
 */
public class DrawingRegion extends JPanel {

	
	private static final long serialVersionUID = 320574687820271280L;
	
	
	/**
	 * Associated CadManager
	 */
	private CadManager cadManager;
	
	/**
	 * Associated navigation pane 
	 */
	private NavigationPane navigationPane;
	
	/**
	 * Associated drawing canvas
	 */
	private DrawingCanvas drawingCanvas;
	
	

	/**
	 * Constructor
	 */
	public DrawingRegion() {
		super();
		setBackground(Color.BLACK);
		initComponents();
		this.cadManager = new CadManager(drawingCanvas);
		
	}

	/**
	 * Components initialization
	 */
	private void initComponents() {
		navigationPane = new NavigationPane(this);
		drawingCanvas = new DrawingCanvas();
		
		setLayout(new BorderLayout());
		this.add(drawingCanvas, BorderLayout.CENTER);
		this.add(navigationPane, BorderLayout.SOUTH);
		
		
	}
	
	/**
	 * Opens a saved drawing
	 * @param source: source from which open the drawing
	 * @param force: true to force the creation of the drawing (like to force existing drawing override)
	 * @throws UnsavedException 
	 */
	public void openDrawing(String source, boolean force) throws UnsavedException, Exception {
		cadManager.openDrawing(source, force);
		drawingCanvas.update(cadManager.getDrawing(), null);
	}
	
	/**
	 * Instantiates a new drawing
	 * @param name: name give to the drawing
	 * @param description: description
	 * @param saveLocation: save location of the drawing (can be file path or host address...)
	 * @param saveType: way of saving the drawing
	 * @param force: true to force the creation of the drawing (like to force existing drawing override)
	 * @throws UnsavedException 
	 */
	public void createDrawing(String name, String description, String saveLocation, SaveType saveType, boolean force) throws UnsavedException {
		try {
			cadManager.newDrawing(name, description, saveLocation, saveType, force);
			drawingCanvas.update(cadManager.getDrawing(), null);
		} catch (UnsavedException e) {
			throw e;
		}
		
	}
	
	/**
	 * Closes the current drawing
	 * @param force: true to force the drawing closure (even if it is no saved)
	 * @throws UnsavedException 
	 */
	public void closeDrawing(boolean force) throws UnsavedException {
		try {
			cadManager.closeDrawing(force);
		} catch (UnsavedException e) {
			throw e;
		}
	}


	

	/**
	 * Applies the given command to the drawing via CadManager
	 * @param command: command generated by the GUI
	 * @throws Exception 
	 */
	public void applyCommand(String command) throws Exception {
		try {
			cadManager.parseCommand(command);
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	
	public void sendAction(ActionCommand cmd) {
		drawingCanvas.parseAction(cmd);
	}


	public CadManager getCadManager() {
		return cadManager;
	}

	public DrawingCanvas getDrawingCanvas() {
		return drawingCanvas;
	}





}
