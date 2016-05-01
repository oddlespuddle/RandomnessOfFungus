/**
 * Player class is an Actor representing the player's own movable character
 * within the current floor of the GameViewer.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-05-01 (ISO)
 */

import info.gridworld.actor.Actor;
import java.awt.Color;

public class Player extends Actor{
	public static final Color PLAYER_COLOR = new Color(203, 152, 0);

	/**
	 * Creates the Player object with an appropriate color that really
	 * brings out the DOGE in the corresponding sprite.
	 */
	public Player()
	{
		super();
		setColor(PLAYER_COLOR);
	}
}
