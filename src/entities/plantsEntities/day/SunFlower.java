package entities.plantsEntities.day;

import entities.SunSpawners;

public class SunFlower extends SunSpawners {
	private static final long serialVersionUID = 1L;

	public SunFlower() {
		super(50, 5, 300, 25, 10, null, 50);
	}

	@Override
	public String printClass() {
		return "SunFlower";
	}
}
