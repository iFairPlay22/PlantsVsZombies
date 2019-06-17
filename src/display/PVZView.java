package display;

import control.Coordinates;
import control.MowerList;
import control.Sun;
import control.SunList;
import control.projectile.ProjectileList;
import control.projectile.Projectiles;
import entities.Zombie;
import fr.umlv.zen5.ApplicationContext;
import maps.Map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;


import control.BoardGame;
import control.Cell;

public class PVZView implements GameView, Serializable {
	private static final long serialVersionUID = 1L;
	private final int xOrigin;
	private final int yOrigin;
	private final int widthData;
	private final int heightData;
	private final int squareSize;
	private final Font arial = new Font("arial", 25, 25);
	private final int width;
	private final int height;
	private final static HashMap<String, Image> entities = new HashMap<String, Image>();;

	public PVZView(int xOrigin, int yOrigin, int widthData, int heightData, int squareSize, int width, int height) {
		this.xOrigin = xOrigin;
		this.yOrigin = yOrigin;
		this.heightData = heightData;
		this.widthData = widthData;
		this.squareSize = squareSize;
		this.width = width;
		this.height = height;
		loadingImage("DayMap");
		loadingImage("NightMap");
		loadingImage("PoolMap");
		loadingImage("RoofMap");
		loadingImage("backgroundimage");
	}

	public static PVZView initGameGraphics(int width, int height, BoardGame data) {
		final double constant = 0.8;
		int nbLines = data.getNbLines();
		int nbColumns = data.getNbColumns();
		int heightData = (int) (height * constant);
		int widthData = (int) (width * constant);
		int squareSize = Integer.min(widthData / nbColumns, heightData / nbLines);
		heightData = squareSize * nbLines;
		widthData = squareSize * nbColumns;
		
		return new PVZView((width - widthData) / 2, 0, widthData, heightData, squareSize, width, height);
	}
	
	//Initialisation des images
	
	public CardList initialisationMenuSave(Map mapType) {
		CardList cards = new CardList();
		cards.addCard(new Card(new Coordinates(width / 2 - 350, height / 2), "Oui", 200, mapType));
		cards.addCard(new Card(new Coordinates(width / 2 + 150, height / 2), "Non", 200, mapType));
		loadingImage("OuiCard");
		loadingImage("NonCard");
		return cards;
	}
	
	public CardList initialisationMenuCardList(ArrayList<String> plantCardsClassList, Map mapType) {
		CardList cards = new CardList();
		int x = 50, y = 50;
		for (String name : plantCardsClassList) {
			cards.addCard(new Card(new Coordinates(x, y), name, 110, mapType));
			loadingImage(name + "Card");
			x += 110;
			if (x >= widthData-100) {
				x = 50;
				y += 150;
			}
		}
		return cards;
	}
	
	public CardList initialisationGameCardList(Set<String> selectedCard, Map mapType) {
		CardList cards = new CardList();
		int i = 0;
		for (String card : selectedCard) {
			cards.addCard(new Card(new Coordinates(xOrigin + squareSize * i + 10 * i, heightData), card, squareSize, mapType));
			i++;
		}
		return cards;
	}
	
	public void initialisationGameImages(BoardGame data, Set<String> selectedCard, Set<String> selectedZombies) {
		try {
			entities.put("plateau", ImageIO.read(new File("img/" + data.typeToString().toLowerCase() + ".png")));
		} catch (IOException e) {
			System.out.println("Problem with the boardgame image");
		}
		loadingImage("projectile");
		loadingImage("grave");
		loadingImage("ladder");
		loadingImage("fog");
		loadingImage("sun");
		loadingImage("Mower");
		loadingImage("pvz");
		loadingImage("background");
		loadingImage("ShovelCard");
		loadingImage("FlowerPot");
		initialisationZombiesImages(selectedZombies);
		initialisationPlantImages(selectedCard);
	}
	
	private void initialisationZombiesImages(Set<String> selectedZombies) {
		for (String string : selectedZombies) {
			loadingImage(string);
		}
		loadingImage("BackupDancer");
	}
	
	private void initialisationPlantImages(Set<String> selectedCard) {
		for (String className : selectedCard) {
			loadingImage(className);
			className += "Card";
			loadingImage(className);
		}
	}
	
	private void loadingImage(String className) {
		//Charge une image de format: img/classname.png
		try {
			entities.put(className, ImageIO.read(new File("img/" + className.toLowerCase() + ".png")));
		} catch (IOException e1) {
			System.out.println("Error while loading images! Class :" + className);
		}
	}
	
	//Menu
	
	public void drawCards(@SuppressWarnings("exports") Graphics2D graphics, ArrayList<Card> cards, float gameSpeed) {
		Coordinates c;
		int x;
		int y;
		for (Card card : cards) {
			c = card.getCoordinates();
			x = c.getX();
			y = c.getY();
			image(graphics, entities.get(card.getPlantClass() + "Card"), x, y);
			if (gameSpeed != -1) {
				string(graphics, card.toString(gameSpeed), (float) x, (float) y);
			}
		}
	}
	
	private void drawChosenCards(Graphics2D graphics, int x, int y, Set<String> chosenCards) {
		string(graphics, "Chosen Plants:", x, y-30);
		for (String plant : chosenCards) {
			specificImage(graphics, entities.get(plant+"Card"), x, y, 100, 120);
			x+=110;
		}
	}
	
	private void drawMaps(Graphics2D graphics) {
		int x = 85;
		int offset = 280;
		
		specificImage(graphics, entities.get("DayMap"), x, 250, 250, 200);
		string(graphics, "DayMap", x, 470);
		
		specificImage(graphics, entities.get("NightMap"), x + offset, 250, 250, 200);
		string(graphics, "NightMap", x + offset, 470);
		
		specificImage(graphics, entities.get("PoolMap"), x + offset * 2, 250, 250, 200);
		string(graphics, "PoolMap", x + offset * 2, 470);
		
		specificImage(graphics, entities.get("RoofMap"), x + offset * 3, 250, 250, 200);
		string(graphics, "RoofMap", x + offset * 3, 470);
		
		specificImage(graphics, entities.get("PoolMap"), x + offset * 4, 250, 250, 200);
		string(graphics, "FogMap", x + offset * 4, 470);
	}

	private void drawLogo(Graphics2D graphics, int width, int heigth) {
		specificImage(graphics, entities.get("pvz"), width/2-175, 10, 250, 150);
	}
	
	public void drawMenu(ApplicationContext context, boolean saveChoice, CardList saveChoiceCards, boolean mapSelection, CardList menuCards, Set<String> chosenCards, Map mapChoice) {
		context.renderFrame( graphics -> {
			drawBackgroundImage(graphics);
			
			drawLogo(graphics, (int) width, (int) height);
			if (saveChoice) {
				saveChoiceCards.drawRestartMenuCards(graphics, this, -1);
			} else if (mapSelection) {
				drawMaps(graphics);
			} else {
				drawZombiesMenu(graphics, mapChoice);
				menuCards.drawMenuPlantsCards(graphics, this, -1);
				drawChosenCards(graphics, 100, (int) height-150, chosenCards);
			}	
		});				
	}
	
	public void drawRestartMenuCards(@SuppressWarnings("exports") Graphics2D graphics, ArrayList<Card> cards, float gameSpeed) {
		string(graphics, "Voulez vous relancer la partie precedente ?", width / 2 - 230, height / 3);
		specificImage(graphics, entities.get("OuiCard"), width / 2 - 350, height / 2, 200, 200);
		specificImage(graphics, entities.get("NonCard"), width / 2 + 150, height / 2, 200, 200);
	}
	
	public void drawMenuPlantsCards(@SuppressWarnings("exports") Graphics2D graphics, ArrayList<Card> cards, float gameSpeed) {
		Coordinates c;
		int x;
		int y;
		for (Card card : cards) {
			c = card.getCoordinates();
			x = c.getX();
			y = c.getY();
			specificImage(graphics, entities.get(card.getPlantClass() + "Card"), x, y, 100, 120);
		}
	}
	
	//Game
	public void drawBackgroundImage(@SuppressWarnings("exports") Graphics2D graphics) {
		specificImage(graphics, entities.get("backgroundimage"), 0, 0, width, height);
	}
	
	private void drawShovelCard(Graphics2D graphics, SelectionCard shovel) {
		image(graphics, entities.get("ShovelCard"), shovel.getCoordinates().getX(), shovel.getCoordinates().getY());
	}
	
	private void drawMower(Graphics2D graphics, MowerList mowers) {
		Image mowerImg = entities.get("Mower");
		Coordinates coordinates;
		for (int i=0; i<mowers.size(); i++) {
			if (mowers.isNotUsed(i)) {
				coordinates = mowers.getCoords(i);
				image(graphics, mowerImg, (int) coordinates.getX()-squareSize, (int) coordinates.getY());
			}
		}
	}
	
	public void drawZombies(@SuppressWarnings("exports")  Graphics2D graphics, ArrayList<Zombie> zombieList) {
		for (Zombie zombie : zombieList) {
			image(graphics, entities.get(zombie.printClass()), zombie.getX(), zombie.getY());
		}
	}
	
	public void drawPlants(@SuppressWarnings("exports")  Graphics2D graphics, BoardGame data, ArrayList<Coordinates> plants) {
		int x, y;
		Image lilypad = entities.get("LilyPad"), flowerPot = entities.get("FlowerPot");
		for (Coordinates coordinates : plants) {
			y = coordinates.getY();
			x = coordinates.getX();
			if (data.hasLilyPad(y, x)) {
				image(graphics, lilypad, (int) xFromJ(x), (int) yFromI(y));
			}
			if (data.hasFlowerPot(y, x)) {
				image(graphics, flowerPot, (int) xFromJ(x), (int) yFromI(y));
			}
			if (data.hasPlant(y, x)) {
				image(graphics, entities.get(data.getClassPlant(coordinates)), (int) xFromJ(x), (int) yFromI(y));
			}
		}
	}

	public void drawSpecialItems(@SuppressWarnings("exports")  Graphics2D graphics, ArrayList<Cell> specialItemsList) {
		Image graveImg = entities.get("grave");
		Image ladderImg = entities.get("ladder");
		Image fogImg = entities.get("fog");
		int x, y;
		Coordinates coords;
		for (Cell cell : specialItemsList) {
			coords = cell.getCoords();
			x = (int) xFromJ(coords.getY());
			y = (int) yFromI(coords.getX());
			if (cell.hasGrave()) {
				image(graphics, graveImg, x, y);
			}
			if (cell.hasLadder()) {
				image(graphics, ladderImg, x, y);
			}
			if (cell.hasFog()) {
				image(graphics, fogImg, x, y);
			}
		}
	}
	
	public void drawSuns(@SuppressWarnings("exports")  Graphics2D graphics, ArrayList<Sun> suns) {
		Image sunImg = entities.get("sun");
		Coordinates coordinates;
		for (Sun sun : suns) {
			coordinates = sun.getCoordinates();
			image(graphics, sunImg, coordinates.getX(), coordinates.getY());
		}
	}
	
	private void drawSunCount(Graphics2D graphics, int x, int y, int sunCount) {
		specificImage(graphics, entities.get("sun"), x, y, 100, 100);
		string(graphics, Integer.toString(sunCount), x+30, y+35);
	}

	private RectangularShape drawCell(int i, int j) {
		return new Rectangle2D.Float(xFromJ(j) + 2, yFromI(i) + 2, squareSize - 4, squareSize - 4);
	}
	
	@Override
	public void drawOnlyOneCell(@SuppressWarnings("exports") Graphics2D graphics, BoardGame data, int x, int y) {
		graphics.setColor(Color.BLACK);
		graphics.fill(new Rectangle2D.Float(x, y, 50, 50));
	}

	private RectangularShape drawSelectedCell(int i, int j) {
		return new Rectangle2D.Float(xFromJ(j), yFromI(i), squareSize, squareSize);
	}
	
	@Override
	public void drawMap(@SuppressWarnings("exports") Graphics2D graphics, BoardGame data) {
		Image plateau =  entities.get("plateau");
		if (plateau != null) { 
			specificImage(graphics, plateau, xOrigin, yOrigin, widthData, heightData);
		} else {
			graphics.setColor(Color.LIGHT_GRAY);
			graphics.fill(new Rectangle2D.Float(xOrigin, yOrigin, widthData, heightData));

			graphics.setColor(Color.WHITE);
			for (int i = 0; i <= data.getNbLines(); i++) {
				graphics.draw(
						new Line2D.Float(xOrigin, yOrigin + i * squareSize, xOrigin + widthData, yOrigin + i * squareSize));
			}
			

			for (int i = 0; i <= data.getNbColumns(); i++) {
				graphics.draw(
						new Line2D.Float(xOrigin + i * squareSize, yOrigin, xOrigin + i * squareSize, yOrigin + heightData));
			}
			
			Coordinates c = data.getSelectedCoordinates();
			if (c != null) {
				graphics.setColor(Color.BLACK);
				graphics.fill(drawSelectedCell(c.getY(), c.getX()));
			}

			for (int i = 0; i < data.getNbLines(); i++) {
				for (int j = 0; j < data.getNbColumns(); j++) {
					graphics.setColor(Color.LIGHT_GRAY);
					graphics.fill(drawCell(i, j));
					graphics.setColor(Color.DARK_GRAY);
				}
			}
		}
	}
	
	public void drawProjectiles(@SuppressWarnings("exports") Graphics2D graphics, @SuppressWarnings("exports") ArrayList<Projectiles> projectiles) {
		Image projectileImage = entities.get("projectile");
		for (Projectiles projectile : projectiles) {
			image(graphics, projectileImage, projectile.getActualCoords().getX(), projectile.getActualCoords().getY());
		}
	}
	
	public void drawGame(ApplicationContext context, BoardGame data, CardList cards, ArrayList<Zombie> waveZombieTeam, @SuppressWarnings("exports") ProjectileList projectiles, MowerList mowers, SunList suns, int sunNumber, float gameSpeed, SelectionCard shovel) {
		context.renderFrame( graphics -> {
			drawBackgroundImage(graphics);
			drawMap(graphics, data);
			cards.drawCards(graphics, this, gameSpeed);
			drawShovelCard(graphics, shovel);
			drawSunCount(graphics, (int) width - squareSize, 5, sunNumber);
			data.drawPlants(this, graphics, data);
			drawZombies(graphics, waveZombieTeam);
			data.drawPlantsTeamZombie(this, graphics, data);
			projectiles.drawProjectiles(graphics, this);
			drawMower(graphics, mowers);
			data.drawSpecialItems(this, graphics, data);
			suns.drawSuns(graphics, this);
		});
	}
	
	public void drawEnd(ApplicationContext context, boolean lost, boolean victory) {
		context.renderFrame( graphics -> {
			graphics.setColor(Color.blue);
			graphics.fill(new Rectangle(0,0, (int)width, (int)height));		
			
			if (lost) {
				drawDefeatImage(graphics, (int) width, (int) height);
			} else if (victory) {
				drawVictoryImage(graphics, (int) width, (int) height);
			}
		});
	}
	
	//Calculs
	
	private int indexFromReaCoord(float coord, int origin) {
		return (int) ((coord - origin) / squareSize);
	}
	
	public int lineFromY(float y) {
		if (0 <= y && y < yOrigin + heightData) {
			return indexFromReaCoord(y, yOrigin);
		} else {
			return -1;
		}
	}
	
	public int columnFromX(float x) {
		if (0 <= x && x < xOrigin + widthData) {
			return indexFromReaCoord(x, xOrigin);
		} else {
			return -1;
		}
	}

	private float realCoordFromIndex(int index, int origin) {
		return origin + index * squareSize;
	}
	
	public float middleXFromJ(int i) {
		return realCoordFromIndex(i, xOrigin) + squareSize / 2;
	}
	
	public float xFromJ(int i) {
		return realCoordFromIndex(i, xOrigin);
	}

	public float yFromI(int j) {
		return realCoordFromIndex(j, yOrigin);
	}
	
	public int getSquareSize() {
		return squareSize;
	}

	@Override
	public void image(@SuppressWarnings("exports")  Graphics2D graphics, @SuppressWarnings("exports") Image img, int x, int y) {
		graphics.drawImage(img, x, y, squareSize, squareSize, null);
	}
	
	@Override
	public void specificImage(@SuppressWarnings("exports")  Graphics2D graphics, @SuppressWarnings("exports") Image img, int x, int y, int width, int height) {
		graphics.drawImage(img, x, y, width, height, null);
	}
	
	public void drawDefeatImage(@SuppressWarnings("exports")  Graphics2D graphics, int width, int height) {
		try {
			Image defeatImage = ImageIO.read(new File("img/defeatimage.jpg"));
			specificImage(graphics, defeatImage, 0, 0, width, height);
		} catch (IOException e) {
			System.out.println("Error when loading defeat image");
		}
	}
	
	public void drawVictoryImage(@SuppressWarnings("exports")  Graphics2D graphics, int width, int height) {
		try {
			Image defeatImage = ImageIO.read(new File("img/victoryimage.jpg"));
			specificImage(graphics, defeatImage, 0, 0, width, height);
		} catch (IOException e) {
			System.out.println("Error when loading victory image");
		}
	}

	@Override
	public void string(@SuppressWarnings("exports") Graphics2D graphics, String str, float x, float y) {
		graphics.setColor(Color.DARK_GRAY);
		graphics.setFont(arial);
		graphics.drawString(str, x, y + 25);
	}
	
	public boolean isXInBoardgame(float f) {
		return xOrigin <= f && f < xOrigin + widthData;
	}
	
	public boolean isYInBoardgame(int y) {
		return yOrigin <= y && y < yOrigin + heightData;
	}
	
	public int getXOrigin() {
		return xOrigin;
	}
	
	public int getYOrigin() {
		return yOrigin;
	}

	public void drawZombiesMenu(@SuppressWarnings("exports") Graphics2D graphics, Map mapChoice) {
		ArrayList<String> zombies = mapChoice.getZombieClasses();
		
		int x = width - 550;
		int y = height - 270;
		int offset = 70;
		
		Set<String> setZombies = new HashSet<String>(zombies);
		initialisationZombiesImages(setZombies);
		
		for (String string : setZombies) {
			specificImage(graphics, entities.get(string), x, y, 100, 100);
			x = x + offset;
			
			if (x >= width-150) {
				x = width - 520;
				y += 100;
			}
		}
		
	}
}
