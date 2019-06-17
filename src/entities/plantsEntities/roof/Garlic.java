package entities.plantsEntities.roof;

import entities.Plant;

public class Garlic extends Plant {
	private static final long serialVersionUID = 1L;

	public Garlic() {
		super(100, 1, 100, 10, 2, false, 1, 0, 0, 1, true, true);
	}
	
	@Override
	public boolean changeZombieLine() {
		return true;
	}
	
	@Override
	public String printClass() {
		return "Garlic";
	}
}
