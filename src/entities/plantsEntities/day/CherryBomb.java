package entities.plantsEntities.day;

import entities.LivingBeing;
import entities.Plant;

public class CherryBomb extends Plant {
	
	public CherryBomb() {
		super(150, 35, 1799, 1800, 1, true, 3, -1, 1, 1, false, false);	//int lifePoints, int attackPoints, int attackSpeed, boolean canAttackBehind, int range
	}
	
	@Override
	public String printClass() {
		return "CherryBomb";
	}
	
	@Override
	public void doActionIfNecessary() {
		destroy();
		super.doActionIfNecessary();
	}
	
	@Override
	public int attack(LivingBeing victim) {
		victim.destroy();
		return 0;
	}
	
	@Override
	public boolean ignoreRoof() {
		return true;
	}
}
