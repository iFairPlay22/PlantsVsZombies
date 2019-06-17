package entities.plantsEntities.night;

import control.SunList;
import entities.SunSpawners;

public class SunShroom extends SunSpawners {
	
	private boolean awake;
	
	public SunShroom(boolean dayTime) {
		super(25, 5, 300, 15, 10, 30, 50); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
		if (dayTime) {
			awake = false;
		} else {
			awake = true;
		}
	}

	@Override
	public String printClass() {
		return "SunShroom";
	}
	
	@Override
	public boolean isReadyToSummon() {
		if (awake) {
			return super.isReadyToSummon();
		} else {
			return false;
		}
	}
}
