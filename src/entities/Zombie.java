package entities;


import java.util.ArrayList;

import control.BoardGame;
import control.Cell;
import control.Coordinates;
import display.PVZView;

public class Zombie extends LivingBeing {
	private static final long serialVersionUID = 1L;
	private Coordinates coord;
	private final int moveSpeed;
	private boolean jumpFirstEnnemies;
	private boolean isFlying;
	
	public Zombie(int lifePoints, int attackPoints, int attackSpeed, int range, Coordinates coord, int moveSpeed) {
		super(lifePoints, attackPoints, attackSpeed, range);
		this.coord = coord;
		this.moveSpeed = moveSpeed;
		jumpFirstEnnemies = false;
		isFlying = false;
	}
	
	public Zombie(int lifePoints, int attackPoints, int attackSpeed, int range, Coordinates coord, int moveSpeed, boolean jumpFirstEnnemies, boolean canBeAttack, Integer timeBetweenScared) {
		super(lifePoints, attackPoints, attackSpeed, range, canBeAttack, timeBetweenScared);
		this.coord = coord;
		this.moveSpeed = moveSpeed;
		this.jumpFirstEnnemies = jumpFirstEnnemies;
	}

	public Zombie(int lifePoints, int attackPoints, int attackSpeed, int range, Coordinates coord, int moveSpeed, boolean isFlying) {
		this(lifePoints, attackPoints, attackSpeed, range, coord, moveSpeed);
		this.isFlying = isFlying;
	}

	@Override
	public String printClass() {
		return "Zombie";
	}
	
	//toString
	
	@Override
	public String toString() {
		return super.toString() + " :\ncoordinateX: " + coord.getX() + "\ncoordinateY: " + coord.getY() + "\nmoveSpeed: " + moveSpeed ;
	}
	
	public String toCoordinates() {
		return "(" + coord.getX() + "," + coord.getY() + ")"; 
	}
	
	//Deplacement horizontal
	
	public boolean advance(PVZView view, Cell cell, float gameSpeed, boolean isPlantTeam, boolean hasAttack) {
		if (!super.isUnmoved() && (hasAttack || jumpFirstEnnemies)) {
			int xZombie = coord.getX();
			int xPlant = cell.getCoords().getY();
			int direction = isPlantTeam ? -1 : 1;
			if (!isPlantTeam && (jumpFirstEnnemies || cell.hasLadder())) {
				if (cell.isAttackable() && !cell.preventFromJump()) {
					coord = coord.advanceX(-2 * direction * view.getSquareSize());
					if (!cell.hasLadder()) {
						jumpFirstEnnemies = false;	
					}
					return true;
				}
			} 
			if (isPlantTeam || (!isPlantTeam && (!cell.isAttackable() || !(view.middleXFromJ(xPlant) < xZombie)))) {
				coord = coord.advanceX(-1 * direction * moveSpeed * isScared(gameSpeed) / (super.isSlow() ? 2 : 1));
				return true;
			}
		}
		return false;
	}
	
	//Prochain x apres deplacement horizontal
	public float nextX(float gameSpeed, boolean isPlantTeam) {
		return coord.getX() + -1 * (isPlantTeam ? -1 : 1) * moveSpeed * isScared(gameSpeed) / (super.isSlow() ? 2 : 1);
	}
	
	//Deplacement vertical (oignon)
	
	public void changeLine(PVZView view, BoardGame data) {
		int line = view.lineFromY(getY());
		int squareSize = view.getSquareSize();
		boolean case1 = false;
		if (data.lineExists(line - 1)) {
			case1 = data.canBePlacedInLine(line - 1, this);
		}
		boolean case2 = false;
		if (data.lineExists(line + 1)) {
			case2 = data.canBePlacedInLine(line + 1, this);
		}
		if (case1 && case2) {
			coord = coord.advanceY(((BoardGame.randint(0, 1) == 0) ? -1 : 1) * squareSize);
		} else if (case1) {
			coord = coord.advanceY(-1 * squareSize);
		} else if (case2) {
			coord = coord.advanceY(squareSize);
		} else {
			System.out.println("Impossible to be moved!");
		}
		coord.advanceX(-squareSize);
	}
	
	//Attaque
	
	//Team des zombies
	public ArrayList<Cell> getVictims(Zombie zombie, BoardGame data, int zombieLine, int zombieColumn) {
		ArrayList<Cell> victims = new ArrayList<>();
		Cell c = data.getFirstPlantCell(zombie, zombieLine, zombieColumn);
		if (c != null) {
			victims.add(c);
		}
		return victims;
	}
	
	//Team des plantes (HypnoShroom)
	public ArrayList<Zombie> getZombieVictims(PVZView view, ArrayList<Zombie> zombieAlive, Zombie zombie) {
		ArrayList<Zombie> victims = new ArrayList<Zombie>();
		ArrayList<Object> rangeAndZombie = rangeClosestZombies(view, zombieAlive);
		Zombie victim = (Zombie) rangeAndZombie.get(0);
		if (victim != null && isInRange((int) rangeAndZombie.get(1))) {
			victims.add(zombie);
		}
		return victims;
	}
	
	//Zombie le plus proche
	public ArrayList<Object> rangeClosestZombies(PVZView view, ArrayList<Zombie> zombieAlive) {
		ArrayList<Object> arrayList = new ArrayList<>();
		if (zombieAlive.size() == 0) {
			arrayList.add(null);
			arrayList.add(-1);
			return arrayList;
		}
		Zombie closestZombie = null;
		int zombieX, zombieY;
		int thisX = view.columnFromX(coord.getX());
		int thisY = view.lineFromY(coord.getY());
		int distance = -1, x;
		for (Zombie zombie : zombieAlive) {
			zombieX = view.columnFromX(zombie.coord.getX());
			zombieY = view.lineFromY(zombie.coord.getY());
			if (zombieY == thisY) { 		//S'ils sont sur la meme ligne
				x = zombieX - thisX;
				if (distance == -1 && x >= 0) {
					distance = x;
					closestZombie = zombie;
				} else if (distance != 1 && x >= 0 && x < distance) {
					distance = x;
					closestZombie = zombie;
				}
			}
		}
		arrayList.add(closestZombie);//ArrayList<Object>[0] --> zombie le plus proche
		arrayList.add(distance);//ArrayList<Object>[1] --> distance entre les deux zombies
		return arrayList;
	}
	
	//FlyingZombie
	public void fall() {
		isFlying = false;
	}
	
	//Getters
	
	public boolean canBeAttack(Plant plant) {
		return super.canBeAttacked() && ((isFlying && plant.canAttackInTheSky()) || (!isFlying));
	}
	
	public int getX() {
		return coord.getX();
	}
	
	public int getY() {
		return coord.getY();
	}
}
