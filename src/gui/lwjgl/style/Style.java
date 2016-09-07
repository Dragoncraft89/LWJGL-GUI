package gui.lwjgl.style;

import gui.lwjgl.util.Texture;
import gui.lwjgl.util.Value;

/**
 * This class stores all information that can be obtained for a group
 * @author Florian
 *
 */
public class Style {
	private Value<float[]> backgroundColor = new Value<float[]>();
	private Value<float[]> foregroundColor = new Value<float[]>();
	private Value<float[]> textColor = new Value<float[]>();
	private Value<float[]> borderColor = new Value<float[]>();
	private Value<float[]> listColor = new Value<float[]>();
	
	private Value<Boolean> drawBorder = new Value<Boolean>();
	
	private Value<Texture> texture = new Value<Texture>();
	
	private Value<Float> rotationSpeed = new Value<Float>();
	
	private Value<Texture> checkboxTextureEnabled = new Value<Texture>();
	private Value<Texture> checkboxTextureDisabled = new Value<Texture>();
	
	/**
	 * Sets the background color
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setBackgroundColor(float r, float g, float b, float a) {
		float[] backgroundColor = {r, g, b, a};
		
		setBackgroundColor(backgroundColor);
	}

	/**
	 * Sets the background color
	 * @param color
	 */
	public void setBackgroundColor(float[] color) {
		this.backgroundColor.setValue(color);
	}

	/**
	 * Sets the foreground color
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setForegroundColor(float r, float g, float b, float a) {
		float[] foregroundColor = {r, g, b, a};
		
		setForegroundColor(foregroundColor);
	}

	/**
	 * Sets the foreground color
	 * @param foregroundColor
	 */
	public void setForegroundColor(float[] foregroundColor) {
		this.foregroundColor.setValue(foregroundColor);
	}

	/**
	 * Sets the text color
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setTextColor(float r, float g, float b, float a) {
		float[] textColor = {r, g, b, a};
		
		setTextColor(textColor);
	}

	/**
	 * Sets the text color
	 * @param foregroundColor
	 */
	public void setTextColor(float[] textColor) {
		this.textColor.setValue(textColor);
	}

	/**
	 * Sets the border color
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setBorderColor(float r, float g, float b, float a) {
		float[] borderColor = {r, g, b, a};
		
		setBorderColor(borderColor);
	}

	/**
	 * Sets the border Color
	 * @param borderColor
	 */
	public void setBorderColor(float[] borderColor) {
		this.borderColor.setValue(borderColor);
	}

	/**
	 * Sets the list color for selected elements
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void setListColor(float r, float g, float b, float a) {
		float[] listColor = {r, g, b, a};
		
		setListColor(listColor);
	}

	/**
	 * Sets the list color for selected elements
	 * @param foregroundColor
	 */
	public void setListColor(float[] listColor) {
		this.listColor.setValue(listColor);
	}

	/**
	 * Sets if the border should be drawn
	 * @param drawBorder
	 */
	public void setDrawBorder(boolean drawBorder) {
		this.drawBorder.setValue(drawBorder);
	}
	
	/**
	 * Sets the texture
	 * @param texture
	 */
	public void setTexture(Texture texture) {
		this.texture.setValue(texture);
	}

	/**
	 * Sets the rotation speed for throbbers
	 * @param rotationSpeed
	 */
	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed.setValue(rotationSpeed);
	}
	
	/**
	 * Sets the texture for checked checkboxes
	 * @param tex
	 */
	public void setCheckboxTextureEnabled(Texture tex) {
		this.checkboxTextureEnabled.setValue(tex);
	}
	
	/**
	 * Sets the texture for unchecked checkboxes
	 * @param tex
	 */
	public void setCheckboxTextureDisabled(Texture tex) {
		this.checkboxTextureDisabled.setValue(tex);
	}

	/**
	 * Returns the backgroundColor
	 * @return the backgroundColor
	 */
	public Value<float[]> getBackgroundColor() {
		return backgroundColor;
	}

	/**
	 * Returns the foregroundColor
	 * @return the foregroundColor
	 */
	public Value<float[]> getForegroundColor() {
		return foregroundColor;
	}

	/**
	 * Returns the textColor
	 * @return the textColor
	 */
	public Value<float[]> getTextColor() {
		return textColor;
	}

	/**
	 * Returns the borderColor
	 * @return the borderColor
	 */
	public Value<float[]> getBorderColor() {
		return borderColor;
	}

	/**
	 * Returns the listColor
	 * @return the listColor
	 */
	public Value<float[]> getListColor() {
		return listColor;
	}

	/**
	 * Returns if the border should be drawn
	 * @return if the border should be drawn
	 */
	public Value<Boolean> getDrawBorder() {
		return drawBorder;
	}

	/**
	 * Returns the texture
	 * @return the texture
	 */
	public Value<Texture> getTexture() {
		return texture;
	}
	
	/**
	 * Returns the rotation speed for throbbers
	 * @return the rotation speed for throbbers
	 */
	public Value<Float> getRotationSpeed() {
		return rotationSpeed;
	}

	/**
	 * Returns the texture for checked checkboxes
	 * @return the texture for checked checkboxes
	 */
	public Value<Texture> getCheckboxTextureEnabled() {
		return checkboxTextureEnabled;
	}

	/**
	 * Returns the texture for unchecked checkboxes
	 * @return the texture for unchecked checkboxes
	 */
	public Value<Texture> getCheckboxTextureDisabled() {
		return checkboxTextureDisabled;
	}
}
