

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

class MemoryGame {
	// This is the ui which displays the game.
	private MemoryGameUI.GameUI ui;
	
	// This is a list of flipped card. Cards get removed when they are
	// re-flipped.
	private List<Card> flippedCards;
	
	private List<Card> flippedPairs;
	
	// This is a matrix of images for each card.
	private BufferedImage[][] images;
    
    private int rows;
    
    private int cols;

	void cardSelected(int row, int col) {
	    Image image = images[row][col];

	    Card card = new Card(row, col);
        ui.setIcon(row, col, new ImageIcon(image));
        
        for(Card flippedCard: flippedCards) {
        	// Ignore it if its already flipped.
        	if(row == flippedCard.getRow() && col == flippedCard.getCol()) {
        	return;
        	}
        	
        	// Stop the other timer and never start the second one
        	// if two pairs are flipped
            if(images[row][col] == images[flippedCard.getRow()][flippedCard.getCol()]) {
                flippedCard.stop();
                flippedCards.remove(flippedCard);
                
                flippedPairs.add(flippedCard);

                // A victory was detected. Stop the timer and give popup.
                if (flippedPairs.size() == rows * cols / 2) {
                	ui.stopTimer();
                    
                    ui.onGameOver(true);
                    }
                return;
            }
        }

        // We need to check if it in the completed pairs in order so another
        // timer doesn't get added.
        for(Card pair: flippedPairs) {
            // Skips the timer if its in a flipped pair
            if (images[row][col] == images[pair.getRow()][pair.getCol()]) {
                return;
            }
        }

        // Add the card to the flipped set. It is removed when the timer goes off.
        flippedCards.add(card);

        // After 1 second, set the card face down and remove from set.
        Timer timer = new Timer(1000, e -> {
            ui.setIcon(row, col, null);
            flippedCards.remove(card);

            // We need to stop the timer after everything is flipped.
            ((Timer)e.getSource()).stop();
        });

        card.setTimer(timer);
        card.start();
    }

    private void getRowCol(Random rand, BufferedImage image) {
        int imgX;
        int imgY;
        do {
            imgX = rand.nextInt(rows);
            imgY = rand.nextInt(cols);
        } while(images[imgX][imgY] != null);

        images[imgX][imgY] = image;
    }

    // Allocates random images and flips them over after a short time.
    private void pickImages() {
    	List<Integer> tempImages = new Vector<>(rows * cols / 2);
        Random rand = new Random();
        File file = new File("Images/");
        File[] files = file.listFiles();
        System.out.println(file.getAbsolutePath());
        
        assert files != null;
        
        for (int i = 0; i < rows * cols / 2; i++) {
            try {
                BufferedImage image;
                Integer num;
                do {
                    num = rand.nextInt(files.length);
                    image = ImageIO.read(files[num]);
                } while(tempImages.contains(num));

                // Get two empty points and assign the image to them.
                getRowCol(rand, image);
                getRowCol(rand, image);

                tempImages.add(num);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    MemoryGame(MemoryGameUI.GameUI ui) {
		this.ui = ui;
		flippedCards = new Vector<>();
		flippedPairs = new Vector<>();

		rows = ui.getNumRows();
		cols = ui.getNumCols();

		images = new BufferedImage[rows][cols];

		pickImages();
	}
}
