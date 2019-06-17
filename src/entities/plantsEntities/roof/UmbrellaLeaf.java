package entities.plantsEntities.roof;

import entities.Plant;

public class UmbrellaLeaf extends Plant {
	private static final long serialVersionUID = 1L;

	public UmbrellaLeaf() {
		super(125, 7, 105, 50, 3, false, 10, 0, 0, 1, true, false);
	}
	
	@Override
	public String printClass() {
		return "UmbrellaLeaf";
	}
	
	@Override
	public boolean summonClassicalProjectile() {
		return false;
	}
}
