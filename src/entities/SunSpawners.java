package entities;

import control.Sun;
import control.SunList;
import control.Time;

public abstract class SunSpawners extends Plant implements SunSpawner {
	private static final long serialVersionUID = 1L;
	
	//Quantite intiale de soleils donnes
	private int sunFlowerRessources;
	
	//Temps entre chaque spawn de soleil
	private final Time timerBetweenSummons;
	private final int timeBetweenNextSummons;
	
	//Variation de la quantite de soleils donnes
	private final Time timerBetweenNewRessources;
	private final Integer timeBeforeNewRessources;
	private final int newRessources;
	
	public SunSpawners(int cost, int recharcheTime, int lifePoints, int sunFlowerRessources, int timeBetweenNextSummons, Integer timeBeforeNewRessources, int newRessources) {
		super(cost, recharcheTime, lifePoints, 0, 2000, false, 0, 0, 0, 0, true, false);
		this.sunFlowerRessources = sunFlowerRessources;
		this.timeBetweenNextSummons = timeBetweenNextSummons;
		this.timeBeforeNewRessources = timeBeforeNewRessources;
		this.newRessources = newRessources;
		timerBetweenSummons = new Time();
		timerBetweenNewRessources = new Time();
	}

	//Summon Sun
	
	@Override
	public boolean isReadyToSummon() {
		if (timeBeforeNewRessources != null && sunFlowerRessources != timeBeforeNewRessources) {
			if (timerBetweenNewRessources.toSeconds() > timeBeforeNewRessources) {
				sunFlowerRessources = newRessources;
			}
		}
		return timerBetweenSummons.toSeconds() >= timeBetweenNextSummons;
	}
	
	@Override
	public boolean canSpawnSun(int gameSpeed) {
		if (gameSpeed != 1) {
			return false;
		}
		return isReadyToAttack(gameSpeed);
	}
	
	@Override
	public void summon(int x, int y, SunList suns) {
		if (!(timerBetweenSummons.toSeconds() >= timeBetweenNextSummons)) {
			throw new IllegalStateException("Impossible to summon !");
		}
		suns.addSun(new Sun(x, y, sunFlowerRessources));
		timerBetweenSummons.restart();
	}
	
	//attack
	
	@Override
	public int attack(LivingBeing victim) {
		throw new IllegalStateException("SunSpawners can't attack !");
	}
}
