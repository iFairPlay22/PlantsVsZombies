package entities.plantsEntities.pool;

import entities.Plant;

public class LilyPad extends Plant {
	private static final long serialVersionUID = 1L;
	
	public LilyPad() {
		super(25, 3, 100, 0, 0, false, 0, 0, 0, 0, false, false); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "LilyPad";
	}
	
	@Override
	public boolean isLilypad() {
		return true;
	}
	
	@Override
	public boolean mustBeOnWater() {
		return true;
	}
}