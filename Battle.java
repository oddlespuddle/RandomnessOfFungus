import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.util.LinkedList;
import java.util.List;

public class Battle extends JPanel
{
	private static final List<Integer> userInputs = new LinkedList<>();
	private Floor floor;
	
	public static void main(String args[])
	{
		//Frame Setup
		JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setContentPane(new Battle(null));
		frame.setVisible(true);
	}
	
	public Battle(Floor floor)
	{
		setFocusable(true);
		this.floor = floor;
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
		
		this.addKeyListener(new KeyListener() 
		{
			public void keyReleased(KeyEvent e)
			{
				char c = e.getKeyChar();
				System.out.println(c);
				if(c >= '1' && c <= '4')
				{
					//~ userInputs.add(Character.getNumericValue(c));
					overworldReturn();
				}
			}
			
			public void keyTyped(KeyEvent e){}
			public void keyPressed(KeyEvent e){}
		});
	}
	
	private void overworldReturn()
	{
		if(floor != null)
			floor.overworldReturn();
	}
	
	private static JTextField centeredTextBox(String message, Color background)
	{
		JTextField ret = new JTextField(message);
		ret.setHorizontalAlignment(JTextField.CENTER);
		ret.setEditable(false);
		ret.setFocusable(false);
		ret.setBackground(background);
		return ret;
	}
	
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

	private static double getChiSquared()
	{
		return chiSquaredUniformityTest(userInputs);
	}

    private static double chiSquaredUniformityTest(List<Integer> data) {
    	int total = 0;
    	for (int n : data) {
    		total += n;
    	}
    	double chiSquared = 0;
    	for (int n : data) {
    		double expected = (double) total / data.size();
    		double diff = n - expected;
    		chiSquared += diff * diff / expected;
    	}
    	return 1 - chiSquaredCDF(chiSquared, data.size() - 1);
    }
    
    private static double gamma(double n) {
    	n--;
    	return Math.sqrt(2*Math.PI*n)*Math.pow(n/Math.E, n);
    }
    private static double lowerIncompleteGamma(double n, double x) {
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
