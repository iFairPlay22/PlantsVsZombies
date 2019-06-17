package control;

import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import display.CardList;
import display.PVZView;
import display.SelectionCard;
import display.SelectionCardList;
import control.WaveGenerator;
import control.projectile.ProjectileList;
import entities.Plant;
import entities.Zombie;
import fr.umlv.zen5.Application;
import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.ScreenInfo;
import fr.umlv.zen5.Event.Action;
import maps.DayMap;
import maps.FogMap;
import maps.Map;
import maps.NightMap;
import maps.PoolMap;
import maps.RoofMap;
import fr.umlv.zen5.KeyboardKey;


public class PVZGameController implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Game controllers
	private final PVZView view;
	private final BoardGame data;
	private Map mapChoice = new DayMap();
	private float gameSpeed = 1;
	private float initialGameSpeed = 1;
	private ArrayList<Map> maps = new ArrayList<Map>();
	
	//Game timers
	private final Time gameTime = new Time();
	private final Time playTime = new Time();
	private final Time botSpeed = new Time();
	private final Time sunSpawn = new Time();
	private final Time displayTime = new Time();
	
	//Game datas
	private Plant selectedPlant;
	private int nbMaxCards = 7;
	private int sunNumber = 500;
	
	//Spawn of zombies
	private int nbZombies = 0;
	private int timeBetweenNextWave = 0;
	private int additionalNbZombies = 1;
	private int timeBetweenZombieSpawn = 2;
	private int additionalTimeBetweenWaves = 5;

	//Class names of entities
	private ArrayList<String> plantCardsClassList = new ArrayList<String>();
	private ArrayList <String> zombieCardsClassList = new ArrayList<String>();
	private final Set<String> selectedCard = new HashSet<String>();
	private final Set<String> selectedZombies = new HashSet<String>();

	
	//Entities objects
	private final ProjectileList projectilesList;
	private final SunList suns = new SunList();
	private final MowerList mowers;
	private final CardList cards;
	private final WaveGenerator waveGenerator;
	private final ArrayList<Zombie> waveZombieTeam = new ArrayList<Zombie>();
	
	//Game type booleans
	private int timeWin = 1000;
	private boolean victory = false;
	private boolean lost = false;
	private boolean bot = false;
	private boolean isPlayable = true;
	
	//View datas
	private final float width;
	private final float height;
	private final int xOrigin;
	private final int squareSize;
	
	
	private PVZGameController(ApplicationContext context) throws IOException, ClassNotFoundException {
		DayMap dayMap = new DayMap();
		NightMap nightMap = new NightMap();
		PoolMap poolMap = new PoolMap();
		RoofMap roofMap = new RoofMap();
		FogMap fogMap = new FogMap();
		try (
				BufferedReader scr = Files.newBufferedReader(Paths.get("src/config.txt"), StandardCharsets.UTF_8); 
		) {
			String line;
			int i = 0;
			while ((line = scr.readLine()) != null) {
				if (i % 2 == 1) {
					switch (i) {
					case 1: sunNumber = Integer.parseInt(line); break;
					case 3: for (String string : line.split(",")) { dayMap.getZombieClasses().add(string); } break;
					case 5: for (String string : line.split(",")) { nightMap.getZombieClasses().add(string); } break;
					case 7: for (String string : line.split(",")) { poolMap.getZombieClasses().add(string); fogMap.getZombieClasses().add(string); } break;
					case 9: for (String string : line.split(",")) { roofMap.getZombieClasses().add(string); } break;
					case 11: for (String string : line.split(",")) { plantCardsClassList.add(string); } break;
					case 13: for (String string : line.split(",")) { zombieCardsClassList.add(string); } break;
					case 15: nbZombies = Integer.parseInt(line); break;
					case 17: timeBetweenNextWave = Integer.parseInt(line); break;
					case 19: additionalNbZombies = Integer.parseInt(line); break;
					case 21: timeBetweenZombieSpawn = Integer.parseInt(line); break;
					case 23: additionalTimeBetweenWaves = Integer.parseInt(line); break;
					case 25: timeWin = Integer.parseInt(line); break;
					case 27: nbMaxCards = Integer.parseInt(line); break;
					case 29: gameSpeed = Float.parseFloat(line); initialGameSpeed = gameSpeed; break;
					default: break;
					}
				}
				i++;
			}
		}
		ScreenInfo screenInfo = context.getScreenInfo();
		width = screenInfo.getWidth();
		height = screenInfo.getHeight();
		System.out.println("size of the screen (" + width + " x " + height + ")\n");
		
		PVZView view = new PVZView(0, 0, (int) width, (int) height, (int) width / 20, (int) width, (int) height);
		maps.add(dayMap); maps.add(nightMap); maps.add(poolMap); maps.add(roofMap); maps.add(fogMap);
		menu(context, view, plantCardsClassList);
		
		this.data = new BoardGame(mapChoice); //0: day, 1: night, 2: pool, 3: roof, 4: fog
		this.view = PVZView.initGameGraphics((int) width, (int) height, data);
		this.selectedZombies.addAll(getZombiesClassName(mapChoice));
		this.xOrigin = this.view.getXOrigin();
		this.squareSize = this.view.getSquareSize();
		this.cards = this.view.initialisationGameCardList(selectedCard, mapChoice);
		this.waveGenerator = new WaveGenerator();
		this.selectedPlant = null;
		this.mowers = new MowerList(data, this.view);
		this.projectilesList = new ProjectileList(squareSize / 2);
	}

	private Plant interaction(Time playTime, int i, int j, boolean isShovel, boolean bot) {
		Coordinates c = new Coordinates(i, j); //(Lines, Columns)
		data.setSelectedCoordinates(c);
		if (isShovel) {
			data.removePlant(c);
			selectedPlant = null;
			System.out.println("Plant removed at " + playTime.toSeconds() + "s\n");
		} else if (bot && selectedPlant != null && data.canPlantBePlaced(i, j, selectedPlant)) {
			data.setPlant(c, selectedPlant, cards, 10000, gameSpeed);
			System.out.println("Plant placed by bot at " + playTime.toSeconds() + "s\n");
			selectedPlant = null; //Deselection
		} else if (!bot && selectedPlant != null && data.canPlantBePlaced(i, j, selectedPlant) && selectedPlant.hasEnoughMoney(j, i, sunNumber)) {
			sunNumber = data.setPlant(c, selectedPlant, cards, sunNumber, gameSpeed);
			System.out.println("Plant placed at " + playTime.toSeconds() + "s\n");
			selectedPlant = null; //Deselection
		}
		return selectedPlant;
	}

	
	private void menu(ApplicationContext context, PVZView view, ArrayList<String> plantCardsClassList) throws IOException, ClassNotFoundException {
		
		String selectedPlant = null, restartChoice = null;
		boolean reloadNewGame = true;
		int choice;
		
		//Cartes selectionnables
		final CardList saveSelection = view.initialisationMenuSave(mapChoice);//Save
		final SelectionCardList mapCards = new SelectionCardList();//Map
		mapCards.changeToMapChoice();
		final CardList cardsSelection = view.initialisationMenuCardList(plantCardsClassList, mapChoice);//Plants
		
		//Etat actuel du menu
		boolean saveChoice = true;
		boolean mapSelection = true;
		
		while (true) {
			
			if (10 <= displayTime.getPastTime()) {
				view.drawMenu(context, saveChoice, saveSelection, mapSelection, cardsSelection, selectedCard, mapChoice);
				displayTime.restart();
			}
			
			Event event = context.pollOrWaitEvent(80);
			
			if (event == null) {
				continue;
			}
			
			Action action = event.getAction();
			
			
			if (event.getKey() == KeyboardKey.UNDEFINED) {
				System.out.println("End of menu!\n");
				context.exit(0);
			} 
			
			if (action != Action.POINTER_DOWN) {
				Point location = MouseInfo.getPointerInfo().getLocation();
				int x = (int) location.getX();
				int y = (int) location.getY();
				
				if (saveChoice) {//Save
					restartChoice = saveSelection.selectedCardClass(x, y);
					if (restartChoice != null) {
						if (restartChoice.equals("Oui")) {
							reloadNewGame = false;
							break; //Reprendre une partie sauvegardee
						} else {
							saveChoice = false; //On charge une nouvelle partie
						}
					}
				} else if (mapSelection) {//Map
					choice = mapCards.clickedOnMap(x, y);
					if (choice != -1) {
						mapChoice = maps.get(choice);
						if (mapChoice != null) {
							mapSelection = false;
						}
					}
					} else {//Plants
					selectedPlant = cardsSelection.selectedCardClass(x, y);
					
					if (selectedPlant != null) {
						if (!selectedCard.add(selectedPlant)) {
							selectedCard.remove(selectedPlant);
						}
						if (selectedCard.size() == nbMaxCards) {
							System.out.println("End of menu!\n");
							return;
						}
					}
				}
			}
		}
		
		if (!reloadNewGame) {
			isPlayable = false;
			reload(context);
		}
	}
	
	private Set<String> getZombiesClassName(Map mapChoice) {
		HashSet<String> set = new HashSet<>(mapChoice.getZombieClasses());
		return set;
	}
	
	private void pause(ApplicationContext context) {
		while (true) {
			Event event = context.pollOrWaitEvent(80);

			if (event == null) {
				continue;
			}
			
			Action action = event.getAction();
			if (action == Action.KEY_PRESSED) {
				if (event.getKey() == KeyboardKey.P) {		//Pause
					break;
				}
			}
		}
	}

	public void playGame(ApplicationContext context) throws IOException {
		if (!isPlayable) { return ; }
		System.out.println("Start of the game!\n");
		
		restartTimers();
		boolean isShovel = false;
		SelectionCard shovel = new SelectionCard(new Coordinates( (int) width-squareSize, (int) height-squareSize), squareSize);
		
		view.initialisationGameImages(data, selectedCard, selectedZombies);
		
		while (true) {
			if (displayTime.getPastTime() * gameSpeed >= 10) {
				view.drawGame(context, data, cards, waveZombieTeam, projectilesList, mowers, suns, sunNumber, gameSpeed, shovel);
				displayTime.restart();
			}
			
			if (gameTime.getPastTime() * gameSpeed >= 50 && !lost && !victory) {
				
				if (waveGenerator.isWaveEmpty()) { //Spawn Zombies
					waveGenerator.generateNewWave(view, data, selectedZombies, nbZombies += additionalNbZombies, timeBetweenNextWave += additionalTimeBetweenWaves, timeBetweenZombieSpawn);
				} else if (waveGenerator.canSpawnZombie()) {
					waveGenerator.spawnZombie(waveZombieTeam);
					System.out.println("Spawn Zombie at " + playTime.toSeconds() + " seconds\n");
				}
				
				if (!bot) { //Spawning Suns, only in !bot mode
					
					if (mapChoice.isDayTime() && sunSpawn.toSeconds() * gameSpeed > 6) {
						suns.addSun(data, view);
						sunSpawn.restart();
					}
				}
				
				//########################################## Mouvements ##########################################
				if (!bot) {
					suns.moveSuns(gameSpeed);
				}
				projectilesList.moveProjectiles(data, view, gameSpeed);
				mowers.advance();

				//########################################## Attaques ##########################################
				data.attackIfPossible(view, waveZombieTeam, gameSpeed);
				mowers.attackIfPossible(view, waveZombieTeam);
				data.generateProjectilesIfPossible(view, waveZombieTeam, projectilesList, suns, gameSpeed, mapChoice, selectedCard);
				projectilesList.AttackIfPossible(view, data, waveZombieTeam, gameSpeed);


				//########################################## Fin de partie ##########################################
				for (int i = 0; i < waveZombieTeam.size(); i++) {
					if (waveZombieTeam.get(i).getX() <= xOrigin) {
						int line = view.lineFromY(waveZombieTeam.get(i).getY());
						if (mowers.isReadyToMove(line)) {
							mowers.setMowerAvailable(line);
						} else {											//If no defense left
							System.out.println("Lost at " + playTime.toSeconds() + " seconds\n");
							lost = true;
							bot = false;
							break;
						}
					}
				}
				gameTime.restart();
			}
			
			if (playTime.toSeconds() >= timeWin) {
				victory = true;
			}

			if (lost || victory) {
				break;
			}

			if (bot) {//Bot mode => Random moves
				Coordinates cell = data.randomCell();
				selectedPlant = cards.randomPlant(mapChoice);
				if (botSpeed.toSeconds() >= 1) {
					selectedPlant = interaction(playTime, cell.getX(), cell.getY(), false, bot);
					botSpeed.restart();
				}
			}

			Event event = context.pollOrWaitEvent(50);

			if (event == null) {
				continue;
			}
			
			Action action = event.getAction();
			if (action == Action.KEY_PRESSED) {
				if (event.getKey() == KeyboardKey.D) {//Activate Bot mode
					if (bot) {
						gameSpeed = initialGameSpeed;
					} else {
						gameSpeed = initialGameSpeed + 3;
					}
					bot = !bot;
				} else if (event.getKey() == KeyboardKey.P) {//Pause
					pause(context);
				} else if (event.getKey() == KeyboardKey.S) {//Save
					save();
				} else {
					System.out.println("End of the game!\n");
					context.exit(0);
					return;
				}
			}

			if (!bot && action != Action.POINTER_DOWN) {
				Point location = MouseInfo.getPointerInfo().getLocation();
				int i = view.lineFromY((float) location.getY());
				int j = view.columnFromX((float) location.getX());

				if (i != -1 && j != -1) {
					//botSpeed.restart();
					int sunWon = suns.catchSun((int) location.getX(), (int) location.getY());
					if (sunWon != -1) {
						sunNumber += sunWon;
						if (selectedPlant != null) {selectedPlant = null;}
					}
					selectedPlant = interaction(playTime, i, j, isShovel, bot);
					if (isShovel) { isShovel = false;}
				} else if (shovel.clickOnCard(new Coordinates((int) location.getX(), (int) location.getY()))) {
					isShovel = !isShovel;
				} else {
					data.setUnselectedCoordinates();
					selectedPlant = cards.selectedCard((int) location.getX(), (int) location.getY(), mapChoice);
				}
				continue;
			}
		}
		endGame(context);
	}
	
	private void restartTimers() {
		playTime.restart();
		botSpeed.restart();
		sunSpawn.restart();
		displayTime.restart();
	}

	private void endGame(ApplicationContext context) {
		view.drawEnd(context, lost, victory);
		
		while (true) {
			Event event = context.pollOrWaitEvent(80);
			if (event == null) {
				continue;
			} else {
				context.exit(0);
				return;
			}
		}
	}
	
	private void save() throws FileNotFoundException, IOException {
		@SuppressWarnings("resource")
		ObjectOutputStream objectOutputStream =  new ObjectOutputStream(new FileOutputStream(new File("src/save.txt")));
		objectOutputStream.writeObject(this);
	}
	
	public static void reload(ApplicationContext context) throws FileNotFoundException, IOException, ClassNotFoundException {
		@SuppressWarnings("resource")
		ObjectInputStream objectInputStream =  new ObjectInputStream(new FileInputStream(new File("src/save.txt")));
		PVZGameController pvzGameController = (PVZGameController) objectInputStream.readObject();
		pvzGameController.playGame(context);
	}
	
		
	public static void main(String[] args) {
		Application.run(Color.WHITE, context -> {
			PVZGameController pvzGameController;
			try {
				pvzGameController = new PVZGameController(context);
				pvzGameController.playGame(context);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
}
