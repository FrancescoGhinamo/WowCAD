package wowcad.backend.beam.shapes;

import wowcad.backend.beam.shapes.exceptions.ScalingException;

/**
 * Geometric segment
 * @author franc
 *
 */
public class Segment extends Primitive {
	
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
			Point m = new Point("", (p1.getxCoord() + p2.getxCoord()) / 2, (p1.getyCoord() + p2.getyCoord()) / 2);
			
			double dX = p1.getxCoord() - m.getxCoord();
			double dY = p1.getyCoord() - m.getyCoord();
			
			dX *= scaleFactor;
			dY *= scaleFactor;
			p1.setxCoord(m.getxCoord() + dX);
			p1.setyCoord(m.getyCoord() + dY);
			
			dX = p2.getxCoord() - m.getxCoord();
			dY = p2.getyCoord() - m.getyCoord();
			
			dX *= scaleFactor;
			dY *= scaleFactor;
			p2.setxCoord(m.getxCoord() + dX);
			p2.setyCoord(m.getyCoord() + dY);
		}
		else {
			throw new ScalingException();
		}
		

	}

	public Point getP1() {
		return p1;
	}

	public Point getP2() {
		return p2;
	}

	
	
}
