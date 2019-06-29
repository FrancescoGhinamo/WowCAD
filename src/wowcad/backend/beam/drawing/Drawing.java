package wowcad.backend.beam.drawing;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Hashtable;

import wowcad.backend.beam.drawing.exceptions.LocationNotSpecifiedException;
import wowcad.backend.beam.shapes.Circle;
import wowcad.backend.beam.shapes.Ellipse;
import wowcad.backend.beam.shapes.Point;
import wowcad.backend.beam.shapes.Polygon;
import wowcad.backend.beam.shapes.Polyline;
import wowcad.backend.beam.shapes.Primitive;
import wowcad.backend.beam.shapes.Segment;
import wowcad.backend.service.ISerializationService;
import wowcad.backend.service.SerializationServiceFactory;
import wowcad.backend.service.SerializationType;

/**
 * CAD drawing
 * @author franc
 *
 */
public class Drawing implements Serializable {


	private static final long serialVersionUID = 2484661260610998267L;
	

	/**
	 * Name assigned to this drawing
	 */
	private String name;

	/**
	 * Description of the drawing
	 */
	private String description;

	/**
	 * Primitives composing the drawing
	 */
	private Hashtable<String, Primitive> primitives;


	/**
	 * Location where the drawing is saved
	 */
	private String saveLocation;

	/**
	 * Reference to the serialization service
	 */
	private transient ISerializationService serService;

	/**
	 * True if the drawing has been modified and not saveds
	 */
	private boolean modified;




	/**
	 * Constructor
	 * @param name: name give to the drawing
	 * @param description: description
	 * @param saveLocation: save location of the drawing (can be file path or host address...)
	 * @param saveType: way of saving the drawing
	 */
	public Drawing(String name, String description, String saveLocation, SaveType saveType) {
		this(name, description);
		this.saveLocation = saveLocation;
		switch(saveType) {
		case FILE_ON_DISK:
			this.serService = SerializationServiceFactory.getSerializationService(SerializationType.ON_FILE_SERIALIZATION);
			break;
		default:
			this.serService = SerializationServiceFactory.getSerializationService(SerializationType.ON_FILE_SERIALIZATION);
			break;

		}
	}

	/**
	 * Constructor
	 * @param name: name given to the drawing
	 */
	public Drawing(String name) {
		super();
		this.modified = false;
		this.name = name;
		this.description = "";
		primitives = new Hashtable<String, Primitive>();
	}

	/**
	 * Constructor
	 * @param name: name given to the drawing
	 * @param description: description of the drawing
	 */
	public Drawing(String name, String description) {
		this(name);
		this.description = description;
	}

	/**
	 * Retrieves the drawing from a saved instance
	 * @param sType: way it was saved
	 * @param saveLocation: location to retrieve the drawing from
	 * @throws Exception 
	 */
	public Drawing(SaveType sType, String saveLocation) throws Exception {
		switch(sType) {
		case FILE_ON_DISK:
			this.serService = SerializationServiceFactory.getSerializationService(SerializationType.ON_FILE_SERIALIZATION);
			break;
		default:
			this.serService = SerializationServiceFactory.getSerializationService(SerializationType.ON_FILE_SERIALIZATION);
			break;		
		}

		try {
			Drawing _t = (Drawing) serService.deserialize(saveLocation);
			this.name = _t.name;
			this.description = _t.description;
			this.primitives = _t.primitives;
			this.saveLocation = _t.saveLocation;
			this.modified = false;
		} catch (Exception e) {
			throw e;
		}
	}

	
	/**
	 * Saves the drawing to the specified save location
	 * @throws Exception
	 * @throws LocationNotSpecifiedException
	 */
	public void saveDrawing() throws Exception, LocationNotSpecifiedException {
		if(!saveLocation.equals("")) {
			try {
				serService.serialize(this, saveLocation);
				modified = false;
			} catch (Exception e) {
				throw e;
			}
		}
		else {
			throw new LocationNotSpecifiedException();
		}

	}


	/**
	 * Adds a primitive to the drawing
	 * @param pr: primitive to add
	 */
	public void addPrimitive(Primitive pr) {
		primitives.put(pr.getName(), pr);
		modified = true;
		
	}

	/**
	 * Removes a primitive from the drawing
	 * @param name: name of the primitive to remove
	 */
	public void removePrimitive(String name) {
		primitives.remove(name);
		modified = true;
	}


	/**
	 * Exports the drawing as a JPEG picture
	 * @param expLocation: location where to export the image
	 * @param axis: true to draw axis on the image
	 * @param grid: true to draw the grid on the image
	 * @param redFactor: 1 millimeter of the drawing is represented by redFactor pixel in the image
	 * @throws IOException 
	 */
	public void exportAsJPEG(String expLocation, boolean axis, boolean grid, int redFactor) throws IOException {
		JPEGExporter exp = new JPEGExporter(this,redFactor);
		if(axis) {
			exp.drawCartesianAxis();
		}
		if(grid) {
			exp.drawGrid();
		}
		exp.drawShapes();
		
		try {
			exp.exportJpegImage(new File(expLocation));
		} catch (IOException e) {
			throw e;
		}
	}
	
	/**
	 * Makes and adds the circle to the drawing
	 * @param name: name of the shape
	 * @param xC: x of the center
	 * @param yC: y of the center
	 * @param radius: radius
	 */
	public void addCircle(String name, double xC, double yC, double radius) {
		Point center = new Point("", xC, yC);
		Circle c = new Circle(name, center, radius);
		this.addPrimitive(c);
	}
	
	/**
	 * Makes and adds the ellipse to the drawing
	 * @param name: name of the shape
	 * @param xC: x of the center
	 * @param yC: y of the center
	 * @param radiusX: radius on x axis
	 * @param radiusY: radius on y axis
	 */
	public void addEllipse(String name, double xC, double yC, double radiusX, double radiusY) {
		Point center = new Point("", xC, yC);
		Ellipse e = new Ellipse(name, center, radiusX, radiusY);
		this.addPrimitive(e);
	}
	
	/**
	 * Makes and adds a point to the drawing
	 * @param name: name of the point
	 * @param x: x of the point
	 * @param y: y of the point
	 */
	public void addPoint(String name, double x, double y) {
		Point p = new Point(name, x, y);
		this.addPrimitive(p);
	}
	
	/**
	 * Makes and adds a polygon to the drawing
	 * @param name: name of the polygon
	 * @param xs: list of x coordinates of the vertexes
	 * @param ys: list of y coordinates of the vertexes
	 */
	public void addPolygon(String name, double[] xs, double[] ys) {
		Point[] v = new Point[xs.length];
		for(int i = 0; i < v.length; i++) {
			v[i] = new Point("", xs[i], ys[i]);
		}
		
		Polygon pol = new Polygon(name);
		for(Point p: v) {
			pol.addPoint(p);
		}
		this.addPrimitive(pol);
		
	}
	
	/**
	 * Makes and adds a polyline to the drawing
	 * @param name: name of the polyline
	 * @param xs: list of x coordinates of the vertexes
	 * @param ys: list of y coordinates of the vertexes
	 */
	public void addPolyline(String name, double[] xs, double[] ys) {
		Point[] v = new Point[xs.length];
		for(int i = 0; i < v.length; i++) {
			v[i] = new Point("", xs[i], ys[i]);
		}
		
		Polyline pol = new Polyline(name);
		for(Point p: v) {
			pol.addPoint(p);
		}
		this.addPrimitive(pol);
		
	}
	
	/**
	 * Makes and adds a segment to the drawing
	 * @param name: name of the segment
	 * @param x1: x of first end
	 * @param y1: y of first end
	 * @param x2: x of second end
	 * @param y2: y of second end
	 */
	public void addSegment(String name, double x1, double y1, double x2, double y2) {
		Point p1 = new Point("", x1, y1);
		Point p2 = new Point("", x2, y2);
		Segment s = new Segment(name, p1, p2);
		this.addPrimitive(s);
	}
	
	
	/**
	 * Rotates a primitive
	 * @param name: name of the primitive to rotate
	 * @param xC: x of the center of rotation
	 * @param yC: y of the center of rotation
	 * @param deg: degrees of rotation
	 */
	public void rotatePrimitive(String name, double xC, double yC, double deg) {
		try {
			primitives.get(name).rotate(new Point("", xC, yC), deg);
		}
		catch(Exception e) {
			
		}
		
	}
	
	/**
	 * Scales a primitive
	 * @param name: name of the primitive to scale
	 * @param scaleFactor: scaling factor
	 */
	public void scalePrimitive(String name, double scaleFactor) {
		try {
			primitives.get(name).scale(scaleFactor);
		}
		catch(Exception e) {
			
		}
	}
	
	/**
	 * Translates a primitive
	 * @param name: name of the primitive to translate
	 * @param deltaX: x movement
	 * @param deltaY: y movement
	 */
	public void translatePrimitive(String name, double deltaX, double deltaY) {
		try {
			primitives.get(name).translate(deltaX, deltaY);
		}
		catch(Exception e) {
			
		}
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Hashtable<String, Primitive> getPrimitives() {
		return primitives;
	}

	public String getSaveLocation() {
		return saveLocation;
	}

	public void setSaveLocation(String saveLocation) {
		this.saveLocation = saveLocation;
	}

	public boolean isModified() {
		return modified;
	}







}
