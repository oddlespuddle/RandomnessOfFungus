/**
 * Overworld class provides a grid in which the overworld of the game takes
 * place, tracking the player, staircase, enemies, traps, loot, and starting
 * battles when necessary.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-05-01 (ISO)
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Overworld extends World<Actor>
{
	private static final int SIDE_LENGTH = 10;
	private static final Map<String, Integer> KEY_DIRECTION;
	
	static
	{
		KEY_DIRECTION = new HashMap<>();
		KEY_DIRECTION.put("W", Location.NORTH);
		KEY_DIRECTION.put("A", Location.WEST);
		KEY_DIRECTION.put("S", Location.SOUTH);
		KEY_DIRECTION.put("D", Location.EAST);
	} 
	
	private Player player;
	private Staircase staircase;
	private Container overworldPane;
	private int floorNumber;
	
	/**
	 * Provides creates a new overworld, initiating gameplay.
	 * @param args - An array of command line positional arguments which
	 * is conventionally required but in this case unusued.
	 */
	public static void main(String args[]) 
	{
		System.setProperty("info.gridworld.gui.selection" ,"hide");
		System.setProperty("info.gridworld.gui.tooltips" ,"hide");
		Overworld floor = new Overworld();
	}

	/**
	 * Takes a reference to the overworld in which this
	 * floor is located. It also stores the current contentPane which
	 * is necessary to return to the overworld from battles.
	 * @param overworld - the GameViewer object that contains this Overworld.
	 */
	public Overworld()
	{
		super();
		this.overworldPane = getContentPane();
		this.floorNumber = 0;
		nextFloor();
	}
	
	/**
	 * Sets the window title to the game title and floor number
	 * creates a new grid, populates it with all game Actors, then masks
	 * all spaces of the board except the user.
	 */
	public void nextFloor()
	{
		setGrid(new BoundedGrid<Actor>(SIDE_LENGTH, SIDE_LENGTH));
		generateMaze();
		
		staircase = new Staircase();
		staircase.putSelfInGrid(getGrid(), getRandomEmptyLocation());
		
		int enemyNumber = 5;
		for(int x = 0; x < enemyNumber; x++)
			new Enemy().putSelfInGrid(getGrid(), getRandomEmptyLocation());
		
		Location playerLoc = getRandomEmptyLocation();
		mask();
		
		player = new Player();
		player.putSelfInGrid(getGrid(), playerLoc);
		unmask(player.getLocation());
		floorNumber++;
		show();
		getWorldFrame().setTitle("Randomness of Fungus - Overworld " + floorNumber);
	}
	
	/**
	 * Hides the value of all tiles by populating them with Mystery objects
	 * containing the values that actually belong in those tiles.
	 */
	public void mask()
	{
		for(int r = 0; r < getGrid().getNumRows(); r++)
			for(int c = 0; c < getGrid().getNumCols(); c++)
			{
				Location loc = new Location(r, c);
				Actor value = getGrid().get(loc);
				if(value != null)
					value.removeSelfFromGrid();
				new Mystery(value).putSelfInGrid(getGrid(), loc);
			}
	}
	
	/**
	 * Resets the frame to view and focus on the overworld, as opposed
	 * to the battle scene.
	 */
	public void overworldReturn()
	{
		setContentPane(overworldPane);
		getWorldFrame().requestFocusInWindow();
		validate();
		repaint();
	}
	
	private void doBattle()
	{
		this.overworldPane = getContentPane();
		Battle battle = new Battle(this);
		setContentPane(battle);
		battle.requestFocusInWindow();
		validate();
		repaint();
	}
	
	/**
	 * Unboxes all Mystery objects adjacent to a given location, making 
	 * them visible to the player.
	 * @param start - the location of the tile whose neighbors shall unbox
	 */
	public void unmask(Location start)
	{
		for(Actor myst : getGrid().getNeighbors(start))
			if(myst instanceof Mystery)
				((Mystery) myst).reveal();
	}
	
	/**
	 * Populates the grid with rocks in a maze like fashion
	 * using the randomized Prim's algorithm
	 */
	private void generateMaze()
	{
		for(int r = 0; r < getGrid().getNumRows(); r++)
			for(int c = 0; c < getGrid().getNumCols(); c++)
				new Rock().putSelfInGrid(getGrid(), new Location(r, c));
		
		int randRow = (int) (Math.random()*getGrid().getNumRows());
		int randCol = (int) (Math.random()*getGrid().getNumCols());
		getGrid().remove(new Location(randRow, randCol));
		
		List<Location> walls = new ArrayList<>();
		walls.addAll(getPrimNeighbours(new Location(randRow, randCol)));
		while(!walls.isEmpty())
		{
			Location randomFrontier = walls.remove((int) (Math.random()*walls.size()));
			List<Location> freeNeighbours = new ArrayList<>();
			for(Location loc : getPrimNeighbours(randomFrontier))
				if(getGrid().get(loc) == null)
					freeNeighbours.add(loc);
				else if(!walls.contains(loc))
					walls.add(loc);
			
			Location randomFree = freeNeighbours.get((int) (Math.random()*freeNeighbours.size()));
			Location randomMiddle = new Location((randomFrontier.getRow() + randomFree.getRow())/2,
												(randomFrontier.getCol() + randomFree.getCol())/2);
			getGrid().remove(randomFrontier);
			getGrid().remove(randomMiddle);
		}
	}
	
	/**
	 * Returns a list of all locations that are two tiles away from a
	 * given location in any cardinal direction which are also within the
	 * boundaries of the grid.
	 * 
	 * @param loc - The location from which neighbours will be found.
	 * @return a list of neighbours fitting the description above.
	 */
	private List<Location> getPrimNeighbours(Location loc)
	{
		List<Location> ret = new ArrayList<>();
		for(int dr = -2; dr <= 2; dr += 2)
			for(int dc = -2; dc <= 2; dc += 2)
				if(dr == 0 ^ dc == 0)
				{
					Location pot = new Location(loc.getRow()+dr, loc.getCol()+dc);
					if(getGrid().isValid(pot))
						ret.add(pot);
				}
		return ret;
	}
	
	/**
	 * Moves the player when the W, A, S, or D keys are pressed.
	 * Both parameters are supplied by the GridWorld GUI.
	 * 
	 * @param description - The string describing the key pressed 
	 * @param loc - The selected location in the grid at the time the key was pressed
	 * @return true if the world consumes the key press, false if the GUI should
	 * consume it.
	 */
	@Override
	public boolean keyPressed(String description, Location loc)
	{
		Location pot = null;
		if(KEY_DIRECTION.containsKey(description))
			pot = player.getLocation().getAdjacentLocation(KEY_DIRECTION.get(description));
		
		if(pot != null && getGrid().isValid(pot))
		{
			Actor destination = getGrid().get(pot);
			if(destination instanceof Enemy)
				doBattle();
			
			if(destination == null || destination instanceof Enemy) 
			{
				player.moveTo(pot);
				unmask(pot);
			}
			if(getGrid().get(pot) == staircase)
				nextFloor();
		}
		
		return pot != null;
	}
}
