package me.cameron.cgrid.utils;

import java.awt.Color;

public enum CellColor {

	RED(Color.RED), GREEN(Color.GREEN), REMOVE(Color.WHITE), BLACK(Color.BLACK), BLUE(Color.BLUE);

	Color color;

	CellColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

}
