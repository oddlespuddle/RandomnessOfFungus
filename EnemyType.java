/** 
 * EnemyType enum stores the messages, images, and clips associated
 * with a variety of mem-based enemies to be used in battle.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-05-14 (ISO)
 */

import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public enum EnemyType {
	RNGESUS("YOU ARE EXPERIENCING AN APPARITION OF RNGESUS",
			"RNGesus angrily mumbles integers to himself",
			"RNGesus is pleased",
			pictureLabel("EnemySprites/RNGesus.jpg"),
			themeClip("EnemyMusic/RNGesus.wav")),
	TROLL("TROLLOLOL",
			"LOLNOPE",
			"Well played, n00b",
			pictureLabel("EnemySprites/Troll.jpg"),
			themeClip("EnemyMusic/Troll.wav")),
	RICK_ASTLEY("NEVER GONNA GIVE YOU UP",
			"NEVER GONNA WIN THIS GAME",
			"NEVER GONNA LET YOU DOWN",
			pictureLabel("EnemySprites/RickAstley.gif"),
			themeClip("EnemyMusic/RickAstley.wav")),
	MUDKIPZ("SO I HERD U LIEK MUDKIPZ",
			"KIP! >_>",
			"Mud!",
			pictureLabel("EnemySprites/Mudkipz.jpg"),
			themeClip("EnemyMusic/Mudkipz.wav")),
	NYAN_CAT("NYANNYANYANYANYANYAN",
			"NYANNYANYANYANYANYAN >_<",
			"NYANNYANYANYAAAAAAAN :3",
			pictureLabel("EnemySprites/NyanCat.jpg"),
			themeClip("EnemyMusic/NyanCat.wav"));
	
	public static final EnemyType[] types = {TROLL, RICK_ASTLEY, NYAN_CAT, MUDKIPZ};
	public static final int IMAGE_WIDTH = 500;
	public static final int IMAGE_HEIGHT = 400;
	private final String text;
	private final String negative;
	private final String positive;
	private final JLabel sprite;
	private final Clip music;
	
	/**
	 * Instantiates an EnemyType with a given intro text, negative text,
	 * positive text, sprite, and music clip.
	 * @param text - intro text
	 * @param negative - negative text
	 * @param positive - positive text
	 * @param sprite - the enemy's image in a battle
	 * @param music - the enemy's background music in battle
	 */
	EnemyType(String text, String negative, String positive, JLabel sprite, Clip music)
	{
		this.text = text;
		this.negative = negative;
		this.positive = positive;
		this.sprite = sprite;
		this.music = music;
	}
	
	/**
	 * Returns a random enemy type that is not RNGesus
	 * @return a random enemy type that is not RNGesus
	 */
	public static EnemyType getRandomEnemyType(){
		return types[(int) (Math.random()*types.length)];
	}
	
	/**
	 * Returns this EnemyType's intro text
	 * @return this EnemyType's intro text
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * Returns this EnemyType's negative text
	 * @return this EnemyType's negative text
	 */
	public String getNegative()
	{
		return negative;
	}

	/**
	 * Returns this EnemyType's positive text
	 * @return this EnemyType's positive text
	 */
	public String getPositive()
	{
		return positive;
	}

	/**
	 * Returns this EnemyType's sprite
	 * @return this EnemyType's sprite
	 */
	public JLabel getSprite() 
	{
		return sprite;
	}

	/**
	 * Returns this EnemyType's background music
	 * @return this EnemyType's background music
	 */
	public Clip getMusic()
	{
		return music;
	}
	
	/**
	 * Returns a JLabel containing the image contained in the file
	 * with the given file name scaled to 500x400.
	 * @param fileName - the name of the image file to be encapsulated.
	 * @return a JLabel containing the given image.
	 */
	private static JLabel pictureLabel(String fileName)
	{
		try
		{
			ImageIcon imgIcon = new ImageIcon(ImageIO.read(
				new FileInputStream(fileName)));
			imgIcon = new ImageIcon(imgIcon.getImage().getScaledInstance(
				IMAGE_WIDTH, IMAGE_HEIGHT, Image.SCALE_AREA_AVERAGING));
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
}
