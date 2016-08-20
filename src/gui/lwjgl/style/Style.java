package gui.lwjgl.style;

import gui.lwjgl.util.Texture;

public class Style {
	private float[] backgroundColor = {-1, -1, -1, -1};
	private float[] foregroundColor = {-1, -1, -1, -1};
	private float[] textColor = {-1, -1, -1, -1};
	private float[] borderColor = {-1, -1, -1, -1};
	
	private float[] listColor = {-1, -1, -1, -1};
	
	private Texture texture;
	
	public void setBackgroundColor(float r, float g, float b, float a) {
		backgroundColor[0] = r;
		backgroundColor[1] = g;
		backgroundColor[2] = b;
		backgroundColor[3] = a;
	}
	
	public void setForegroundColor(float r, float g, float b, float a) {
		foregroundColor[0] = r;
		foregroundColor[1] = g;
		foregroundColor[2] = b;
		foregroundColor[3] = a;
	}

	public void setTextColor(float r, float g, float b, float a) {
		textColor[0] = r;
		textColor[1] = g;
		textColor[2] = b;
		textColor[3] = a;
	}

	public void setBorderColor(float r, float g, float b, float a) {
		borderColor[0] = r;
		borderColor[1] = g;
		borderColor[2] = b;
		borderColor[3] = a;
	}

	public void setListColor(float r, float g, float b, float a) {
		listColor[0] = r;
		listColor[1] = g;
		listColor[2] = b;
		listColor[3] = a;
	}
	
	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public float[] getBackgroundColor() {
		return backgroundColor;
	}

	public float[] getForegroundColor() {
		return foregroundColor;
	}

	public float[] getTextColor() {
		return textColor;
	}

	public float[] getBorderColor() {
		return borderColor;
	}
	
	public float[] getListColor() {
		return listColor;
	}

	public Texture getTexture() {
		return texture;
	}
}
