package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import control.BoardGame;
import control.SunList;
import control.Time;
import display.PVZView;
import entities.plantsEntities.day.SnowPea;
import entities.plantsEntities.night.IceShroom;

public class LivingBeing implements Serializable {
	private static final long serialVersionUID = 1L;
	private int lifePoints;
	private final int attackPoints;
	private final int attackSpeed;
	private final int range;
	private final Time attackTime;
	private final Time slowTime;
	private final Time unmovingTime;
	private boolean setUnmmoved;
	private boolean canBeAttacked;
	private final Integer lifePointsToBeScared;


	public LivingBeing(int lifePoints, int attackPoints, int attackSpeed, int range) {
		this(lifePoints, attackPoints, attackSpeed, range, true, null);
	}
	
	public LivingBeing(int lifePoints, int attackPoints, int attackSpeed, int range, boolean canBeAttacked, Integer lifePointsToBeScared) {
		this.lifePoints = lifePoints;
		this.attackPoints = attackPoints;
		this.attackSpeed = attackSpeed;
		this.range = range;
		attackTime = new Time();
		slowTime = new Time();
		unmovingTime = new Time();
		setUnmmoved = false;
		this.canBeAttacked = canBeAttacked;
		this.lifePointsToBeScared = lifePointsToBeScared;
	}

	@Override
	public String toString() {
		return printClass() + " :\nlifePoints: " + lifePoints + "\nattackPoints: " + attackPoints + "\nattackSpeed: " + attackSpeed + "\nrange: " + range;
	}
	
	public String printClass() {
		return "LivingBeing";
	}

	//Tests
	
	public boolean isDead() {
		return lifePoints <= 0;
	}
	
	public boolean isInRange(int distance) {
		return 0 <= distance && distance <= this.range;
	}
	
	public boolean isInNegativeRange(int distance) {
		return -1 * this.range <= distance && distance <= 0;
	}

	public boolean isReadyToAttack(float gameSpeed) {
		return (attackSpeed * gameSpeed / isScared(gameSpeed)) / ((slowTime.toSeconds() < SnowPea.speedReductionTime) ? 2f : 1f) <= attackTime.toSeconds();
	}

	//Attaque
	
	public int attack(LivingBeing victim) {
		Objects.requireNonNull(victim);
		if (victim.canBeAttacked) {
			victim.lifePoints -= this.attackPoints;
			return this.attackPoints;
		}
		return 0;
	}
	
	public void restartTimer() {
		attackTime.restart();
	}

	public int destroy() {
		int lifePointsDamages = lifePoints;
		lifePoints = 0;
		return lifePointsDamages;
	}
	
	public void setAttackable(boolean canBeAttack) {
		this.canBeAttacked = canBeAttack;
	}
	
	public int getJWithRange(int i, int nbColumns, boolean directionRight) {
		int resultat;
		if (directionRight) {
			resultat = i + range;
			if (resultat > nbColumns - 1) {
				return nbColumns - 1;
			}
		} else {
			resultat = i - range;
			if (0 > resultat) {
				return 0;
			}
		}
		return resultat;
	}

	public void fall() {}
	
	//Slow
	
	public void setSlow() {
		slowTime.restart();
	}
	
	public Boolean isSlow() {
		return (slowTime.toSeconds() < SnowPea.speedReductionTime);
	}
	
	//Unmoved
	
	public boolean isUnmoved() {
		if (setUnmmoved && (unmovingTime.toSeconds() < IceShroom.unmovedTime)) {
			return true;
		}
		return false;
	}
	
	public void setUnmoved() {
		unmovingTime.restart();
		setUnmmoved = true;
	}
	
	//Scary
	
	public float isScared(float gameSpeed) {
		if (lifePointsToBeScared == null) {
			return 1;
		}
		if (lifePoints <= lifePointsToBeScared) {
			return gameSpeed * 5;
		}
		return gameSpeed;
	}
	
	//Summon
	
	public boolean isReadyToSummon() {
		return false;
	}
	
	public void summon(BoardGame data, PVZView view, ArrayList<Zombie> zombieAlive) {
		
	}

	public void summon(int x, int y, SunList suns) {
		
	}
	
	//Getters
	
	public boolean canBeAttacked() {
		return canBeAttacked;
	}
	
	public boolean mustBeOnWater() {
		return false;
	}
	
	public boolean doubleDamages() {
		return false;
	}
	
	public boolean hasTire() {
		return false;
	}
}
