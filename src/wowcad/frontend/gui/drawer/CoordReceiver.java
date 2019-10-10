package wowcad.frontend.gui.drawer;

/**
 * The class that implement this can receive coordinates from a specific drawing canvas
 * @author franc
 *
 */
public interface CoordReceiver {

	/**
	 * Method called when coordinates are sent and so received
	 * @param x
	 * @param y
	 */
	public void onReceive(double x, double y);
	
}
