package control;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;

import display.PVZView;

public class SunList implements Serializable {
	private static final long serialVersionUID = 1L;
	ArrayList<Sun> sunList;
	
	public SunList() {
		sunList = new ArrayList<>();
	}
	
	public int catchSun(int x, int y) {
		int i = 0, tmp;
		Sun sun;
		while (i < sunList.size()) {
			sun = sunList.get(i);
			tmp = sun.clicked(new Coordinates(x, y));
			if (tmp != -1) {
				sunList.remove(sun);
				return tmp;
			}
			i++;
		}
		return -1;
	}
	
	public void moveSuns(float gameSpeed) {
		for (int i = 0; i < sunList.size(); i++) {
			if (!sunList.get(i).hasReachedPosition()) {
				sunList.get(i).advance(gameSpeed);
			}
		}
	}
	
	public void drawSuns(Graphics2D graphics, PVZView view) {
		view.drawSuns(graphics, sunList);
	}
	
	public void addSun(BoardGame data, PVZView view) {
		sunList.add(new Sun(data, view));
	}

	public void addSun(Sun sun) {
		sunList.add(sun);
	}
}
