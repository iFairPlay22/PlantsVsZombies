package entities.plantsEntities.roof;

import entities.Plant;

public class CabbagePult extends Plant {
	private static final long serialVersionUID = 1L;

	public CabbagePult() {
		super(100, 5, 200, 100, 2, false, 10, 0, 0, 1, true, true);
	}
	
	@Override
	public String printClass() {
		return "CabbagePult";
	}
	
	@Override
	public boolean isProjectileParabola() {
		return true;
	}
	
	@Override
	public boolean ignoreRoof() {
		return true;
	}
}
