package me.cameron.cgrid;

public class Main {
	
	private static Window window;
	
	public static boolean running = true;
	
	public static void main(String[] args) {
		window = new Window();
	}
	
	public static Window getWindow() {
		return window;
	}

}
