package control.projectile;

import java.util.ArrayList;

import control.BoardGame;
import control.Coordinates;
import display.PVZView;
import entities.Plant;
import entities.Zombie;

public class ClassicalProjectile extends Projectile {
	private static final long serialVersionUID = 1L;
	private final Coordinates destinationCoordinates;
	private Coordinates actualCoordinates;
	//private final int initialX;
	private boolean rightDirection;
	private final boolean isParabola;

	public ClassicalProjectile(Plant violentPlant, int xBegin, int yBegin, int xEnd, int yEnd,
			Boolean disappearedAfterOneAttack, boolean rightDirection, boolean isParabola) {
		super(violentPlant, xBegin, yBegin, xEnd, yEnd, disappearedAfterOneAttack, rightDirection);
		actualCoordinates = new Coordinates(xBegin, yBegin);
		destinationCoordinates = new Coordinates(xEnd, yEnd);
		//initialX = xBegin;
		this.rightDirection = rightDirection;
		this.isParabola = isParabola;
	}
	
	@Override
	public void move(BoardGame data, PVZView view, int distance, float gameSpeed) {
		int i = view.lineFromY(actualCoordinates.getY()), j = view.columnFromX(actualCoordinates.getX());
		if (data.lineExists(i) && data.columnExists(j) && data.isPlantDoubleDamages(i, j)) {
			setDoubleDamages(true);
		}
		int k = rightDirection ? 1 : -1;
		actualCoordinates = actualCoordinates.advanceX(distance * k * gameSpeed);
		if (isParabola) {
			//actualCoordinates = actualCoordinates.advanceY((initialX + destinationCoordinates.getX()) / 2 < actualCoordinates.getX() ? 1 : -1);
		}
	}
	
	@Override
	public boolean attack(PVZView view, BoardGame data, ArrayList<Zombie> zombieAlive, float gameSpeed) {
		return super.attack(view, data, zombieAlive, gameSpeed, view.lineFromY(actualCoordinates.getY()), view.columnFromX(actualCoordinates.getX()));
	}
	
	@Override
	public boolean hasArrivedToDestination(int distance, float gameSpeed) {
		int destinationX = destinationCoordinates.getX();
		return (rightDirection && actualCoordinates.getX() + distance * gameSpeed > destinationX) || (!rightDirection && actualCoordinates.getX() - distance * gameSpeed < destinationX);
	}
	
	@Override
	public Coordinates getActualCoords() {
		return actualCoordinates;
	}
	
	protected void setRightDirection(boolean rightDirection) {
		this.rightDirection = rightDirection;
	}
}
