package display;

import java.io.Serializable;
import java.util.ArrayList;

import control.Coordinates;

public class SelectionCardList implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<SelectionCard> mapCards = new ArrayList<SelectionCard>();
	
	public SelectionCardList() {
		for (int i = 0; i < 2; i++) {
			mapCards.add(new SelectionCard(new Coordinates(85 +(300 * i), 250), 200));
		}
	}
	
	public void changeToMapChoice() {
		mapCards = new ArrayList<SelectionCard>();
		for (int i = 0; i < 5; i++) {
			mapCards.add(new SelectionCard(new Coordinates(85+(280*i), 250), 200));
		}
	}
	
	
	public int clickedOnMap(int x, int y) {
		Coordinates coordinates = new Coordinates(x, y);
		for (int i = 0; i < mapCards.size(); i++) {
			if (mapCards.get(i).clickOnCard(coordinates)) {
				return i;
			}
		}
		return -1;
	}
}
