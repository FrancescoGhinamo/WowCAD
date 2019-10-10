package wowcad.backend.beam.shapes;

import java.io.Serializable;

import wowcad.backend.beam.shapes.exceptions.ScalingException;

/**
 * General graphic primitive
 * @author franc
 *
 */
public abstract class Primitive implements Serializable {


	private static final long serialVersionUID = 2118409659123500683L;

	/**
	 * Name assigned to the Primitive
	 */
	private String name;
	
	


	/**
	 * Constructor
	 * @param name: name assigned to the primitive
	 */
	public Primitive(String name) {
		super();
		this.name = name;
	}

	/**
	 * Translates the primitive
	 * @param deltaX: translation on x axis
	 * @param deltaY: translation on y axis
	 */
	public abstract void translate(double deltaX, double deltaY);

	/**
	 * Rotates the primitive of the specified a angle around the specified point
	 * @param center: center of rotation
	 * @param degAngle: angle in degrees of rotation
	 */
	public abstract void rotate(Point center, double degAngle);

	/**
	 * Scaled the primitive of the specified factor
	 * @param scaleFactor: scale factor. If 0 < scaleFactor < 1, the primitive gets smaller, if scaleFactor > 1 the primitive gets bigger
	 * @throws ScalingException
	 */
	public abstract void scale(double scaleFactor) throws ScalingException;
	
	/**
	 * Returns the "center of gravity" of the polygon
	 * @return Returns the "center of gravity" of the polygon
	 */
	public abstract Point getCenter();
	
	/**
	 * Returns the farthest point of the shape from the origin, only positive axis
	 * @return: Returns the farthest point of the shape from the origin, only positive axis
	 */
	public abstract Point getAbsoluteMax();


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
