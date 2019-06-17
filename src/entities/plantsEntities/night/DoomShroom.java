package entities.plantsEntities.night;

import entities.LivingBeing;
import entities.Plant;
import entities.Shroom;

public class DoomShroom extends Shroom {
	private static final long serialVersionUID = 1L;
	
	public DoomShroom(boolean dayTime) {
		super(125, 20, 200, 10000, 2, true, 10, -10, 10, 1, false, false, dayTime); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "DoomShroom";
	}
	
	@Override
	public void doActionIfNecessary() {
		//Ajouter un trou dans la cell
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