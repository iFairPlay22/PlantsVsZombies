package entities.plantsEntities.roof;

import entities.Plant;

public class CoffeeBean extends Plant {
	private static final long serialVersionUID = 1L;

	public CoffeeBean() {
		super(75, 10, 50, 0, 0, false, 0, 0, 0, 0, false, true);
	}

	@Override
	public boolean canSetAwakeShrooms() {
		return true;
	}
	
	@Override
	public String printClass() {
		return "CoffeeBean";
	}
	
}
