package gui.lwjgl.style;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import gui.lwjgl.components.Component;
import gui.lwjgl.components.List;
import gui.lwjgl.util.Texture;

public class StyleTemplate {
	
	private StyleTemplate parent;
	
	private HashMap<String, Style> data;
	
	public StyleTemplate(StyleTemplate parent, HashMap<String, Style> data) {
		this.parent = parent;
		this.data = data;
	}

	public Value<float[]> getBackgroundColor(String group) {
		return data.get(group).getBackgroundColor();
	}

	public Value<float[]> getForegroundColor(String group) {
		return data.get(group).getForegroundColor();
	}

	public Value<float[]> getTextColor(String group) {
		return data.get(group).getTextColor();
	}

	public Value<float[]> getBorderColor(String group) {
		return data.get(group).getBorderColor();
	}
	
	private Value<float[]> getListColor(String group) {
		return data.get(group).getListColor();
	}

	public Value<Texture> getTexture(String group) {
		return data.get(group).getTexture();
	}
	
	private Value<Boolean> getDrawBorder(String group) {
		return data.get(group).getDrawBorder();
	}
	
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

	public void setParent(StyleTemplate parent) {
		this.parent = parent;
	}
	
	public static StyleTemplate loadFromFile(File file) throws IOException, ParsingException {
		return StyleTemplateFactory.loadFromFile(file);
	}
	
	public static StyleTemplate loadFromStream(InputStream stream) throws IOException, ParsingException {
		return StyleTemplateFactory.loadFromStream(stream);
	}
	
	public static StyleTemplate loadDefaultStyle(String style) throws IOException, ParsingException {
		return StyleTemplateFactory.loadDefaultStyle(style);
	}
}
