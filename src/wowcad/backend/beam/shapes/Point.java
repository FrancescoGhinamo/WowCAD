package wowcad.backend.beam.shapes;

import wowcad.backend.beam.shapes.exceptions.ScalingException;

/**
 * Geometric Point
 * @author franc
 *
 */
public class Point extends Primitive {
	
	

	/**
	 * x coordinate
	 */
	private double xCoord;
	/**
	 * y coordinate
	 */
	private double yCoord;
	
	

	/**
	 * Constructor
	 * @param name: name assigned to the object
	 * @param xCoord: x coordinate
	 * @param yCoord: y coordinate
	 */
	public Point(String name, double xCoord, double yCoord) {
		super(name);
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	
	@Override
	public void translate(double deltaX, double deltaY) {
		this.xCoord = this.xCoord + deltaX;
		this.yCoord = this.yCoord + deltaY;

	}

	@Override
	public void rotate(Point center, double degAngle) {
		double a = center.getxCoord();
		double b = center.getyCoord();
		double radAng = Math.toRadians(degAngle);
		double newX = (this.getxCoord() - a) * Math.cos(radAng) - (this.getyCoord() - b) * Math.sin(radAng) + a;
		double newY= (this.getxCoord() - a) * Math.sin(radAng) + (this.getyCoord() - b) * Math.cos(radAng) + b;
		
		this.setxCoord(newX);
		this.setyCoord(newY);
		

	}

	@Override
	public void scale(double scaleFactor) throws ScalingException {
		//scaling a point makes no sense!
		
	}

	
	
	
	public double getxCoord() {
		return xCoord;
	}

	void setxCoord(double xCoord) {
		this.xCoord = xCoord;
	}

	public double getyCoord() {
		return yCoord;
	}

	void setyCoord(double yCoord) {
		this.yCoord = yCoord;
	}

	
}
