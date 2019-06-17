package entities.plantsEntities.fog;

import entities.LivingBeing;
import entities.Plant;

public class Cactus extends Plant {
	private static final long serialVersionUID = 1L;

	public Cactus() {
		super(125, 7, 300, 20, 2, false, 10, 0, 0, 1, true, false);
	}
	
	@Override
	public String printClass() {
		return "Cactus";
	}
	
	@Override
	public int attack(LivingBeing victim) {
		victim.fall();
		return super.attack(victim);
	}
	
	@Override
	public boolean canAttackInTheSky() {
		return true;
	}

}
