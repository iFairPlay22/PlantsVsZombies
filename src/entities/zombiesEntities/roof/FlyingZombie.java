package entities.zombiesEntities.roof;

import control.Coordinates;
import entities.Zombie;

public class FlyingZombie extends Zombie {
	private static final long serialVersionUID = 1L;

	public FlyingZombie(Coordinates coord) {
		super(250, 50, 2, 0, coord, 1, true);
	}

	@Override
	public String printClass() {
		return "FlyingZombie";
	}
}
