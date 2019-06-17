package entities.plantsEntities.day;

import entities.Plant;

public class Repeater extends Plant {
	private static final long serialVersionUID = 1L;

	public Repeater() {
		super(200, 5, 300, 20, 5, false, 10, 0, 0, 2, true, true); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "Repeater";
	}
}
