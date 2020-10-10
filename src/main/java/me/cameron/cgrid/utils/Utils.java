package me.cameron.cgrid.utils;

import java.awt.Point;

import me.cameron.cgrid.Main;

public class Utils {
	
	public static Cell getCell(int x, int y) {
		if(Main.getWindow().getScreen().getGridInfo().get(new Point(x,y)) == null) {
			return new Cell(new Point(x,y),CellColor.REMOVE);
		}
		return Main.getWindow().getScreen().getGridInfo().get(new Point(x,y));
	}

	public static void changeCell(Point point, CellColor color, boolean b) {
		Main.getWindow().getScreen().getChanges().put(b, new Cell(point,color));
	}

	public static int getGridSize() {
		return (int) Main.getWindow().getScreen().getGridSize();
	}

}
