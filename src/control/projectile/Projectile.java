package control.projectile;

import java.io.Serializable;
import java.util.ArrayList;

import control.BoardGame;
import control.Coordinates;
import display.PVZView;
import entities.Plant;
import entities.Zombie;

public abstract class Projectile implements Projectiles, Serializable {
	private static final long serialVersionUID = 1L;
	
	private final Plant violentPlant;
	private final boolean disappearedAfterOneAttack;
	private boolean doubleDamages;
	
	public Projectile(Plant violentPlant, int xBegin, int yBegin, int xEnd, int yEnd, boolean disappearedAfterOneAttack, boolean rightDirection) { //ArrayList<Zombie> victimsList
		this.violentPlant = violentPlant;
		this.disappearedAfterOneAttack = disappearedAfterOneAttack;
		doubleDamages = false;
	}

	public void setDoubleDamages(boolean doubleDamages) {
		this.doubleDamages = doubleDamages;
	}
	
	public boolean attack(PVZView view, BoardGame data, ArrayList<Zombie> zombieAlive, float gameSpeed) {
		return false;
	}
	
	public boolean attack(PVZView view, BoardGame data, ArrayList<Zombie> zombieAlive, float gameSpeed, int actualI, int actualJ) {
		Zombie zombie;
		int zombieI, zombieJ;
		Integer damages;
		
		int i = 0;
		while (i<zombieAlive.size()) {
			zombie = zombieAlive.get(i);
			if (zombie.canBeAttacked()) {
				zombieI = view.lineFromY(zombie.getY());
				zombieJ = view.columnFromX(zombie.getX());
				if (actualI <= zombieI && zombieI <= actualI + (doubleDamages || violentPlant.groupDamages() ? 1 : 0) && actualJ == zombieJ) {
					if (violentPlant.tranformZombies()) {
						data.transformZombie(zombieAlive, zombie);
					} else if (violentPlant.changeZombieLine()) {
						zombie.changeLine(view, data);
					} else {
						damages = violentPlant.attack(zombie);
						if (doubleDamages) {
							damages += violentPlant.attack(zombie);
						}
						System.out.println(zombie.printClass() + " lost " + damages + " lifepoints !");
						//view.string(graphics, Integer.toString(damages), zombie.getX(), zombie.getY());
						if (zombie.isDead()) {
							zombieAlive.remove(zombie);
						}
						if (disappearedAfterOneAttack) {
							return true;
						}
					}
				}
			}
			i++;
		}
		return false; //retouner si le projectile doit disparaitre ou non
	}
	
	public Coordinates getActualCoords() {
		return null;
	}

	public boolean hasArrivedToDestination(int distance, float gameSpeed) {
		return false;
	}
}
