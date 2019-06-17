package entities.plantsEntities.night;

import entities.Shroom;

public class PuffShroom extends Shroom {
	private static final long serialVersionUID = 1L;

	public PuffShroom(boolean dayTime) {
		super(0, 10, 200, 20, 2, false, 3, 0, 0, 1, true, true, dayTime); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "PuffShroom";
	}
}
