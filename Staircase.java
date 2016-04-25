import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.awt.Color;

public class Staircase extends Actor{
	public static final Color STAIR_COLOR = new Color(203, 152, 0);
	public Staircase()
	{
		super();
		setColor(STAIR_COLOR);
	}
}
