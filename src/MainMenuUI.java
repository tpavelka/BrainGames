
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MainMenuUI extends JFrame {
    public static MainMenuUI frame;
	public static MemoryGameUI memoryGame;
	public static CodeGameUI codeGame;
	
	private static JButton code;
	private static JButton memory;
	
	public void abspos() {
		int uiwidth = MainMenuUI.frame.getLayeredPane().getWidth();
		int uiheight = MainMenuUI.frame.getLayeredPane().getHeight();
		
		int row_hght = 40;
		int brdr = 10;
		
		int row1 = 0;
		int row2 = row1 + row_hght + brdr;
		
		int col_wdth = 200;
		int col1 = 0;
        int hght = row2 + row_hght;
		
		Point ref = new Point((uiwidth/2) - (col_wdth /2), (uiheight/2) - (hght/2));
		
		memory.setBounds(ref.x + col1, ref.y + row1, col_wdth, row_hght);
		code.setBounds	(ref.x + col1, ref.y + row2, col_wdth, row_hght);
		
		display();
	}
	
	private void display() {
		frame.getLayeredPane().removeAll();
		
		frame.getLayeredPane().add(memory, 20);
		frame.getLayeredPane().add(code, 20);
		
		frame.getLayeredPane().validate();
		frame.getLayeredPane().repaint();
	}
	
	public MainMenuUI() {
		super("Memory Games");

		frame = this;
		setBounds(10, 10, 800, 600);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		memory = new JButton("Memory Match");
		memory.addActionListener(e -> memoryGame = new MemoryGameUI());
		
		code = new JButton("Master Mind");
		code.addActionListener(e -> codeGame = new CodeGameUI());
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			frame = new MainMenuUI();
			frame.setVisible(true);
			frame.abspos();
		});
	}
}
