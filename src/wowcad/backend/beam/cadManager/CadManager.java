package wowcad.backend.beam.cadManager;

import java.util.Observer;

import wowcad.backend.beam.cadManager.exceptions.DrawingNotPresentException;
import wowcad.backend.beam.cadManager.exceptions.MalformedCommandException;
import wowcad.backend.beam.cadManager.exceptions.ParameterException;
import wowcad.backend.beam.cadManager.exceptions.UnsavedException;
import wowcad.backend.beam.drawing.Drawing;
import wowcad.backend.beam.drawing.SaveType;
import wowcad.backend.beam.drawing.exceptions.LocationNotSpecifiedException;

/**
 * Command interpreter for the cad
 * @author franc
 *
 */
@SuppressWarnings("deprecation")
public class CadManager {

	/**
	 * Character to separate the name of the command from the parameters
	 */
	public static final String COMMAND_SPLITTER = ":";

	/**
	 * Character to separate the parameters
	 */
	public static final String PARAMETER_SPLITTER = ",";

	/**
	 * Drawing the manager operates on
	 */
	private Drawing drawing;

	/**
	 * Forwarded observer for the drawing
	 */
	private Observer forwardedObserver;

	/**
	 * Constructor
	 */
	public CadManager() {
		super();
		this.drawing = null;
		this.forwardedObserver = null;
	}



	/**
	 * Constructor
	 * @param forwardedObserver: forwarded observer for the drawing
	 */
	public CadManager(Observer forwardedObserver) {
		this();
		this.forwardedObserver = forwardedObserver;
	}


	/**
	 * Opens a saved drawing
	 * @param source: source from which open the drawing
	 * @param force: true to force the creation of the drawing (like to force existing drawing override)
	 * @throws UnsavedException 
	 */
	public void openDrawing(String source, boolean force) throws UnsavedException, Exception {
		if(force || drawing == null) {
			drawing = new Drawing(SaveType.FILE_ON_DISK, source);
			if(forwardedObserver != null) {
				drawing.addObserver(forwardedObserver);
			}
		}
		else {
			if(!drawing.isModified()) {
				drawing = new Drawing(SaveType.FILE_ON_DISK, source);
				if(forwardedObserver != null) {
					drawing.addObserver(forwardedObserver);
				}
			}
			else {
				throw new UnsavedException();
			}
		}
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
			if(forwardedObserver != null) {
				drawing.addObserver(forwardedObserver);
			}
		}
		else {
			if(!drawing.isModified()) {
				drawing = new Drawing(name);
				if(forwardedObserver != null) {
					drawing.addObserver(forwardedObserver);
				}
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
			if(forwardedObserver != null) {
				drawing.addObserver(forwardedObserver);
			}
		}
		else {
			if(!drawing.isModified()) {
				drawing = new Drawing(name, description);
				if(forwardedObserver != null) {
					drawing.addObserver(forwardedObserver);
				}
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
			if(forwardedObserver != null) {
				drawing.addObserver(forwardedObserver);
			}
		}
		else {
			if(!drawing.isModified()) {
				drawing = new Drawing(name, description, saveLocation, saveType);
				if(forwardedObserver != null) {
					drawing.addObserver(forwardedObserver);
				}
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
	public void parseCommand(String command) throws MalformedCommandException, ParameterException, DrawingNotPresentException, LocationNotSpecifiedException, Exception {
		if(drawing != null) {
			
			String cmd = command;
			try {
				if(cmd.equals(CommandKeys.SAVE)) {
					/*
					 * no parameters
					 */
					parseSave();
				}
				else {
					//				String[] parts = cmd.split(COMMAND_SPLITTER);
					String[] parts = new String[2];
					parts[0] = cmd.substring(0, cmd.indexOf(COMMAND_SPLITTER));
					parts[1] = cmd.substring(cmd.indexOf(COMMAND_SPLITTER) + 1);
					
					parts[0] = parts[0].toUpperCase();
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
						 * <newName>
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
						/*
						 * no parameters
						 */
						parseSave();
						break;

					case CommandKeys.SAVE_LOC:
						/*
						 * parameters:
						 * <newSaveLocation>
						 */
						parseSaveLoc(parts[1]);
						break;

					case CommandKeys.SCALE:
						/*
						 * parameters:
						 * <primitiveName>,<scaleFactor>
						 */
						parseScale(parts[1]);
						break;

					case CommandKeys.TRANSLATE:
						/*
						 * parameters:
						 * <primitiveName>,<deltaX>,<deltaY>
						 */
						parseTranslate(parts[1]);
						break;

					case CommandKeys.EXPORT:
						/*
						 * parameters:
						 * <expLocation>,<axis?>,<grid?>,<fact>
						 */
						parseExport(parts[1]);
						break;

					default:
						throw new MalformedCommandException();
					}
				}
			}
			catch(ArrayIndexOutOfBoundsException e) {
				throw new MalformedCommandException();
			} catch (ParameterException e) {
				throw e;
			} catch (LocationNotSpecifiedException e) {
				throw e;
			} catch (Exception e) {
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
			String p = parameters + PARAMETER_SPLITTER;
			String[] parts = p.split(PARAMETER_SPLITTER);

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
			String name = parts[0];
			double xc = Double.parseDouble(parts[1]);
			double yc = Double.parseDouble(parts[2]);
			double ang = Double.parseDouble(parts[3]);
			drawing.rotatePrimitive(name, xc, yc, ang);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			throw new MalformedCommandException();
		}
		catch(Exception e) {
			throw new ParameterException();
		}
	}

	/**
	 * Scales a shape
	 * @param params: parameters
	 * @throws MalformedCommandException 
	 * @throws ParameterException 
	 */
	private void parseScale(String params) throws MalformedCommandException, ParameterException {
		String[] parts = params.split(PARAMETER_SPLITTER);
		try {
			String name = parts[0];
			double fact = Double.parseDouble(parts[1]);
			drawing.scalePrimitive(name, fact);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			throw new MalformedCommandException();
		}
		catch(Exception e) {
			throw new ParameterException();
		}

	}

	private void parseTranslate(String params) throws MalformedCommandException, ParameterException {
		String[] parts = params.split(PARAMETER_SPLITTER);
		try {
			String name = parts[0];
			double dX = Double.parseDouble(parts[1]);
			double dY = Double.parseDouble(parts[2]);
			drawing.translatePrimitive(name, dX, dY);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			throw new MalformedCommandException();
		}
		catch(Exception e) {
			throw new ParameterException();
		}
	}

	/**
	 * Saves the drawing
	 * @throws Exception 
	 */
	private void parseSave() throws LocationNotSpecifiedException, Exception {
		try {
			drawing.saveDrawing();
		} catch (LocationNotSpecifiedException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}

	}

	private void parseExport(String params) throws MalformedCommandException, ParameterException {
		String[] parts = params.split(PARAMETER_SPLITTER);
		try {
			String loc = parts[0];
			boolean axis = true;
			switch(parts[1]) {
			case KeyWords.TRUE:
				axis = true;
				break;

			case KeyWords.FALSE:
				axis = false;
				break;
			}

			boolean grid = true;
			switch(parts[2]) {
			case KeyWords.TRUE:
				grid = true;
				break;

			case KeyWords.FALSE:
				grid = false;
				break;
			}

			int f = Integer.parseInt(parts[3]);

			drawing.exportAsJPEG(loc, axis, grid, f);
		}
		catch(ArrayIndexOutOfBoundsException e) {
			throw new MalformedCommandException();
		}
		catch(Exception e) {
			throw new ParameterException();
		}
	}

	/**
	 * Changes the save location of the drawing
	 * @param loc: new save location
	 */
	private void parseSaveLoc(String loc) {
		drawing.setSaveLocation(loc);

	}

	public Drawing getDrawing() {
		return drawing;
	}




}