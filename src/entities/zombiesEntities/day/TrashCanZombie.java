package entities.zombiesEntities.day;

import control.Coordinates;
import entities.Zombie;

public class TrashCanZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	public TrashCanZombie(Coordinates coord) {
		super(1000, 100, 2, 0, coord, 2); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, int coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "TrashCanZombie";
	}
}
