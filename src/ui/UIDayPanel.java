package ui;

import main.CalendarEvent;
import main.MovingInterval;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class UIDayPanel extends JPanel {

    private final int MIN_WIDTH = 100;
    private final int MAX_WIDTH = 200;
    private final int MIN_HEIGHT = 1200;

    private ArrayList<CalendarEvent> rawEvents;
    private ArrayList<CalendarEvent> sortedEvents;

    public UIDayPanel() {
        super();
        this.rawEvents = new ArrayList<>();
        this.sortedEvents = new ArrayList<>();
    }

    public UIDayPanel(ArrayList<CalendarEvent> events) {
        super();
        this.rawEvents = events;
        this.sortedEvents = CalendarEvent.optimize(rawEvents);
    }

    @Override
    public void paint(Graphics g) {
        // One hour is this many pixels tall
        float step = getHeight() / 24f;

        // Background
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Black gridlines
        g.setColor(Color.BLACK);
        for (int i = 0; i < 24; i++) {
            g.drawLine(0, (int)(i * step), getWidth(), (int)(i * step));
        }
        g.drawLine(getWidth(), 0, getWidth(), getHeight());

        // Draw the actual events
        for (CalendarEvent event : sortedEvents) {
            MovingInterval interval = event.interval;

            // Range of possibilities
            int offset = 5;
            g.setColor(Color.LIGHT_GRAY);
            g.drawLine(
                    (int)(getWidth() * 0.05f),
                    (int)(interval.min * step + offset),
                    (int)(getWidth() * 0.95f),
                    (int)(interval.min * step + offset)
            );
            g.drawLine(
                    (int)(getWidth() * 0.05f),
                    (int)(interval.max * step - offset),
                    (int)(getWidth() * 0.95f),
                    (int)(interval.max * step - offset)
            );
            g.drawLine(
                    (int)(getWidth() * 0.5f),
                    (int)(interval.min * step + offset),
                    (int)(getWidth() * 0.5f),
                    (int)(interval.max * step - offset)
            );

            // Event as scheduled
            g.setColor(new Color(68, 111, 200));
            g.fillRect(
                    (int)(getWidth() * 0.05f),
                    (int)(interval.pos * step + offset),
                    (int)(getWidth() * 0.9f),
                    (int)(interval.width * step - offset)
            );
        }
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(MIN_WIDTH, MIN_HEIGHT);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MIN_WIDTH, MIN_HEIGHT);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(MAX_WIDTH, Integer.MAX_VALUE);
    }

    public void addEvent(CalendarEvent event) {
        this.rawEvents.add(event);
        this.sortedEvents = CalendarEvent.optimize(rawEvents);
    }

    public void addEvent(MovingInterval interval) {
        this.rawEvents.add(new CalendarEvent(interval));
        this.sortedEvents = CalendarEvent.optimize(rawEvents);
    }

}
