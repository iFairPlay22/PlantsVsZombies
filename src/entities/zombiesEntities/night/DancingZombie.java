package entities.zombiesEntities.night;

import java.util.ArrayList;

import control.BoardGame;
import control.Coordinates;
import control.Time;
import display.PVZView;
import entities.Zombie;

public class DancingZombie extends Zombie {
	private static final long serialVersionUID = 1L;
	
	private final Time timerBetweenSummons;
	private final static int timeBetweenNextSummons = 10;

	public DancingZombie(Coordinates coord) {
		super(340, 150, 2, 0, coord, 2); //lifePoints, attackPoints, attackSpeed, canAttackBehind, range, coordinateX, int coordinateY, moveSpeed
		timerBetweenSummons = new Time();
	}
	
	@Override
	public String printClass() {
		return "DancingZombie";
	}
	
	@Override
	public boolean isReadyToSummon() {
		return timerBetweenSummons.toSeconds() >= timeBetweenNextSummons;
	}
	
	@Override
	public void summon(BoardGame data, PVZView view, ArrayList<Zombie> zombieAlive) {
		if (!(timerBetweenSummons.toSeconds() >= timeBetweenNextSummons)) {
			throw new IllegalStateException("Impossible to summon !");
		}
		int zombieX = super.getX();
		int zombieY = super.getY();
		int squareSize = view.getSquareSize();
		int x = zombieX - squareSize;
		int y;
		BackupDancer backupDancer;
		if (view.isXInBoardgame(x)) {
			x = (int) view.xFromJ(view.columnFromX(x));
			y = zombieY - squareSize;
			if (view.isYInBoardgame(y)) {
				backupDancer = new BackupDancer(new Coordinates(x, (int) view.yFromI(view.lineFromY(y))));
				if (data.canZombieBePlaced(view, backupDancer)) {
					zombieAlive.add(backupDancer);
				}
			}
			y = zombieY + squareSize;
			if (view.isYInBoardgame(y)) {
				backupDancer = new BackupDancer(new Coordinates(x, (int) view.yFromI(view.lineFromY(y))));
				if (data.canZombieBePlaced(view, backupDancer)) {
					zombieAlive.add(backupDancer);
				}
			}
		}
		x = zombieX + squareSize;
		if (view.isXInBoardgame(x)) {
			x = (int) view.xFromJ(view.columnFromX(x));
			y = zombieY - squareSize;
			if (view.isYInBoardgame(y)) {
				backupDancer = new BackupDancer(new Coordinates(x, (int) view.yFromI(view.lineFromY(y))));
				if (data.canZombieBePlaced(view, backupDancer)) {
					zombieAlive.add(backupDancer);
				}
			}
			y = zombieY + squareSize;
			if (view.isYInBoardgame(y)) {
				backupDancer = new BackupDancer(new Coordinates(x, (int) view.yFromI(view.lineFromY(y))));
				if (data.canZombieBePlaced(view, backupDancer)) {
					zombieAlive.add(backupDancer);
				}
			}
		}
		timerBetweenSummons.restart();
	}
}
