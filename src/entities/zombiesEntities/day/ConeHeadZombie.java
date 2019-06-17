package entities.zombiesEntities.day;

import control.Coordinates;
import entities.Zombie;

public class ConeHeadZombie extends Zombie {
	private static final long serialVersionUID = 1L;

	public ConeHeadZombie(Coordinates coord) {
		super(560, 100, 2, 0, coord, 2); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, int coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "ConeHeadZombie";
	}
}
