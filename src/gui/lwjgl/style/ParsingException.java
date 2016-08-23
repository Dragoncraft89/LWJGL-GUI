package gui.lwjgl.style;

/**
 * This Exception is thrown when an error occurs while parsing<br>
 * Most commonly used for invalid css stylesheets
 * @author Florian
 *
 */
public class ParsingException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -715352733312213482L;

	/**
	 * Constructor
	 * @param message
	 */
	public ParsingException(String message) {
		super(message);
	}
}
