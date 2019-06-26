package wowcad.backend.beam.drawing;

import java.util.Hashtable;

import wowcad.backend.beam.shapes.Primitive;

/**
 * CAD drawing
 * @author franc
 *
 */
public class Drawing {
	
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
	 * Constructor
	 * @param name: name given to the drawing
	 */
	public Drawing(String name) {
		super();
		this.name = name;
		this.description = "";
		primitives = new Hashtable<String, Primitive>();
	}

	public Drawing(String name, String description) {
		this(name);
		this.description = description;
	}

	/**
	 * Adds a primitive to the drawing
	 * @param pr: primitive to add
	 */
	public void addPrimitive(Primitive pr) {
		primitives.put(pr.getName(), pr);
	}
	
	/**
	 * Removes a primitive from the drawing
	 * @param name: name of the primitive to remove
	 */
	public void removePrimitive(String name) {
		primitives.remove(name);
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
	
	
	
	
	
	
}
