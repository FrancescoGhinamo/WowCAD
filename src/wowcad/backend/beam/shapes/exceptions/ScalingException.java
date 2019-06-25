package wowcad.backend.beam.shapes.exceptions;

/**
 * Exception thrown if scaling by a factor less or equal to 0
 * @author franc
 *
 */
public class ScalingException extends Exception {

	
	private static final long serialVersionUID = -6234028812230453116L;

	/**
	 * Constructor with default message
	 */
	public ScalingException() {
		super("Scaling by a factor less or equal to 0 is not allowed");
	}
}
