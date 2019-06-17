package entities.plantsEntities.pool;

import entities.LivingBeing;
import entities.Plant;

public class SpikeWeed extends Plant {
	private static final long serialVersionUID = 1L;

	public SpikeWeed() {
		super(100, 5, 300, 20, 2, false, 0, 0, 0, 1, true, true, false, null, false, null); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}
	
	@Override
	public String printClass() {
		return "SpikeWeed";
	}
	
	@Override
	public int attack(LivingBeing victim) {
		if (victim.hasTire()) {
			return victim.destroy();
		}
		return super.attack(victim);
	}
}
