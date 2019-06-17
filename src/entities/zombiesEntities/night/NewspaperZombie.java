package entities.zombiesEntities.night;

import control.Coordinates;
import entities.Zombie;

public class NewspaperZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	public NewspaperZombie(Coordinates coord) {
		super(340, 150, 2, 0, coord, 2, false, true, 300); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, int coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "NewspaperZombie";
	}
}
