package entities.zombiesEntities.day;

import control.Coordinates;
import entities.Zombie;

public class PoleVaultingZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	public PoleVaultingZombie(Coordinates coord) {
		super(100, 100, 2, 0, coord, 5, true, true, null); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "PoleVaultingZombie";
	}
}