package me.cameron.cgrid.utils;

import java.awt.Color;
import java.awt.Point;

import me.cameron.cgrid.Main;

public class Cell {

	Point point;
	CellColor color;
	int lifetime = 0;

	public Cell(Point point, CellColor color) {

		this.point = point;
		this.color = color;
		if (point.x > Main.getWindow().getScreen().getGridSize() || point.y > Main.getWindow().getScreen().getGridSize()
				|| point.x < 0 || point.y < 0)
			remove();
	}

	private void remove() {
		Main.getWindow().getScreen().getChanges().put(false, this);
	}

	public CellColor getColor() {
		return color;
	}

	public Point getPoint() {
		return point;
	}

	public void update() {
		switch (color) {
		case GREEN:
			if (Utils.getCell(point.x + 1, point.y).getColor().equals(CellColor.RED)) {
				if (point.x + 1 <= Utils.getGridSize())
					Utils.changeCell(new Point(point.x + 1, point.y), color, true);
			}
			if (Utils.getCell(point.x, point.y + 1).getColor().equals(CellColor.RED)) {
				if (point.y + 1 <= Utils.getGridSize())
					Utils.changeCell(new Point(point.x, point.y + 1), color, true);
			}
			if (Utils.getCell(point.x - 1, point.y).getColor().equals(CellColor.RED)) {
				if (point.x - 1 >= 0)
					Utils.changeCell(new Point(point.x - 1, point.y), color, true);
			}
			if (Utils.getCell(point.x, point.y - 1).getColor().equals(CellColor.RED)) {
				if (point.y - 1 >= 0)
					Utils.changeCell(new Point(point.x, point.y - 1), color, true);
			}

			if (lifetime > 2)
				remove();
			break;
		case RED:
			if (Utils.getCell(point.x + 1, point.y).getColor().equals(CellColor.REMOVE)) {
				if (point.x + 1 <= Utils.getGridSize())
					Utils.changeCell(new Point(point.x + 1, point.y), color, true);
			}
			if (Utils.getCell(point.x, point.y + 1).getColor().equals(CellColor.REMOVE)) {
				if (point.y + 1 <= Utils.getGridSize())
					Utils.changeCell(new Point(point.x, point.y + 1), color, true);
			}

			if (Utils.getCell(point.x - 1, point.y).getColor().equals(CellColor.REMOVE)) {
				if (point.x - 1 >= 0)
					Utils.changeCell(new Point(point.x - 1, point.y), color, true);
			}
			if (Utils.getCell(point.x, point.y - 1).getColor().equals(CellColor.REMOVE)) {
				if (point.y - 1 >= 0)
					Utils.changeCell(new Point(point.x, point.y - 1), color, true);
			}
			break;
		default:
			break;
		}
		lifetime = lifetime + 1;
	}

}
