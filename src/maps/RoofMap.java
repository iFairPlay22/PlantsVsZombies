package maps;

import java.util.ArrayList;

import control.BoardGame;
import control.Cell;
import control.Coordinates;
import control.Line;
import display.CardList;
import entities.plantsEntities.roof.FlowerPot;

public class RoofMap extends Map {
	private static final long serialVersionUID = 1L;
	
	private static final ArrayList<String> zombieClasses = new ArrayList<String>();

	public RoofMap() {
		super(5, 9);
	}
	
	@Override
	public boolean isDayTime() {
		return true;
	}
	
	@Override
	public boolean isRoof() {
		return true;
	}
	
	@Override
	public ArrayList<String> getZombieClasses() {
		return zombieClasses;
	}
	
	@Override
	public String printClass() {
		return "RoofMap";
	}
	
	@Override
	public Line buildLine(int i, ArrayList<Cell> specialItemsList) {
		return new Line(i, getNbColumns(), false, true, false, specialItemsList);
	}
	
	@Override
	public void placeFlowerPots(BoardGame data, ArrayList<Coordinates> plantsCoordinates) {
		for (int i = 0; i <= 2; i++) {
			for (int j = 0; j < data.getNbLines(); j++) {
				data.setPlant(new Coordinates(j, i), new FlowerPot());
				plantsCoordinates.add(new Coordinates(i, j));
			}
		}
	}
}
