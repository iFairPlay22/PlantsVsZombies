package display;

import java.awt.Graphics2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

import control.BoardGame;
import control.Coordinates;
import entities.Plant;
import maps.Map;

public class CardList implements Serializable {
	private static final long serialVersionUID = 1L;
	private final ArrayList<Card> cards;
	private int lastCardClickedIndex;
	
	public CardList() {
		cards = new ArrayList<Card>();
		lastCardClickedIndex = -1;
	}
	
	public void addCard(Card card) {
		cards.add(Objects.requireNonNull(card));
	}
	
	public String selectedCardClass(int x, int y) { //(int) location.getX(), (int) location.getY()
		Coordinates coordinates = new Coordinates(x, y);
		for (Card card : cards) {
			if (card.clickOnCard(coordinates)) {
				return card.getPlantClass();
			}
		}
		return null;
	}
	
	public Plant selectedCard(int x, int y, Map mapType) { //(int) location.getX(), (int) location.getY()
		Plant selectedPlant = null;
		for (int k=0; k<cards.size(); k++) {
			if (cards.get(k).clickOnCard(new Coordinates(x, y)) /*&& cards.get(k).readyToClick()*/) {
				selectedPlant = cards.get(k).getNewPlant(mapType);
				lastCardClickedIndex = k;
			}
		}
		return selectedPlant;
	}
	
	public void restartCoolDown() {
		if (lastCardClickedIndex == -1) {
			throw new IllegalStateException("No card is selected !");
		}
		cards.get(lastCardClickedIndex).restartCooldown();	
		lastCardClickedIndex = -1;
	}
	
	public Plant randomPlant(Map mapType) {
		int rand = BoardGame.randint(0, cards.size() - 1);
		Plant plant = cards.get(rand).getNewPlant(mapType);
		lastCardClickedIndex = rand;
		return plant;
	}

	public int size() {
		return cards.size();
	}

	public Card get(int i) {
		if (!(0 <= i && i < cards.size())) {
			throw new IllegalAccessError();
		}
		return cards.get(i);
	}
	
	public boolean cardReady(float gameSpeed) {
		return cards.get(lastCardClickedIndex).readyToClick(gameSpeed);
	}
	
	public void drawCards(@SuppressWarnings("exports") Graphics2D graphics, PVZView view, float gameSpeed) {
		view.drawCards(graphics, cards, gameSpeed);
	}
	
	public void drawRestartMenuCards(@SuppressWarnings("exports") Graphics2D graphics, PVZView view, float gameSpeed) {
		view.drawRestartMenuCards(graphics, cards, gameSpeed);
	}
	
	public void drawMenuPlantsCards(@SuppressWarnings("exports") Graphics2D graphics, PVZView view, float gameSpeed) {
		view.drawMenuPlantsCards(graphics, cards, gameSpeed);
	}

}
