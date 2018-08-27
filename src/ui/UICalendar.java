package ui;

import main.MovingInterval;

import javax.swing.*;
import java.awt.*;

public class UICalendar {
    private static JFrame frame;
    private static JScrollPane mainScroll;
    private static JPanel mainPanel;

    private static UIInfoColumn infoColumn;
    private static UIDayPanel day1Panel;
    private static UIDayPanel day2Panel;
    private static UIDayPanel day3Panel;
    private static UIDayPanel day4Panel;
    private static UIDayPanel day5Panel;

    public static void main(String[] args) {
        frame = new JFrame("Calendar");
        frame.setSize(640, 600);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(600, 400));
        frame.setMaximumSize(new Dimension(1050, Integer.MAX_VALUE));

        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));

        infoColumn = new UIInfoColumn();
        day1Panel = new UIDayPanel();
        day2Panel = new UIDayPanel();
        day3Panel = new UIDayPanel();
        day4Panel = new UIDayPanel();
        day5Panel = new UIDayPanel();

        mainPanel.add(infoColumn);
        mainPanel.add(day1Panel);
        mainPanel.add(day2Panel);
        mainPanel.add(day3Panel);
        mainPanel.add(day4Panel);
        mainPanel.add(day5Panel);

        day1Panel.addEvent(new MovingInterval(9, 17, 8)); // work
        day1Panel.addEvent(new MovingInterval(10, 20, 1)); // running
        day1Panel.addEvent(new MovingInterval(10, 20f, 1)); // running

        mainScroll = new JScrollPane(mainPanel);

        frame.add(mainScroll);
        frame.setVisible(true);
    }
}
