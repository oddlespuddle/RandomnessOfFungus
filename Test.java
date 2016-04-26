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

public class Test{
	public static void main(String args[]) throws Exception {
		//Frame Setup
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.CYAN);
		
		//Panel Setup
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		//Battle Declaration
		JTextArea messageArea = new JTextArea(3, 20);
		messageArea.setEditable(false);
		messageArea.setFocusable(false);
		messageArea.setBackground(new Color(0xFAFAD2));
		messageArea.setText("\nYOU ARE EXPERIENCING AN APPARITION OF RNGESUS");
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.fill = GridBagConstraints.BOTH;
		panel.add(messageArea, c);
		
		//RNGesus
		JLabel rnGesus = pictureLabel("EnemySprites/RNGesusSprite.png");
		c.gridx = 0;
		c.gridy = 2;
		c.fill = GridBagConstraints.HORIZONTAL;
		panel.add(rnGesus, c);
		
		panel.add(rnGesus);

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
			panel.add(playerOption, c);
		}
		
		frame.add(panel);
		frame.setVisible(true);
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
