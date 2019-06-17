package entities.plantsEntities.fog;

import entities.Plant;

public class Blover extends Plant {
	private static final long serialVersionUID = 1L;

	public Blover() {
		super(150, 15, 100, 0, 0, false, 0, 0, 0, 0, false, true, false, null, false, null);
	}
	
	@Override
	public String toString() {
		return "Blover";
	}
	
	@Override
	public boolean illuminate() {
		return true;
	}
	
	@Override
	public boolean illuminateLine() {
		return true;
	}
}
