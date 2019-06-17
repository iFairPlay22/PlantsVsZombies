package entities.plantsEntities.pool;

import entities.Plant;

public class Threepeater extends Plant {
	private static final long serialVersionUID = 1L;
	
	public Threepeater() {
		super(325, 20, 300, 20, 2, false, 10, -1, 1, 1, true, true); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "Threepeater";
	}
}
