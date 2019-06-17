package entities.zombiesEntities.day;

import control.Coordinates;
import entities.Zombie;

public class ClassicalZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	public ClassicalZombie(Coordinates coord) {
		super(100, 100, 2, 0, coord, 2); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "ClassicalZombie";
	}
}
