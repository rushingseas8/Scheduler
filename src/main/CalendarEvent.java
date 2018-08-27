package main;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toCollection;

import java.util.stream.Stream;

public class CalendarEvent {
    public MovingInterval interval;

    public CalendarEvent(MovingInterval interval) {
        this.interval = interval;
    }

    public static ArrayList<CalendarEvent> optimize(ArrayList<CalendarEvent> input) {
        return optimizeMI
                (
                    input.stream().map(c -> c.interval).collect(toCollection(ArrayList::new))
                ).stream().map(CalendarEvent::new).collect(toCollection(ArrayList::new));

    }

    private static ArrayList<MovingInterval> optimizeMI(ArrayList<MovingInterval> input) {
        input.sort((a, b) -> {
            if (a.max < b.max) {
                return -1;
            } else if (a.max == b.max) {
                System.out.println("Found equal endpoints!");
                return 0;
            }
            return 1;
        });

        ArrayList<MovingInterval> output = new ArrayList<>();
        for (int i = 0; i < input.size(); i++) {
            // Try to add the first one.
            if (output.size() == 0) {
                output.add(input.get(i));
                continue;
            }

            MovingInterval A = output.get(output.size() - 1);
            MovingInterval B = input.get(i);

            // Guaranteed that the new block won't fit given A in the sol'n.
            if (B.max - B.width < A.pos + A.width) {
                System.out.println("Not placing block " + B.toString() + " due to conflict with " + A.toString());
                continue;
            }

            // Here, we should split into two attempts and pick the best one.
            // For now we're just taking the first solution.
            //if (A.pos + A.width + B.width < B.max) {
            B.setPosition(Math.max(B.min, A.pos + A.width));
            output.add(B);
            //}
        }

        System.out.println("Found max size to be " + output.size());

        return output;
    }
}
