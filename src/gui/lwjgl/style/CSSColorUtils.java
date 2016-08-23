package gui.lwjgl.style;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides tools to convert color names, hex color codes, rgb and rgba colors into float[]
 * @author Florian
 *
 */
public class CSSColorUtils {
	private static HashMap<String, float[]> colors = new HashMap<String, float[]>();

	static {
		BufferedReader reader = new BufferedReader(new InputStreamReader(CSSColorUtils.class.getResourceAsStream("/asset/colorNames.txt")));
		
		try {
			String line = "";
			
			while((line = reader.readLine()) != null) {
				String name = line.split(" ")[0];
				String color = line.split(" ")[1];
				
				colors.put(name, CSSColorUtils.decodeHexRGBColor(color));
			}
			
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private CSSColorUtils() {}
	
	/**
	 * Checks if the argument is a valid CSS color name
	 * @param color
	 * @return true if the argument is a valid CSS color name, false otherwise
	 */
	public static boolean isColorName(String color) {
		return colors.containsKey(color.toLowerCase());
	}
	
	/**
	 * Returns the float[] for a CSS color name
	 * @param color
	 * @return float[] r, g, b, a colors ranging from 0 to 1
	 */
	public static float[] getColorByName(String color) {
		return colors.get(color.toLowerCase());
	}

	/**
	 * Returns the float[] for a Hex RGB color, such as #000000
	 * @param color
	 * @return float[] r, g, b, a colors ranging from 0 to 1
	 */
	public static float[] decodeHexRGBColor(String color) {
		float red = Integer.parseInt(color.substring(1, 3), 16) / 255f;
		float green = Integer.parseInt(color.substring(3, 5), 16) / 255f;
		float blue = Integer.parseInt(color.substring(5, 7), 16) / 255f;
		
		return new float[]{red, green, blue, 1};
	}

	/**
	 * Returns the float[] for a Hex RGBA color, such as #000000ff
	 * @param color
	 * @return float[] r, g, b, a colors ranging from 0 to 1
	 */
	public static float[] decodeHexRGBAColor(String color) {
		float red = Integer.parseInt(color.substring(1, 3), 16) / 255f;
		float green = Integer.parseInt(color.substring(3, 5), 16) / 255f;
		float blue = Integer.parseInt(color.substring(5, 7), 16) / 255f;
		float alpha = Integer.parseInt(color.substring(7, 9), 16) / 255f;
		
		return new float[]{red, green, blue, alpha};
	}

	/**
	 * Returns the float[] for a RGB color, such as rgb(100, 100, 100)
	 * @param color
	 * @return float[] r, g, b, a colors ranging from 0 to 1
	 */
	public static float[] decodeRGBColor(String color) {
		String regex = "^rgb\\([ ]*?([0-9]{1,3}),[ ]*?([0-9]{1,3}),[ ]*?([0-9]{1,3})\\)$";
		
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(color);
		m.matches();
		
		float red = Integer.parseInt(m.group(1)) / 255f;
		float green = Integer.parseInt(m.group(2)) / 255f;
		float blue = Integer.parseInt(m.group(3)) / 255f;
		
		return new float[] {red, green, blue, 1};
	}

	/**
	 * Returns the float[] for a RGBA color, such as rgba(100, 100, 100, 255)
	 * @param color
	 * @return float[] r, g, b, a colors ranging from 0 to 1
	 */
	public static float[] decodeRGBAColor(String color) {
		String regex = "^rgba\\([ ]*?([0-9]{1,3}),[ ]*?([0-9]{1,3}),[ ]*?([0-9]{1,3}),[ ]*?([0-9]{1,3})\\)$";
		
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		Matcher m = pattern.matcher(color);
		m.matches();
		
		float red = Integer.parseInt(m.group(1)) / 255f;
		float green = Integer.parseInt(m.group(2)) / 255f;
		float blue = Integer.parseInt(m.group(3)) / 255f;
		float alpha = Integer.parseInt(m.group(4)) / 255f;
		
		return new float[] {red, green, blue, alpha};
	}
}
