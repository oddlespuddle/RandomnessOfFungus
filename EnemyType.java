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
			pictureLabel("EnemySprites/RNGesus.jpg"),
			themeClip("EnemyMusic/RNGesus.wav")),
	TROLL("TROLLOLOL",
			pictureLabel("EnemySprites/Troll.jpg"),
			themeClip("EnemyMusic/Troll.wav")),
	RICK_ASTLEY("NEVER GONNA GIVE YOU UP",
			pictureLabel("EnemySprites/RickAstley.gif"),
			themeClip("EnemyMusic/RickAstley.wav")),
	NYAN_CAT("NYANNYANYANYANYANYAN",
			pictureLabel("EnemySprites/NyanCat.jpg"),
			themeClip("EnemyMusic/NyanCat.wav"));
	
	public static final EnemyType[] types = {TROLL, RICK_ASTLEY, NYAN_CAT};
	private final String text;
	private final JLabel sprite;
	private final Clip music;
	
	EnemyType(String text, JLabel sprite, Clip music)
	{
		this.text = text;
		this.sprite = sprite;
		this.music = music;
	}
	
	public static EnemyType getRandomEnemyType(){
		return types[(int) (Math.random()*types.length)];
	}
	
	public String getText()
	{
		return text;
	}
	
	public JLabel getSprite() 
	{
		return sprite;
	}
	
	public Clip getMusic()
	{
		return music;
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
}
