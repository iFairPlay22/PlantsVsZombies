package entities.zombiesEntities.roof;

import java.util.ArrayList;

import control.BoardGame;
import control.Cell;
import control.Coordinates;
import display.PVZView;
import entities.Zombie;

public class LadderZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	private boolean hasLadder = true;
	
	public LadderZombie(Coordinates coord) {
		super(400, 50, 2, 0, coord, 2);
	}
	
	@Override
	public String printClass() {
		return "LadderZombie";
	}
	
	@Override
	public boolean isReadyToSummon() {
		return hasLadder;
	}
	
	@Override
	public void summon(BoardGame data, PVZView view, ArrayList<Zombie> zombieAlive) {
		if (!hasLadder) {
			return ;
		}
		int line = view.lineFromY(this.getY()), column = view.columnFromX(this.getX());
		Cell cell = data.getElement(line, column);
		if (cell.isAttackable() && !cell.hasLadder()) {
			data.addLadder(line, column);
			hasLadder = false;
		}
	}
}
