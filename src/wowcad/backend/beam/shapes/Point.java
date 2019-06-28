package wowcad.backend.beam.shapes;

import java.io.Serializable;

import wowcad.backend.beam.shapes.exceptions.ScalingException;

/**
 * Geometric Point
 * @author franc
 *
 */
public class Point extends Primitive implements Serializable {




	private static final long serialVersionUID = -3436361261680210362L;

	/**
	 * x coordinate
	 */
	private double x;
	/**
	 * y coordinate
	 */
	private double y;



	/**
	 * Constructor
	 * @param name: name assigned to the object
	 * @param x: x coordinate
	 * @param y: y coordinate
	 */
	public Point(String name, double x, double y) {
		super(name);
		this.x = x;
		this.y = y;
	}


	@Override
	public void translate(double deltaX, double deltaY) {
		this.x = this.x + deltaX;
		this.y = this.y + deltaY;

	}

	@Override
	public void rotate(Point center, double degAngle) {
		double a = center.getX();
		double b = center.getY();
		double radAng = Math.toRadians(degAngle);
		double newX = (this.getX() - a) * Math.cos(radAng) - (this.getY() - b) * Math.sin(radAng) + a;
		double newY= (this.getX() - a) * Math.sin(radAng) + (this.getY() - b) * Math.cos(radAng) + b;

		this.setX(newX);
		this.setY(newY);


	}

	@Override
	public void scale(double scaleFactor) throws ScalingException {
		//scaling a point makes no sense!

	}

	@Override
	public Point getCenter() {
		
		return new Point("", x, y);
	}

	@Override
	public Point getAbsoluteMax() {
		// TODO Auto-generated method stub
		return new Point("", Math.abs(x), Math.abs(y));
	}


	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}


	


	


}
