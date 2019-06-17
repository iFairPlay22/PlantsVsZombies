package control.projectile;

import entities.Plant;

public class BoomerangProjectile extends ClassicalProjectile {
	private static final long serialVersionUID = 1L;
	
	private final int xBegin;
	private boolean rightDirection;
	
	public BoomerangProjectile(Plant violentPlant, int xBegin, int yBegin, int xEnd, int yEnd) {
		super(violentPlant, xBegin, yBegin, xEnd, yEnd, false, true, false);
		this.xBegin = xBegin;
		rightDirection = true;
	}
	
	@Override
	public boolean hasArrivedToDestination(int distance, float gameSpeed) {
		if (rightDirection && super.hasArrivedToDestination(distance, gameSpeed)) {
			rightDirection = false;
			setRightDirection(false);
		} else if (rightDirection == false) {
			return super.getActualCoords().getX() - distance * gameSpeed < xBegin;
		}
		return false;
	}
}
