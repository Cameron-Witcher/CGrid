package me.cameron.cgrid.utils;

import java.awt.Point;

public enum Direction {

	UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0), UP_LEFT(-1, -1), UP_RIGHT(1, -1), DOWN_LEFT(-1, 1),
	DOWN_RIGHT(1, 1);

	int dx;
	int dy;

	Direction(int dx, int dy) {
		this.dx = dx;
		this.dy = dy;
	}

	public int getDeltaX() {
		return dx;
	}

	public int getDeltaY() {
		return dy;
	}

	public Point getPoint(Point point) {
		return new Point(point.x + dx, point.y + dy);
	}

	private boolean check(Point point, Direction d) {
		switch (d) {
		case UP:
			return point.y + dy >= 0;
		case DOWN:
			return point.y + dy <= Utils.getGridSize();
		case LEFT:
			return point.x + dx >= 0;
		case RIGHT:
			return point.x + dx <= Utils.getGridSize();
		default:
			return false;

		}
	}

	public boolean isAvalible(Point point) {

		switch (this) {
		case UP:
		case DOWN:
		case LEFT:
		case RIGHT:
			return check(point, this);
		case UP_LEFT:
			return check(point, UP) && check(point, LEFT);
		case UP_RIGHT:
			return check(point, UP) && check(point, RIGHT);
		case DOWN_LEFT:
			return check(point, DOWN) && check(point, LEFT);
		case DOWN_RIGHT:
			return check(point, DOWN) && check(point, RIGHT);
		default:
			return false;
		}

	}

}
