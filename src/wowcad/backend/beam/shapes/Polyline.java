package wowcad.backend.beam.shapes;

import java.io.Serializable;
import java.util.ArrayList;

import wowcad.backend.beam.shapes.exceptions.ScalingException;

/**
 * Geometric polyline
 * @author franc
 *
 */
public class Polyline extends Primitive implements Serializable {


	private static final long serialVersionUID = -7754045267412342837L;

	/**
	 * Points that are part of the polyline
	 */
	private ArrayList<Point> points;



	/**
	 * Constructor
	 * @param name: name assigned to the shape
	 */
	public Polyline(String name) {
		super(name);
		points = new ArrayList<Point>();
	}

	@Override
	public void translate(double deltaX, double deltaY) {
		for(Point p: points) {
			p.translate(deltaX, deltaY);
		}

	}

	@Override
	public void rotate(Point center, double degAngle) {
		for(Point p: points) {
			p.rotate(center, degAngle);
		}

	}

	@Override
	public void scale(double scaleFactor) throws ScalingException {
		if(scaleFactor > 0) {
			try {
				Point reference = points.get(0);
				double dX;
				double dY;

				for(int i = 1; i < points.size(); i++) {
					dX = points.get(i).getX() - reference.getX();
					dY = points.get(i).getY() - reference.getY();

					dX *= scaleFactor;
					dY *= scaleFactor;
					points.get(i).setX(reference.getX() + dX);
					points.get(i).setY(reference.getY() + dY);
				}

			}
			catch(IndexOutOfBoundsException e) {

			}
		}
		else {
			throw new ScalingException();
		}



	}

	@Override
	public Point getCenter() {
		
		double sumX = 0;
		
		double sumY = 0;
		
		for(Point p: points) {
			sumX += p.getX();
			sumY += p.getY();
		}
		
		return new Point("", sumX / points.size(), sumY / points.size());
	}
	
	@Override
	public Point getAbsoluteMax() {
		double maxX = - Double.MAX_VALUE;
		double maxY = - Double.MAX_VALUE;		

		//determines the dimension of the resulting picture, where a millimeter corresponds to a pixel
		for(Point p: points) {
			if(Math.abs(p.getX()) > maxX) {
				maxX = Math.abs(p.getX());
			}
			if(Math.abs(p.getY()) > maxY) {
				maxY = Math.abs(p.getX());
			}
		}
		return new Point("", maxX, maxY);
	}
	
	/**
	 * Adds a point to the polyline
	 * @param p: point to add
	 */
	public void addPoint(Point p) {
		points.add(p);
	}

	/**
	 * Removes a point from the polyline
	 * @param index: index of the point to remove
	 */
	public void removePoint(int index) {
		points.remove(index);

	}

	public ArrayList<Point> getPoints() {
		return points;
	}

	

	



}
