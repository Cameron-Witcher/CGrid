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
	int DELAY = 50;
	Timer timer;
	Random random;

	private double gridSize = 10;
	int agsx;
	int agsy;

	int lifetime = 0;

	private Map<Point, Cell> gridInfo = new HashMap<>();
	private Map<Boolean, Cell> changes = new HashMap<>();

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
			getChanges().put(false, entry.getValue());
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
		setGridSize(10);
		agsx = (int) Math.floor(Main.getWindow().getWidth() / getGridSize()) - 1;
		agsy = (int) Math.floor(Main.getWindow().getHeight() / getGridSize()) - 1;
		for (Entry<Boolean, Cell> entry : getChanges().entrySet()) {
			if (entry.getKey()) {
				getGridInfo().put(entry.getValue().getPoint(), entry.getValue());
			} else {
				getGridInfo().remove(entry.getValue().getPoint());
			}
		}

		getChanges().clear();
		for (Entry<Point, Cell> entry : getGridInfo().entrySet()) {
			g.setColor(entry.getValue().getColor().getColor());
			g.fillRect(entry.getKey().x * agsx, entry.getKey().y * agsy, agsx, agsy);
			entry.getValue().update();
		}

		for (int i = 0; !(i >= Main.getWindow().getWidth() / agsx); i++) {
			for (int j = 0; !(j >= Main.getWindow().getHeight() / agsy); j++) {
//				g.setColor(generateColor(i+j, 0.9,lifetime*(0.125/100)));
//				g.fillRect((int)(i*agsx), (int)(j*agsy), (int)agsx, (int)agsy);
				g.setColor(Color.GRAY);
				g.drawLine(0, j * agsy, getWidth(), j * (agsy));
				g.drawLine(j * agsx, 0, j * agsx, getHeight());
			}
		}

		lifetime = lifetime + 1;

		g.dispose();
	}

	public Color generateColor(int seed, double frequency) {
		return generateColor(seed, frequency, 0);
	}

	public Color generateColor(int seed, double frequency, double shift) {
		int red = (int) (Math.sin(frequency * (seed + shift) + 0) * 127 + 128);
		int green = (int) (Math.sin(frequency * (seed + shift) + 2 * Math.PI / 3) * 127 + 128);
		int blue = (int) (Math.sin(frequency * (seed + shift) + 4 * Math.PI / 3) * 127 + 128);
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
			default:
				color = CellColor.REMOVE;
				break;
			}
			if (color.equals(CellColor.REMOVE)) {
				getChanges().put(false, new Cell(new Point(x, y), color));
			} else
				getChanges().put(true, new Cell(new Point(x, y), color));
		}

		@Override
		public void mouseMoved(MouseEvent e) {

			// mx = e.getX();
			// my = e.getY();

		}
	}

	public void pause() {
	}

	public double getGridSize() {
		return gridSize;
	}

	public void setGridSize(double gridSize) {
		this.gridSize = gridSize;
	}

	public Map<Boolean, Cell> getChanges() {
		return changes;
	}

	public void setChanges(Map<Boolean, Cell> changes) {
		this.changes = changes;
	}

	public Map<Point, Cell> getGridInfo() {
		return gridInfo;
	}

	public void setGridInfo(Map<Point, Cell> gridInfo) {
		this.gridInfo = gridInfo;
	}

}
