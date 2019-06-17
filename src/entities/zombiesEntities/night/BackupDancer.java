package entities.zombiesEntities.night;


import control.Coordinates;
import entities.Zombie;

public class BackupDancer extends Zombie {
	private static final long serialVersionUID = 1L;
	
	protected BackupDancer(Coordinates coord) {
		super(200, 150, 3, 0, coord, 2); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, int coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "BackupDancer";
	}
}
