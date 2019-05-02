

import java.text.SimpleDateFormat;
import javax.swing.JLabel;
import javax.swing.Timer;

class MemTimer extends JLabel {
	private int count;
    private Timer timer;
    
    MemTimer() {
    	this.setText("00:00");
    	this.setHorizontalAlignment(JLabel.CENTER);
    	
        count = 0;
        SimpleDateFormat date_format = new SimpleDateFormat("mm:ss");
        timer = new Timer(1000, e -> {
            count += 1000;
            this.setText(date_format.format(count));
        });
        timer.start();
    }

    void stop() {
        timer.stop();
    }
}
