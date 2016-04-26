import info.gridworld.actor.Actor;
import info.gridworld.actor.ActorWorld;
import info.gridworld.grid.BoundedGrid;
import info.gridworld.grid.Grid;
import info.gridworld.grid.Location;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.Graphics;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

public class Test{
	public static void main(String args[]) throws Exception {
		//Frame Setup
		final JFrame frame = new JFrame();
		frame.setSize(500, 500);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.BLACK);
		
		//Picture Setup
		JLabel rnGesus = pictureLabel("EnemySprites/RNGesusSprite.png");
		JPanel panel = new JPanel();
		panel.add(rnGesus);
		panel.setBackground(Color.WHITE);
		//~ rnGesus.setSize(525, 207);
		//~ rnGesus.setLocation(20, 20);
		frame.add(panel);
		//~ (new Timer(50, new ActionListener() {
				//~ public void actionPerformed(ActionEvent e){
					//~ if(pen.getX() + pen.getWidth() > frame.getWidth()- boy.getWidth())
						//~ pen.setLocation(girl.getWidth(), 85);
					//~ else
						//~ pen.setLocation(pen.getX() + frame.getWidth()/80, 85);
				//~ }
		//~ })).start();
		
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
