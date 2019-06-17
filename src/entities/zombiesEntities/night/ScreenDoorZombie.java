package entities.zombiesEntities.night;

import control.Coordinates;
import entities.Zombie;

public class ScreenDoorZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	public ScreenDoorZombie(Coordinates coord) {
		super(1000, 150, 2, 0, coord, 2); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, int coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "ScreenDoorZombie";
	}
}
