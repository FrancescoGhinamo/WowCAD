package wowcad.backend.beam.drawing;

import java.io.Serializable;
import java.util.Hashtable;

import wowcad.backend.beam.drawing.exceptions.LocationNotSpecifiedException;
import wowcad.backend.beam.shapes.Primitive;
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
	 */
	public void saveDrawing() throws Exception {
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
