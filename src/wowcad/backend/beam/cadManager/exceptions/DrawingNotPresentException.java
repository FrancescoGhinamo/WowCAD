package wowcad.backend.beam.cadManager.exceptions;

/**
 * Exception thrown when operating on a drawing not instantiated
 * @author franc
 *
 */
public class DrawingNotPresentException extends Exception {

	
	private static final long serialVersionUID = -7849278359228880733L;
	
	public DrawingNotPresentException() {
		super("You have to create a drawing before performing this operation");
	}

}
