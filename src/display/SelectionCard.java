package display;

import java.io.Serializable;

import control.Coordinates;

public class SelectionCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Coordinates coordinates;
	private final int cardSize;
	
	public SelectionCard(Coordinates coordinates, int cardSize) {
		this.coordinates=coordinates;
		this.cardSize=cardSize;
	}
	
	@Override
	public String toString() {
		return "Card at " + coordinates.toString() + " with a size of: " + cardSize;
	}
	
	public boolean clickOnCard(Coordinates c) {
		int x = (int) c.getX();
		int y = c.getY();
		int xThis = (int) coordinates.getX();
		int yThis = coordinates.getY();
		
		if ((xThis <= x && x <= xThis + cardSize && (yThis <= y && y <= yThis + cardSize))) {
			return true;
		}
		return false;
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}
}
