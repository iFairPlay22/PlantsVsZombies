package entities.plantsEntities.night;

import entities.LivingBeing;
import entities.Shroom;
import maps.Map;

public class IceShroom extends Shroom {
	private static final long serialVersionUID = 1L;
	public final static int unmovedTime = 5;
	
	public IceShroom(boolean dayTime) {
		super(75, 20, 200, 0, 2, true, 10, -10, 10, 1, false, false, dayTime); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "IceShroom";
	}
	
	@Override
	public int attack(LivingBeing victim) {
		victim.setUnmoved();
		return super.attack(victim);
	}
	
	@Override
	public void doActionIfNecessary() {
		destroy();
		super.doActionIfNecessary();
	}
}
