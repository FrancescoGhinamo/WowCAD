package wowcad.backend.beam.cadManager;

import wowcad.backend.beam.cadManager.exceptions.MalformedCommandException;
import wowcad.backend.beam.cadManager.exceptions.UnsavedException;
import wowcad.backend.beam.drawing.Drawing;
import wowcad.backend.beam.drawing.SaveType;

/**
 * Command interpreter for the cad
 * @author franc
 *
 */
public class CadManager {

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
	 */
	public void parseCommand(String command) throws MalformedCommandException {
		String cmd = command.toUpperCase();
		try {
			String[] parts = cmd.split(":");
			switch(parts[0]) {
			case CommandKeys.ADD:
				break;

			case CommandKeys.DESCRIPTION:
				break;

			case CommandKeys.NAME:
				break;

			case CommandKeys.REMOVE:
				break;

			case CommandKeys.ROTATE:
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
		}

	}
	
	/*
	 * per ogni comando del metodo sopra creare un metodo che separa i vari parametri passati (se sono mancanti o incorretti sollevare Malfrormed...)
	 * tale metodo richiama un metodo specifico che effettua il comando effettivamente (passando i parametri ricavati dalla stringa cmd)
	 * gestire le varie eccezioni e passare i parametri con lo stesso ordine con cui verranno passati ai metodi di dovere (quando si tratta di dover aggiungere una serie di punti (come poliliena o poligono) passare tutti i punti nella riga di comando omettendo il relativo nome (che sarà ""))
	 */
	


}
