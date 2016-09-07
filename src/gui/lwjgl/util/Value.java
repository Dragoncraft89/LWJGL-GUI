package gui.lwjgl.util;

/**
 * This class stores a value of the datatype T<br>
 * Furthermore this class keeps track if there was already value set<br>
 * 
 * @author Florian
 *
 * @param <T> the datatype to store
 */
public class Value<T> {
	private boolean isSet;
	
	private T value;
	
	/**
	 * Constructor
	 */
	public Value() {
	}
	
	/**
	 * Returns the value or null if there is none
	 * @return the value or null
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Sets the value and the isSet mark
	 * @see #clearValue()
	 * @see #isSet()
	 * @param value
	 */
	public void setValue(T value) {
		isSet = true;
		this.value = value;
	}
	
	/**
	 * clears the value and removes the isSet mark
	 * @see #setValue(T)
	 * @see #isSet()
	 */
	public void clearValue() {
		this.value = null;
		isSet = false;
	}
	
	/**
	 * Checks if the value has already been set
	 * @see #setValue(T)
	 * @see #clearValue()
	 * @return true if there was a value set, false otherwise
	 */
	public boolean isSet() {
		return isSet;
	}
}
