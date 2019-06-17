package entities.plantsEntities.day;

import entities.Plant;

public class Chomper extends Plant {
	private static final long serialVersionUID = 1L;

	public Chomper() {
		super(150, 5, 300, 10000, 5, false, 0, 0, 0, 1, true, true); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "Chomper";
	}
}
