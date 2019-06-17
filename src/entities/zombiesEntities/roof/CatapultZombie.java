package entities.zombiesEntities.roof;

import java.util.ArrayList;

import control.BoardGame;
import control.Cell;
import control.Coordinates;
import entities.Zombie;

public class CatapultZombie extends Zombie {
	private static final long serialVersionUID = 1L;

	public CatapultZombie(Coordinates coord) {
		super(200, 100, 1, 10, coord, 3);  //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "CatapultZombie";
	}
	
	public ArrayList<Cell> getVictims(Zombie zombie, BoardGame data, int zombieLine, int zombieColumn) {
		ArrayList<Cell> victims = new ArrayList<>();
		Cell cell = data.getLastPlantCell(zombie, zombieLine, zombieColumn);
		if (cell != null && (cell.isAttackable())) {
			victims.add(cell);
		}
		return victims;
	}
	
	@Override
	public boolean hasTire() {
		return true;
	}
}
