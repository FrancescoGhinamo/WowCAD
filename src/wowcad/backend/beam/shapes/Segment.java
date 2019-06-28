package wowcad.backend.beam.shapes;

import java.io.Serializable;

import wowcad.backend.beam.shapes.exceptions.ScalingException;

/**
 * Geometric segment
 * @author franc
 *
 */
public class Segment extends Primitive implements Serializable {


	private static final long serialVersionUID = -6848463404727608751L;

	/**
	 * First extreme of the segment
	 */
	private Point p1;
	/**
	 * Last extreme of the segment
	 */
	private Point p2;



	/**
	 * Constructor
	 * @param name: name assigned to the segment
	 * @param p1: starting point
	 * @param p2: ending point
	 */
	public Segment(String name, Point p1, Point p2) {
		super(name);
		this.p1 = p1;
		this.p2 = p2;
	}

	@Override
	public void translate(double deltaX, double deltaY) {
		p1.translate(deltaX, deltaY);
		p2.translate(deltaX, deltaY);

	}

	@Override
	public void rotate(Point center, double degAngle) {
		p1.rotate(center, degAngle);
		p2.rotate(center, degAngle);

	}

	@Override
	public void scale(double scaleFactor) throws ScalingException {
		if(scaleFactor > 0) {
			//find the middle point of the segment
			Point m = getCenter();

			double dX = p1.getX() - m.getX();
			double dY = p1.getY() - m.getY();

			dX *= scaleFactor;
			dY *= scaleFactor;
			p1.setX(m.getX() + dX);
			p1.setY(m.getY() + dY);

			dX = p2.getX() - m.getX();
			dY = p2.getY() - m.getY();

			dX *= scaleFactor;
			dY *= scaleFactor;
			p2.setX(m.getX() + dX);
			p2.setY(m.getY() + dY);
		}
		else {
			throw new ScalingException();
		}


	}
	
	@Override
	public Point getCenter() {
		return new Point("", (p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
	}
	
	@Override
	public Point getAbsoluteMax() {
		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;		

		if(Math.abs(p1.getX()) > maxX) {
			maxX = p1.getX();
		}
		if(Math.abs(p1.getY()) > maxY) {
			maxY = p1.getY();
		}
		if(Math.abs(p2.getX()) > maxX) {
			maxX = p2.getX();
		}
		if(Math.abs(p2.getY()) > maxY) {
			maxY = p2.getY();
		}
		
		return new Point("", maxX, maxY);
	}

	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
	}

	

	




}
