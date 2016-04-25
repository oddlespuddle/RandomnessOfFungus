import info.gridworld.world.World;

public class Overworld{
	private Floor floor;
	private int floorNumber;
	
	public static void main(String args[])throws Exception{
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
