import info.gridworld.actor.Actor;
import info.gridworld.grid.Location;
import info.gridworld.grid.Grid;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.Timer;

public class RNGesus extends Actor{
	public static final int DELAY = 100;
	private Timer colorTimer;
	private final Floor floor;
	
	public RNGesus(final Floor floor)
	{
		super();
		this.floor = floor;
		colorTimer = new Timer(DELAY, new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				setColor(new Color((float) Math.random(), 
								   (float) Math.random(), 
								   (float) Math.random()));
				floor.show();
			}
		});
		
		startFlashing();
	}
	
	public void stopFlashing()
	{
		colorTimer.stop();
	}
	
	public void startFlashing()
	{
		colorTimer.start();
	}
}
