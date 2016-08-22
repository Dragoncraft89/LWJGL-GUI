package test.components;

import java.io.IOException;

import org.lwjgl.opengl.Display;

import gui.lwjgl.components.*;
import gui.lwjgl.dialogs.Dialog;
import gui.lwjgl.dialogs.DialogManager;
import gui.lwjgl.util.Texture;
import test.Test;

public class ComponentTest extends Test {

	public ComponentTest() {
		GUI overlay = new GUI();
		
		Panel center = new Panel(Display.getWidth() / 2, Display.getHeight() / 2, 700, 500);
		
		Button button = new Button(110, 35, 200, 50, "Button");
		
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
		List list = new List(110, 145, 200, 150, 5, elements);
		MultilineLabel multiline = new MultilineLabel(320, 145, 200, 150, "1\n2\n3\n4\n5\n6\n7\n8");
		ProgressBar progressbar = new ProgressBar(110, 305, 200, 150, true, 0, 2);
		progressbar.setValue(1);
		Slider slider = new Slider(320, 305, 200, 150, true, 0, 100);
		TextField textfield = new TextField(110, 415, 200, 50);
		Throbber throbber = new Throbber(320, 415, 50, 50);
		
		center.addComponent(button);
		center.addComponent(label);
		center.addComponent(list);
		center.addComponent(multiline);
		center.addComponent(progressbar);
		center.addComponent(slider);
		center.addComponent(textfield);
		center.addComponent(throbber);
		
		overlay.addComponent(center);
		
		DialogManager.setOverlayDefault(overlay);
		
		Dialog dialog = new Dialog();
		
		Panel contentPane = new Panel(Display.getWidth() / 2, Display.getHeight() / 2, 200, 100);
		Window window = new Window(contentPane, "Test");
		
		dialog.addComponent(window);
		
		DialogManager.openDialogDefault(dialog);
		
		renderLoop();
	}
	
	@Override
	public void render() {
	}

	public static void main(String[] args) {
		new ComponentTest();
	}
}
