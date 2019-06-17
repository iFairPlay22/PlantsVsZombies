package control;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import control.projectile.ProjectileList;
import display.CardList;
import display.PVZView;
import entities.Grave;
import entities.Plant;
import entities.Zombie;
import maps.Map;

public class BoardGame implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private final ArrayList<Line> lineList;
	private final int lineListLength;
	
	private Coordinates selected;
	
	private final ArrayList<Coordinates> plantsCoordinates; //Stocke les coordonnees (i,j) des plantes sur le plateau
	private final ArrayList<Cell> specialItemsList; //Graves, Ladders, Fog
	private final ArrayList<Zombie> wavePlantTeam;//Zombies de la team plante
	
	private final Map mapChoice;//0: day, 1: night, 2: pool, 3: roof, 4: fog
	
	public BoardGame(Map map) {
		mapChoice = map;
		lineList = new ArrayList<Line>();
		specialItemsList = new ArrayList<Cell>();
		selected = null;
		plantsCoordinates = new ArrayList<Coordinates>();
		wavePlantTeam = new ArrayList<Zombie>();
		
		int nbLines = map.getNbLines();
		int nbColumns = map.getNbColumns();
		
		lineListLength = nbLines;
		if (0 <= nbLines && 0 <= nbColumns) {
			for (int i = 0; i < nbLines; i++) {
				lineList.add(map.buildLine(i, specialItemsList));
			}
		} else {
			throw new IllegalStateException();
		}
		
		if (!map.isDayTime()) {
			int i=0;
			while (i<=4) {
				int x = randint(5, nbColumns-1);
				int y = randint(0, nbLines-1);
				Cell cell = getElement(y, x);
				
				if (!cell.hasGrave()) {
					specialItemsList.add(cell);
					cell.setGrave(new Grave(x, y));
					i++;
				}
			}
		}
		
		map.placeFlowerPots(this, plantsCoordinates);
	}

	@Override
	public String toString() {
		int n = 0;
		if (lineListLength != 0) {
			n = lineList.get(0).getNbColumns();
		}
		StringBuilder res = new StringBuilder("Boardgame: " + lineListLength + " lines - " + n + " columns\n");
		
		for (Line line : lineList) {
			res.append(line.toString());
		}
		
		return res.toString();
	}
	
	// Plants
	
	public int setPlant(Coordinates c, Plant plant, CardList cards, int sunNumber, float gameSpeed) {
		Objects.requireNonNull(c);
		Objects.requireNonNull(plant);
		int i = (int) c.getX(); //y
		int j = c.getY(); //x
		Cell cell = getElement(i, j);
		
		if (0 <= i && i < lineListLength && 0 <= j && j < getNbColumns() && cards.cardReady(gameSpeed)) {
			if (plant.destroyGrave() && !cell.hasLadder() && !cell.hasFog()) {
				specialItemsList.remove(cell);
			} else if (plant.canSetAwakeShrooms()) {
				cell.setAwakeShrooms();
			} else {
				if (plant.illuminate()) {
					if (plant.illuminateLine()) {
						lineList.get(i).updateLineFog(false);
					} else {
						updateFog(i, j, false);
					}
				}
				plantsCoordinates.add(new Coordinates(j, i));
				cell.setPlant(plant);
			}
			cards.restartCoolDown();
			return plant.buyPlant(sunNumber);
		} else {
			System.out.println("Vous ne pouvez pas placer cette plante pour le moment!");
		}
		
		return sunNumber;
	}
	
	public void setPlant(Coordinates c, Plant plant) {
		Objects.requireNonNull(c);
		Objects.requireNonNull(plant);
		int i = (int) c.getX(); //y
		int j = c.getY(); //x
		Cell cell = getElement(i, j);
		
		if (0 <= i && i < lineListLength && 0 <= j && j < getNbColumns()) {
			if (plant.destroyGrave() && !cell.hasLadder() && !cell.hasFog()) {
				specialItemsList.remove(cell);
			} else if (plant.canSetAwakeShrooms()) {
				cell.setAwakeShrooms();
			} else {
				if (plant.illuminate()) {
					if (plant.illuminateLine()) {
						lineList.get(i).updateLineFog(false);
					} else {
						updateFog(i, j, false);
					}
				}
				plantsCoordinates.add(new Coordinates(j, i));
				cell.setPlant(plant);
			}
		} else {
			System.out.println("Vous ne pouvez pas placer cette plante pour le moment!");
		}
	}
	
	public void addLadder(int line, int column) {
		Cell cell = getElement(line, column);
		specialItemsList.add(cell);
		cell.summonLadder();
	}
	
	public void removePlant(Coordinates c) {
		int i = (int) c.getX(); //y
		int j = c.getY(); //x
		Cell cell = getElement(i, j);
		removePlant(c, (cell.isPlantIlluminate() ? (cell.isPlantIlluminateLine() ? 2 : 1): 0));
	}
	
	protected void removePlant(Coordinates c, int illuminate) {
		int i = (int) c.getX(); //y
		int j = c.getY(); //x
		Cell cell = getElement(i, j);
		if (illuminate == 2) {
			lineList.get(i).updateLineFog(true);
		} else if (illuminate == 1) {
			updateFog(i, j, true);
		}
		
		if (!cell.hasPlant() && cell.hasFlowerPot()) {
			cell.removeFlowerPot();
		} else {
			cell.setPlant(null);
		} 
	}

	//Brouillard
	
	private void updateFog(int i, int j, boolean state) { //Plantern
		for (int line = i - 1; line <= i + 1; line++) {
			if (lineExists(line)) {
				for (int column = j - 2; column <= j +2; column++) {
					if (columnExists(column)) {
						getElement(line, column).setFog(state);
					}
				}
			}
		}
	}
	
	public boolean contains(Coordinates c) {
		int index = plantsCoordinates.indexOf(Objects.requireNonNull(c));
		if (index == -1) {
			return false;
		}
		Coordinates coordinates = plantsCoordinates.get(index);
		return getElement(coordinates.getY(), coordinates.getX()).hasPlant();
	}
	
	//Methodes d'affichage pour eviter les getters
	
	public void drawPlantsTeamZombie(PVZView view, @SuppressWarnings("exports") Graphics2D graphics, BoardGame data) {
		view.drawZombies(graphics, wavePlantTeam);
	}
	
	public void drawPlants(PVZView view, @SuppressWarnings("exports") Graphics2D graphics, BoardGame data) {
		view.drawPlants(graphics, data, plantsCoordinates);
	}
	
	public void drawSpecialItems(PVZView view, @SuppressWarnings("exports") Graphics2D graphics, BoardGame data) {
		view.drawSpecialItems(graphics, specialItemsList);
	}
	
	//Methodes utilitaires d'attaque
	
	public Cell getFirstPlantCell(Zombie zombie, int zombieLine, int zombieColumn) {
		if (!(0 <= zombieLine && zombieLine < lineListLength)) {
			throw new IllegalStateException("Index out of range");
		}
		return lineList.get(zombieLine).getFirstPlantCell(zombie, zombieColumn);
	}
	
	public Cell getLastPlantCell(Zombie zombie, int zombieLine, int zombieColumn) {
		if (!(0 <= zombieLine && zombieLine < lineListLength)) {
			throw new IllegalStateException("Index out of range");
		}
		return lineList.get(zombieLine).getLastPlantCell(zombie, zombieColumn);
	}
	
	// Select Cell
	
	public void setSelectedCoordinates(Coordinates c) {
		Objects.requireNonNull(c);
		int i = (int) c.getX(); //y
		int j = c.getY(); //x
		if (0 <= i && i < lineListLength && 0 <= j && j < getNbColumns()) {
			selected = new Coordinates(j, i);
		} else {
			throw new IllegalStateException();
		}
	}
	
	public void setUnselectedCoordinates() {
		selected = null;
	}
	
	public Coordinates getSelectedCoordinates() {
		return selected;
	}
	
	//Attaque
	
	//Plantes
	public void generateProjectilesIfPossible(PVZView view, ArrayList<Zombie> zombieAlive, ProjectileList projectiles, SunList suns, float gameSpeed, Map mapChoice, Set<String> selectedZombies) {
		int plantI, plantJ;
		Cell cellPlant;
		
		for (int i = 0; i < plantsCoordinates.size(); i++) { //Pour toutes plantes
			plantI = plantsCoordinates.get(i).getX();
			plantJ = plantsCoordinates.get(i).getY();
			cellPlant = getElement(plantJ, plantI);
			
			if (!cellPlant.hasPlant()) {
				continue;
			}
			
			if (cellPlant.isReadyToSummons()) { //Timer ecoule
				cellPlant.summon(view, suns);
			}
			
			if (cellPlant.isPlantReadyToAttack(gameSpeed)) {
				cellPlant.addProjectilesIfPossible(view, zombieAlive, projectiles, getNbColumns(), getNbLines(), mapChoice);
			}
			
			if (cellPlant.isDead()) {
				removePlant(new Coordinates(plantJ, plantI));
			}
		}
		
		for (Cell cell : specialItemsList) {
			if (cell.hasGrave()) {
				cell.graveSummons(this, view, zombieAlive, selectedZombies);
			}
		}
	}

	//Zombies
	public void attackIfPossible(PVZView view, ArrayList<Zombie> waveZombieTeam, float gameSpeed) {		
		teamAttackIfPossible(view, waveZombieTeam, wavePlantTeam, gameSpeed, false);
		teamAttackIfPossible(view, wavePlantTeam, waveZombieTeam, gameSpeed, true);
		zombiesAttackPlantsIfPossible(view, waveZombieTeam, gameSpeed);
	}
	
	private void zombiesAttackPlantsIfPossible(PVZView view, ArrayList<Zombie> waveZombieTeam, float gameSpeed) {
		int zombieLine, zombieColumn, plantLine, plantColumn;
		ArrayList<Cell> victimCells;
		int victimCellsSize;
		int illuminate;
		Zombie zombie;
		Cell victimCell;
		Coordinates c;
		for (int i=0; i<waveZombieTeam.size(); i++) {
			zombie = waveZombieTeam.get(i);
			zombieLine = view.lineFromY(zombie.getY());
			zombieColumn = view.columnFromX(zombie.getX());
			if (zombie.isReadyToSummon()) { //Timer ecoule
				zombie.summon(this, view, waveZombieTeam);
			}
			if (zombie.isReadyToAttack(gameSpeed)) {
				victimCells = zombie.getVictims(zombie, this, zombieLine, zombieColumn);
				victimCellsSize = victimCells.size();
				for (int j=0; j<victimCellsSize; j++) {
					victimCell = victimCells.get(j);
					c = victimCell.getCoords();
					plantLine = c.getX();
					plantColumn = c.getY();
					System.out.println(zombie.printClass() + " attacks " + victimCell.getClassPlant());
					illuminate = victimCell.beAttacked(zombie);
					//view.string(context, damages.toString(), view.xFromJ(plantColumn), view.yFromI(plantLine));
					if (victimCell.isDead()) {
						removePlant(new Coordinates(plantLine, plantColumn), illuminate);
						plantsCoordinates.remove(new Coordinates(plantColumn, plantLine));
					}
				}
				if (victimCellsSize != 0) {
					zombie.restartTimer();
				}
				if (zombie.isDead()) {
					waveZombieTeam.remove(zombie);
				}	
			}
		}
	}
	
	private void teamAttackIfPossible(PVZView view, ArrayList<Zombie> team1, ArrayList<Zombie> team2, float gameSpeed, boolean isPlantTeam) {
		ArrayList<Zombie> victims;
		int victimsSize;
		Integer damages;
		int i = 0;
		Zombie zombie;
		boolean hasAttack;
		while (i < team1.size()) {
			hasAttack = false;
			zombie = team1.get(i);
			if (zombie.isReadyToAttack(gameSpeed)) {
				victims = zombie.getZombieVictims(view, team2, zombie);
				victimsSize = victims.size();
				for (Zombie victim : victims) {
					damages = zombie.attack(victim);
					System.out.println((isPlantTeam ? "Transform " : "") + zombie.printClass() + " attacks a zombie doing " + damages + " damages");
					//view.string(graphics, damages.toString(), victim.getX(), victim.getY());
					if (victim.isDead()) {
						team2.remove(victim);
					}
				}
				
				if (victimsSize != 0) {
					zombie.restartTimer();
				} else {
					hasAttack = true;
				}
				
				if (zombie.isDead()) {
					team1.remove(zombie);
				}	
			}	
			
			if (zombie.advance(view, getElement(view.lineFromY(zombie.getY()), view.columnFromX(zombie.getX())), gameSpeed, isPlantTeam, hasAttack)) {
				if (isPlantTeam && !view.isXInBoardgame(view.getSquareSize() + zombie.nextX(gameSpeed, isPlantTeam))) {
					team1.remove(zombie);
				}
			}
			i++;
		}
	}
	
	//HypnoShroom
	public void transformZombie(ArrayList<Zombie> waveZombieTeam, Zombie zombie) {
		waveZombieTeam.remove(zombie);
		wavePlantTeam.add(zombie);
	}
	
	//Methodes random utilitaires
	
	public static int randint(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}
	
	public Coordinates randomCell() {
		return new Coordinates(randint(0, lineListLength- 1), randint(0, getNbColumns() - 1));
	}
	
	public Cell randomPlantCell() {
		int max = plantsCoordinates.size() - 1;
		if (0 < max) {
			Coordinates coordinates = plantsCoordinates.get(randint(0, max));
			return getElement(coordinates.getX(), coordinates.getY());
		}
		return null;
	}

	//Placage de plantes
	
	public boolean canPlantBePlaced(int line, int column, Plant plant) {
		if (!(0 <= line && line < lineListLength)) {
			throw new IllegalStateException("Index out of range");
		}
		return lineList.get(line).canPlantBePlaced(column, plant);
	}
	
	public boolean canZombieBePlaced(PVZView view, Zombie zombie) {
		if (zombie == null) { return false; }
		return canBePlacedInLine(view.lineFromY(zombie.getY()), zombie);
	}
	
	public boolean canBePlacedInLine(int line, Zombie zombie) {
		if (!(0 <= line && line < lineListLength)) {
			throw new IllegalStateException("Index out of range");
		}
		return lineList.get(line).canZombieBePlaced(zombie);
	}
	
	public boolean lineExists(int line) {
		return (0 <= line && line < lineListLength);
	}
	
	public boolean columnExists(int column) {
		return (0 <= column && column < getNbColumns());
	}
	
	//Getters
	
	public Cell getElement(int line, int column) {
		if (0 <= line && line < lineListLength && 0 <= column && column < getNbColumns()) {
			return lineList.get(line).getElement(column);
		} else {
			throw new IllegalStateException();
		}
	}
	
	public String getClassPlant(Coordinates c) {
		int i = (int) c.getX(); //y
		int j = c.getY(); //x
		return getElement(j, i).getClassPlant();
	}
	
	public boolean hasLilyPad(int line, int column) {
		return getElement(line, column).hasLilyPad();
	}
	
	public boolean hasFlowerPot(int line, int column) {
		return getElement(line, column).hasFlowerPot();
	}
	
	public boolean hasPlant(int line, int column) {
		return getElement(line, column).hasPlant();
	}
	
	public boolean isPlantDoubleDamages(int line, int column) {
		return getElement(line, column).isPlantDoubleDamages();
	}
	
	//Getters pour l'affichage
	
	public String typeToString() {
		return mapChoice.printClass();
	}
	
	public int getNbLines() {
		return lineListLength;
	}
	
	public int getNbColumns() {
		if (lineListLength == 0) {
			throw new IllegalStateException();
		}
		return lineList.get(0).getNbColumns();
	}
}
