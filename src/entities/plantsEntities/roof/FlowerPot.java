package entities.plantsEntities.roof;

import entities.Plant;

public class FlowerPot extends Plant {
	private static final long serialVersionUID = 1L;

	public FlowerPot() {
		super(25, 5, 100, 0, 0, false, 0, 0, 0, 0, false, true, false, 0, true, null);
	}

	@Override
	public boolean mustBeOnRoof() {
		return true;
	}
	
	@Override
	public String printClass() {
		return "FlowerPot";
	}
	
	@Override
	public boolean isFlowerPot() {
		return true;
	}
}
