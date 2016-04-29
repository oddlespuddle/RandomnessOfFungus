import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class Battle extends JPanel
{
	public static void main(String args[]) throws Exception 
	{
		//Frame Setup
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setContentPane(new Battle());
		frame.setVisible(true);
	}
	
	public Battle() throws Exception
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Battle Declaration
		JTextArea messageArea = new JTextArea(3, 20);
		messageArea.setEditable(false);
		messageArea.setFocusable(false);
		messageArea.setBackground(new Color(0xFAFAD2));
		messageArea.setText("\nYOU ARE EXPERIENCING AN APPARITION OF RNGESUS");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 4;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		this.add(messageArea, c);
		
		//RNGesus
		JLabel rnGesus = pictureLabel("EnemySprites/RNGesusSprite.png");
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		this.add(rnGesus, c);

		//Options
		c.gridy = 3;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.BOTH;
		for(int gridx = 0; gridx < 4; gridx++)
		{
			c.gridx = gridx;
			JTextArea playerOption = new JTextArea(3, 20);
			playerOption.setEditable(false);
			playerOption.setFocusable(false);
			playerOption.setBackground(new Color(0xFAFAD2));
			playerOption.setText("Option No. " + gridx + "\nSquirrel");
			this.add(playerOption, c);
		}
	}
	
	public static JLabel pictureLabel(String fileName) throws Exception
	{
		return new JLabel(
					new ImageIcon(
						ImageIO.read(
							ClassLoader.
								getSystemClassLoader().
									getResourceAsStream(fileName))));
	}
}
