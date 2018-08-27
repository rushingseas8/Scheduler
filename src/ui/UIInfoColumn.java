package ui;

import javax.swing.*;
import java.awt.*;

public class UIInfoColumn extends JPanel {

    private final int WIDTH = 40;
    private final int MIN_HEIGHT = 1200;

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        float step = getHeight() / 24f;
        for (int i = 0; i < 24; i++) {
            g.drawString((i % 12 == 0 ? 12 : i % 12) + (i >= 12 ? " pm" : " am"), 0, (int)(i * step) + 4);
        }
        g.drawLine(getWidth(), 0, getWidth(), getHeight());
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(WIDTH, MIN_HEIGHT);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, MIN_HEIGHT);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(WIDTH, Integer.MAX_VALUE);
    }
}
