package entities.plantsEntities.night;


import entities.Shroom;

public class HypnoShroom extends Shroom {
	private static final long serialVersionUID = 1L;

	public HypnoShroom(boolean dayTime) {
		super(75, 10, 200, 0, 1, false, 0, 0, 0, 1, true, true, dayTime); //int lifePoints, int attackPoints, int attackSpeed, canAttackBehind, int range
	}

	@Override
	public String printClass() {
		return "HypnoShroom";
	}
	
	@Override
	public void doActionIfNecessary() {
		destroy();
		super.doActionIfNecessary();
	}
	
	@Override
	public boolean tranformZombies() {
		return true;
	}
}