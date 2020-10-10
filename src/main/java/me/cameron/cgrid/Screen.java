package me.cameron.cgrid;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import me.cameron.cgrid.utils.Cell;
import me.cameron.cgrid.utils.CellColor;

public class Screen extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int DELAY = 1;
	Timer timer;
	Random random;

	private double gridSize = 5;
	int agsx;
	int agsy;

	int lifetime = 0;

	private Map<Point, Cell> gridInfo = new HashMap<>();
	private Map<Cell, Boolean> changes = new HashMap<>();

	private boolean paused = true;

	public Screen() {
		setBackground(Color.WHITE);
		timer = new Timer(DELAY, this);
		timer.start();
		random = new Random();
		MouseListener mouseListener = new MouseListener();
		addKeyListener(new TAdapter());
		addMouseMotionListener(mouseListener);
		addMouseListener(mouseListener);
		setFocusable(true);
		requestFocusInWindow();
		load();

	}

	public void load() {

	}

	public void unload() {
		for (Entry<Point, Cell> entry : getGridInfo().entrySet()) {
			getChanges().put(entry.getValue(), false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
//		g.setColor(Color.BLACK);
//		g.fillRect(0, 0, getWidth(), getHeight());
		setGridSize(100);
		agsx = (int) Math.ceil(Main.getWindow().getWidth() / getGridSize()) ;
		agsy = (int) Math.ceil(Main.getWindow().getHeight() / getGridSize()) - 1;
		for (int i = 0; !(i >= Main.getWindow().getWidth() / agsx); i++) {
			for (int j = 0; !(j >= Main.getWindow().getHeight() / agsy); j++) {
				g.setColor(generateColor(i + j, 0.1));
				g.fillRect((int) (i * agsx), (int) (j * agsy), (int) agsx, (int) agsy);
			}
		}
		for (Entry<Cell, Boolean> entry : getChanges().entrySet()) {
			if (entry.getValue()) {
				getGridInfo().put(entry.getKey().getPoint(), entry.getKey());
			} else {
				getGridInfo().remove(entry.getKey().getPoint());
			}
		}
		changes.clear();
		for (Entry<Point, Cell> entry : getGridInfo().entrySet()) {
			g.setColor(entry.getValue().getColor().getColor());
			g.fillRect(entry.getKey().x * agsx, entry.getKey().y * agsy, agsx, agsy);
			if (!paused)
				entry.getValue().update();
		}

		for (int i = 0; !(i >= Main.getWindow().getWidth() / agsx); i++) {
			for (int j = 0; !(j >= Main.getWindow().getHeight() / agsy); j++) {
				g.setColor(Color.GRAY);
				g.drawLine(0, j * agsy, getWidth(), j * (agsy));
				g.drawLine(j * agsx, 0, j * agsx, getHeight());
			}
		}

		if (!paused)
			lifetime = lifetime + 1;

		g.dispose();
		
	}

	public Color generateColor(int seed, double frequency) {
		return generateColor(seed, frequency, 0);
	}

	public Color generateColor(int seed, double frequency, double shift) {

		int amp = 50;
		if (amp > 127)
			amp = 127;
		int peak = 255 - amp;
		int red = (int) (Math.sin(frequency * (seed + shift) + 0) * amp + peak);
		int green = (int) (Math.sin(frequency * (seed + shift) + 2 * Math.PI / 3) * amp + peak);
		int blue = (int) (Math.sin(frequency * (seed + shift) + 4 * Math.PI / 3) * amp + peak);
		if (red > 255)
			red = 255;
		if (green > 255)
			green = 255;
		if (blue > 255)
			blue = 255;

		return new Color(red, green, blue);
	}

	private void reset() {
	}

	private class TAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_R) {
				unload();
				load();
			}
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				pause();
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				reset();
				load();
			}

		}
	}

	private class MouseListener extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent e) {
			int x = (int) Math.ceil(e.getX() / agsx);
			int y = (int) Math.ceil(e.getY() / agsy);
//			System.out.println("X:" + x + " Y:" + y);
			CellColor color;
			switch (e.getButton()) {
			case MouseEvent.BUTTON1:
				color = CellColor.RED;
				break;
			case MouseEvent.BUTTON3:
				color = CellColor.GREEN;
				break;
			case MouseEvent.BUTTON2:
				color = CellColor.BLUE;
				break;
			default:
				color = CellColor.REMOVE;
				break;
			}
			if (color.equals(CellColor.REMOVE)) {
				getChanges().put(new Cell(new Point(x, y), color), false);
			} else {
				Cell c = new Cell(new Point(x, y), color);
				getChanges().put(c, true);
//				c.update();
			}
		}

		@Override
		public void mouseMoved(MouseEvent e) {

			// mx = e.getX();
			// my = e.getY();

		}
	}

	public void pause() {
		paused = !paused;
	}

	public double getGridSize() {
		return gridSize;
	}

	public void setGridSize(double gridSize) {
		this.gridSize = gridSize;
	}

	public Map<Cell, Boolean> getChanges() {
		return changes;
	}

	public void setChanges(Map<Cell, Boolean> changes) {
		this.changes = changes;
	}

	public Map<Point, Cell> getGridInfo() {
		return gridInfo;
	}

	public void setGridInfo(Map<Point, Cell> gridInfo) {
		this.gridInfo = gridInfo;
	}

}
