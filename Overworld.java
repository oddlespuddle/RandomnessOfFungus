import info.gridworld.world.World;

public class Overworld{
	private Floor floor;
	private int floorNumber;
	
	public static void main(String args[])throws Exception{
		System.setProperty("info.gridworld.gui.selection" ,"hide");
		System.setProperty("info.gridworld.gui.tooltips" ,"hide");
		Overworld world = new Overworld();
	}
	
	public Overworld()
	{
		floorNumber = 1;
		floor = new Floor(this);
		show();
	}
	
	public void nextFloor()
	{
		floorNumber++;
		floor.nextFloor();
		show();
	}
	
	public int getFloorNumber()
	{
		return floorNumber;
	}
	
	public void show()
	{
		floor.show();
	}
}
