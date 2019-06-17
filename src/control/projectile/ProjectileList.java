package control.projectile;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import control.BoardGame;
import display.PVZView;
import entities.Zombie;

public class ProjectileList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final int distance;
	private final ArrayList<Projectiles> projectiles;
	
	public ProjectileList(int squareSize) {
		projectiles = new ArrayList<>();
		distance = squareSize;
	}
	
	public void add(Projectiles p) {
		projectiles.add(Objects.requireNonNull(p));
	}
	
	public void moveProjectiles(BoardGame data, PVZView view, float gameSpeed) {
		Projectiles projectile;
		int i = 0;
		while (i < projectiles.size()) {
			projectile = projectiles.get(i);
			if (projectile.hasArrivedToDestination(distance, gameSpeed)) {
				projectiles.remove(projectile);
			} else {
				projectile.move(data, view, distance, gameSpeed);
			}
			i++;
		}
	}

	public void AttackIfPossible(PVZView view, BoardGame data, ArrayList<Zombie> zombieAlive, float gameSpeed) {
		Projectiles projectile;
		int i = 0;
		while (i < projectiles.size()) {
			projectile = projectiles.get(i);
			if (projectile.attack(view, data, zombieAlive, gameSpeed)) {
				projectiles.remove(projectile);
			}
			i++;
		}
	}
	
	public void drawProjectiles(@SuppressWarnings("exports") Graphics2D graphics, PVZView view) {
		view.drawProjectiles(graphics, projectiles);
	}
}
