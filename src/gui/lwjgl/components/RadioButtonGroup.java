package gui.lwjgl.components;

/**
 * This class handles the logic behind radio buttons
 * @author Florian
 *
 */
public class RadioButtonGroup {
	private RadioButton selected;
	
	/**
	 * Constructor
	 * 
	 */
	public RadioButtonGroup() {
		
	}
	
	/**
	 * Adds a radio button to this group
	 * @param button
	 */
	public void addToGroup(RadioButton button) {
		button.setSelected(false);
		button.setRadioButtonGroup(this);
	}
	
	/**
	 * Selects a Radio button from this group and deselects the old selection, if present
	 * @param button
	 */
	public void select(RadioButton button) {
		if(selected != null && selected != button)
			selected.setSelected(false);
		selected = button;
	}
}
