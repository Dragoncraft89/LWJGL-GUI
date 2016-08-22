package gui.lwjgl.style;

import gui.lwjgl.util.Texture;

public class Style {
	private Value<float[]> backgroundColor = new Value<float[]>();
	private Value<float[]> foregroundColor = new Value<float[]>();
	private Value<float[]> textColor = new Value<float[]>();
	private Value<float[]> borderColor = new Value<float[]>();
	private Value<float[]> listColor = new Value<float[]>();
	
	private Value<Boolean> drawBorder = new Value<Boolean>();
	
	private Value<Texture> texture = new Value<Texture>();
	
	public void setBackgroundColor(float r, float g, float b, float a) {
		float[] backgroundColor = {r, g, b, a};
		
		setBackgroundColor(backgroundColor);
	}
	
	public void setBackgroundColor(float[] color) {
		this.backgroundColor.setValue(color);
	}
	
	public void setForegroundColor(float r, float g, float b, float a) {
		float[] foregroundColor = {r, g, b, a};
		
		setForegroundColor(foregroundColor);
	}

	public void setForegroundColor(float[] foregroundColor) {
		this.foregroundColor.setValue(foregroundColor);
	}

	public void setTextColor(float r, float g, float b, float a) {
		float[] textColor = {r, g, b, a};
		
		setTextColor(textColor);
	}

	public void setTextColor(float[] textColor) {
		this.textColor.setValue(textColor);
	}

	public void setBorderColor(float r, float g, float b, float a) {
		float[] borderColor = {r, g, b, a};
		
		setBorderColor(borderColor);
	}

	public void setBorderColor(float[] borderColor) {
		this.borderColor.setValue(borderColor);
	}

	public void setListColor(float r, float g, float b, float a) {
		float[] listColor = {r, g, b, a};
		
		setListColor(listColor);
	}
	
	public void setListColor(float[] listColor) {
		this.listColor.setValue(listColor);
	}

	public void setDrawBorder(boolean drawBorder) {
		this.drawBorder.setValue(drawBorder);
	}
	
	public void setTexture(Texture texture) {
		this.texture.setValue(texture);
	}

	public Value<float[]> getBackgroundColor() {
		return backgroundColor;
	}

	public Value<float[]> getForegroundColor() {
		return foregroundColor;
	}

	public Value<float[]> getTextColor() {
		return textColor;
	}

	public Value<float[]> getBorderColor() {
		return borderColor;
	}
	
	public Value<float[]> getListColor() {
		return listColor;
	}

	public Value<Boolean> getDrawBorder() {
		return drawBorder;
	}

	public Value<Texture> getTexture() {
		return texture;
	}
}
