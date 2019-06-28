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
	 * Radius on x axis
	 */
	private double xRadius;

	/**
	 * Radius in y axis
	 */
	private double yRadius;



	/**
	 * Constructor
	 * @param name: name given to the shape
	 * @param center: center of the ellipse
	 * @param xRadius: major radius
	 * @param yRadius:minor radius
	 */
	public Ellipse(String name, Point center, double xRadius, double yRadius) {
		super(name);
		this.center = center;
		this.xRadius = xRadius;
		this.yRadius = yRadius;
	}

	@Override
	public void translate(double deltaX, double deltaY) {
		center.translate(deltaX, deltaY);

	}

	@Override
	public void rotate(Point center, double degAngle) {
		double rT = this.xRadius;
		this.xRadius = this.yRadius;
		this.yRadius = rT;

	}

	@Override
	public void scale(double scaleFactor) throws ScalingException {
		if(scaleFactor > 0) {
			this.xRadius *= scaleFactor;
			this.yRadius *= scaleFactor;
		}
		else {
			throw new ScalingException();
		}
		

	}

	public Point getCenter() {
		return center;
	}

	public double getxRadius() {
		return xRadius;
	}

	public double getyRadius() {
		return yRadius;
	}

	public void setCenter(Point center) {
		this.center = center;
	}

	public void setxRadius(double xRadius) {
		this.xRadius = xRadius;
	}

	public void setyRadius(double yRadius) {
		this.yRadius = yRadius;
	}


}
