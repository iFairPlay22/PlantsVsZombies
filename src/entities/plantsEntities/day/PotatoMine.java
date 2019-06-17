package entities.plantsEntities.day;

import entities.LivingBeing;
import entities.Plant;

public class PotatoMine extends Plant {
	private static final long serialVersionUID = 1L;

	public PotatoMine() {
		super(50, 20, 2000, 1800, 14, false, 0, 0, 0, 1, true, false); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "PotatoMine";
	}
	
	@Override
	public int attack(LivingBeing victim) {
		victim.destroy();
		return 0;
	}
	
	@Override
	public void doActionIfNecessary() {
		destroy();
		super.doActionIfNecessary();
	}
	
}
