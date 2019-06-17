package control;

import java.io.Serializable;
import java.util.ArrayList;
import entities.Plant;
import entities.Zombie;

public class Line implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ArrayList<Cell> cellList;
	private final boolean isWater;
	
	public Line(int y, int nbColums, boolean isWater, boolean isRoof, boolean isFog, ArrayList<Cell> specialItemsList) {
		this.isWater = isWater;
		cellList = new ArrayList<Cell>();
		for (int i = 0; i < nbColums; i++) {
			if (isRoof) {
				cellList.add(new Cell(new Coordinates(y, i), true, false));
			} else if (isFog && 5 <= i) {
				Cell cell = new Cell(new Coordinates(y, i), false, true);
				cellList.add(cell);
				specialItemsList.add(cell);
			} else {
				cellList.add(new Cell(new Coordinates(y, i), false, false));
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder res = new StringBuilder("Line (length " + cellList.size() + ")\n");
		
		for (Cell cell : cellList) {
			res.append(cell.toString());
		}
		return res.toString();
	}
	
	//Methodes utilitaires
	
	//Retoune la Cell la plus proche du zombie (qu'il peut attaquer) et qui possede une plante (gauche)
	public Cell getFirstPlantCell(Zombie zombie, int zombieColumn) {
		Cell c;
		for (int i=zombieColumn; 0 <= i; i--) {
			c = cellList.get(i);
			if (c.isAttackable() && zombie.isInRange(zombieColumn - i)) { 		//S'ils sont sur la meme ligne
				return c;
			}
		}
		return null;
	}
	
	public Cell getLastPlantCell(Zombie zombie, int zombieColumn) {
		Cell c;
		for (int i=0; i < zombieColumn; i++) {
			c = cellList.get(i);
			if (c.isAttackable() && zombie.isInRange(zombieColumn - i)) { 		//S'ils sont sur la meme ligne
				return c;
			}
		}
		return null;
	}
	
	//Placer des LivingBeing
	
	public boolean canPlantBePlaced(int column, Plant plant) {
		if (!(0 <= column && column < cellList.size())) {
			throw new IllegalStateException("Index out of range");
		}
		if (plant != null && plant.canSetAwakeShrooms() && cellList.get(column).hasPlant() && cellList.get(column).hasShroom()) {
			return true;
		}
		return cellList.get(column).canBePlaced(plant, isWater);
	}
	
	public boolean canZombieBePlaced(Zombie zombie) {
		return (isWater && zombie.mustBeOnWater()) || (!isWater && !zombie.mustBeOnWater());
	}
	
	public void setPlant(int nbColums, Plant plant) throws IllegalStateException {
		if (0 <= nbColums && nbColums < cellList.size()) {
			cellList.get(nbColums).setPlant(plant);
		} else {
			throw new IllegalStateException();
		}
	}

	//Brouillard
	
	public void updateLineFog(boolean bool) {
		for (Cell cell : cellList) {
			cell.setFog(bool);
		}
	}
	
	//Getters
	
	protected Cell getElement(int j) {
		if (0 <= j && j < cellList.size()) {
			return cellList.get(j);
		} else {
			throw new IllegalStateException();
		}
	}
	
	public int getNbColumns() {
		return cellList.size();
	}
}
