package gui.lwjgl.style;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import gui.lwjgl.components.Checkbox;
import gui.lwjgl.components.Component;
import gui.lwjgl.components.List;
import gui.lwjgl.components.RadioButton;
import gui.lwjgl.components.Throbber;
import gui.lwjgl.util.Texture;
import gui.lwjgl.util.Value;

/**
 * Stores all data from a css Stylesheet
 * @author Florian
 *
 */
public class StyleTemplate {
	
	private StyleTemplate parent;
	
	private HashMap<String, Style> data;
	
	/**
	 * Constructor
	 * @param parent
	 * @param data
	 */
	public StyleTemplate(StyleTemplate parent, HashMap<String, Style> data) {
		this.parent = parent;
		this.data = data;
	}

	/**
	 * Returns the backgroundColor
	 * @return the backgroundColor
	 */
	public Value<float[]> getBackgroundColor(String group) {
		return data.get(group).getBackgroundColor();
	}

	/**
	 * Returns the foregroundColor
	 * @return the foregroundColor
	 */
	public Value<float[]> getForegroundColor(String group) {
		return data.get(group).getForegroundColor();
	}

	/**
	 * Returns the textColor
	 * @return the textColor
	 */
	public Value<float[]> getTextColor(String group) {
		return data.get(group).getTextColor();
	}

	/**
	 * Returns the borderColor
	 * @return the borderColor
	 */
	public Value<float[]> getBorderColor(String group) {
		return data.get(group).getBorderColor();
	}

	/**
	 * Returns the listColor
	 * @return the listColor
	 */
	private Value<float[]> getListColor(String group) {
		return data.get(group).getListColor();
	}

	/**
	 * Returns the texture
	 * @return the texture
	 */
	public Value<Texture> getTexture(String group) {
		return data.get(group).getTexture();
	}
	
	/**
	 * Returns if the border should be drawn
	 * @param group
	 * @return true if the border should be drawn, false otherwise
	 */
	private Value<Boolean> getDrawBorder(String group) {
		return data.get(group).getDrawBorder();
	}
	
	/**
	 * Returns the rotation speed for throbbers
	 * @param group
	 * @return the rotation speed for throbbers
	 */
	private Value<Float> getRotationSpeed(String group) {
		return data.get(group).getRotationSpeed();
	}
	
	private Value<Texture> getTextureEnabled(String group) {
		return data.get(group).getTextureEnabled();
	}

	private Value<Texture> getTextureDisabled(String group) {
		return data.get(group).getTextureDisabled();
		}
	
	/**
	 * Loads all properties for a Checkbox
	 * @param checkbox
	 */
	public void load(Checkbox checkbox) {
		if(parent != null)
			parent.load(checkbox);
		
		for(String group : data.keySet()) {
			String[] groups = group.split(",");
			
			if(!hasAtLeastOneGroup(checkbox, groups)) {
				continue;
			}
			
			loadProperties(checkbox, group);
		}
	}
	
	public void load(RadioButton button) {
		if(parent != null)
			parent.load(button);
		
		for(String group : data.keySet()) {
			String[] groups = group.split(",");
			
			if(!hasAtLeastOneGroup(button, groups)) {
				continue;
			}
			
			loadProperties(button, group);
		}
	}

	/**
	 * Loads all properties for a Throbber
	 * @param throbber
	 */
	public void load(Throbber throbber) {
		if(parent != null)
			parent.load(throbber);
		
		for(String group : data.keySet()) {
			String[] groups = group.split(",");
			
			if(!hasAtLeastOneGroup(throbber, groups)) {
				continue;
			}
			
			loadProperties(throbber, group);
		}
	}

	/**
	 * Loads all properties for a List
	 * @param list
	 */
	public void load(List list) {
		if(parent != null)
			parent.load(list);
		
		for(String group : data.keySet()) {
			String[] groups = group.split(",");
			
			if(!hasAtLeastOneGroup(list, groups)) {
				continue;
			}
			
			loadProperties(list, group);
		}
	}

	/**
	 * Loads all properties for any component, which does not have any special data
	 * @param component
	 */
	public void load(Component component) {
		if(parent != null)
			parent.load(component);
		
		for(String group : data.keySet()) {
			String[] groups = group.split(",");
			
			if(!hasAtLeastOneGroup(component, groups)) {
				continue;
			}
			
			loadProperties(component, group);
		}
	}
	
	private void loadProperties(Component component, String group) {
		Value<float[]> background = getBackgroundColor(group);
		Value<float[]> foreground = getForegroundColor(group);
		Value<float[]> text = getTextColor(group);
		Value<float[]> border = getBorderColor(group);
		
		Value<Boolean> drawBorder = getDrawBorder(group);
			
		Value<Texture> tex = getTexture(group);
		
		if(background.isSet()) {
			float[] b = background.getValue();
			component.setBackgroundColor(b[0], b[1], b[2], b[3]);
		}
		if(foreground.isSet()) {
			float[] f = foreground.getValue();
			component.setForegroundColor(f[0], f[1], f[2], f[3]);
		}
		if(text.isSet()) {
			float[] t = text.getValue();
			component.setTextColor(t[0], t[1], t[2], t[3]);
		}
		if(border.isSet()) {
			float[] bo = border.getValue();
			component.setBorderColor(bo[0], bo[1], bo[2], bo[3]);
		}

		if(drawBorder.isSet())
			component.setDrawBorder(drawBorder.getValue());

		if(tex.isSet())
			component.setTexture(tex.getValue());
	}

	private void loadProperties(List list, String group) {
		loadProperties((Component)list, group);
		
		Value<float[]> listColor = getListColor(group);
		
		if(listColor.isSet()) {
			float[] l = listColor.getValue();
			list.setListColor(l[0], l[1], l[2], l[3]);
		}
	}
	
	private void loadProperties(Throbber throbber, String group) {
		loadProperties((Component)throbber, group);
		
		Value<Float> rotationSpeed = getRotationSpeed(group);
		
		if(rotationSpeed.isSet()) {
			throbber.setRotationSpeed(rotationSpeed.getValue());
		}
	}
	
	private void loadProperties(Checkbox checkbox, String group) {
		loadProperties((Component) checkbox, group);
		
		Value<Texture> texOn = getTextureEnabled(group);
		Value<Texture> texOff = getTextureDisabled(group);
		
		if(texOn.isSet())
			checkbox.setTextureEnabled(texOn.getValue());
		if(texOff.isSet())
			checkbox.setTextureDisabled(texOff.getValue());
	}
	
	private void loadProperties(RadioButton button, String group) {
		loadProperties((Component) button, group);
		
		Value<Texture> texOn = getTextureEnabled(group);
		Value<Texture> texOff = getTextureDisabled(group);
		
		if(texOn.isSet())
			button.setTextureEnabled(texOn.getValue());
		if(texOff.isSet())
			button.setTextureDisabled(texOff.getValue());
	}

	private boolean hasAtLeastOneGroup(Component component, String[] groups) {
		for(String group : groups) {
			String[] sub = group.split("\\.");
			
			if(hasAllGroups(component, sub)) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean hasAllGroups(Component component, String[] groups) {
		for(String s : groups) {
			if(!component.hasGroup(s.trim())) {
				return false;
			}
		}
		
		return true;
	}

	/**
	 * Sets the StyleTemplates parent<br>
	 * intern use only
	 * @param parent
	 */
	public void setParent(StyleTemplate parent) {
		this.parent = parent;
	}
	
	/**
	 * Loads a CSS StyleTemplate from a given file
	 * @see StyleTemplateFactory#loadFromFile(File)
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ParsingException
	 */
	public static StyleTemplate loadFromFile(File file) throws IOException, ParsingException {
		return StyleTemplateFactory.loadFromFile(file);
	}

	/**
	 * Loads a CSS StyleTemplate from a given stream
	 * @see StyleTemplateFactory#loadFromStream(InputStream)
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ParsingException
	 */
	public static StyleTemplate loadFromStream(InputStream stream) throws IOException, ParsingException {
		return StyleTemplateFactory.loadFromStream(stream);
	}

	/**
	 * Loads a CSS StyleTemplate from a given package inside the gui jar
	 * @see StyleTemplateFactory#loadDefaultStyle(String)
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ParsingException
	 */
	public static StyleTemplate loadDefaultStyle(String style) throws IOException, ParsingException {
		return StyleTemplateFactory.loadDefaultStyle(style);
	}
}
