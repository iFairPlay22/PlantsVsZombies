package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import control.BoardGame;
import control.Time;
import control.ZombieGenerator;
import display.PVZView;

public class Grave implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final ZombieGenerator zombieGenerator = new ZombieGenerator();
	
	private final Time waitTime = new Time();
	private final Time summonTime = new Time();
	
	private final int x;
	private final int y;

	public Grave(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	@Override
	public String toString() {
		return "Grave";
	}
	
	public void summon(BoardGame data, ArrayList<Zombie> waveZombieTeam, PVZView view, Set<String> selectedZombies) {
		if (5 <= waitTime.toSeconds()) {
			if (BoardGame.randint(0, 3) == 0) {
				if (BoardGame.randint(15, 25) <= summonTime.toSeconds()) {
					Zombie zombie = zombieGenerator.randZombie(view, data, selectedZombies, 1, y, x, 0, 0, 0);
					waveZombieTeam.add(zombie);
					System.out.println(zombie.printClass() + "has been sumon in Grave (" + x + ", " + y + ")");
					summonTime.restart();
				}
			}
			waitTime.restart();
		}
	}
}
