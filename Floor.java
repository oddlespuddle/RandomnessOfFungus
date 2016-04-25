import info.gridworld.actor.Actor;
import info.gridworld.actor.Flower;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Floor extends World<Actor>
{
	//~ public static final Color PLAYER_COLOR = new Color(203, 152, 0);
	public static final int SIDE_LENGTH = 10;
	private Overworld overworld;
	private Actor player;
	private Actor staircase;
	
	/**
	 * This constructor takes a reference to the overworld in which this
	 * floor is located. It generates the grid and the maze, then places
	 * the player and staircase.
	 */
	public Floor(Overworld overworld)
	{
		super();
		this.overworld = overworld;
		nextFloor();
	}
	
	/**
	 * This method sets the message to inform the user of the floor number
	 * and controls, creates a new grid, populates it with rocks to form
	 * a maze, places the staircase, masks all pieces except the player and
	 * 
	 */
	public void nextFloor()
	{
		setMessage("Welcome to floor " + overworld.getFloorNumber() + "!\nUse WASD to move!");
		setGrid(new BoundedGrid<Actor>(SIDE_LENGTH, SIDE_LENGTH));
		generateMaze();
		
		staircase = new Flower();
		staircase.putSelfInGrid(getGrid(), getRandomEmptyLocation());
		
		Location playerLoc = getRandomEmptyLocation();
		mask();
		
		player = new Player();
		player.putSelfInGrid(getGrid(), playerLoc);
		unmask(player.getLocation());
	}
	
	public void mask()
	{
		//Fill the board with walls
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
	
	public void unmask(Location start)
	{
		for(Location loc : getGrid().getValidAdjacentLocations(start))
			if(getGrid().get(loc) instanceof Mystery)
				((Mystery) getGrid().get(loc)).reveal();
	}
	
	/**
	 * This method populates the grid with rocks in a maze like fashion
	 * using a randomized Prim's algorithm
	 */
	private void generateMaze()
	{
		//Fill the board with walls
		for(int r = 0; r < getGrid().getNumRows(); r++)
			for(int c = 0; c < getGrid().getNumCols(); c++)
				new Rock().putSelfInGrid(getGrid(), new Location(r, c));
		
		//Pick a starting point and free it
		int randRow = (int) (Math.random()*getGrid().getNumRows());
		int randCol = (int) (Math.random()*getGrid().getNumCols());
		getGrid().remove(new Location(randRow, randCol));
		
		//Add all neighbours of the starting point
		List<Location> walls = new ArrayList<>();
		walls.addAll(getPrimNeighbours(new Location(randRow, randCol)));
		
		//Exhaust all neighbours
		while(!walls.isEmpty())
		{
			//Pick a random point of expansion
			Location randomFrontier = walls.remove((int) (Math.random()*walls.size()));
			
			//Make a list of neighbours who are not walls
			List<Location> freeNeighbours = new ArrayList<>();
			for(Location loc : getPrimNeighbours(randomFrontier))
				if(getGrid().get(loc) == null)
					freeNeighbours.add(loc);
				//Neighbours that are walls are added to the frontier if not already
				else if(!walls.contains(loc))
					walls.add(loc);
			
			//Pick a random non-wall neighbour
			Location randomFree = freeNeighbours.get((int) (Math.random()*freeNeighbours.size()));
			
			//And the point between the random frontier point and its neighbour
			Location randomMiddle = new Location((randomFrontier.getRow() + randomFree.getRow())/2,
												(randomFrontier.getCol() + randomFree.getCol())/2);
			
			//And remove them both.
			getGrid().remove(randomFrontier);
			getGrid().remove(randomMiddle);
		}
	}
	
	/**
	 * Returns a list of all locations that are two tiles away from a
	 * given location in a cardinal direction which are also within the
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
	 * This method is called when a key was pressed. 
	 * It is overridden to consume the W, A, S, and D keys as arrow keys
	 * and uses them to move the player sprite around the grid.
	 * 
	 * Both parameters are supplied by the GridWorld GUI.
	 * 
	 * @param description - The string describing the key pressed 
	 * @param loc - The selected location in the grid at the time the key was pressed
	 * @return true if the world consumes the key press, false if the GUI should
	 * consume it.
	 */
	@Override
	public boolean keyPressed(String description, Location loc){
		Location pot = null;
		switch(description)
		{
			case "W":
				pot = player.getLocation().getAdjacentLocation(Location.NORTH);
				break;
			case "A":
				pot = player.getLocation().getAdjacentLocation(Location.WEST);
				break;
			case "S":
				pot = player.getLocation().getAdjacentLocation(Location.SOUTH);
				break;
			case "D":
				pot = player.getLocation().getAdjacentLocation(Location.EAST);
				break;
			default:
				return false;
		}
		
		if(getGrid().isValid(pot))
		{
			Actor destination = getGrid().get(pot);
			if(destination == null) //Destination will never be a Mystery
			{
				player.moveTo(pot);
				unmask(pot);
			}
			else if(getGrid().get(pot) == staircase)
				overworld.nextFloor();
		}
		return true;
	}
}
