package entities.zombiesEntities.day;

import control.Coordinates;
import entities.Zombie;

public class BucketheadZombie extends Zombie {
	private static final long serialVersionUID = 1L;

	public BucketheadZombie(Coordinates coord) {
		super(760, 100, 2, 0, coord, 2); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, int coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "BucketheadZombie";
	}
}
