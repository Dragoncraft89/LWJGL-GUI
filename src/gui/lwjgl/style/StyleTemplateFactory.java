package gui.lwjgl.style;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class StyleTemplateFactory {
	private StyleTemplateFactory() {}

	/**
	 * Loads a CSS StyleTemplate from a given file
	 * @see StyleTemplate#loadFromFile(File)
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ParsingException
	 */
	public static StyleTemplate loadFromFile(File file) throws IOException, ParsingException {
		return loadFromStream(new FileInputStream(file));
	}

	/**
	 * Loads a CSS StyleTemplate from a given stream
	 * @see StyleTemplate#loadFromStream(InputStream)
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ParsingException
	 */
	public static StyleTemplate loadFromStream(InputStream stream) throws IOException, ParsingException {
		CSSParser parser = new CSSParser(stream);
		HashMap<String, Style> rules = parser.parse();
		stream.close();
		return new StyleTemplate(null, rules);
	}

	/**
	 * Loads a CSS StyleTemplate from a given package inside the gui jar
	 * @see StyleTemplate#loadDefaultStyle(String)
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws ParsingException
	 */
	public static StyleTemplate loadDefaultStyle(String style) throws IOException, ParsingException {
		String path = "/asset/" + style.replace('.', '/') + "/style.css";
		return loadFromStream(StyleTemplateFactory.class.getResourceAsStream(path));
	}
}
