package gui.lwjgl.components;

import static org.lwjgl.opengl.GL11.*;

import gui.lwjgl.listener.ClickEventListener;
import gui.lwjgl.style.StyleManager;
import gui.lwjgl.style.StyleTemplate;
import gui.lwjgl.util.LWJGLFont;

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
	
	private int tabHeight;
	
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
		glTranslatef(centerX - sizeX / 2, centerY - sizeY / 2 + tabHeight, 0);
		
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
		
		TabGroup group = new TabGroup();
		
		for(int i = 0; i < tabNames.length; i++) {
			int width = Math.min(sizePerButton, font.getWidth(tabNames[i]) + padding);
			
			Tab b = new Tab(x + width / 2, tabHeight / 2, width, tabHeight, tabNames[i]);
			group.addToGroup(b);
			b.addClickEventListener(new TabbedPanelListener(this, i));
			
			if(i == 0) {
				b.setSelected(true);
			}
			
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

	public void setFont(LWJGLFont font) {
		super.setFont(font);
		
		tabHeight = font.getHeight() + 2;
		
		for(Component c:components) {
			c.moveTo(c.centerX, tabHeight / 2);
			c.resize(c.sizeX, tabHeight);
		}
	}
	
	@Override
	public boolean keyUp(GUI gui, int eventKey, char character) {
		if (panels[activeTab].keyUp(gui, eventKey, character))
			return true;

		for(Component c:components) {
			if(c instanceof Tab) {
				if(c.keyUp(gui, eventKey, character))
					return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean keyDown(GUI gui, int eventKey, char character) {
		if (panels[activeTab].keyDown(gui, eventKey, character))
				return true;

		for(Component c:components) {
			if(c instanceof Tab) {
				if(c.keyDown(gui, eventKey, character))
					return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean mouseDown(GUI gui, int button, int x, int y) {
		int relativeX = x - (centerX - sizeX / 2);
		int relativeY = y + (centerY - sizeY / 2);

		for(Component c:components) {
			if(c instanceof Tab) {
				if(c.mouseDown(gui, button, relativeX, relativeY)) {
					return true;
				}
			}
		}
		
		if (panels[activeTab].mouseDown(gui, button, relativeX, relativeY + tabHeight))
				return true;

		return isInComponent(x, y);
	}

	@Override
	public boolean mouseUp(GUI gui, int button, int x, int y) {
		int relativeX = x - (centerX - sizeX / 2);
		int relativeY = y + (centerY - sizeY / 2);

		for(Component c:components) {
			if(c instanceof Tab) {
				if(c.mouseUp(gui, button, relativeX, relativeY))
					return true;
			}
		}
		
		if (panels[activeTab].mouseUp(gui, button, relativeX, relativeY + tabHeight))
			return true;
		
		return isInComponent(x, y);
	}

	@Override
	public boolean mouseWheelChanged(GUI gui, int mouseWheel, int x, int y) {
		int relativeX = x - (centerX - sizeX / 2);
		int relativeY = y + (centerY - sizeY / 2);

		for(Component c:components) {
			if(c instanceof Tab) {
				if(c.mouseWheelChanged(gui, mouseWheel, relativeX, relativeY))
					return true;
			}
		}

		if (panels[activeTab].mouseWheelChanged(gui, mouseWheel, relativeX, relativeY + tabHeight))
			return true;
		
		return false;
	}

	@Override
	public boolean mouseMoved(GUI gui, int x, int y, int dX, int dY) {
		int relativeX = x - (centerX - sizeX / 2);
		int relativeY = y + (centerY - sizeY / 2);
		
		for(Component c:components) {
			if(c instanceof Tab) {
				if(c.mouseMoved(gui, relativeX, relativeY, dX, dY))
					return true;
			}
		}
		
		if (panels[activeTab].mouseMoved(gui, relativeX, relativeY + tabHeight, dX, dY))
			return true;
		
		return false;
	}
}
