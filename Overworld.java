/**
 * Overworld class is the main class of this project, which uses the
 * Floor class to provide an overworld and user interface to this game.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-04-30 (ISO)
 */

import info.gridworld.world.World;

public class Overworld{
	private Floor floor;
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
		Overworld world = new Overworld();
	}
	
	/**
	 * Sets the floor number to 1, creates a new floor, and initializes
	 * the GUI.
	 */
	public Overworld()
	{
		floorNumber = 1;
		floor = new Floor(this);
		show();
		if(floor.getWorldFrame() != null)
			floor.getWorldFrame().setTitle("Randomness of Fungus - Floor " + floorNumber);
	}
	
	/**
	 * Increases the floor number, generates a new floor, and reinizialises
	 * the GUI.
	 */
	public void nextFloor()
	{
		floorNumber++;
		floor.nextFloor();
		show();
	}
	
	/**
	 * Returns the current floor number.
	 * @return the current floor number.
	 */
	public int getFloorNumber()
	{
		return floorNumber;
	}
	
	/**
	 * Initialize or restart the GUI.
	 */
	public void show()
	{
		floor.show();
	}
}
