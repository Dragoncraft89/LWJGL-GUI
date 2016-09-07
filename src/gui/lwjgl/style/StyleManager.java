package gui.lwjgl.style;

import java.io.IOException;
import java.util.Stack;

import gui.lwjgl.dialogs.DialogManager;

/**
 * This class manages all loaded stylesheets.<br>
 * By default the stylesheet themes.bright is loaded.<br>
 * Other stylesheets can be added to the stack.<br>
 * When a GUI/Dialog is opened, the current stylesheet is used to init the components<br>
 * This process starts at the lowest element of the stack (themes.bright) and is passed to the next element of the stack
 * @author Florian
 *
 */
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

	/**
	 * Returns the last element from the stack without removing it
	 * @return
	 */
	public static StyleTemplate getTemplate() {
		return currentTemplate.peek();
	}
	
	/**
	 * Clears all Stylesheets from the stack except the default one
	 */
	public static void clearTemplates() {
		currentTemplate.clear();
		
		currentTemplate.push(defaultTemplate);
		
		updateGUI();
	}
	
	/**
	 * Pushes a Stylesheet onto the stack
	 * @param template
	 */
	public static void pushTemplate(StyleTemplate template) {
		template.setParent(currentTemplate.peek());
		currentTemplate.push(template);
		
		updateGUI();
	}
	
	/**
	 * Removes the topmost Stylesheet from the stack
	 * @return
	 */
	public static StyleTemplate popTemplate() {
		StyleTemplate template = currentTemplate.pop();
		
		updateGUI();
		
		return template;
	}
	
	private static void updateGUI() {
		DialogManager.updateStyle(getTemplate());
	}
}
