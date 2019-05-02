

import java.awt.Font;
import java.awt.Point;

import javax.swing.JLabel;

public class GameOverUI {
	private JLabel message;
	
	public void display(boolean has_won) {
		if(has_won) {
			message.setText("You Won!");
		} else {
			message.setText("Game Over!");
		}
		
		MainMenuUI.frame.getLayeredPane().removeAll();
		
		MainMenuUI.frame.getLayeredPane().add(message, 20);
		
		MainMenuUI.frame.getLayeredPane().validate();
		MainMenuUI.frame.getLayeredPane().repaint();
	}
	
	public GameOverUI() {
		message = new JLabel();
		message.setHorizontalAlignment(JLabel.CENTER);
		message.setFont(new Font("Calibri", Font.PLAIN, 60));
		
		int width = MainMenuUI.frame.getLayeredPane().getWidth();
		int height = MainMenuUI.frame.getLayeredPane().getHeight();
		
		int wdth = 340;
		int hght = 80;
		
		Point ref = new Point((width/2) - (wdth/2), (height/2) - (hght/2));
		
		message.setBounds(ref.x, ref.y, wdth, hght);
	}
}
