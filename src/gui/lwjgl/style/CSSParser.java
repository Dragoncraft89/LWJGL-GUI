package gui.lwjgl.style;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

public class CSSParser {
	
	private InputStream stream;

	public CSSParser(InputStream stream) {
		this.stream = stream;
	}
	
	public HashMap<String, Style> parse() throws ParsingException, IOException {
		HashMap<String, Style> rules = new HashMap<String, Style>();
		
		int depth = 0;
		int line = 1;
		
		String selectorName = "";
		String attribName = "";
		String value = "";
		
		Style style = null;
		//The side we're currently parsing, false = left; true = right
		boolean side = false;
		
		// The character we're currently processing
		int c = 0;
		while((c = stream.read()) != -1) {
			switch(c) {
			case '{':{
						if(depth == 1) {
							throwExceptionOnToken(c, line);
						}
						depth++;
						if(depth == 1) {
							style = new Style();
						}
						break;
					 }
			case '}':{
						if(depth == 0) {
							throwExceptionOnToken(c, line);
						}
						depth--;
						if(depth == 0) {
							rules.put(selectorName, style);
							selectorName = "";
						}
						break;
					 }
			case ';':{
						if(depth == 0) {
							throwExceptionOnToken(c, line);
						}
						insertAttrib(style, attribName, value);
						attribName = "";
						side = false;
						break;
					 }
			case ':':side = true;break;
			case '\n':line++; //line gets incremented and \n is processed in default:
			default:{
						if(depth == 0) {
							selectorName += c;
						}
						if(depth == 1) {
							if(side) {
								//We're reading the value
								value += c;
							} else {
								//We're reading the attribute name
								attribName += c;
							}
						}
					}
			}
		}
		
		return rules;
	}
	
	private void insertAttrib(Style style, String attribName, String value) throws ParsingException{
		attribName = attribName.trim();
		value = value.trim();
		
		switch(attribName) {
		case "background-color":style.setBackgroundColor(parseColor(value));break;
		case "foreground-color":style.setForegroundColor(parseColor(value));break;
		case "text-color":style.setTextColor(parseColor(value));break;
		case "border-color":style.setBorderColor(parseColor(value));break;
		case "list-color":style.setListColor(parseColor(value));
		case "draw-border":style.setDrawBorder(asBoolean(value));break;
		}
	}

	private void throwExceptionOnToken(int c, int line) throws ParsingException{
		throw new ParsingException("Invalid token: \'" + c + "\' on line " + line);
	}
	
	private boolean asBoolean(String s) throws ParsingException {
		if(s.toLowerCase().equals("true"))
			return true;
		
		if(s.toLowerCase().equals("false"))
			return false;
		
		throw new ParsingException("Boolean expected, got \'" + s + "\'");
	}
	
	private float[] parseColor(String color) throws ParsingException{
		if(CSSColorUtils.isColorName(color)) {
			return CSSColorUtils.getColorByName(color);
		}
		
		if(color.matches("^#[0-f]{6}$")) {
			return CSSColorUtils.decodeHexRGBColor(color);
		}
		
		if(color.matches("^#[0-f]{8}$")) {
			return CSSColorUtils.decodeHexRGBAColor(color);
		}
		
		if(color.toLowerCase().matches("^rgb\\(([ ]*?[0-9]{1,3},){2}([ ]*?[0-9]{1,3})\\)$")) {
			return CSSColorUtils.decodeRGBColor(color);
		}
		
		if(color.toLowerCase().matches("^rgba\\(([ ]*?[0-9]{1,3},){3}([ ]*?[0-9]{1,3})\\)$")) {
			return CSSColorUtils.decodeRGBAColor(color);
		}
		
		throw new ParsingException(color + " is no valid color value");
	}
}
