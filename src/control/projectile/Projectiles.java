package control.projectile;

import java.util.ArrayList;

import control.BoardGame;
import control.Coordinates;
import display.PVZView;
import entities.Zombie;

public interface Projectiles {
	public void move(BoardGame data, PVZView view, int distance, float gameSpeed);
	
	public boolean hasArrivedToDestination(int distance, float gameSpeed);
	
	public boolean attack(PVZView view, BoardGame data, ArrayList<Zombie> zombieAlive, float gameSpeed);
	
	public Coordinates getActualCoords();
}
