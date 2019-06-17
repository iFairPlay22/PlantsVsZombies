package entities.plantsEntities.roof;

import entities.Plant;

public class MelonPult extends Plant {
	private static final long serialVersionUID = 1L;

	public MelonPult() {
		super(225, 5, 400, 100, 5, false, 10, 0, 0, 1, true, true);
	}
	
	@Override
	public String printClass() {
		return "MelonPult";
	}
	
	@Override
	public boolean isProjectileParabola() {
		return true;
	}
	
	@Override
	public boolean groupDamages() {
		return true;
	}
	
	@Override
	public boolean ignoreRoof() {
		return true;
	}
}
