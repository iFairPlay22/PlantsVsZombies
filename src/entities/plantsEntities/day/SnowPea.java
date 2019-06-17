package entities.plantsEntities.day;

import entities.LivingBeing;
import entities.Plant;

public class SnowPea extends Plant {
	private static final long serialVersionUID = 1L;
	public static final  int speedReductionTime = 5; //seconds
	
	public SnowPea() {
		super(175, 5,300, 20, 5, false, 10, 0, 0, 1, true, true); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}
	
	@Override
	public int attack(LivingBeing victim) {
		victim.setSlow();
		return super.attack(victim);
	}

	@Override
	public String printClass() {
		return "SnowPea";
	}
}
