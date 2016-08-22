package gui.lwjgl.style;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class StyleTemplateFactory {
	private StyleTemplateFactory() {}

	public static StyleTemplate loadFromFile(File file) throws IOException, ParsingException {
		return loadFromStream(new FileInputStream(file));
	}

	public static StyleTemplate loadFromStream(InputStream stream) throws IOException, ParsingException {
		CSSParser parser = new CSSParser(stream);
		HashMap<String, Style> rules = parser.parse();
		stream.close();
		return new StyleTemplate(null, rules);
	}

	public static StyleTemplate loadDefaultStyle(String style) throws IOException, ParsingException {
		return loadFromStream(StyleTemplateFactory.class.getResourceAsStream(style.replace('.', '/')));
	}
}
