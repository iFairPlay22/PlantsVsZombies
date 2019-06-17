package entities.plantsEntities.night;

import entities.Plant;
import entities.Shroom;

public class ScaredyShroom extends Shroom{
	
	public ScaredyShroom(boolean dayTime) {
		super(25, 5, 200, 15, 2, false, 10, 0, 0, 1, true, true, false, 3, true, null, dayTime); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}
	
	@Override
	public String printClass() {
		return "ScaredyShroom";
	}
}
