package gui.lwjgl.style;

import java.io.IOException;
import java.util.Stack;

public class StyleManager {
	
	private static StyleTemplate defaultTemplate;
	
	static {
		try {
			defaultTemplate = StyleTemplateFactory.loadDefaultStyle("themes.bright");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParsingException e) {
			e.printStackTrace();
		}
	}
	
	private static Stack<StyleTemplate> currentTemplate;

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
