package entities.zombiesEntities.fog;

import control.Coordinates;
import entities.Zombie;

public class PogoZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	public PogoZombie(Coordinates coord) {
		super(400, 50, 1, 0, coord, 10);
	}

	@Override
	public String printClass() {
		return "PogoZombie";
	}
}
