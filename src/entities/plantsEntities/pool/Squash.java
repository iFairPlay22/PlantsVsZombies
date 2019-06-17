package entities.plantsEntities.pool;

import entities.Plant;

public class Squash extends Plant {
	private static final long serialVersionUID = 1L;
	public Squash() {
		super(50, 10, 200, 10000, 2, false, 2, 0, 0, 1, true, true);
	}

	@Override
	public String printClass() {
		return "Squash";
	}
	
	@Override
	public void doActionIfNecessary() {
		destroy();
		super.doActionIfNecessary();
	}
	
	@Override
	public boolean ignoreRoof() {
		return true;
	}
}