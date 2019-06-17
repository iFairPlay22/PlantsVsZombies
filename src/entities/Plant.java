package entities;

public class Plant extends LivingBeing {
	private static final long serialVersionUID = 1L;
	
	private final int cost;
	private final int recharcheTime;

	private final Integer scaredRange;
	
	private final int projectilesNumberByLine;
	private final int topLineRange;
	private final int bottomLineRange;
	
	private final boolean mustDetectZombiesToAttack;
	private final boolean canAttackBehind;
	private final boolean projectileImplyOnlyOneVictims;

	
	public Plant(int cost, int recharcheTime, int lifePoints, int attackPoints, int attackSpeed, boolean canAttackBehind,  int range, int bottomLineRange, int topLineRange, int projectilesNumberByLine, boolean mustDetectZombiesToAttack, boolean projectileImplyOnlyOneVictims) {
		super(lifePoints, attackPoints, attackSpeed, range);
		this.cost = cost;
		if (recharcheTime < 0) {
			throw new IllegalStateException("Recharge time cannot be inferior to 0 !");
		}
		this.recharcheTime = recharcheTime;
		this.topLineRange = topLineRange;
		this.bottomLineRange = bottomLineRange;
		this.mustDetectZombiesToAttack = mustDetectZombiesToAttack;
		this.canAttackBehind = canAttackBehind;
		this.projectileImplyOnlyOneVictims = projectileImplyOnlyOneVictims;
		this.projectilesNumberByLine = projectilesNumberByLine;
		scaredRange = null;
	}
	
	public Plant(int cost, int recharcheTime, int lifePoints, int attackPoints, int attackSpeed, boolean canAttackBehind,  int range, int bottomLineRange, int topLineRange, int projectilesNumberByLine, boolean mustDetectZombiesToAttack, boolean projectileImplyOnlyOneVictims, boolean canSummon, Integer scaredRange, boolean canBeAttack, Integer timeBetweenScared) {
		super(lifePoints, attackPoints, attackSpeed, range, canBeAttack,  timeBetweenScared);
		this.cost = cost;
		if (recharcheTime < 0) {
			throw new IllegalStateException("Recharge time can't be inferior to 0 !");
		}
		this.recharcheTime = recharcheTime;
		this.topLineRange = topLineRange;
		this.bottomLineRange = bottomLineRange;
		this.mustDetectZombiesToAttack = mustDetectZombiesToAttack;
		this.canAttackBehind = canAttackBehind;
		this.projectileImplyOnlyOneVictims = projectileImplyOnlyOneVictims;
		this.projectilesNumberByLine = projectilesNumberByLine;
		if (scaredRange != null) {
			if (scaredRange < 0) {
				throw new IllegalStateException("Scared range can't be inferior to 0 !");
			}
		}
		this.scaredRange = scaredRange;
	}

	@Override
	public String printClass() {
		return "Plant";
	}
	
	@Override
	public boolean isInRange(int distance) {
		if (canAttackBehind && isInNegativeRange(distance)) {
			return true;
		}
		return super.isInRange(distance);
	}
	
	//Achat de plantes
	
	public boolean hasEnoughMoney(int j, int i, int sunNumber) {
		if (sunNumber >= cost) {
			return true;
		}
		return false;
	}
	
	public int buyPlant(int sunNumber) {
		return sunNumber - cost;
	}
	
	//getters pour les parametres des projectiles
	
	public boolean mustDetectZombiesToAttack() {
		return mustDetectZombiesToAttack;
	}
	
	public int getTopLineRange() {
		return topLineRange;
	}

	public int getBottomLineRange() {
		return bottomLineRange;
	}
	
	public int getProjectilesNumberByLine() {
		return projectilesNumberByLine;
	}
	
	public boolean canAttackBehind() {
		return canAttackBehind;
	}
	
	public boolean projectileImplyOnlyOneVictims() {
		return projectileImplyOnlyOneVictims;
	}
	
	//Getters utilises pour l'attaque (Boardgame / Cell)
	
	public int getRecharcheTime() {
		return recharcheTime;
	}
	
	public Integer getScaredRange() {
		return scaredRange;
	}
	
	public boolean preventFromJump() {
		return false;
	}
	
	public boolean destroyGrave() {
		return false;
	}
	
	public boolean tranformZombies() {
		return false;
	}
	
	public boolean canAttackInTheSky() {
		return false;
	}

	public boolean mustBeOnRoof() {
		return false;
	}
	
	public boolean isLilypad() {
		return false;
	}
	
	public boolean isFlowerPot() {
		return false;
	}
	
	public boolean illuminate() {
		return false;
	}

	public boolean illuminateLine() {
		return false;
	}
	
	public boolean isProjectileParabola() {
		return false;
	}
	
	public boolean changeZombieLine() {
		return false;
	}
	
	public boolean canSetAwakeShrooms() {
		return false;
	}
	
	public boolean groupDamages() {
		return false;
	}
	
	public boolean ignoreRoof() {
		return false;
	}

	public boolean summonClassicalProjectile() {
		return true;
	}
	
	public boolean isShroom() {
		return false;
	}
	
	//Methodes utilitaires
	
	//Reveil des Shrooms
	public void wakeUp() {
			
	}
	
	//Action apres attaque
	public void doActionIfNecessary() {
		restartTimer();
	}
}
