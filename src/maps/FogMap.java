package maps;

import java.util.ArrayList;

import control.Cell;
import control.Line;

public class FogMap extends Map {
	private static final long serialVersionUID = 1L;
	
	private static final ArrayList<String> zombieClasses = new ArrayList<String>();

	public FogMap() {
		super(6, 9);
	}
	
	@Override
	public boolean isDayTime() {
		return true;
	}
	
	@Override
	public boolean isRoof() {
		return false;
	}
	
	@Override
	public ArrayList<String> getZombieClasses() {
		return zombieClasses;
	}
	
	@Override
	public String printClass() {
		return "FogMap";
	}
	
	@Override
	public Line buildLine(int i, ArrayList<Cell> specialItemsList) {
		return new Line(i, getNbColumns(), (i == 2 || i == 3), false, true, specialItemsList);
	}
}
