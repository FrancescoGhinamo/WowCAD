package wowcad.backend.service;

/**
 * ISerializationService factory class
 * @author franc
 *
 */
public class SerializationServiceFactory {

	/**
	 * Gets the serialization service
	 * @param sType: type of serialization service
	 * @return Instance of the serialization service
	 */
	public static ISerializationService getSerializationService(SerializationType sType) {
		ISerializationService service = null;
		
		switch(sType) {
		case FILE_SERIALIZATION:
			service = new FileSerializationServiceImpl();
			break;
		default:
			break;
		
		}
		
		return service;
	}
	
}
