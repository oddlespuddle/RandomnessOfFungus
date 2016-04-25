import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.awt.Color;

public class Mystery extends Actor{
	private Actor value;
	
	public Mystery(Actor value)
	{
		super();
		this.value = value;
		setColor(Color.GRAY);
	}
	
	public boolean isFree()
	{
		return value == null;
	}
	
	public Actor getValue()
	{
		return value;
	}
	
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
