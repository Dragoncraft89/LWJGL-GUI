package test.components;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import gui.lwjgl.components.*;
import gui.lwjgl.dialogs.Dialog;
import gui.lwjgl.dialogs.DialogManager;
import gui.lwjgl.listener.ChangeEventListener;
import gui.lwjgl.listener.ClickEventListener;
import gui.lwjgl.style.ParsingException;
import gui.lwjgl.style.StyleManager;
import gui.lwjgl.style.StyleTemplate;
import gui.lwjgl.util.Texture;
import test.Test;

public class ComponentTest extends Test {

	public ComponentTest() {
		
		GUI overlay = new GUI();
		
		Panel center = new Panel(Display.getWidth() / 2, Display.getHeight() / 2, 700, 500);
		
		Button button = new Button(110, 35, 200, 50, "themes.bright");
		
		button.addClickEventListener(new ClickEventListener() {
			private int i;
			private String[] themes = {"themes.bright", "themes.dark"};
			@Override
			public void action(Component component, int x, int y, int type) {
				if(type != RELEASED)
					return;
				
				try {
					StyleManager.pushTemplate(StyleTemplate.loadDefaultStyle(themes[++i % themes.length]));
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParsingException e) {
					e.printStackTrace();
				}
				
				button.setText(themes[i % themes.length]);
			}
			
		});
		
		ListElement l = new ListElement(){

			@Override
			public Texture getIcon() {
				try {
					return Texture.createTextureFromFile("test.png");
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			public String getName() {
				return "Test";
			}};
		
		ListElement[] elements = new ListElement[]{l,l,l,l,l,l,l,l,l,l,l,l,l,l,l};
		Label label = new Label(320, 35, 200, 50, "Label");
		List list = new List(110, 145, 200, 150, elements);
		MultilineLabel multiline = new MultilineLabel(320, 145, 200, 150, "1\n2\n3\n4\n5\n6\n7\n8");
		ProgressBar progressbar = new ProgressBar(110, 305, 200, 150, true, 0, 100);
		Slider slider = new Slider(320, 305, 200, 150, true, 0, 100);
		slider.addChangeEventListener(new ChangeEventListener() {

			@Override
			public void action(Component c) {
				progressbar.setValue(slider.getValue());
			}
			
		});
		TextField textfield = new TextField(110, 415, 200, 50);
		Throbber throbber = new Throbber(320, 415, 50, 50);
		Checkbox checkbox = new Checkbox(530, 35, 200, 50, "Checkbox");
		
		RadioButton r1 = new RadioButton(530, 95, 200, 50, "Radio1");
		RadioButton r2 = new RadioButton(530, 155, 200, 50, "Radio2");
		RadioButton r3 = new RadioButton(530, 215, 200, 50, "Radio3");
		
		RadioButtonGroup group = new RadioButtonGroup();
		group.addToGroup(r1);
		group.addToGroup(r2);
		group.addToGroup(r3);
		
		center.addComponent(button);
		center.addComponent(label);
		center.addComponent(list);
		center.addComponent(multiline);
		center.addComponent(progressbar);
		center.addComponent(slider);
		center.addComponent(textfield);
		center.addComponent(throbber);
		center.addComponent(checkbox);
		
		center.addComponent(r1);
		center.addComponent(r2);
		center.addComponent(r3);
		
		overlay.addComponent(center);
		
		DialogManager.setOverlayDefault(overlay);
		
		Dialog dialog1 = new Dialog();
		
		Panel contentPane = new Panel(Display.getWidth() / 2, Display.getHeight() / 2, 200, 100);
		Window window = new Window(contentPane, "Test");
		
		dialog1.addComponent(window);
		
		DialogManager.openDialogDefault(dialog1);

		Dialog dialog2 = new Dialog();
		
		Panel contentPane2 = new Panel(Display.getWidth() / 2, Display.getHeight() / 2, 200, 100);
		Window window2 = new Window(contentPane2, "Test");
		
		dialog2.addComponent(window2);
		
		DialogManager.openDialogDefault(dialog2);
		
		renderLoop();
	}
	
	@Override
	public void render() {
	}

	public static void main(String[] args) {
		new ComponentTest();
	}
}
