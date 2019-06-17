package entities.plantsEntities.fog;

import entities.Plant;

public class Plantern extends Plant {
	private static final long serialVersionUID = 1L;

	public Plantern() {
		super(25, 2, 150, 0, 0, false, 0, 0, 0, 0, false, true);
	}
	
	@Override
	public boolean illuminate() {
		return true;
	}
	
	@Override
	public String printClass() {
		return "Plantern";
	}
	
}
