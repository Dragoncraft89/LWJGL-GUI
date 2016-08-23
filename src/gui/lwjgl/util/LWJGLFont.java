package gui.lwjgl.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import static org.lwjgl.opengl.GL11.*;

/**
 * This class converts every {@link java.awt.Font} into a usable format for lwjgl
 * @author Florian
 *
 */
public final class LWJGLFont {

	private Font font;
	private FontMetrics metrics;
	
	private static int HEIGHT_PADDING = 10;

	private static char[] glyphs = { '?', ',', '.', '-', ';', ':', '_', '!', '"',
			'§', '\'', '$', '%', '&', '/', '(', ')', '=', 'ß', '\\', '{', '}', '´', '`',
			'²', '³', '€', 'µ', '<', '>', '|', '@', '*', '+', '~', '#', '^',
			'°', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', ' ', 'Ä',
			'Ö', 'Ü', 'ä', 'ö', 'ü'};

	private Texture[] glyphTex = new Texture[glyphs.length];

	/**
	 * Constructor
	 * @param font
	 */
	public LWJGLFont(Font font) {
		this.font = font;
		BufferedImage temp = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
		metrics = temp.getGraphics().getFontMetrics(font);
		for (int i = 0; i < glyphs.length; i++) {
			final BufferedImage img = new BufferedImage(metrics.charWidth(glyphs[i]), metrics.getHeight() + HEIGHT_PADDING, BufferedImage.TYPE_4BYTE_ABGR);
			Graphics g = img.getGraphics();
			g.setColor(Color.white);
			g.setFont(font);
			g.drawString("" + glyphs[i], 0, img.getHeight() - 10);
			glyphTex[i] = Texture.createTextureFromImage(img);
		}
	}

	/**
	 * Draws the given string to the given coordinates as top left corner with the given color
	 * @param string
	 * @param x
	 * @param y
	 * @param r
	 * @param g
	 * @param b
	 * @param a
	 */
	public void drawString(String string, int x, int y, float r, float g, float b, float a) {
		for (char c : string.toCharArray()) {
			int charwidth = metrics.charWidth(c);
			int charheight = metrics.getHeight() + 10;
			Texture tex = glyphTex[getCharIndex(c)];
			glBindTexture(GL_TEXTURE_2D, tex.getID());
			glColor4f(r, g, b, a);
			glBegin(GL_QUADS);
			glTexCoord2f(0, 0);
			glVertex2f(x, y);
			glTexCoord2f(1, 0);
			glVertex2f(x + charwidth, y);
			glTexCoord2f(1, 1);
			glVertex2f(x + charwidth, y + charheight);
			glTexCoord2f(0, 1);
			glVertex2f(x, y + charheight);
			glEnd();
			x += charwidth;
		}
		
		glBindTexture(GL_TEXTURE_2D, 0);
	}
	
	/**
	 * Draws the given string to the given coordinates as top left corner with black color
	 * @param string
	 * @param x
	 * @param y
	 */
	public void drawString(String string, int x, int y) {
		drawString(string, x, y, 0, 0, 0, 1);
	}
	

	private int getCharIndex(char c) {
		for (int i = 0; i < glyphs.length; i++) {
			if (glyphs[i] == c) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * Returns the {@link java.awt.Font} used to create this font
	 * @return the {@link java.awt.Font} used to create this font
	 */
	public Font getFont() {
		return font;
	}

	/**
	 * Returns a strings width
	 * @param string
	 * @return the width
	 */
	public int getWidth(String string) {
		return metrics.stringWidth(string);
	}
	
	/**
	 * Returns a string height
	 * @param string
	 * @return the height
	 */
	public int getHeight(String string) {
		return metrics.getHeight() + HEIGHT_PADDING;
	}

	/**
	 * Returns a string height
	 * @param string
	 * @return the height
	 */
	public int getHeight() {
		return metrics.getHeight() + HEIGHT_PADDING;
	}

	/**
	 * Gets a characters max width
	 * @return
	 */
	public int getMaxWidth() {
		return metrics.getMaxAdvance();
	}
}
