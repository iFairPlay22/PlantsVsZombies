package entities.zombiesEntities.pool;

import control.Coordinates;
import entities.Zombie;

public class Zomboni extends Zombie {
	private static final long serialVersionUID = 1L;

	public Zomboni(Coordinates coord) {
		super(500, 1000, 1, 0, coord, 1);  //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "Zomboni";
	}
}
