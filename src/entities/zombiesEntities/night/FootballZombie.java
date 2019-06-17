package entities.zombiesEntities.night;

import java.util.ArrayList;

import control.Cell;
import control.Coordinates;
import control.Line;
import entities.Zombie;

public class FootballZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	public FootballZombie(Coordinates coord) {
		super(1290, 150, 2, 0, coord, 5); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, int coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "FootballZombie";
	}
}
