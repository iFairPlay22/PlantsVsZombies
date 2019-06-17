package display;

import java.io.Serializable;
import java.util.Objects;

import control.Coordinates;
import control.Time;
import entities.Plant;
import entities.plantsEntities.day.CherryBomb;
import entities.plantsEntities.day.Chomper;
import entities.plantsEntities.day.Peashooter;
import entities.plantsEntities.day.PotatoMine;
import entities.plantsEntities.day.Repeater;
import entities.plantsEntities.day.SnowPea;
import entities.plantsEntities.day.SunFlower;
import entities.plantsEntities.day.WallNut;
import entities.plantsEntities.fog.Blover;
import entities.plantsEntities.fog.Cactus;
import entities.plantsEntities.fog.Plantern;
import entities.plantsEntities.fog.SeaShroom;
import entities.plantsEntities.fog.SplitPea;
import entities.plantsEntities.night.DoomShroom;
import entities.plantsEntities.night.FumeShroom;
import entities.plantsEntities.night.GraveBuster;
import entities.plantsEntities.night.HypnoShroom;
import entities.plantsEntities.night.IceShroom;
import entities.plantsEntities.night.PuffShroom;
import entities.plantsEntities.night.ScaredyShroom;
import entities.plantsEntities.night.SunShroom;
import entities.plantsEntities.pool.Jalapeno;
import entities.plantsEntities.pool.LilyPad;
import entities.plantsEntities.pool.SpikeWeed;
import entities.plantsEntities.pool.Squash;
import entities.plantsEntities.pool.TallNut;
import entities.plantsEntities.pool.TangleKelp;
import entities.plantsEntities.pool.Threepeater;
import entities.plantsEntities.pool.Torchwood;
import entities.plantsEntities.roof.CabbagePult;
import entities.plantsEntities.roof.CoffeeBean;
import entities.plantsEntities.roof.FlowerPot;
import entities.plantsEntities.roof.Garlic;
import entities.plantsEntities.roof.KernelPult;
import entities.plantsEntities.roof.MelonPult;
import entities.plantsEntities.roof.UmbrellaLeaf;
import maps.Map;

public class Card extends SelectionCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int cooldown;
	private Time cooldownTimer;
	private final String plant;
	
	public Card(Coordinates coordinates, String plant, int cardSize, Map mapType) {
		super(coordinates, cardSize);
		cooldownTimer = new Time();
		this.plant = Objects.requireNonNull(plant);
		this.cooldown = getNewPlant(mapType).getRecharcheTime();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(plant);
	}
	
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Card)) {
			return false;
		}
		Card c = (Card) o;
		return plant.equals(c.plant);
	}

	
	public boolean readyToClick(float gameSpeed) {
		return cooldownTimer.toSeconds() * gameSpeed >= cooldown;
	}
	
	protected void restartCooldown() {
		cooldownTimer.restart();
	}
	
	public String getPlantClass() {
		return plant;
	}
	
	public Plant getNewPlant(Map mapType) {
		if (plant.equals("Peashooter")) {
			return new Peashooter();
		} else if (plant.equals("WallNut")) {
			return new WallNut();
		} else if (plant.equals("CherryBomb")) {
			return new CherryBomb();
		} else if (plant.equals("SunFlower")) {
			return new SunFlower();
		}	else if (plant.equals("Repeater")) {
			return new Repeater();
		} else if (plant.equals("Chomper")) {
			return new Chomper();
		}	else if (plant.equals("SnowPea")) {
			return new SnowPea();
		} else if (plant.equals("DoomShroom")) {
			return new DoomShroom(mapType.isDayTime());
		} else if (plant.equals("IceShroom")) {
			return new IceShroom(mapType.isDayTime());
		} else if (plant.equals("Jalapeno")) {
			return new Jalapeno();
		} else if (plant.equals("Threepeater")) {
			return new Threepeater();
		} else if (plant.equals("ScaredyShroom")) {
			return new ScaredyShroom(mapType.isDayTime());
		} else if (plant.equals("SunShroom")) {
			return new SunShroom(mapType.isDayTime()); 
		} else if (plant.equals("SpikeWeed")) {
			return new SpikeWeed(); 
		} else if (plant.equals("LilyPad")) {
			return new LilyPad();
		} else if (plant.equals("TallNut")) {
			return new TallNut();
		} else if (plant.equals("Torchwood")) {
			return new Torchwood();
		} else if (plant.equals("GraveBuster")) {
			return new GraveBuster();
		} else if (plant.equals("HypnoShroom")) {
			return new HypnoShroom(mapType.isDayTime());
		} else if (plant.equals("FumeShroom")) {
			return new FumeShroom(mapType.isDayTime());
		} else if (plant.equals("PuffShroom")) {
			return new PuffShroom(mapType.isDayTime());	
		} else if (plant.equals("Squash")) {
			return new Squash();
		} else if (plant.equals("TangleKelp")) {
			return new TangleKelp();
		} else if (plant.equals("Cactus")) {
			return new Cactus(); 
		} else if (plant.equals("SeaShroom")) {
			return new SeaShroom(mapType.isDayTime());  
		} else if (plant.equals("SplitPea")) {
			return new SplitPea();   
		} else if (plant.equals("FlowerPot")) {
			return new FlowerPot();
		} else if (plant.equals("Plantern")) {
			return new Plantern();
		} else if (plant.equals("CabbagePult")) {
			return new CabbagePult();
		} else if (plant.equals("CoffeeBean")) {
			return new CoffeeBean();
		} else if (plant.equals("Garlic")) {
			return new Garlic();
		} else if (plant.equals("KernelPult")) {
			return new KernelPult();
		} else if (plant.equals("Blover")) {
			return new Blover();
		} else if (plant.equals("MelonPult")) {
			return new MelonPult();
		} else if (plant.equals("UmbrellaLeaf")) {
			return new UmbrellaLeaf();
		} else {
			return new PotatoMine();
		}
	}
	
	public String toString(float gameSpeed) {
		long time = cooldownTimer.toSeconds();
		if (time * gameSpeed >= cooldown) {
			return "Ok";
		}
		return "" + (int) (cooldown - time * gameSpeed);
	}
}
