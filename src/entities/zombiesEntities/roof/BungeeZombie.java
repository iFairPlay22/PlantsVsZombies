package entities.zombiesEntities.roof;

import java.util.ArrayList;

import control.BoardGame;
import control.Cell;
import control.Coordinates;
import entities.LivingBeing;
import entities.Zombie;

public class BungeeZombie extends Zombie {
	private static final long serialVersionUID = 1L;

	public BungeeZombie(Coordinates coord) {
		super(460, 10000, 5, 1, coord, 0, false, true, null);
	}

	@Override
	public String printClass() {
		return "BungeeZombie";
	}
	
	@Override
	public ArrayList<Cell> getVictims(Zombie zombie, BoardGame data, int zombieLine, int zombieColumn) {
		ArrayList<Cell> victims = new ArrayList<>();
		Cell victim = data.getElement(zombieLine, zombieColumn);
		if (victim.isAttackable()) {
			victims.add(victim);
		} else {
			destroy();
		}
		return victims;
	}
	
	@Override
	public int attack(LivingBeing victim) {
		victim.destroy();
		destroy();
		return 0;
	}
}