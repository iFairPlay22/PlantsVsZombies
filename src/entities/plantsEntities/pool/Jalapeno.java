package entities.plantsEntities.pool;

import entities.LivingBeing;
import entities.Plant;

public class Jalapeno extends Plant {
	private static final long serialVersionUID = 1L;

	public Jalapeno() {
		super(125, 20, 200, 10000, 2, true, 10, 0, 0, 1, false, false); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "Jalapeno";
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
