package gui.lwjgl.style;

import java.util.HashMap;

import gui.lwjgl.util.Texture;

public class StyleTemplate {
	
	private HashMap<String, Style> data = new HashMap<String, Style>();

	public float[] getBackgroundColor(String group) {
		return data.get(group).getBackgroundColor();
	}

	public float[] getForegroundColor(String group) {
		return data.get(group).getForegroundColor();
	}

	public float[] getTextColor(String group) {
		return data.get(group).getTextColor();
	}

	public float[] getBorderColor(String group) {
		return data.get(group).getBorderColor();
	}

	public Texture getTexture(String group) {
		return data.get(group).getTexture();
	}

}
