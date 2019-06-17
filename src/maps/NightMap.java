package maps;

import java.util.ArrayList;

import control.Cell;
import control.Line;

public class NightMap extends Map {
	private static final long serialVersionUID = 1L;
	
	private static final ArrayList<String> zombieClasses = new ArrayList<String>();

	public NightMap() {
		super(5, 9);
	}
	
	@Override
	public boolean isDayTime() {
		return false;
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
		return "NightMap";
	}
	
	@Override
	public Line buildLine(int i, ArrayList<Cell> specialItemsList) {
		return new Line(i, getNbColumns(), false, false, false, specialItemsList);
	}

}
