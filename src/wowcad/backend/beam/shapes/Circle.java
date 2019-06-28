package wowcad.backend.beam.shapes;

import java.io.Serializable;

/**
 * Geometric circle
 * @author franc
 *
 */
public class Circle extends Ellipse implements Serializable {


	private static final long serialVersionUID = 2033604994498427530L;

	public Circle(String name, Point center, double radius) {
		super(name, center, radius, radius);
	}

	public double getRadius() {
		return super.getxRadius();
	}

	public void setRadius(double radius) {
		super.setxRadius(radius);
		super.setyRadius(radius);
	}
	

}
