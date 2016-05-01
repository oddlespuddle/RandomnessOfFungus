/**
 * Staircase class is an Actor representing the exit from a Overworld of the
 * GameViewer.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-04-30 (ISO)
 */

import info.gridworld.actor.Actor;
import java.awt.Color;

public class Staircase extends Actor{
	public static final Color STAIR_COLOR = new Color(203, 152, 0);
	
	/**
	 * Creates the Staircase object with an appropriate color.
	 */
	public Staircase()
	{
		super();
		setColor(STAIR_COLOR);
	}
}
