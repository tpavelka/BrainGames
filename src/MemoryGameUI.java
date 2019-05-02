
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MemoryGameUI {
	private MemoryGame game;
    private GameUI gameui;
		
	public MemoryGameUI() {
        ConfigUI configui = new ConfigUI();
		configui.display();
	}
	
	private class ConfigUI {
		private JLabel header;
		
		private JLabel grid_width;
		private JLabel grid_height;

		private JTextField width;
		private JTextField height;

		private JButton start;
		
		private void display() {
			JLayeredPane pane = MainMenuUI.frame.getLayeredPane();

			pane.removeAll();
			
			pane.add(header, 20);

			pane.add(grid_width, 20);
			pane.add(grid_height, 20);
			pane.add(width, 20);
			pane.add(height, 20);
			pane.add(start, 20);
			
			pane.validate();
			pane.repaint();
		}
		
		private ConfigUI() {
			header = new JLabel("Memory Game Configurations");
			header.setVerticalAlignment(JLabel.TOP);
			header.setHorizontalAlignment(JLabel.CENTER);
			header.setFont(new Font("Calibri", Font.PLAIN, 30));
			
			grid_width = new JLabel("Grid Width: ");
			grid_height = new JLabel("Grid Height: ");
			
			width = new JTextField("4");
			height = new JTextField("4");
			
			start = new JButton("Start Game!");
			
			start.addActionListener(e -> {
                boolean pass = false;
                int cells_width = Integer.parseInt(width.getText());
                int cells_height = Integer.parseInt(height.getText());
                
                if(cells_width % 2 == 0 && cells_height % 2 == 0
                && cells_width > 0 		&& cells_height > 0      ) {
                	pass = true;
                } else {
                	JOptionPane.showMessageDialog(MainMenuUI.frame,
                			"Both inputs must be even and greater than zero.",
                			"User Input Error!", JOptionPane.ERROR_MESSAGE);
                }
                
                if(pass) {
	                gameui = new GameUI(cells_width, cells_height);
	                gameui.display();
	
	                game = new MemoryGame(gameui);
                }
            });
			
			int uiWidth = MainMenuUI.frame.getLayeredPane().getWidth();
			int uiheight = MainMenuUI.frame.getLayeredPane().getHeight();
			
			int hdr_hght = 60;
			int row_hght = 40;
			int brdr = 10;
			
			int row_hdr = 0;
			int row1 = row_hdr + hdr_hght + brdr;
			int row2 = row1 + row_hght + brdr;
			int row3 = row2 + row_hght + brdr;
			
			int col_wdth = 200;
			int hdr_wdth = col_wdth * 2;
			
			int col1 = 0;
			int col2 = col1 + col_wdth + brdr;
			
			int wdth = col2 + col_wdth;
			int hght = row3 + row_hght;
			
			Point ref = new Point((uiWidth/2) - (wdth/2), (uiheight/2) - (hght/2));
			
			header.setBounds	((uiWidth/2) - (hdr_wdth/2), ref.y + row_hdr, hdr_wdth, hdr_hght);
			
			grid_width.setBounds	(ref.x + col1, ref.y + row1, col_wdth, row_hght);
			grid_height.setBounds	(ref.x + col1, ref.y + row2, col_wdth, row_hght);
			
			width.setBounds	(ref.x + col2, ref.y + row1, col_wdth, row_hght);
			height.setBounds(ref.x + col2, ref.y + row2, col_wdth, row_hght);

			start.setBounds	(ref.x + col2, ref.y + row3, col_wdth, row_hght);
		}
	}
	
	class GameUI {
		private MemTimer hdr_timer;
		private GameOverUI gameoverui;
		
		private int cols;

		private int rows;

        private JButton[][] cells;

        int getNumCols() {
            return cols;
        }

		int getNumRows() {
			return rows;
		}

		/**
		 * Setting the icon to null should symbolize flipping the card face down.
		 */
        void setIcon(int row, int col, ImageIcon icon) {
		    cells[row][col].setIcon(icon);
		}

		void stopTimer() {
			hdr_timer.stop();
		}
		
		void onGameOver(boolean has_won) {
			gameoverui.display(has_won);
		}

		private void display() {
			MainMenuUI.frame.getLayeredPane().removeAll();
			
			MainMenuUI.frame.getLayeredPane().add(hdr_timer, 20);

            for (JButton[] cell : cells) {
                for (JButton jButton : cell) {
                    MainMenuUI.frame.getLayeredPane().add(jButton, 20);
                }
            }
			
			MainMenuUI.frame.getLayeredPane().validate();
			MainMenuUI.frame.getLayeredPane().repaint();
		}
		
		private GameUI(int width, int height) {
			this.cols = width;
			this.rows = height;

			hdr_timer = new MemTimer();
			hdr_timer.setFont(new Font("Calibri", Font.PLAIN, 30));
			gameoverui = new GameOverUI();
			
			cells = new JButton[height][width];
			
			double uiwidth = MainMenuUI.frame.getLayeredPane().getWidth();
			double uiheight = MainMenuUI.frame.getLayeredPane().getHeight();

            double hdr_hght = 30d;
			
			double timer_wdth = 200d;

            double game_hght = uiheight - hdr_hght;
			
			double cell_wdth = uiwidth / cols;
			double cell_hght = game_hght/ rows;
			double inset = 1;
			
			hdr_timer.setBounds((int) ((uiwidth /2) - (timer_wdth/2)), 0, (int) timer_wdth, (int) hdr_hght);
			
			for(int row = 0; row < rows; row++) {
				for(int col = 0; col < cols; col++) {
					cells[row][col] = new JButton();
					cells[row][col].setBounds(
							(int) ((col * cell_wdth) + inset),
							(int) ((row * cell_hght) + inset + hdr_hght),
							(int) (cell_wdth - (2 * inset)),
							(int) (cell_hght - (2 * inset)) );
					cells[row][col].addActionListener(new CardListener(row, col));
				}
			}
		}
		
		private class CardListener implements ActionListener {
			int row;
			int col;
			
			@Override
			public void actionPerformed(ActionEvent e) {
				game.cardSelected(row, col);
			}
			
			CardListener(int row, int col) {
				this.row = row;
				this.col = col;
			}
		}
	}
}
