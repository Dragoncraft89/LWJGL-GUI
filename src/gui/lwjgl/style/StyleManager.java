package gui.lwjgl.style;

import java.io.IOException;
import java.util.Stack;

public class StyleManager {
	
	private static StyleTemplate defaultTemplate;
	
	private static Stack<StyleTemplate> currentTemplate;
	
	static {
		currentTemplate = new Stack<StyleTemplate>();
		
		try {
			defaultTemplate = StyleTemplateFactory.loadDefaultStyle("themes.bright");
			
			currentTemplate.push(defaultTemplate);
		} catch (IOException e) {
			System.err.println("Failed to load default style file");
			e.printStackTrace();
		} catch (ParsingException e) {
			System.err.println("Failed to load default style file");
			e.printStackTrace();
		}
	}

	public static StyleTemplate getTemplate() {
		return currentTemplate.peek();
	}
	
	public static void clearTemplates() {
		currentTemplate.clear();
		
		currentTemplate.push(defaultTemplate);
	}
	
	public static void pushTemplate(StyleTemplate template) {
		template.setParent(currentTemplate.peek());
		currentTemplate.push(template);
	}
	
	public static StyleTemplate popTemplate() {
		return currentTemplate.pop();
	}
}
