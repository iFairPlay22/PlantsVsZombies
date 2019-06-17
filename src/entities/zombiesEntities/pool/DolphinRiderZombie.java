package entities.zombiesEntities.pool;

import control.Coordinates;
import entities.Zombie;

public class DolphinRiderZombie extends Zombie {
	private static final long serialVersionUID = 1L;

	public DolphinRiderZombie(Coordinates coord) {
		super(300, 50, 2, 0, coord, 5); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "DolphinRiderZombie";
	}
	
	@Override
	public boolean mustBeOnWater() {
		return true;
	}
}
