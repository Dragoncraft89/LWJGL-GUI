package gui.lwjgl.style;

public class Value<T> {
	private boolean isSet;
	
	private T value;
	
	public Value() {
	}
	
	public T getValue() {
		return value;
	}
	
	public void setValue(T value) {
		isSet = true;
		this.value = value;
	}
	
	public void clearValue() {
		this.value = null;
		isSet = false;
	}
	
	public boolean isSet() {
		return isSet;
	}
}
