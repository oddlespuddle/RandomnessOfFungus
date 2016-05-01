/**
 * RNGesus class is an Actor representing the boss from any 10th Overworld 
 * of the GameViewer.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-04-30 (ISO)
 */

import info.gridworld.actor.Actor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import javax.swing.Timer;

public class RNGesus extends Actor{
	public static final int DELAY = 100;
	private final Overworld floor;
	private Timer colorTimer;
	
	/**
	 * Creates the RNGesus object, keeping a reference to the floor
	 * containing it so that color may be changed and creating a Timer
	 * to time that color changing effect.
	 * @param floor - the floor containing this RNGesus
	 */
	public RNGesus(final Overworld floor)
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
	
	/**
	 * Stops the timer controling the flashing colors of this RNGesus.
	 */
	public void stopFlashing()
	{
		colorTimer.stop();
	}
	
	/**
	 * Starts the timer controling the flashing colors of this RNGesus.
	 */
	public void startFlashing()
	{
		colorTimer.start();
	}
}
