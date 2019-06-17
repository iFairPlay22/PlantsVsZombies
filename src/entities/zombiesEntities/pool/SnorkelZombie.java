
package entities.zombiesEntities.pool;

import control.Cell;
import control.Coordinates;
import display.PVZView;
import entities.Zombie;

public class SnorkelZombie extends Zombie {
	private static final long serialVersionUID = 1L;

	public SnorkelZombie(Coordinates coord) {
		super(200, 50, 3, 0, coord, 2);  //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "SnorkelZombie";
	}
	
	@Override
	public boolean advance(PVZView view, Cell c, float gameSpeed, boolean isPlantTeam, boolean hasAttack) {
		boolean hasAttacked = super.advance(view, c, gameSpeed, isPlantTeam, hasAttack);
		if (hasAttacked) {
			setAttackable(false);
		} else {
			setAttackable(true);
		}
		return hasAttacked;
	}
	
	@Override
	public boolean mustBeOnWater() {
		return true;
	}
}
