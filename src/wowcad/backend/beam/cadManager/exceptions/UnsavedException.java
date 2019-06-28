package wowcad.backend.beam.cadManager.exceptions;

/**
 * Exception thrown when trying to close an unsaved drawing
 * @author franc
 *
 */
public class UnsavedException extends Exception {

	
	private static final long serialVersionUID = 3675712777763363793L;
	
	public UnsavedException() {
		super("The last changes haven't been saved");
	}

}
