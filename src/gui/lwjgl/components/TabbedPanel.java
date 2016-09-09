package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import gui.lwjgl.listener.ClickEventListener;
import gui.lwjgl.style.StyleManager;
import gui.lwjgl.style.StyleTemplate;

/**
 * 
 * This is a tabbed panel, therefore for every tab name there exists a panel which will be shown when the tab is selected<br>
 * All components assigned to this panel are placed relatively to this component,<br>
 * therefore (0|0) is the top left corner of the panel, below the tab area<br>
 * <br>
 * default groups:<br>
 * all<br>
 * panels<br>
 * @author Florian
 *
 */
public class TabbedPanel extends Panel {
	
	private static final int TAB_HEIGHT = 30;
	
	private class TabbedPanelListener implements ClickEventListener {
		private int index;
		private TabbedPanel panel;
		
		public TabbedPanelListener(TabbedPanel panel, int index) {
			this.index = index;
			this.panel = panel;
		}

		@Override
		public void action(Component component, int x, int y, int type) {
			panel.setSelectedTab(index);
		}
		
	}
	
	private int activeTab;
	
	private Panel[] panels;

	/**
	 * Constructor
	 * @param centerX
	 * @param centerY
	 * @param sizeX
	 * @param sizeY
	 */
	public TabbedPanel(int centerX, int centerY, int sizeX, int sizeY) {
		super(centerX, centerY, sizeX, sizeY);
		
		panels = new Panel[0];
	}
	
	public void addToGroup(String group) {
		super.addToGroup(group);
		
		if(panels != null)
			for(Panel p:panels) {
				p.addToGroup(group);
			}
	}

	@Override
	public void paint(float delta) {
		super.paint(delta);
		
		glPushMatrix();
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2 + TAB_HEIGHT, 0);
		
		panels[activeTab].paint(delta);
		
		glPopMatrix();
	}
	
	/**
	 * Sets the selected tab index
	 * @throws IndexOutOfBoundsException when the index is greater than the biggest tab index
	 * @param tab
	 */
	public void setSelectedTab(int tab) {
		if(tab >= panels.length)
			throw new IndexOutOfBoundsException("Tab index " + tab + " is out of bounds (" + (panels.length - 1) + ")");
		this.activeTab = tab;
	}
	
	/**
	 * Sets the tabs which this component manages and displays
	 * @throws IllegalArgumentException when tabNames.length != panels.length
	 * @param tabNames
	 * @param panels
	 */
	public void setTabs(String[] tabNames, Panel[] panels) {
		if(tabNames.length != panels.length)
			throw new IllegalArgumentException("Amount of elements in tabNames and panels is not equal");
		
		clearComponents();
		
		this.panels = panels;
		
		int padding = 10;
		int sizePerButton = sizeX / tabNames.length;
		
		int x = 0;
		
		for(int i = 0; i < tabNames.length; i++) {
			int width = Math.min(sizePerButton, font.getWidth(tabNames[i]) + padding);
			
			Button b = new Button(x + width / 2, TAB_HEIGHT / 2, width, TAB_HEIGHT, tabNames[i]);
			b.addToGroup("tab");
			b.addClickEventListener(new TabbedPanelListener(this, i));
			
			addComponent(b);
			x += width;
		}
		
		loadTemplate(StyleManager.getTemplate());
	}
	
	public void loadTemplate(StyleTemplate style){
		style.load(this);
		
		for(Panel p:panels) {
			p.loadTemplate(style);
		}
		
		for(Component c:components) {
			c.loadTemplate(style);
		}
	}

}