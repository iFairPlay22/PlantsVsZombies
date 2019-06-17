package entities.zombiesEntities.fog;

import java.util.ArrayList;

import control.BoardGame;
import control.Cell;
import control.Coordinates;
import entities.Zombie;

public class JackInTheBoxZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	public JackInTheBoxZombie(Coordinates coord) {
		super(340, 1000, 10, 3, coord, 2, false, true, null);
	}
	
	@Override
	public String printClass() {
		return "JackInTheBoxZombie";
	}
	
	@Override
	public ArrayList<Cell> getVictims(Zombie zombie, BoardGame data, int zombieLine, int zombieColumn) {
		ArrayList<Cell> victims = new ArrayList<>();
		Cell victim;
		for (int i = zombieLine - 1; i <= zombieLine + 1; i++) {
			for (int j = zombieColumn - 2; j <= zombieColumn + 2; j++) {
				if (data.lineExists(i) && data.columnExists(j)) {
					victim = data.getElement(i, j);
					if (victim.isAttackable()) {
						victims.add(victim);
					}
				}
			}
		}

		
		return victims;
	}
}
