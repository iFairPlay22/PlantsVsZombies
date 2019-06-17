package entities.plantsEntities.night;

import entities.Plant;

public class GraveBuster extends Plant {
	private static final long serialVersionUID = 1L;

	public GraveBuster() {
		super(75, 15, 100, 0, 0, false, 0, 0, 0, 0, true, true);	//int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}
	
	@Override
	public String printClass() {
		return "GraveBuster";
	}
	
	@Override
	public boolean destroyGrave() {
		return true;
	}
	
	@Override
	public boolean ignoreRoof() {
		return true;
	}
}