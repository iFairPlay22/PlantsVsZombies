package display;

import java.awt.Graphics2D;
import java.awt.Image;

import control.BoardGame;
import fr.umlv.zen5.ApplicationContext;

/**
 * The GameView class is in charge of the graphical view of a clicky game.
 */
public interface GameView {
	/**
	 * Transforms a real y-coordinate into the index of the corresponding line.
	 * 
	 * @param y a float y-coordinate
	 * @return the index of the corresponding line.
	 * @throws IllegalArgumentException if the float coordinate doesn't fit in the
	 *                                  game board.
	 */
	public int lineFromY(float y);

	/**
	 * Transforms a real x-coordinate into the index of the corresponding column.
	 * 
	 * @param x a float x-coordinate
	 * @return the index of the corresponding column.
	 * @throws IllegalArgumentException if the float coordinate doesn't fit in the
	 *                                  game board.
	 */
	public int columnFromX(float x);

	/**
	 * Draws the game board from its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 */
	public void drawMap(Graphics2D graphics, BoardGame data);

	/**
	 * Draws the game board from its data, using an existing
	 * {@code ApplicationContext}.
	 * 
	 * @param context the {@code ApplicationContext} of the game
	 * @param data    the GameData containing the game data.
	 */
	
	public default void draw(ApplicationContext context, BoardGame data) {
		context.renderFrame(graphics -> drawMap(graphics, data));
	}

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 * @param x        the float x-coordinate of the cell.
	 * @param y        the float y-coordinate of the cell.
	 */
	public void drawOnlyOneCell(Graphics2D graphics, BoardGame data, int x, int y);

	/**
	 * Draws only the cell specified by the given coordinates in the game board from
	 * its data, using an existing {@code ApplicationContext}.
	 * 
	 * @param context the {@code ApplicationContext} of the game
	 * @param data    the GameData containing the game data.
	 * @param x       the float x-coordinate of the cell.
	 * @param y       the float y-coordinate of the cell.
	 */
	/*
	public default void drawOnlyOneCell(Graphics2D graphics, BoardGame data, int x, int y) {
		context.renderFrame(graphics -> drawOnlyOneCell(graphics, data, x, y));
	}*/

	void image(Graphics2D graphics, Image img, int x, int y);
	
	/*
	public default void image(ApplicationContext context, Image img, int x, int y) {
		context.renderFrame(graphics -> image(graphics, img, x, y));
	}
	*/
	

	/**
	 * Draws only only the specified moving element in the game board from its data,
	 * using an existing Graphics2D object.
	 * 
	 * @param graphics a Graphics2D object provided by the default method
	 *                 {@code draw(ApplicationContext, GameData)}
	 * @param data     the GameData containing the game data.
	 * @param moving   the moving element.
	 */
	/*public void moveAndDrawElement(Graphics2D graphics, BoardGame data, MovingElement moving);

	/**
	 * Draws only the specified moving element in the game board from its data,
	 * using an existing {@code ApplicationContext}.
	 * 
	 * @param context the {@code ApplicationContext} of the game
	 * @param data    the GameData containing the game data.
	 * @param moving  the moving element.
	 
	public default void moveAndDrawElement(ApplicationContext context, BoardGame data, MovingElement moving) {
		context.renderFrame(graphics -> moveAndDrawElement(graphics, data, moving));
	}*/
	void specificImage(Graphics2D graphics, Image img, int x, int y, int width, int height);
	/*
	public default void specificImage(ApplicationContext context, Image img, int x, int y, int width, int height) {
		context.renderFrame(graphics -> specificImage(graphics, img, x, y, width, height));
	}
	*/
	public void string(Graphics2D graphics, String str, float x, float y);
	/*
	public default void string(ApplicationContext context, String str, float x, float y) {
		context.renderFrame(graphics -> string(graphics, str, x, y));
	}*/
}
