package entities.plantsEntities.roof;


import control.BoardGame;
import entities.LivingBeing;
import entities.Plant;

public class KernelPult extends Plant {
	private static final long serialVersionUID = 1L;

	public KernelPult() {
		super(125, 15, 200, 50, 3, false, 10, 0, 0, 1, true, true);
	}
	
	@Override
	public String printClass() {
		return "KernelPult";
	}

	@Override
	public boolean isProjectileParabola() {
		return true;
	}
	
	@Override
	public int attack(LivingBeing victim) {
		if (BoardGame.randint(0, 3) == 1) {
			victim.setUnmoved();
		}
		return super.attack(victim);
	}
	
	@Override
	public boolean ignoreRoof() {
		return true;
	}
}