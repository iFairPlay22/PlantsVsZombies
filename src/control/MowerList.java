package control;

import java.io.Serializable;
import java.util.ArrayList;

import display.PVZView;
import entities.Mower;
import entities.Zombie;

public class MowerList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ArrayList<Mower> activeMowers;
	private final int activeMowersSize;
	private final int xEnd;
	
	public MowerList(BoardGame data, PVZView view) {
		int xOrigin = view.getXOrigin();
		xEnd = (data.getNbColumns() - 1) * view.getSquareSize() + xOrigin;
		activeMowers = new ArrayList<Mower>();
		activeMowersSize = data.getNbLines();
		for (int i = 0; i < activeMowersSize; i++) {
			activeMowers.add(new Mower(xOrigin, (int) view.yFromI(i)));
		}
	}
	
	public int size() {
		return activeMowersSize;
	}


	public boolean isReadyToMove(int i) {
		if (!(0 <= i && i < activeMowersSize)) {
			throw new IllegalStateException("Index out of range !");
		}
		if (activeMowers.get(i) == null) {
			return false;
		}
		return activeMowers.get(i).isNotActivated();
	}
	
	public void setMowerAvailable(int i) {
		if (!(0 <= i && i < activeMowersSize)) {
			throw new IllegalStateException("Index out of range !");
		}
		if (activeMowers.get(i) == null) {
			throw new IllegalStateException("Null pointer exception !");
		}
		Mower mover = activeMowers.get(i);
		if (!mover.isNotActivated()) {
			throw new IllegalStateException("Mower already used !");
		}
		mover.activate();
	}
	
	public void advance() {
		Mower mower;
		for (int i = 0; i < activeMowersSize; i++) {
			mower = activeMowers.get(i);
			if (mower != null) {
				if (mower.advanceX(xEnd)) {
					activeMowers.set(i, null);
				}
			}
		}
	}
	
	public Coordinates getCoords(int i) {
		if (!(0 <= i && i < activeMowersSize)) {
			throw new IllegalStateException("Index out of range !");
		}
		if (activeMowers.get(i) == null) {
			throw new IllegalStateException("Null pointer exception !");
		}
		return activeMowers.get(i).getCoords();
	}
	
	public boolean isNotUsed(int i) {
		if (!(0 <= i && i < activeMowersSize)) {
			throw new IllegalStateException("Index out of range !");
		}
		return activeMowers.get(i) != null;
	}
	
	public void attackIfPossible(PVZView view, ArrayList<Zombie> zombiesAlive) {
		int line;
		Mower mower;
		Zombie zombie;
		int i = 0;
		while (i < zombiesAlive.size()) {
			zombie = zombiesAlive.get(i);
			line = view.lineFromY(zombie.getY());
			mower = activeMowers.get(line);
			if (mower != null) {
				mower.attackIfPossible(zombiesAlive, zombie);
			}
			i++;
		}
	}
}
