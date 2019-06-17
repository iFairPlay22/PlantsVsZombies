package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import display.PVZView;
import entities.Zombie;

public class WaveGenerator implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final ZombieGenerator genesis;
	private ArrayList<Zombie> wave;
	private int index;
	private int timeBetweenNextWave;
	private int timeBetweenZombieSpawn;
	private final Time timeSpawnZombie;
	private final Time timeSpawnWave;
	
	public WaveGenerator() {
		genesis = new ZombieGenerator();
		wave = new ArrayList<>();
		index = 0;
		timeBetweenNextWave = 0;
		timeBetweenZombieSpawn = 0;
		timeSpawnZombie = new Time();
		timeSpawnWave = new Time();
	}

	public void generateNewWave(PVZView view, BoardGame data, Set<String> selectedZombies, int nbZombies, int timeBetweenNextWave, int timeBetweenZombieSpawn) {
		if (wave.size() != index && timeBetweenNextWave < 0 && timeBetweenZombieSpawn < 0) {
			throw new IllegalStateException();
		}
		this.timeBetweenNextWave = timeBetweenNextWave;
		this.timeBetweenZombieSpawn = timeBetweenZombieSpawn;
		index = 0;
		wave = genesis.randZombieList(view, selectedZombies, nbZombies, data);
		timeSpawnWave.restart();
	}
	
	public void spawnZombie(ArrayList<Zombie> zombiesAlive) {
		if (!(index < wave.size() && timeBetweenZombieSpawn <= timeSpawnZombie.toSeconds() &&  timeBetweenNextWave <= timeSpawnWave.toSeconds())) {
			throw new IllegalStateException();
		}
		if (index == 0) {
			
		}
		zombiesAlive.add(wave.get(index));
		timeSpawnZombie.restart();
		index++;
	}
	
	public boolean isWaveEmpty() {
		return wave.size() == index;
	}
	
	public boolean canSpawnZombie() {
		return index < wave.size() && timeBetweenZombieSpawn <= timeSpawnZombie.toSeconds() &&  timeBetweenNextWave <= timeSpawnWave.toSeconds();
	}
	
	@Override
	public String toString() {
		return "WaveGenerator";
	}
}
