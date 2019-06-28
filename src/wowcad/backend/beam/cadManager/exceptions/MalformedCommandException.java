package wowcad.backend.beam.cadManager.exceptions;

/**
 * Exception thrown when the command is received in the wrong format
 * @author franc
 *
 */
public class MalformedCommandException extends Exception {


	private static final long serialVersionUID = 1860702128648814680L;
	
	/**
	 * Constructor with default message
	 */
	public MalformedCommandException() {
		super("Command sintax incorrect");
	}

}
