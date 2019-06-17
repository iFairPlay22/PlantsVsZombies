package entities.plantsEntities.fog;

import entities.Plant;

public class SplitPea extends Plant {
	private static final long serialVersionUID = 1L;

	public SplitPea() {
		super(125, 5, 300, 20, 2, true, 10, 0, 0, 1, true, true);
	}
	
	@Override
	public String printClass() {
		return "SplitPea";
	}


}
