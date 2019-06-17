package entities;

public abstract class Shroom extends Plant {
	private static final long serialVersionUID = 1L;
	
	private boolean awake;
	
	public Shroom(int cost, int recharcheTime, int lifePoints, int attackPoints, int attackSpeed, boolean canAttackBehind,  int range, int bottomLineRange, int topLineRange, int projectilesNumberByLine, boolean mustDetectZombiesToAttack, boolean projectileImplyOnlyOneVictims, boolean dayTime) {
		super(cost, recharcheTime, lifePoints, attackPoints, attackSpeed, canAttackBehind, range, bottomLineRange, topLineRange, projectilesNumberByLine, mustDetectZombiesToAttack, projectileImplyOnlyOneVictims);
		if (dayTime) {
			awake = false;
		} else {
			awake = true;
		}
	}
	
	public Shroom(int cost, int recharcheTime, int lifePoints, int attackPoints, int attackSpeed, boolean canAttackBehind,  int range, int bottomLineRange, int topLineRange, int projectilesNumberByLine, boolean mustDetectZombiesToAttack, boolean projectileImplyOnlyOneVictims, boolean canSummon, Integer scaredRange, boolean canBeAttack, Integer timeBetweenScared, boolean dayTime) {
		super(cost, recharcheTime, lifePoints, attackPoints, attackSpeed, canAttackBehind, range, bottomLineRange, topLineRange, projectilesNumberByLine, mustDetectZombiesToAttack, projectileImplyOnlyOneVictims, canSummon, scaredRange, canBeAttack, timeBetweenScared);
		if (dayTime) {
			awake = false;
		} else {
			awake = true;
		}
	}
	
	@Override
	public boolean isReadyToAttack(float gameSpeed) {
		if (awake) {
			return super.isReadyToAttack(gameSpeed);
		}
		return false;
	}
	
	@Override
	public boolean isInRange(int distance) {
		if (awake) {
			return super.isInRange(distance);
		}
		return false;
	}
	
	@Override
	public void wakeUp() {
		awake = true;
	}
	
	@Override
	public boolean isShroom() {
		return true;
	}
	
}
