package entities.plantsEntities.day;

import entities.Plant;

public class Peashooter extends Plant {
	private static final long serialVersionUID = 1L;
	
	public Peashooter() {
		super(100, 5, 300, 20, 2, false, 10, 0, 0, 1, true, true); 
	}

	@Override
	public String printClass() {
		return "Peashooter";
	}
}
