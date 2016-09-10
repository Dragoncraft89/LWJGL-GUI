package gui.lwjgl.components;

/**
 * This class handles the logic behind tabs
 * @author Florian
 *
 */
public class TabGroup {
	private Tab selected;
	
	/**
	 * Constructor
	 * 
	 */
	public TabGroup() {
		
	}
	
	/**
	 * Adds a tab to this group
	 * @param button
	 */
	public void addToGroup(Tab button) {
		button.setSelected(false);
		button.setTabGroup(this);
	}
	
	/**
	 * Selects a tag from this group and deselects the old selection, if present
	 * @param button
	 */
	public void select(Tab button) {
		if(selected != null && selected != button)
			selected.setSelected(false);
		selected = button;
	}
}
