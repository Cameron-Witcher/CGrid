package me.cameron.cgrid.utils;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;

import me.cameron.cgrid.Main;

public class Cell {

	Point point;
	CellColor color;
	int lifetime = 0;
	List<Cell> changedCells = new ArrayList<>();
	Direction[] allowedDirections;

	public Cell(Point point, CellColor color) {

		this.point = point;
		setColor(color);
		if (point.x > Main.getWindow().getScreen().getGridSize() || point.y > Main.getWindow().getScreen().getGridSize()
				|| point.x < 0 || point.y < 0)
			remove();

	}

	private void setColor(CellColor color) {
		this.color = color;
		switch (color) {
		case BLACK:
		case REMOVE:
			allowedDirections = new Direction[] {};
			break;
		case BLUE:
			allowedDirections = new Direction[] { Direction.LEFT, Direction.DOWN, Direction.RIGHT };
			break;
		case GREEN:
			allowedDirections = Direction.values();
			break;
		case RED:
		default:
			allowedDirections = new Direction[] { Direction.UP, Direction.LEFT, Direction.DOWN, Direction.RIGHT };
			break;
		}
	}

	private void remove() {
		Main.getWindow().getScreen().getChanges().put(this, false);
	}

	public CellColor getColor() {
		return color;
	}

	public Point getPoint() {
		return point;
	}

	public void update() {
		if (lifetime >= 0) {
			for (Direction d : allowedDirections) {
				Point p = d.getPoint(point);
				if (color.equals(CellColor.RED)) {
					if (Utils.getCell(p.x, p.y).getColor().equals(CellColor.REMOVE)) {
						if (d.isAvalible(point)) {
							changedCells.add(Utils.changeCell(p, color, true));
						}

					}
				}
				if (color.equals(CellColor.GREEN)) {
					if (Utils.getCell(p.x, p.y).getColor().equals(CellColor.RED)
							|| Utils.getCell(p.x, p.y).getColor().equals(CellColor.BLUE)) {
						if (d.isAvalible(point)) {
							changedCells.add(Utils.changeCell(p, color, true));
						}
					}
				}
//				if (color.equals(CellColor.BLUE)) {
//					if (!Utils.getCell(p.x, p.y).getColor().equals(CellColor.BLACK)) {
//						if (d.isAvalible(point)) {
//							if (new Random().nextBoolean()) {
//								changedCells.add(Utils.changeCell(p, color, true));
//
//								break;
//							}
//						}
//					}
//				}
			}
			if (color.equals(CellColor.BLUE)) {
				Direction d = allowedDirections[new Random().nextInt(allowedDirections.length)];
				Point p = d.getPoint(point);
				if (!Utils.getCell(p.x, p.y).getColor().equals(CellColor.BLACK)
						|| !Utils.getCell(p.x, p.y).getColor().equals(CellColor.BLUE)) {
					if (d.isAvalible(point)) {
						changedCells.add(Utils.changeCell(p, color, true));
					} else {
						changedCells.add(Utils.changeCell(point, color, true));
					}
				} else {
					changedCells.add(Utils.changeCell(point, color, true));
				}
			}
			changedCells.clear();
			switch (color) {
			case GREEN:
				if (lifetime > 1)
					remove();
				break;
			case BLUE:
				if (lifetime >= 0)
					remove();
				break;
			case RED:
				if (lifetime > new Random().nextInt(50) + 20)
					Utils.changeCell(point, CellColor.BLACK, true);
				break;
			case BLACK:
				if (lifetime > new Random().nextInt(100) + 100)
					remove();
				break;
			default:
				break;

			}

//			switch (color) {
////			case BLUE:
////				if (!Utils.getCell(point.x - 1, point.y - 1).getColor().equals(CellColor.BLACK) && !Utils.getCell(point.x - 1, point.y - 1).getColor().equals(CellColor.GREEN)) {
////					if (point.x - 1 >= 0 && point.y - 1 >= 0)
////						changedCells.add(Utils.changeCell(new Point(point.x - 1, point.y - 1), color, true));
////				}
////				if (!Utils.getCell(point.x - 1, point.y + 1).getColor().equals(CellColor.BLACK) && !Utils.getCell(point.x - 1, point.y + 1).getColor().equals(CellColor.GREEN)) {
////					if (point.x - 1 >= 0 && point.y + 1 <= Utils.getGridSize())
////						changedCells.add(Utils.changeCell(new Point(point.x - 1, point.y + 1), color, true));
////				}
//////				if (Utils.getCell(point.x + 1, point.y + 1).getColor().equals(CellColor.REMOVE)) {
//////					if (point.x + 1 <= Utils.getGridSize() && point.y + 1 <= Utils.getGridSize())
//////						changedCells.add(Utils.changeCell(new Point(point.x + 1, point.y + 1), color, true));
//////				}
//////				if (Utils.getCell(point.x + 1, point.y - 1).getColor().equals(CellColor.REMOVE)) {
//////					if (point.x + 1 <= Utils.getGridSize() && point.y - 1 >= 0)
//////						changedCells.add(Utils.changeCell(new Point(point.x + 1, point.y - 1), color, true));
//////				}
////				if (lifetime > 2)
////					remove();
////				break;
//			case GREEN:
////				if (Utils.getCell(point.x + 1, point.y).getColor().equals(CellColor.RED)) {
////					if (point.x + 1 <= Utils.getGridSize())
////						changedCells.add(Utils.changeCell(new Point(point.x + 1, point.y), color, true));
////				}
////				if (Utils.getCell(point.x, point.y + 1).getColor().equals(CellColor.RED)) {
////					if (point.y + 1 <= Utils.getGridSize())
////						changedCells.add(Utils.changeCell(new Point(point.x, point.y + 1), color, true));
////				}
////				if (Utils.getCell(point.x - 1, point.y).getColor().equals(CellColor.RED)) {
////					if (point.x - 1 >= 0)
////						changedCells.add(Utils.changeCell(new Point(point.x - 1, point.y), color, true));
////				}
////				if (Utils.getCell(point.x, point.y - 1).getColor().equals(CellColor.RED)) {
////					if (point.y - 1 >= 0)
////						changedCells.add(Utils.changeCell(new Point(point.x, point.y - 1), color, true));
////				}
////
////				if (Utils.getCell(point.x - 1, point.y - 1).getColor().equals(CellColor.RED)) {
////					if (point.x - 1 >= 0 && point.y - 1 >= 0)
////						changedCells.add(Utils.changeCell(new Point(point.x - 1, point.y - 1), color, true));
////				}
////				if (Utils.getCell(point.x - 1, point.y + 1).getColor().equals(CellColor.RED)) {
////					if (point.x - 1 >= 0 && point.y + 1 <= Utils.getGridSize())
////						changedCells.add(Utils.changeCell(new Point(point.x - 1, point.y + 1), color, true));
////				}
////				if (Utils.getCell(point.x + 1, point.y + 1).getColor().equals(CellColor.RED)) {
////					if (point.x + 1 <= Utils.getGridSize() && point.y + 1 <= Utils.getGridSize())
////						changedCells.add(Utils.changeCell(new Point(point.x + 1, point.y + 1), color, true));
////				}
////				if (Utils.getCell(point.x + 1, point.y - 1).getColor().equals(CellColor.RED)) {
////					if (point.x + 1 <= Utils.getGridSize() && point.y - 1 >= 0)
////						changedCells.add(Utils.changeCell(new Point(point.x + 1, point.y - 1), color, true));
////				}
////
////				changedCells.clear();
////
////				if (lifetime > 2)
////					remove();
////				break;
//			case RED:
//
//				if (Utils.getCell(point.x + 1, point.y).getColor().equals(CellColor.REMOVE)) {
//					if (point.x + 1 <= Utils.getGridSize())
//						changedCells.add(Utils.changeCell(new Point(point.x + 1, point.y), color, true));
//
//				}
//				if (Utils.getCell(point.x, point.y + 1).getColor().equals(CellColor.REMOVE)) {
//					if (point.y + 1 <= Utils.getGridSize()) {
////						System.out.println("Up");
//						changedCells.add(Utils.changeCell(new Point(point.x, point.y + 1), color, true));
//
//					}
//				}
//
//				if (Utils.getCell(point.x - 1, point.y).getColor().equals(CellColor.REMOVE)) {
//					if (point.x - 1 >= 0)
//						changedCells.add(Utils.changeCell(new Point(point.x - 1, point.y), color, true));
//
//				}
//				if (Utils.getCell(point.x, point.y - 1).getColor().equals(CellColor.REMOVE)) {
//					if (point.y - 1 >= 0) {
//
//						changedCells.add(Utils.changeCell(new Point(point.x, point.y - 1), color, true));
//					}
//
//				}
//				if (changedCells.size() > 0) {
//
//				} else {
//					Utils.changeCell(point, CellColor.BLACK, true);
//				}
//
//				break;
//			default:
//				break;
//			}

			lifetime = lifetime + 1;
		}
	}

}
