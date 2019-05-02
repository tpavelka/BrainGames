

import java.awt.*;
import javax.swing.Timer;

public class Card {
    private int row;
    private int col;
    private Timer timer;

    Card(int row, int col) {
        this.row = row;
        this.col = col;
    }

    int getRow() {
        return row;
    }

    int getCol() {
        return col;
    }

    void setTimer(Timer timer) {
        this.timer = timer;
    }

    void start() {
        timer.start();
    }

    void stop() {
        timer.stop();
    }

}

