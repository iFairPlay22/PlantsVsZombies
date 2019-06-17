package entities.plantsEntities.day;

import entities.LivingBeing;
import entities.Plant;

public class WallNut extends Plant{
	private static final long serialVersionUID = 1L;

	public WallNut() {
		super(50, 20, 4000, 0, 0, false, 0, 0, 0, 0, true, true);
	}
	
	@Override
	public String printClass() {
		return "WallNut";
	}

	@Override
	public int attack(LivingBeing victim) {
		throw new IllegalStateException("WallNut can't attack !");
	}
}
