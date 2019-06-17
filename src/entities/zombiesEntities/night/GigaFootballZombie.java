package entities.zombiesEntities.night;

import control.Coordinates;
import entities.Zombie;

public class GigaFootballZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	public GigaFootballZombie(Coordinates coord, int speedCoefficient) {
		super(3000, 150, 2, 0, coord, 2); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, int coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "GigaFootballZombie";
	}
}
