package entities.zombiesEntities.day;

import control.Coordinates;
import entities.Zombie;

public class FlagZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	public FlagZombie(Coordinates coord) {
		super(200, 100, 1, 0, coord, 3);  //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "FlagZombie";
	}
}
