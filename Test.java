import javax.sound.sampled.*;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;

public class Test {
        
    public static void main(String[] args) throws Exception {
        File soundFile = new File("chimes.wav");
        AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
        final Clip clip = AudioSystem.getClip(null);
        clip.open(audioIn);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        JButton button = new JButton();
        button.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (clip.isRunning()) {
        			clip.stop();
        		}
        		else {
        			clip.loop(Clip.LOOP_CONTINUOUSLY);
        		}
        	}
        });
        frame.setContentPane(button);
        frame.setVisible(true);
    }
}
