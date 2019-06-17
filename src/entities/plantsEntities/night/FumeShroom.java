package entities.plantsEntities.night;

import entities.Shroom;

public class FumeShroom extends Shroom {
	private static final long serialVersionUID = 1L;

	public FumeShroom(boolean dayTime) {
		super(75, 5, 300, 20, 2, false, 3, 0, 0, 1, true, false, dayTime);
	}

	@Override
	public String printClass() {
		return "FumeShroom";
	}
}
