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

	public static Cell changeCell(Point point, CellColor color, boolean b) {
		Cell c = new Cell(point,color);
		Main.getWindow().getScreen().getChanges().put(c, b);
		return c;
	}

	public static int getGridSize() {
		return (int) Main.getWindow().getScreen().getGridSize();
	}

}
