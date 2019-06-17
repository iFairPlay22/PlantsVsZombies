package control;

import java.io.Serializable;
import java.util.Objects;

public class Coordinates implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int x;
	private int y;

	
	public Coordinates(int x, int y) {
		this.y = y;
		this.x = x;
	}

	
	public int getY() {
		return y;
	}

	
	public int getX() {
		return x;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Coordinates)) {
			return false;
		}
		Coordinates cc = (Coordinates) o;
		return y == cc.y && x == cc.x;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	
	public Coordinates advanceX(double speed) {
		x += speed;
		return this;
	}


	public Coordinates advanceY(int speed) {
		y += speed;
		return this;
	}
}

