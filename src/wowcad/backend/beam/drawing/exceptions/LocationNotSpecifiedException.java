package wowcad.backend.beam.drawing.exceptions;

/**
 * Exception thrown when the location where to save the drawing is not specified
 * @author franc
 *
 */
public class LocationNotSpecifiedException extends Exception {

	
	private static final long serialVersionUID = 3501391171660639250L;

	/**
	 * Constructor with default message
	 */
	public LocationNotSpecifiedException() {
		super("Save location not specified!");
	}
}
