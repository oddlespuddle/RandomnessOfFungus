/** 
 * Mystery class hides the values of other Actors by storing them
 * and taking their place in the grid until they are revealed.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-05-14 (ISO)
 */

import info.gridworld.actor.Actor;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.Color;

public class Mystery extends Actor{
	private Actor value;
	
	/**
	 * Instantiates this Mystery with a reference to the Actor being hidden.
	 */
	public Mystery(Actor value)
	{
		super();
		this.value = value;
		setColor(Color.GRAY);
	}
	
	/**
	 * Reveals the value of this Mystery by replacing itself in the grid
	 * with its own value.
	 */
	public Actor reveal()
	{
		Location loc = getLocation();
		Grid<Actor> grid = getGrid();
		removeSelfFromGrid();
		if(value != null)
			value.putSelfInGrid(grid, loc);
		return value;
	}
}
