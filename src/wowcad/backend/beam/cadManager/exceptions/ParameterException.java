package wowcad.backend.beam.cadManager.exceptions;

/**
 * Exception thrown when passing wrong parameters to a command
 * @author franc
 *
 */
public class ParameterException extends Exception {

	
	private static final long serialVersionUID = 928896654269331777L;
	
	/**
	 * Constructor with default message
	 */
	public ParameterException() {
		super("The command received wrong parameters");
	}

}
