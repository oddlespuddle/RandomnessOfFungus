/**
 * Battle class is a JPanel representing an RPG style battle scene
 * and also keeps track of user input data and its randomness, ending
 * the game when necessary. 
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-04-30 (ISO)
 */

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Battle extends JPanel
{
	private static final List<Integer> userInputs = new LinkedList<>();
	private Overworld floor;

	/**
	 * Stores a reference to the floor whose GUI must be returned to after
	 * this battle, adds scenery and music, and responds to user input.
	 * @param floor - the floor to which the GUI must return.
	 */
	public Battle(Overworld floor)
	{
		setFocusable(true);
		this.floor = floor;
		addComponents();
		takeInput();
	}
	
	/**
	 * Adds scenery to the GUI: Title text, enemy sprite, and options.
	 */
	private void addComponents()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.fill = GridBagConstraints.BOTH;
		
		this.add(centeredTextBox("YOU ARE EXPERIENCING AN APPARITION OF RNGESUS", Color.GRAY), c);
		c.gridy++;
		
		this.add(pictureLabel("EnemySprites/RNGesusSprite.jpg"), c);
		c.gridy++;

		this.add(centeredTextBox("Options:", Color.GRAY), c);
		c.gridy++;
		
		JPanel options = new JPanel(new GridLayout(2, 2));
		for(int gridx = 0; gridx < 4; gridx++)
			options.add(centeredTextBox(""+gridx, Color.GRAY));
		
		this.add(options, c);
	}
	
	/**
	 * Plays thematic music while responding to user data, returning to
	 * the overworld or ending the game as necessary.
	 */
	private void takeInput()
	{
		final Clip clip = themeClip("EnemyMusic/RickRoll.wav");
		if(clip != null)
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		
		this.addKeyListener(new KeyListener() 
		{
			public void keyReleased(KeyEvent e)
			{
				char c = e.getKeyChar();
				if(c >= '1' && c <= '4')
				{
					//~ userInputs.add(Character.getNumericValue(c));
					if(clip != null)
						clip.stop();
					overworldReturn();
				}
			}
			
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
	}
	
	/**
	 * Ends the battle by returning the GUI to the Overworld.
	 */
	private void overworldReturn()
	{
		if(floor != null)
			floor.overworldReturn();
	}
	
	/**
	 * Returns a JTextField with a centered, non-editable, non-focusable 
	 * given text with a given color background.
	 * @param message - the text to be displayed in this JTextField
	 * @param background - the background color for this JTextField
	 * @return a JTextField with a centered, non-editable, non-focusable 
	 *         given text with a given color background.
	 */
	private static JTextField centeredTextBox(String message, Color background)
	{
		JTextField ret = new JTextField(message);
		ret.setHorizontalAlignment(JTextField.CENTER);
		ret.setEditable(false);
		ret.setFocusable(false);
		ret.setBackground(background);
		return ret;
	}
	
	/**
	 * Returns a JLabel containing the image contained in the file
	 * with the given file name.
	 * @param fileName - the name of the image file to be encapsulated.
	 * @return a JLabel containing the given image.
	 */
	private static JLabel pictureLabel(String fileName)
	{
		try
		{
			ImageIcon imgIcon = new ImageIcon(ImageIO.read(new FileInputStream(fileName)));
			imgIcon = new ImageIcon(imgIcon.getImage().getScaledInstance(500, 400, Image.SCALE_AREA_AVERAGING));
			return new JLabel(imgIcon);
		}
		catch(IOException ioe)
		{
			System.err.println("Missing file: " + fileName);
			return new JLabel();
		}
	}

	/**
	 * Returns a Clip object corresponding to a given WAV file, handling
	 * exceptions as necessary.
	 * @param fileName - the name of a WAV file.
	 * @return a Clip object corresponding to a given WAV file, or null
	 *         if exceptions prevent the file from being created.
	 */
	private static Clip themeClip(String fileName)
	{
		try
		{
			File soundFile = new File(fileName);
			AudioSystem.getAudioInputStream(soundFile);
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
			Clip ret = AudioSystem.getClip(null);
			ret.open(audioIn);
			return ret;
		}
		catch(IOException e)
		{
			System.err.println("Missing file: " + fileName);
		}
		catch(UnsupportedAudioFileException uafe)
		{
			System.err.println("Unsupported Audio File: " + fileName);
		}
		catch(LineUnavailableException lue)
		{
			System.err.println("Unavailable line for: " + fileName);
		}
		return null;
	}

	/**
	 * Performs a chi squared statistical test on all user inputs throughout
	 * the game so far.
	 * @return a value in [0, 1] describing randomness, with 1 being more
	 *         random than 0.
	 */
	private static double getChiSquared()
	{
		return chiSquaredUniformityTest(userInputs);
	}

	/**
	 * Performs a chi squared statistical test on a given list of Integers
	 * @param data - a list of Integers on which to perform analysis
	 * @return a value in [0, 1] describing randomness, with 1 being more
	 *         random than 0.
	 */
	private static double chiSquaredUniformityTest(List<Integer> data) 
	{
		int total = 0;
		for (int n : data)
			total += n;
		
		double chiSquared = 0;
		for (int n : data) 
		{
			double expected = (double) total / data.size();
			double diff = n - expected;
			chiSquared += diff * diff / expected;
		}
		return 1 - chiSquaredCDF(chiSquared, data.size() - 1);
	}
	
	/**
	 * Approximates the gamma function for a given real number n.
	 * @param n - the number for which gamma shall be approximated.
	 * @return an approximation of the gamma function for the given n.
	 */
	private static double gamma(double n) 
	{
		n--;
		return Math.sqrt(2 * Math.PI * n) * Math.pow(n / Math.E, n);
	}
	

	private static double lowerIncompleteGamma(double n, double x) 
	{
		double s = 0;
		for (double t = 0; t < x; t+= .001) {
			s += Math.pow(t, n-1)*Math.pow(Math.E, -t)*.001;
		}
		return s;
	}
	
	private static double chiSquaredCDF(double x, double k) {
		return lowerIncompleteGamma(k/2, x/2)/gamma(k/2);
	}
}
