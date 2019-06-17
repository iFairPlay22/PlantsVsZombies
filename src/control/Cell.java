package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import control.projectile.BoomerangProjectile;
import control.projectile.ClassicalProjectile;
import control.projectile.ProjectileList;
import display.PVZView;
import entities.Grave;
import entities.Plant;
import entities.Zombie;
import maps.Map;

public class Cell implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Coordinates coord;
	private Plant plant;
	private Plant lilypad;
	private Plant flowerPot;
	private Grave grave;
	private final boolean isRoof;
	private boolean isFog;
	private boolean hasLadder;

	protected Cell(Coordinates coord, boolean isRoof, boolean isFog) {
		this.coord = coord;
		plant = null;
		lilypad = null;
		grave = null;
		this.isRoof = isRoof;
		this.isFog = isFog;
		hasLadder = false;
	}

	@Override
	public String toString() {
		return "Cell coordinates: (" + coord.getX() + ", " + coord.getY() + ")" + ", Contains: " + plant + "\n";
	}
	
	//equals
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Cell)) {
			return false;
		}
		Cell c = (Cell) o;
		return c.coord.equals(coord);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(coord);
	}

	//Placer une plante
	
	public boolean canBePlaced(Plant plant, boolean isWater) {
		//Destroy a grave
		if (plant.destroyGrave()) {
			return hasGrave();
		}
		//Water
		if (isWater) {
			if (plant.mustBeOnWater()) {
				return (plant.isLilypad() && lilypad == null) || (!plant.isLilypad() && this.plant == null && lilypad == null);
			} else if (lilypad != null) {
				return true;
			} else {
				return false;
			}
		}
		//Roof
		if (isRoof) {
			if (plant.mustBeOnRoof()) {
				return (plant.isFlowerPot() && flowerPot == null) || (!plant.isFlowerPot() && this.plant == null);
			} else if (flowerPot != null && !plant.mustBeOnWater()) {
				return true;
			} else {
				return false;
			}
		}
		//None
		return this.plant == null && (!plant.mustBeOnRoof() && !plant.mustBeOnWater()) && grave == null;
	}
	
	public void setPlant(Plant plant) {
		if (plant == null) {
			if (this.plant != null) {
				this.plant = null;
			}  else if (this.lilypad != null) {
				lilypad = null;
			} else if (this.grave != null) {
				grave = null;
			}
		} else {
			if (plant != null && plant.destroyGrave() && grave != null) {
				grave = null;
			} else if (plant != null && grave == null) {
				if (plant.isLilypad()) {
					this.lilypad = plant;
					return ;
				} else if (plant.isFlowerPot()) {
					this.flowerPot = plant;
					return ;
				}
			} 
			this.plant = plant;
		}
	}
	
	public void setGrave(Grave grave) {
		this.grave = Objects.requireNonNull(grave);
	}
	
	//Attaque
	
	public boolean isAttackable() {
		if (plant == null) {
			if (lilypad != null) {
				return lilypad.canBeAttacked();
			} else if (flowerPot != null) {
				return flowerPot.canBeAttacked();
			} else {
				return false;
			}
		} else {
			if (lilypad != null) {
				return plant.canBeAttacked() || lilypad.canBeAttacked();
			} else if (flowerPot != null) {
				return flowerPot.canBeAttacked() || lilypad.canBeAttacked();
			} else {
				return plant.canBeAttacked();
			}
		}
	}
	
	public boolean isPlantReadyToAttack(float gameSpeed) {
		if (plant == null) {
			throw new IllegalStateException();
		}
		return plant.isReadyToAttack(gameSpeed);
	}
	
	public void addProjectilesIfPossible(PVZView view, ArrayList<Zombie> zombieAlive, ProjectileList projectiles, int nbColumns, int nbLines, Map mapChoice) {
		Objects.requireNonNull(plant);
		if (detectZombies(view, zombieAlive) || !plant.mustDetectZombiesToAttack()) {
			Integer scaredRange = plant.getScaredRange();
			if (scaredRange != null) {//Peut etre effraye
				if ((int) rangeClosestZombies(view, zombieAlive).get(1) < scaredRange) {
					return ;
				}
			}
			int plantI = coord.getX();
			int plantJ = coord.getY();
			int fireI;
			int fireX = (int) view.middleXFromJ(plantJ);
			int fireY, fireDestinationLeftX = 0;
			boolean canAttackBehind = plant.canAttackBehind();
			int fireDestinationRightX = (int) view.xFromJ(plant.getJWithRange(plantJ, nbColumns, true));
			if (canAttackBehind) {
				fireDestinationLeftX = (int) view.xFromJ(plant.getJWithRange(plantJ, nbColumns, false));
			}
			boolean projectileImplyOnlyOneVictims = plant.projectileImplyOnlyOneVictims();
			boolean isParabola = plant.isProjectileParabola();
			for (int projectilesNumber = 0; projectilesNumber < plant.getProjectilesNumberByLine(); projectilesNumber++) {
				for (int k = plant.getBottomLineRange(); k <= plant.getTopLineRange(); k++) {
					fireI = plantI + k;
					if (0 <= fireI && fireI <= nbLines - 1) {
						fireY = (int) (view.yFromI(fireI));
						if (mapChoice.isRoof() && plantJ <= 2 && !plant.ignoreRoof()) {
							if (plant.summonClassicalProjectile()) {
								projectiles.add(new ClassicalProjectile(plant, fireX - projectilesNumber * 50, fireY, (int) view.xFromJ(plantJ+1), fireY, projectileImplyOnlyOneVictims, true, isParabola));
							} else {
								projectiles.add(new BoomerangProjectile(plant, fireX - projectilesNumber * 50, fireY, (int) view.xFromJ(plantJ+1), fireY));
							}		
						} else {
							if (plant.summonClassicalProjectile()) {
								projectiles.add(new ClassicalProjectile(plant, fireX - projectilesNumber * 50, fireY, fireDestinationRightX, fireY, projectileImplyOnlyOneVictims, true, isParabola));
							} else {
								projectiles.add(new BoomerangProjectile(plant, fireX - projectilesNumber * 50, fireY, fireDestinationRightX, fireY));
								System.out.println("--ok--");
							}
						}
						
						if (canAttackBehind) {
							if (plant.summonClassicalProjectile()) {
								projectiles.add(new ClassicalProjectile(plant, fireX, fireY, fireDestinationLeftX, fireY, projectileImplyOnlyOneVictims, false, isParabola));
							} else {
								projectiles.add(new BoomerangProjectile(plant, fireX, fireY, fireDestinationLeftX, fireY));
							}
						}
					}
				}
			}
			plant.doActionIfNecessary();
		}
	}
	
	private boolean detectZombies(PVZView view, ArrayList<Zombie> zombieAlive) {
		if (plant == null) {
			throw new IllegalStateException();
		} else if (zombieAlive.size() == 0) {
			return false;
		}
		Zombie zombie;
		int zombieLine, zombieColumn;
		int plantLine = coord.getX();
		int plantColumn = coord.getY();
		for (int i=0; i<zombieAlive.size(); i++) {
			zombie = zombieAlive.get(i);
			zombieLine = view.lineFromY(zombie.getY());
			zombieColumn = view.columnFromX(zombie.getX());
			for (int k = plant.getBottomLineRange(); k <= plant.getTopLineRange(); k++) {
				if (zombieLine == plantLine + k && zombie.canBeAttacked() && zombie.canBeAttack(plant)) { 		//S'ils sont sur la meme ligne
					if (plant.isInRange(zombieColumn - plantColumn)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private ArrayList<Object> rangeClosestZombies(PVZView view, ArrayList<Zombie> zombieAlive) {
		if (zombieAlive.size() == 0) {
			ArrayList<Object> res = new ArrayList<Object>();
			res.add(null);
			res.add(-1);
			return res;
		}
		Zombie zombie, closestZombie = null;
		int zombieLine, zombieColumn;
		int plantLine = coord.getX();
		int plantColumn = coord.getY();
		int result = -1, x;
		for (int i=0; i<zombieAlive.size(); i++) {
			zombie = zombieAlive.get(i);
			zombieLine = view.lineFromY(zombie.getY());
			zombieColumn = view.columnFromX(zombie.getX());
			for (int k = plant.getBottomLineRange(); k <= plant.getTopLineRange(); k++) {
				if (zombieLine == plantLine + k && zombie.canBeAttack(plant)) { 		//S'ils sont sur la meme ligne
					x = zombieColumn - plantColumn;
					if (result == -1 && x >= 0) {
						result = x;
						closestZombie = zombie;
					} else if (result != 1 && x >= 0 && x < result) {
						result = x;
						closestZombie = zombie;
					}
				}
			}
		}
		ArrayList<Object> res = new ArrayList<Object>();
		res.add(closestZombie);
		res.add(result);
		return res;
	}
	
	public int beAttacked(Zombie zombie) {
		if (plant != null) {
			zombie.attack(plant);
			int isPlantIlluminate = (plant.illuminate() ? (plant.illuminateLine() ? 2 : 1): 0);//2: illuminateLine, 1: illuminate, 0: no
			if (plant.isDead()) {
				plant = null;
			}
			return isPlantIlluminate;
		}
		if (lilypad != null) {
			zombie.attack(lilypad);
			if (lilypad.isDead()) {
				lilypad = null;
			}
		}
		if (flowerPot != null) {
			zombie.attack(flowerPot);
			if (flowerPot.isDead()) {
				flowerPot = null;
			}
		}
		return 0;
	}
	
	public boolean isDead() {
		boolean plantIsDead = true, lilypadIsDead = true;
		if (plant != null) {
			plantIsDead = plant.isDead();
		}
		if (lilypad != null) {
			lilypadIsDead = lilypad.isDead();
		}
		return plantIsDead && lilypadIsDead;
	}

	//Summon
	
	public boolean isReadyToSummons() {
		Objects.requireNonNull(plant);
		return plant.isReadyToSummon();
	}

	public void summon(PVZView view, SunList suns) {
		if (plant != null) {
			plant.summon((int) view.xFromJ(coord.getY()), (int) view.yFromI(coord.getX()), suns);
		} else {
			throw new IllegalStateException();
		}
	}
	
	public void graveSummons(BoardGame data, PVZView view, ArrayList<Zombie> waveZombieTeam, Set<String> selectedZombies) {
		if (grave != null) {
			grave.summon(data, waveZombieTeam, view, selectedZombies);
		} else {
			throw new IllegalStateException();
		}
	}
	
	protected void summonLadder() {
		this.hasLadder = true;
	}
	
	public void setFog(boolean isFog) {
		this.isFog = isFog;
	}

	protected void setAwakeShrooms() {
		if (plant == null) {
			return ;
		}
		plant.wakeUp();
	}
	
	//Getters
	
	public boolean isPlantIlluminate() {
		if (plant == null) { return false; }
		return plant.illuminate();
	}
	
	public boolean isPlantIlluminateLine() {
		if (plant == null) { return false; }
		return plant.illuminateLine();
	}
	
	public boolean hasLadder() {
		return hasLadder;
	}
	
	public boolean hasFog() {
		return isFog;
	}
	
	public void removeFlowerPot() {
		flowerPot = null;
	}
	
	//(pour PVZView)
	
	protected String getClassPlant() {
		if (plant != null) {
			return plant.printClass();
		} else if (flowerPot != null) {
			return flowerPot.printClass();
		} else if (lilypad != null) {
			return lilypad.printClass();
		}
		throw new IllegalStateException();
	}
		
	public Coordinates getCoords() {
		return coord;
	}
	
	public boolean hasLilyPad() {
		return lilypad != null;
	}
	
	public boolean hasPlant() {
		return plant != null;
	}
	
	public boolean hasShroom() {
		return plant.isShroom();
	}
	
	public boolean hasGrave() {
		return grave != null;
	}
	
	public boolean hasFlowerPot() {
		return flowerPot != null;
	}
	
	public boolean preventFromJump() {
		Objects.requireNonNull(plant);
		return plant.preventFromJump();
	}

	public boolean isPlantDoubleDamages() {
		if (plant == null) {
			return false;
		}
		return plant.doubleDamages();
	}

}
