package wowcad.backend.beam.shapes;

import java.io.Serializable;

import wowcad.backend.beam.shapes.exceptions.ScalingException;

/**
 * Geometric ellipse
 * @author franc
 *
 */
public class Ellipse extends Primitive implements Serializable {


	private static final long serialVersionUID = -5877835259695106195L;

	/**
	 * Center of the ellipse
	 */
	private Point center;

	/**
	 * Major radius
	 */
	private double majorRadius;

	/**
	 * Minor radius
	 */
	private double minorRadius;



	/**
	 * Constructor
	 * @param name: name given to the shape
	 * @param center: center of the ellipse
	 * @param majorRadius: major radius
	 * @param minorRadius:minor radius
	 */
	public Ellipse(String name, Point center, double majorRadius, double minorRadius) {
		super(name);
		this.center = center;
		this.majorRadius = majorRadius;
		this.minorRadius = minorRadius;
	}

	@Override
	public void translate(double deltaX, double deltaY) {
		center.translate(deltaX, deltaY);

	}

	@Override
	public void rotate(Point center, double degAngle) {
		double rT = this.majorRadius;
		this.majorRadius = this.minorRadius;
		this.minorRadius = rT;

	}

	@Override
	public void scale(double scaleFactor) throws ScalingException {
		if(scaleFactor > 0) {
			this.majorRadius *= scaleFactor;
			this.minorRadius *= scaleFactor;
		}
		else {
			throw new ScalingException();
		}
		

	}

	public Point getCenter() {
		return center;
	}

	public double getMajorRadius() {
		return majorRadius;
	}

	public double getMinorRadius() {
		return minorRadius;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public void setMajorRadius(double majorRadius) {
		this.majorRadius = majorRadius;
	}

	public void setMinorRadius(double minorRadius) {
		this.minorRadius = minorRadius;
	}


}
