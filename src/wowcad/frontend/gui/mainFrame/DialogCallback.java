package wowcad.frontend.gui.mainFrame;

/**
 * Callback for non modal dialogs
 * @author franc
 *
 */
public interface DialogCallback {

	/**
	 * Parses the specified command
	 * @param cmd: command to parse
	 */
	public void parseCommand(String cmd);
	
}
