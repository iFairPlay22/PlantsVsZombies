package entities;

import control.SunList;

public interface SunSpawner {
	
	public boolean isReadyToSummon();
	public void summon(int x, int y, SunList suns);
	public boolean canSpawnSun(int gameSpeed);
}
