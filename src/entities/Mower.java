package entities;


import java.io.Serializable;
import java.util.ArrayList;

import control.Coordinates;

public class Mower implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Coordinates coord;
	private boolean activated;
	
	public Mower(int column, int line) {
		coord = new Coordinates(column, line);
		activated = false;
	}
	
	public String printClass() {
		return "Mower";
	}
	
	public boolean isNotActivated() {
		return !activated;
	}

	public void activate() {
		activated = true;
	}

	public boolean advanceX(int xEnd) {
		if (activated) {
			if (coord.getX() + 25 > xEnd) {
				return true;
			} else {
				coord = coord.advanceX(25);
			}
		}
		return false;
	}
	
	public Coordinates getCoords() {
		return coord;
	}
	
	public void attackIfPossible(ArrayList<Zombie> zombiesAlive, Zombie zombie) {
		if (activated && zombie.getX() < coord.getX()) {
			zombiesAlive.remove(zombie);
		}
	}
}
