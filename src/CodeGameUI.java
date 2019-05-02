
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.border.LineBorder;

public class CodeGameUI {
	private CodeGame game;
	private GameOverUI gameoverui;
	
	private boolean pushing_colors;
	public void setPushingColors(boolean is_pushing) {
		pushing_colors = is_pushing;
	}
	
	private int cur_row;
	private int num_rows;
	/**
	 * Row zero is at the top<br>
	 * Row zero has the controls<br>
	 * num_rows - 1 is the bottom row
	 */
	public int getNumRows() {
		return num_rows;
	}
	
	private ColorPusherTask go;
	private JButton push;
	private Color[] colors;
	private ColoredCellRenderer rend;
	private JComboBox<Color> box1;
	private JComboBox<Color> box2;
	private JComboBox<Color> box3;
	private JComboBox<Color> box4;
	
	private boolean pushable;
	// row zero is null, it has the controls
	// bottom row has highest position
	// positioning for colored as follows:
	// zero one two three
	private JLabel[][] colored;
	
	private JLabel[][] blk_wte;
	/**
	 * Row zero is null, it has the controls.<br>
	 * Bottom row has highest position.<br>
	 * Positioning for blk_wte as follows:<br>
	 * zero 	one<br>
	 * two		three<br>
	 */
	public void setWhite(int row_num, int pos) {
		blk_wte[row_num][pos].setBackground(Color.WHITE);
	}
	/**
	 * Row zero is null, it has the controls.<br>
	 * Bottom row has highest position.<br>
	 * Positioning for blk_wte as follows:<br>
	 * zero 	one<br>
	 * two		three<br>
	 */
	public void setBlack(int row_num, int pos) {
		blk_wte[row_num][pos].setBackground(Color.BLACK);
	}
	
	public void onGameOver(boolean has_won) {
		gameoverui.display(has_won);
	}
	
	private void display() {
		MainMenuUI.frame.getLayeredPane().removeAll();
		
		MainMenuUI.frame.getLayeredPane().add(push, 20);
		MainMenuUI.frame.getLayeredPane().add(box1, 20);
		MainMenuUI.frame.getLayeredPane().add(box2, 20);
		MainMenuUI.frame.getLayeredPane().add(box3, 20);
		MainMenuUI.frame.getLayeredPane().add(box4, 20);
		
		for(int row = 1; row < num_rows; row++) {
			for(int c = 0; c < 4; c++) {
				MainMenuUI.frame.getLayeredPane().add(blk_wte[row][c], 20);
			}
			for(int c = 0; c < 4; c++) {
				MainMenuUI.frame.getLayeredPane().add(colored[row][c], 20);
			}
		}
		
		MainMenuUI.frame.getLayeredPane().validate();
		MainMenuUI.frame.getLayeredPane().repaint();
		
		gameoverui = new GameOverUI();
		game = new CodeGame(this);
	}
	
	public CodeGameUI() {
		num_rows = 7 + 1;
		cur_row = num_rows - 1;
		
		go = new ColorPusherTask();
		push = new JButton("Go");
		push.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!pushing_colors) {
					Thread colorpushtask = new Thread(go);
					colorpushtask.start();
				}
			}
		});
		
		colors = new Color[] {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.YELLOW, Color.PINK};
		rend = new ColoredCellRenderer();
	
		box1 = new JComboBox<Color>(colors);
		box1.setBackground(Color.RED);
		box1.addActionListener(new ColorSetListener(box1));
		box1.setFocusable(false);
		box1.setEditable(false);
		box1.setRenderer(rend);
		
		box2 = new JComboBox<Color>(colors);
		box2.setBackground(Color.RED);
		box2.addActionListener(new ColorSetListener(box2));
		box2.setFocusable(false);
		box2.setEditable(false);
		box2.setRenderer(rend);
		
		box3 = new JComboBox<Color>(colors);
		box3.setBackground(Color.RED);
		box3.addActionListener(new ColorSetListener(box3));
		box3.setFocusable(false);
		box3.setEditable(false);
		box3.setRenderer(rend);
		
		box4 = new JComboBox<Color>(colors);
		box4.setBackground(Color.RED);
		box4.addActionListener(new ColorSetListener(box4));
		box4.setFocusable(false);
		box4.setEditable(false);
		box4.setRenderer(rend);
		
		colored = new JLabel[num_rows][4];
		blk_wte = new JLabel[num_rows][4];
		
		LineBorder border = new LineBorder(Color.BLACK, 1);
		for(int row = 1; row < num_rows; row++) {
			for(int c = 0; c < 4; c++) {
				colored[row][c] = new JLabel();
				colored[row][c].setBackground(Color.LIGHT_GRAY);
				colored[row][c].setBorder(border);
				colored[row][c].setOpaque(true);
				
				blk_wte[row][c] = new JLabel();
				blk_wte[row][c].setBackground(Color.LIGHT_GRAY);
				blk_wte[row][c].setBorder(border);
				blk_wte[row][c].setOpaque(true);
			}
		}
		
		int brdr = 10;
		int row_hght = 50;
		int col_wdth = 50;
		
		int[] rows = new int[num_rows];
		for(int row = 0; row < rows.length; row++) {
			if(row > 0) {
				rows[row] = rows[row-1] + row_hght + brdr;
			} else if(row == 0) {
				rows[0] = 0;
			}
		}
		int hght = rows[rows.length-1] + row_hght;
		
		int col1 = 0;
		int col2 = col1 + col_wdth + brdr;
		int col3 = col2 + col_wdth + brdr;
		int col4 = col3 + col_wdth + brdr;
		int col5 = col4 + col_wdth + brdr;
		int col6 = col5 + col_wdth + brdr;
		int col7 = col6 + col_wdth + brdr;
		
		int wdth = col7 + col_wdth;
		Point ref = new Point((MainMenuUI.frame.getLayeredPane().getWidth()/2) - (wdth/2),
							(MainMenuUI.frame.getLayeredPane().getHeight()/2) - (hght/2));
		
		push.setBounds(ref.x + col7, ref.y + rows[0], col_wdth, row_hght);
		box1.setBounds(ref.x + col3, ref.y + rows[0], col_wdth, row_hght);
		box2.setBounds(ref.x + col4, ref.y + rows[0], col_wdth, row_hght);
		box3.setBounds(ref.x + col5, ref.y + rows[0], col_wdth, row_hght);
		box4.setBounds(ref.x + col6, ref.y + rows[0], col_wdth, row_hght);
		
		brdr = 1;
		int split_wdth = (col_wdth/2) - brdr;
		int split_hght = (row_hght/2) - brdr;
		for(int row = 1; row < num_rows; row++) {
			colored[row][0].setBounds(ref.x + col3, ref.y + rows[row], row_hght, col_wdth);
			colored[row][1].setBounds(ref.x + col4, ref.y + rows[row], row_hght, col_wdth);
			colored[row][2].setBounds(ref.x + col5, ref.y + rows[row], row_hght, col_wdth);
			colored[row][3].setBounds(ref.x + col6, ref.y + rows[row], row_hght, col_wdth);
			
			// top left of blk_wte group
			Point ref1 = new Point(ref.x + col1, ref.y + rows[row]);
			blk_wte[row][0].setBounds(ref1.x, 							ref1.y, 						split_wdth, split_hght);
			blk_wte[row][1].setBounds(ref1.x + split_wdth + (brdr*2), 	ref1.y,							split_wdth, split_hght);
			blk_wte[row][2].setBounds(ref1.x, 							ref1.y + split_hght + (brdr*2),	split_wdth, split_hght);
			blk_wte[row][3].setBounds(ref1.x + split_wdth + (brdr*2), 	ref1.y + split_hght + (brdr*2), split_wdth, split_hght);
		}
		
		display();
	}
	
	private class ColoredCellRenderer implements ListCellRenderer<Color> {
		@Override
		public Component getListCellRendererComponent(JList<? extends Color> list, Color value, int index,
												 	boolean isSelected, boolean cellHasFocus) {
			JLabel comp = new JLabel();
			comp.setBackground(value);
			comp.setOpaque(true);
			comp.setPreferredSize(new Dimension(50, 30));
			return comp;
		}
	}
	
	private class ColorSetListener implements ActionListener {
		JComboBox<Color> owner;
		
		@Override
		public void actionPerformed(ActionEvent e) {
			owner.setBackground((Color) owner.getSelectedItem());
		}
		
		public ColorSetListener(JComboBox<Color> owner) {
			this.owner = owner;
		}
	}
	
	private class ColorPusherTask implements Runnable {
		@Override
		public void run() {
			if(cur_row > 0) {
				Color c1 = (Color) box1.getSelectedItem();
				Color c2 = (Color) box2.getSelectedItem();
				Color c3 = (Color) box3.getSelectedItem();
				Color c4 = (Color) box4.getSelectedItem();
			
				colored[cur_row][0].setBackground(c1);
				colored[cur_row][1].setBackground(c2);
				colored[cur_row][2].setBackground(c3);
				colored[cur_row][3].setBackground(c4);
				
				setPushingColors(true);
				game.pushColors(c1, c2, c3, c4, cur_row);
				cur_row--;
			}
		}
	}
}
