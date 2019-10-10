package wowcad.backend.beam.cadManager;

/**
 * Keys that define the command to be executed by the CadManager
 * @author franc
 *
 */
public class CommandKeys {

	/**
	 * Command to add a primitive to the drawing
	 */
	public static final String ADD = "ADD";
	
	/**
	 * Command to translate a primitive
	 */
	public static final String TRANSLATE = "TRANSLATE";
	
	/**
	 * Command to rotate a primitive
	 */
	public static final String ROTATE = "ROTATE";
	
	/**
	 * Command to scale a primitive
	 */
	public static final String SCALE = "SCALE";
	
	/**
	 * Command to remove a primitive
	 */
	public static final String REMOVE = "REMOVE";
	
	/**
	 * Command to save the drawing
	 */
	public static final String SAVE = "SAVE";
	
	/**
	 * Command to change the name to the drawing
	 */
	public static final String NAME = "NAME";
	
	/**
	 * Command to change the description to the drawing
	 */
	public static final String DESCRIPTION = "DESCRIPTION";
	
	/**
	 * Command to change the save location of the file
	 */
	public static final String SAVE_LOC = "SAVE_LOC";
		
	/**
	 * Command to export the drawing
	 */
	public static final String EXPORT = "EXPORT";
	
}
