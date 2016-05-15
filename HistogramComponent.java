/**
 * HistogramComponent class draws a histogram of the player's attack
 * choices and combinations thereof.
 * @author Alexander Wong and Jiaming Chen
 * Period: 2
 * Date: 2016-05-15 (ISO)
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class HistogramComponent extends JComponent 
{
	public static final int TEXT_FONT_SIZE = 25;
	public static final int LABEL_FONT_SIZE = 20;
	public static final int LABEL_WIDTH = 100;
	public static final int BREAK_HEIGHT = 20;
	private int[] frequencies;
	private int max = 0;
	private int groupSize;
	
	/**
	 * Stores an array of frequencies, determines the maximum value therein
	 * and the size of its group.
	 * @param frequencies - an array of frequencies to operate on.
	 */
	HistogramComponent(int[] frequencies) 
	{
		this.frequencies = frequencies;
		for (int n : frequencies) 
			if (n > max)
				max = n;
		groupSize = (int) (Math.log(frequencies.length) / Math.log(Battle.NUM_OPTIONS));
	}

	/**
	 * Paints the histogram and all relevant information.
	 * @param g - The graphics object to draw with.
	 */
	public void paintComponent(Graphics g) 
	{
		int y = 0;
		int width = getWidth() - JScrollBar.WIDTH;
		g.setFont(new Font("Comic Sans MS", Font.PLAIN, TEXT_FONT_SIZE));
		
		y += TEXT_FONT_SIZE;
		g.drawString("You have lost the game!", 0, y);
		
		y += TEXT_FONT_SIZE;
		g.drawString("Here's how you failed to be random:", 0, y);
		g.setFont(new Font("Courier", Font.PLAIN, LABEL_FONT_SIZE));
		
		y += BREAK_HEIGHT + LABEL_FONT_SIZE;
		g.drawString("Move Set", 0, y);
		g.drawString("Frequency", (width - LABEL_WIDTH) / 2 + LABEL_WIDTH, y);
		drawRectangles(g, y, width);
		setPreferredSize(new Dimension(width, y));
	}
	
	/**
	 * Draws all bars of the histogram.
	 * @param g - The graphics object to draw with.
	 * @param y - The starting height of the bars.
	 * @param width - The width of the bars.
	 */
	private void drawRectangles(Graphics g, int y, int width)
	{
		for (int i = 0; i < frequencies.length; i++) 
		{
			g.fillRect(LABEL_WIDTH, y, 
				(width - LABEL_WIDTH) * frequencies[i] / max, LABEL_FONT_SIZE);
			y += LABEL_FONT_SIZE;
			char[] moves = new char[groupSize];
			int n = i;
			for (int j = groupSize - 1; j >= 0; j--) {
				moves[j] = (char) (n % Battle.NUM_OPTIONS + '1');
				n /= Battle.NUM_OPTIONS;
			}
			String label = String.format("%8s", new String(moves));
			g.drawString(label, 0, y);
		}
	}
}
