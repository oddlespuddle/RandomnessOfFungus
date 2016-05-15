/**
 * Overworld class provides a grid in which the overworld of the game takes
 * place, tracking the player, enemies, traps, loot, and starting battles
 * when necessary.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-05-14 (ISO)
 */

import info.gridworld.actor.Actor;
import info.gridworld.actor.Rock;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import info.gridworld.world.World;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Overworld extends World<Actor>
{
	private static final int SIDE_LENGTH = 10;
	private static final int TUTORIAL_BATTLE_LENGTH = 5;
	private static final int BOSS_FLOOR_MOD = 10;
	private static final int MID_ROW = SIDE_LENGTH / 2;
	private static final int NUM_ENEMIES = 5;
	private static final int NUM_LOOT = 5;
	private static final double MID_ALPHA = 0.5;
	private static final double AMPLITUDE = 0.5;
	private static final double CURSE_PROBABILITY = 0.2;
	private static final Map<String, Integer> KEY_DIRECTION;
	
	static
	{
		KEY_DIRECTION = new HashMap<>();
		KEY_DIRECTION.put("W", Location.NORTH);
		KEY_DIRECTION.put("UP", Location.NORTH);
		KEY_DIRECTION.put("A", Location.WEST);
		KEY_DIRECTION.put("LEFT", Location.WEST);
		KEY_DIRECTION.put("S", Location.SOUTH);
		KEY_DIRECTION.put("DOWN", Location.SOUTH);
		KEY_DIRECTION.put("D", Location.EAST);
		KEY_DIRECTION.put("RIGHT", Location.EAST);
	} 
	
	private Player player;
	private Container overworldPane;
	private int floorNumber;
	private int lootGained;
	private boolean isBattling;
	private boolean isCursed;
	private Set<Location> lootSpots;
	
	/**
	 * Provides creates a new overworld, initiating gameplay.
	 * Hides gui selection and tooltips because they are unnecessary.
	 * @param args - An array of command line positional arguments which
	 * is conventionally required but in this case unusued.
	 */
	public static void main(String args[]) 
	{
		System.setProperty("info.gridworld.gui.selection", "hide");
		System.setProperty("info.gridworld.gui.tooltips", "hide");
		Overworld floor = new Overworld();
	}

	/**
	 * Stores the current contentPane, sets the floor number and loot to
	 * 0, sets isBattline and isCursed to false, and begins the tutorial.
	 */
	public Overworld()
	{
		super();
		overworldPane = getContentPane();
		floorNumber = 0;
		lootGained = 0;
		isBattling = false;
		isCursed = false;
		tutorialFloor();
	}
	
	/**
	 * Advances to the next floor, increasing the floor number.
	 * Brings the player to a boss floor every 10th floor.
	 */
	public void nextFloor()
	{
		floorNumber++;
		if(floorNumber % BOSS_FLOOR_MOD == 0)
			bossFloor();
		else
			generalFloor();
		show();
	}
	
	/**
	 * Resets the frame to view and focus on the overworld, as opposed
	 * to the battle scene.
	 */
	public void overworldReturn()
	{
		isBattling = false;
		setContentPane(overworldPane);
		getWorldFrame().requestFocusInWindow();
		validate();
		repaint();
	}
	
	/**
	 * Ends the game
	 */
	public void loseTheGame()
	{
		System.out.println("You just lost the game!");
		System.exit(0);
	}
	
	/**
	 * Moves the player when the W, A, S, D, or arrow keys are pressed,
	 * starting battles, advancing floors, cursing, looting, and unmasking
	 * as necessary. Both parameters are supplied by the GridWorld GUI.
	 * 
	 * @param description - The string describing the key pressed 
	 * @param loc - The selected location in the grid at the time the key was pressed
	 * @return true if the world consumes the key press, false if the GUI should
	 * consume it.
	 */
	@Override
	public boolean keyPressed(String description, Location loc)
	{
		if(!isBattling && KEY_DIRECTION.containsKey(description))
		{
			Location pot = player.getLocation().getAdjacentLocation(
				KEY_DIRECTION.get(description));
			if(pot != null && getGrid().isValid(pot))
			{
				Actor destination = getGrid().get(pot);
				if(destination instanceof Enemy)
					doBattle((Enemy) destination);
				if(destination == null || destination instanceof Enemy) 
					player.moveTo(unmask(pot));
				if(lootSpots != null && lootSpots.contains(pot))
					determineLoot(pot);
				if(destination instanceof Staircase)
					nextFloor();
			}
		}
		return true;
	}
	
	/**
	 * Sets the window title to the game title and "Tutorial Floor,"
	 * and places rocks, enemies, a staircase, and the player appropriately.
	 */
	private void tutorialFloor()
	{
		setGrid(new BoundedGrid<Actor>(SIDE_LENGTH, SIDE_LENGTH));
		for (int col = 0; col < SIDE_LENGTH; col++) 
		{
			new Rock().putSelfInGrid(getGrid(), new Location(MID_ROW - 1, col));
			new Rock().putSelfInGrid(getGrid(), new Location(MID_ROW + 1, col));
		}
		
		new Staircase().putSelfInGrid(getGrid(), 
			new Location(MID_ROW, SIDE_LENGTH - 1));
		
		for (int col = 2; col < SIDE_LENGTH; col += 2)
			new Enemy(EnemyType.getRandomEnemyType())
				.putSelfInGrid(getGrid(), new Location(MID_ROW, col));
		
		player = new Player();
		player.putSelfInGrid(getGrid(), new Location(MID_ROW, 0));
		unmask(player.getLocation());
		show();
		getWorldFrame().setTitle("Randomness of Fungus - Tutorial Floor");
	}
	
	/**
	 * Sets the window title to the game title and information aboue the
	 * floor. Places rocks, enemies, a staircase, and the player
	 * randomly, then masks all spaces of the board except the user.
	 */
	private void generalFloor()
	{
		setGrid(new BoundedGrid<Actor>(SIDE_LENGTH, SIDE_LENGTH));
		generateMaze();
		new Staircase().putSelfInGrid(getGrid(), getRandomEmptyLocation());
		
		for(int x = 0; x < NUM_ENEMIES; x++)
			new Enemy(EnemyType.getRandomEnemyType())
				.putSelfInGrid(getGrid(), getRandomEmptyLocation());
		
		Location playerLoc = getRandomEmptyLocation();
		lootSpots = pickLootSpots();
		mask();
		
		player = new Player();
		player.putSelfInGrid(getGrid(), playerLoc);
		unmask(player.getLocation());
		
		show();
		String floorInfo = floorNumber + " (Loot: " + lootGained + ")";
		getWorldFrame().setTitle("Randomness of Fungus - Floor " + floorInfo);
	}
	
	/**
	 * Sets the window title to the game title and "Boss Floor,"
	 * and places rocks, RNGesus, a staircase, and the player appropriately.
	 */
	private void bossFloor()
	{
		setGrid(new BoundedGrid<Actor>(SIDE_LENGTH, SIDE_LENGTH));
		for (int col = 0; col < SIDE_LENGTH; col++) 
		{
			new Rock().putSelfInGrid(getGrid(), new Location(MID_ROW - 1, col));
			new Rock().putSelfInGrid(getGrid(), new Location(MID_ROW + 1, col));
		}
		
		new Staircase().putSelfInGrid(getGrid(), 
			new Location(MID_ROW, SIDE_LENGTH - 1));
		
		new RNGesus(this).putSelfInGrid(getGrid(), 
			new Location(MID_ROW, SIDE_LENGTH - 2));
		
		player = new Player();
		player.putSelfInGrid(getGrid(), new Location(MID_ROW, 0));

		show();
		getWorldFrame().setTitle("Randomness of Fungus - Boss Floor");
	}
	
	/**
	 * Hides the value of all tiles by populating them with Mystery objects
	 * containing the values that actually belong in those tiles.
	 */
	private void mask()
	{
		for(int row = 0; row < SIDE_LENGTH; row++)
		{
			for(int col = 0; col < SIDE_LENGTH; col++)
			{
				Location loc = new Location(row, col);
				Actor value = getGrid().get(loc);
				if(value != null)
					value.removeSelfFromGrid();
				new Mystery(value).putSelfInGrid(getGrid(), loc);
			}
		}
	}
	
	/**
	 * Instantiate a battle with an appropriate number of moves against
	 * a given enemy and change the GUI content pane to be that battle.
	 * @param enemy - An enemy to fight against
	 */
	private void doBattle(Enemy enemy)
	{
		overworldPane = getContentPane();
		isBattling = true;
		
		int numTurns;
		if(floorNumber == 0)
			numTurns = TUTORIAL_BATTLE_LENGTH;
		else if(floorNumber % BOSS_FLOOR_MOD == 0)
			numTurns = floorNumber;
		else
			numTurns = (int) Math.round(Math.log(floorNumber)) + 1;
		
		double alphaCurse = 0;
		if(isCursed)
			alphaCurse = MID_ALPHA + AMPLITUDE * Math.sin(floorNumber * Math.PI);
		
		Battle battle = new Battle(this, enemy, numTurns, alphaCurse);
		setContentPane(battle);
		battle.requestFocusInWindow();
		validate();
		repaint();
	}
	
	/**
	 * Returns a set of 5 random free spaces to become loot spots
	 * @return a set of 5 random free spaces
	 */
	private Set<Location> pickLootSpots()
	{
		Set<Location> ret = new HashSet<Location>();
		if(getGrid() != null)
			for(int x = 0; x < NUM_LOOT; x++)
				ret.add(getRandomEmptyLocation());
		return ret;
	}
	
	/**
	 * There is a 1/5 chance that this method will curse the player.
	 * If the player is already cursed, the player will cease to be cursed.
	 * Otherwise, the player will gain a loot point.
	 * Removes the given loot spot from the set of loot spots.
	 * @param the loot spot to remove. 
	 */
	private void determineLoot(Location loc)
	{
		lootSpots.remove(loc);
		if(Math.random() < CURSE_PROBABILITY && !isCursed)
		{
			isCursed = true;
			setCursed(true);
		}
		else if(isCursed)
		{
			isCursed = false;
			setCursed(false);
		}
		else
		{
			lootGained++;
			String floorInfo = floorNumber + " (Loot: " + lootGained + ")";
			getWorldFrame().setTitle("Randomness of Fungus - Floor " + floorInfo);
		}
	}
	
	/**
	 * Unboxes all Mystery objects adjacent to a given location, making 
	 * them visible to the player and return the starting point.
	 * @param start - the location of the tile whose neighbors shall unbox
	 * @return the given location
	 */
	private Location unmask(Location start)
	{
		for(Actor myst : getGrid().getNeighbors(start))
			if(myst instanceof Mystery)
				((Mystery) myst).reveal();
		return start;
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
}
