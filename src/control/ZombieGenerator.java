package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;

import display.PVZView;
import entities.Zombie;
import entities.zombiesEntities.day.BucketheadZombie;
import entities.zombiesEntities.day.ClassicalZombie;
import entities.zombiesEntities.day.ConeHeadZombie;
import entities.zombiesEntities.day.FlagZombie;
import entities.zombiesEntities.day.PoleVaultingZombie;
import entities.zombiesEntities.day.TrashCanZombie;
import entities.zombiesEntities.fog.JackInTheBoxZombie;
import entities.zombiesEntities.fog.PogoZombie;
import entities.zombiesEntities.night.DancingZombie;
import entities.zombiesEntities.night.FootballZombie;
import entities.zombiesEntities.night.NewspaperZombie;
import entities.zombiesEntities.night.ScreenDoorZombie;
import entities.zombiesEntities.pool.DolphinRiderZombie;
import entities.zombiesEntities.pool.SnorkelZombie;
import entities.zombiesEntities.roof.BungeeZombie;
import entities.zombiesEntities.roof.CatapultZombie;
import entities.zombiesEntities.roof.FlyingZombie;
import entities.zombiesEntities.roof.LadderZombie;

public class ZombieGenerator implements Serializable {
	private static final long serialVersionUID = 1L;

	public Zombie randZombie(PVZView view, BoardGame data, Set<String> selectedZombies, int lengthList, int nbLines, int nbColumns, int squareSize, int xOrigin, int yOrigin) {
		Zombie zombie;
		do {
		
			String string = randomSetElement(selectedZombies);
			Coordinates coordinates;
			if (squareSize != 0) {
				coordinates = new Coordinates((nbColumns-1) * squareSize + xOrigin, squareSize * BoardGame.randint(0, nbLines - 1) + yOrigin);
			} else {
				coordinates = new Coordinates((int) view.xFromJ(nbColumns), (int) view.yFromI(nbLines));
			}
			if (string.equals("ClassicalZombie")) {
				zombie = new ClassicalZombie(coordinates);
			} else if (string.equals("ConeHeadZombie")) {
				zombie = new ConeHeadZombie(coordinates);
			} else if (string.equals("DancingZombie")) {
				zombie = new DancingZombie(coordinates);
			} else if (string.equals("TrashCanZombie")) {
				zombie = new TrashCanZombie(coordinates);
			} else if (string.equals("BucketheadZombie")) {
				zombie = new BucketheadZombie(coordinates);
			} else if (string.equals("FootballZombie")) {
				zombie = new FootballZombie(coordinates);
			} else if (string.equals("ScreenDoorZombie")) {
				zombie = new ScreenDoorZombie(coordinates);
			} else if (string.equals("PoleVaultingZombie")) {
				zombie = new PoleVaultingZombie(coordinates); 
			} else if (string.equals("NewspaperZombie")) {
				zombie = new NewspaperZombie(coordinates);
			} else if (string.equals("DolphinRiderZombie")) {
				zombie = new DolphinRiderZombie(coordinates);
			} else if (string.equals("SnorkelZombie")) {
				zombie = new SnorkelZombie(coordinates);
			} else if (string.equals("BungeeZombie")) {
				Cell randomCell = data.randomPlantCell();
				if (randomCell == null) { return null; }
				Coordinates randomCoordinates = randomCell.getCoords();
				zombie = new BungeeZombie(new Coordinates((int) view.xFromJ(randomCoordinates.getX()), (int) view.yFromI(randomCoordinates.getY())));
			} else if (string.equals("CatapultZombie")) {
				zombie = new CatapultZombie(coordinates);
			} else if (string.equals("JackInTheBoxZombie")) {
				zombie = new JackInTheBoxZombie(coordinates);
			} else if (string.equals("PogoZombie")) {
				zombie = new PogoZombie(coordinates);
			} else if (string.equals("LadderZombie")) {
				zombie = new LadderZombie(coordinates);
			} else if (string.equals("FlyingZombie")) {
				zombie = new FlyingZombie(coordinates);
			} else {
				zombie = new FlagZombie(coordinates);
			}
		} while (!data.canZombieBePlaced(view, zombie));
		return zombie;
	}
	
	public ArrayList<Zombie> randZombieList(PVZView view, Set<String> selectedZombies, int numberOfZombies, BoardGame data) {
		int nbLines = data.getNbLines(), nbColumns = data.getNbColumns();
		int lengthList = selectedZombies.size();
		int squareSize = view.getSquareSize();
		int xOrigin = view.getXOrigin();
		int yOrigin = view.getYOrigin();
		Zombie zombie;
		if (lengthList == 0) {
			throw new IllegalArgumentException();
		} else {
			ArrayList<Zombie> randomZombieList = new ArrayList<Zombie>();
			do {
				zombie = new FlagZombie(new Coordinates((nbColumns-1) * squareSize + xOrigin, squareSize * BoardGame.randint(0, nbLines - 1) + yOrigin));
			} while (!data.canZombieBePlaced(view, zombie));
			randomZombieList.add(zombie);
			for (int i = 1; i < numberOfZombies; i++) {
				randomZombieList.add(randZombie(view, data, selectedZombies, lengthList, nbLines, nbColumns, squareSize, xOrigin, yOrigin));
			}
			return randomZombieList;	
		}
	}
	
	private String randomSetElement(Set<String> set) {
		int item = BoardGame.randint(0, set.size() - 1); // In real life, the Random object should be rather more shared than this
		int i = 0;
		for(String string : set) {
		    if (i == item) {
		        return string;
		    }
		    i++;
		}
		return null;
	}
}
