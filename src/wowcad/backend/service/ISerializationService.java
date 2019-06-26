package wowcad.backend.service;

/**
 * PRovides functions to serialize and deserialize objects
 * @author franc
 *
 */
public interface ISerializationService {

	/**
	 * Serializes an object to the specified destination
	 * @param what: object to serialize
	 * @param destination: destination of serialization: can be a file or host address...
	 */
	public void serialize(Object what, String destination);
	
	/**
	 * Deserializes an object from a source
	 * @param source: source: can be a file or host address...
	 * @return deserialized object
	 */
	public Object deserialize(String source);
}
