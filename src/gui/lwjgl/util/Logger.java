package gui.lwjgl.util;

public class Logger {
	private Logger() {
		
	}
	
	public static void log(String s) {
		System.out.println("[LWJGL-GUI]" + s);
	}
}
