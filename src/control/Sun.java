package control;

import java.io.Serializable;

import display.PVZView;

public class Sun implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Coordinates coordinates;
	private final int finalPosition;
	private final int ressources;
	
	
	public Sun(int x, int y, int ressources) {
		coordinates = new Coordinates(x, y);
		finalPosition = y;
		if (ressources < 0) {
			throw new IllegalStateException("Ressources can't be inferior to 0");
		}
		this.ressources = ressources;
	}
	
	public Sun(BoardGame data, PVZView view) {
		coordinates = new Coordinates((int) view.xFromJ(BoardGame.randint(0, data.getNbColumns()-1)), 0);
		finalPosition = (int) view.yFromI(BoardGame.randint(0, data.getNbLines()-1));
		ressources = 50;
	}
	
	public int clicked(Coordinates coord) {
		int xThis = (int) coordinates.getX();
		int yThis = coordinates.getY();
		int x = (int) coord.getX();
		int y = coord.getY();
		
		if (xThis <= x && x <= xThis + 100 && (yThis <= y && y <= yThis + 100) ) {
			System.out.println("Sun is clicked !");
			return ressources;
		}
		return -1;
	}
	
	public boolean hasReachedPosition() {
		if (coordinates.getY() >= finalPosition) {
			return true;
		} else {
			return false;
		}
	}
	
	public void advance(float gameSpeed) {
		coordinates.advanceY((int) gameSpeed * 5);
	}
	
	public Coordinates getCoordinates() {
		return coordinates;
	}

}
