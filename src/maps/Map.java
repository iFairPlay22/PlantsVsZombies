package maps;

import java.io.Serializable;
import java.util.ArrayList;

import control.BoardGame;
import control.Cell;
import control.Coordinates;
import control.Line;
import display.CardList;

public class Map implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int nbLines;
	private final int nbColumns;
	
	protected Map(int nbLines, int nbColumns) {
		this.nbLines = nbLines;
		this.nbColumns = nbColumns;
	}
	
	public int getNbLines() {
		return nbLines;
	}
	
	public int getNbColumns() {
		return nbColumns;
	}

	public boolean isDayTime() {
		return true;
	}
	
	public boolean isRoof() {
		return false;
	}
	
	public ArrayList<String> getZombieClasses() {
		return null;
	}

	public Line buildLine(int i, ArrayList<Cell> specialItemsList) {
		return null;
	}

	public String printClass() {
		return "Map";
	}

	public void placeFlowerPots(BoardGame data, ArrayList<Coordinates> plantsCoordinates) {
		
	}

}
