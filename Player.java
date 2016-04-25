import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.awt.Color;

public class Player extends Actor{
	public static final Color PLAYER_COLOR = new Color(203, 152, 0);
	public Player()
	{
		super();
		setColor(PLAYER_COLOR);
	}
}
