import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.awt.Color;

public class Enemy extends Actor{
	public static final Color ENEMY_COLOR = Color.WHITE;
	public Enemy()
	{
		super();
		setColor(ENEMY_COLOR);
	}
}
