

import java.awt.Color;
import java.util.Random;

public class CodeGame {
	CodeGameUI ui;
	
	Random rand = new Random();
	
	Color[] winningColors =  { Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN };
	
	// these are the colors a player adds to each row
	// this is called when the player presses go
	public void pushColors(Color c1, Color c2, Color c3, Color c4, int currentRow) {
		int correct = 0;
		int white = 0;
		int currentSquare = 0;
		
		// Austin logic for win ui
		// 1 = win, 0 = lose
		int win = 0; 
		
		if(c1 == winningColors[0]) {
			correct += 1;
		} else if(c1 == winningColors[1]
				|| c1 == winningColors[2]
				|| c1 == winningColors[3]) {
				white++;
		}
		
		if(c2 == winningColors[1]) {
			correct += 1;
		} else if (c2 == winningColors[0]
				|| c2 == winningColors[2]
				|| c2 == winningColors[3]) {
				white++;
		}
		
		if(c3 == winningColors[2]) {
			correct += 1;
		} else if (c3 == winningColors[0]
				|| c3 == winningColors[1]
				|| c3 == winningColors[3]) {
				white++;
		}
		
		if(c4 == winningColors[3]) {
			correct += 1;
		} else if(c4 == winningColors[0]
				|| c4 == winningColors[1]
				|| c4 == winningColors[2]) {
				white++;
		}
		
		for(int i=0; i<correct; i++) {
            ui.setBlack(currentRow, i);
            currentSquare++;
            if (i == 3) {
            	win = 1;  // Austin
            }
		}
		
		for(int i=0; i<white; i++) {
            ui.setWhite(currentRow, currentSquare);
            currentSquare++;
		}
		System.out.println(currentRow);
		
		// Austin added code to show game over message
		try {
			Thread.sleep(600);  // so the colors show before going to the game over ui
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(win == 1) {
			ui.onGameOver(true);
		}
		if(currentRow == 1 && win == 0) {
			ui.onGameOver(false);
		}
		ui.setPushingColors(false);
		// end Austin code
	}
	
	public CodeGame(CodeGameUI ui) {
		this.ui = ui;
	}
}
