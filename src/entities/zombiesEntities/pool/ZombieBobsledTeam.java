/*
package entities.zombiesEntities.pool;

import java.util.ArrayList;

import control.Cell;
import control.Coordinates;
import control.Line;
import entities.Zombie;

public class ZombieBobsledTeam extends Zombie {
	public ZombieBobsledTeam(Coordinates coord) {
		super(5000, 8000, 1, 0, coord, 2); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, int coordinateY, moveSpeed
	}
	
	@Override
	public String printClass() {
		return "ZombieBobsledTeam";
	}
	
	@Override
	public ArrayList<Cell> getVictims(Zombie zombie, Line zombieLine, int zombieColumn) {
		ArrayList<Cell> victims = new ArrayList<>();
		Cell c = zombieLine.getFirstPlantCell(zombie, zombieColumn);
		if (c != null) {
			victims.add(c);
		}
		return victims;
	}
}
*/
