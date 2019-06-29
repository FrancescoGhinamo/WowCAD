package wowcad.backend.beam.cadManager;

import wowcad.backend.beam.cadManager.exceptions.DrawingNotPresentException;
import wowcad.backend.beam.cadManager.exceptions.MalformedCommandException;
import wowcad.backend.beam.cadManager.exceptions.ParameterException;
import wowcad.backend.beam.cadManager.exceptions.UnsavedException;
import wowcad.backend.beam.drawing.Drawing;
import wowcad.backend.beam.drawing.SaveType;

/**
 * Command interpreter for the cad
 * @author franc
 *
 */
public class CadManager {

	private static final String COMMAND_SPLITTER = ":";

	private static final String PARAMETER_SPLITTER = ",";

	/**
	 * Drawing the manager operates on
	 */
	private Drawing drawing;

	/**
	 * Constructor
	 */
	public CadManager() {
		super();
		this.drawing = null;
	}

	/**
	 * Instantiates a new drawing
	 * @param name: name of the drawing
	 * @param force: true to force the creation of the drawing (like to force existing drawing override)
	 * @throws UnsavedException 
	 */
	public void newDrawing(String name, boolean force) throws UnsavedException {
		if(force || drawing == null) {
			drawing = new Drawing(name);
		}
		else {
			if(!drawing.isModified()) {
				drawing = new Drawing(name);
			}
			else {
				throw new UnsavedException();
			}
		}
	}

	/**
	 * Instantiates a new drawing
	 * @param name: name of the drawing
	 * @param description: description assigned to the drawing
	 * @param force: true to force the creation of the drawing (like to force existing drawing override)
	 * @throws UnsavedException 
	 */
	public void newDrawing(String name, String description, boolean force) throws UnsavedException {
		if(force || drawing == null) {
			drawing = new Drawing(name, description);
		}
		else {
			if(!drawing.isModified()) {
				drawing = new Drawing(name, description);
			}
			else {
				throw new UnsavedException();
			}
		}
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
	public void newDrawing(String name, String description, String saveLocation, SaveType saveType, boolean force) throws UnsavedException {
		if(force || drawing == null) {
			drawing = new Drawing(name, description, saveLocation, saveType);
		}
		else {
			if(!drawing.isModified()) {
				drawing = new Drawing(name, description, saveLocation, saveType);
			}
			else {
				throw new UnsavedException();
			}
		}
	}

	/**
	 * Closes the current drawing
	 * @param force: true to force the drawing closure (even if it is no saved)
	 * @throws UnsavedException 
	 */
	public void closeDrawing(boolean force) throws UnsavedException {
		if(force || drawing == null) {
			drawing = null;
		}
		else {
			if(!drawing.isModified()) {
				drawing = null;
			}
			else {
				throw new UnsavedException();
			}
		}
	}

	/**
	 * Receives a string containing the command to perform, analyzes it and executes the command
	 * The command must be formatted in the following way:
	 * <command type>:<parameter1>,<parameter2>,<parameter3>,...,<parameterN>
	 * The order and the type of parameters are the same of the relative methods in this class
	 * @param command: command line
	 * @exception MalformedCommandException
	 * @throws ParameterException 
	 * @throws DrawingNotPresentException 
	 */
	public void parseCommand(String command) throws MalformedCommandException, ParameterException, DrawingNotPresentException {
		if(drawing != null) {
			String cmd = command.toUpperCase();
			cmd = cmd.strip();
			try {
				String[] parts = cmd.split(COMMAND_SPLITTER);
				switch(parts[0]) {
				case CommandKeys.ADD:
					/*
					 * parameters:
					 * <shape>,<param1>,<param2>,<param3>...
					 */
					parseAdd(parts[1]);
					break;

				case CommandKeys.DESCRIPTION:
					/*
					 * parameters:
					 * <newDescription>
					 */
					parseDescription(parts[1]);
					break;

				case CommandKeys.NAME:
					/*
					 * parameters:
					 * <newNamen>
					 */
					parseName(parts[1]);
					break;

				case CommandKeys.REMOVE:
					/*
					 * parameters:
					 * <primitiveName>
					 */
					parseRemove(parts[1]);
					break;

				case CommandKeys.ROTATE:
					/*
					 * parameters:
					 * <primitiveName>,<rotCenterX>,<rotCenterY>,<rotDegrees>
					 */
					parseRotate(parts[1]);
					break;

				case CommandKeys.SAVE:
					break;

				case CommandKeys.SAVE_LOC:
					break;

				case CommandKeys.SAVE_TYPE:
					break;

				case CommandKeys.SCALE:
					break;

				case CommandKeys.TRANSLATE:
					break;

				case CommandKeys.EXPORT:
					break;

				default:
					throw new MalformedCommandException();
				}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				throw new MalformedCommandException();
			} catch (ParameterException e) {
				throw e;
			}
		}
		else {
			throw new DrawingNotPresentException();
		}


	}

	/**
	 * Parses operations to add a primitive to the drawing
	 * @param parameters: line of parameters
	 * @throws MalformedCommandException 
	 * @throws ParameterException 
	 */
	private void parseAdd(String parameters) throws MalformedCommandException, ParameterException {
		try {
			String[] parts = parameters.split(PARAMETER_SPLITTER);

			switch(parts[0]) {
			case KeyWords.CIRCLE:
				/*
				 * parameters:
				 * <name>,<centerX>,<centerY>,<radius>
				 */

				String cName = parts[1];
				String centerX = parts[2];
				String centerY = parts[3];
				String radius = parts[4];
				try {
					double xC = Double.parseDouble(centerX);
					double yC = Double.parseDouble(centerY);
					double r = Double.parseDouble(radius);
					drawing.addCircle(cName, xC, yC, r);
				}
				catch(Exception e) {
					throw new ParameterException();
				}

				break;

			case KeyWords.ELLIPSE:
				/*
				 * parameters:
				 * <name>,<centerX>,<centerY>,<radiusX>,<radiusY>
				 */

				String eName = parts[1];
				String ecenterX = parts[2];
				String ecenterY = parts[3];
				String eradiusX = parts[4];
				String eradiusY = parts[5];
				try {
					double xC = Double.parseDouble(ecenterX);
					double yC = Double.parseDouble(ecenterY);
					double rX = Double.parseDouble(eradiusX);
					double rY = Double.parseDouble(eradiusY);
					drawing.addEllipse(eName, xC, yC, rX, rY);
				}
				catch(Exception e) {
					throw new ParameterException();
				}

				break;

			case KeyWords.POINT:
				/*
				 * parameters:
				 * <name>,<x>,<y>
				 */

				String pName = parts[1];
				String pX = parts[2];
				String pY = parts[3];

				try {
					double x = Double.parseDouble(pX);
					double y = Double.parseDouble(pY);
					drawing.addPoint(pName, x, y);
				}
				catch(Exception e) {
					throw new ParameterException();
				}


				break;

			case KeyWords.POLYGON:
				/*
				 * parameters:
				 * <name>,<x1>,<y1>,<x2>,<y2>,...,<xn>,<yn>
				 */
				String pgName = parts[1];
				if((parts.length - 2) % 2 == 0) {
					try {
						double[] xCoor = new double[(parts.length - 2) / 2];
						double[] yCoor = new double[(parts.length - 2) / 2];
						int index = 0;
						for(int i = 2; i < parts.length; i += 2) {
							xCoor[index] = Double.parseDouble(parts[i]);
							yCoor[index] = Double.parseDouble(parts[i + 1]);
							index++;
						}
						drawing.addPolygon(pgName, xCoor, yCoor);
					}
					catch(Exception e) {
						throw new ParameterException();
					}
				}
				else {
					throw new MalformedCommandException();
				}

				break;

			case KeyWords.POLYLINE:
				/*
				 * parameters:
				 * <name>,<x1>,<y1>,<x2>,<y2>,...,<xn>,<yn>
				 */
				String plName = parts[1];
				if((parts.length - 2) % 2 == 0) {
					try {
						double[] xCoor = new double[(parts.length - 2) / 2];
						double[] yCoor = new double[(parts.length - 2) / 2];
						int index = 0;
						for(int i = 2; i < parts.length; i += 2) {
							xCoor[index] = Double.parseDouble(parts[i]);
							yCoor[index] = Double.parseDouble(parts[i + 1]);
							index++;
						}
						drawing.addPolyline(plName, xCoor, yCoor);
					}
					catch(Exception e) {
						throw new ParameterException();
					}
				}
				else {
					throw new MalformedCommandException();
				}
				break;

			case KeyWords.SEGMENT:
				/*
				 * parameters:
				 * <name>,<x1>,<y1>,<x2>,<y2>
				 */

				String name = parts[1];
				String x1 = parts[2];
				String y1 = parts[3];
				String x2 = parts[4];
				String y2 = parts[5];

				try {
					double x = Double.parseDouble(x1);
					double y = Double.parseDouble(y1);
					double xa = Double.parseDouble(x2);
					double ya = Double.parseDouble(y2);
					drawing.addSegment(name, x, y, xa, ya);
				}
				catch(Exception e) {
					throw new ParameterException();
				}
				break;

			default:
				throw new MalformedCommandException();

			}

		}
		catch(ArrayIndexOutOfBoundsException e) {
			throw new MalformedCommandException();
		}
	}

	/**
	 * Changes the description of the drawing
	 * @param des: new description
	 */
	private void parseDescription(String des) {
		drawing.setDescription(des);
	}
	
	/**
	 * Changes the description of the drawing
	 * @param name: new name
	 */
	private void parseName(String name) {
		drawing.setName(name);
	}
	
	/**
	 * Removes a primitive from the drawing
	 * @param prName: name of the primitive to remove
	 */
	private void parseRemove(String prName) {
		
		drawing.removePrimitive(prName);
		
	}
	
	/**
	 * Rotates a shape
	 * @param params: parameters
	 * @throws MalformedCommandException 
	 * @throws ParameterException 
	 */
	private void parseRotate(String params) throws MalformedCommandException, ParameterException {
		String[] parts = params.split(PARAMETER_SPLITTER);
		try {
			
		}
		catch(ArrayIndexOutOfBoundsException e) {
			throw new MalformedCommandException();
		}
		catch(Exception e) {
			throw new ParameterException();
		}
	}
	
	public Drawing getDrawing() {
		return drawing;
	}


	

}
