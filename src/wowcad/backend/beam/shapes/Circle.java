package wowcad.backend.beam.shapes;

/**
 * Geometric circle
 * @author franc
 *
 */
public class Circle extends Ellipse {

	public Circle(String name, Point center, double radius) {
		super(name, center, radius, radius);
	}

	public double getRadius() {
		return super.getMajorRadius();
	}
	
	public void setRadius(double radius) {
		super.setMajorRadius(radius);
		super.setMinorRadius(radius);
	}
}
