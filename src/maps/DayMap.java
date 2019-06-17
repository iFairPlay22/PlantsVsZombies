package maps;

import java.util.ArrayList;

import control.Cell;
import control.Line;

public class DayMap extends Map  {
	private static final long serialVersionUID = 1L;
	
	private static final ArrayList<String> zombieClasses = new ArrayList<String>();

	public DayMap() {
		super(5, 9);
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
	public String printClass() {
		return "DayMap";
	}
	
	@Override
	public ArrayList<String> getZombieClasses() {
		return zombieClasses;
	}
	
	@Override
	public Line buildLine(int i, ArrayList<Cell> specialItemsList) {
		return new Line(i, getNbColumns(), false, false, false, specialItemsList);
	}
}
