package entities.plantsEntities.pool;

import entities.Plant;

public class TangleKelp extends Plant {
	private static final long serialVersionUID = 1L;
	
	public TangleKelp() {
		super(25, 10, 200, 10000, 2, true, 0, 0, 0, 1, true, false); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "TangleKelp";
	}
	
	@Override
	public boolean mustBeOnWater() {
		return true;
	}
}