package entities.plantsEntities.pool;

import entities.Plant;

public class TallNut extends Plant {
	private static final long serialVersionUID = 1L;
	
	public TallNut() {
		super(125, 20, 5000, 0, 0, false, 0, 0, 0, 0, true, true);	//int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}
	
	@Override
	public String printClass() {
		return "TallNut";
	}
	
	@Override
	public boolean preventFromJump() {
		return true;
	}
}