package entities.plantsEntities.fog;

import entities.Shroom;

public class SeaShroom extends Shroom {
	private static final long serialVersionUID = 1L;

	public SeaShroom(boolean dayTime) {
		super(0, 30, 300, 20, 2, false, 3, 0, 0, 1, true, true, dayTime);
	}
	
	@Override
	public String printClass() {
		return "SeaShroom";
	}
	
	@Override
	public boolean mustBeOnWater() {
		return true;
	}
}
