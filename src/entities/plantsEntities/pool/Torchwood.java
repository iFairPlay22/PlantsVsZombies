package entities.plantsEntities.pool;

import entities.Plant;

public class Torchwood extends Plant {
	private static final long serialVersionUID = 1L;
	
	public Torchwood() {
		super(175, 15, 175, 0, 0, false, 0, 0, 0, 0, true, true);	//int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}
	
	@Override
	public String printClass() {
		return "Torchwood";
	}
	
	@Override
	public boolean doubleDamages() {
		return true;
	}
}