package test;

import main.MovingInterval;

import java.util.ArrayList;

public class SchedulerTest {

    public static void main(String[] args) {

        int n = 1000;
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            if (i % 100 == 0) {
                System.out.println((i / 100f) + "% done!");
            }
            ArrayList<MovingInterval> intervals = generateRandom(35);
            //System.out.println("Generated list of intervals:");
            //intervals.forEach(System.out::println);

            ArrayList<MovingInterval> maximal = findMax(intervals);
            //System.out.println("Maximum subset of size " + maximal.size() + ":");
            //maximal.forEach(System.out::println);

            ArrayList<MovingInterval> brute = findMaxBrute(intervals);
            //System.out.println("Brute force of size " + brute.size() + ":");
            //brute.forEach(System.out::println);

            if (brute.size() != maximal.size()) {
                flag = true;
                System.out.println("Found discrepancy! Maximal size: " + maximal.size() + " and Brute size: " + brute.size());

                System.out.println("Maximal: ");
                maximal.forEach(System.out::println);

                System.out.println("Brute: ");
                brute.forEach(System.out::println);
            }
        }

        if (!flag) {
            System.out.println("Nothing wrong found in " + n + " iterations.");
        }
    }

    private static ArrayList<MovingInterval> findMax(ArrayList<MovingInterval> input) {
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
            if (B.max - B.width < A.max) {
                continue;
            }

            // Here, we should split into two attempts and pick the best one.
            // For now we're just taking the first solution.
            //if (A.pos + A.width + B.width < B.max) {
                B.setPosition(Math.max(B.min, A.pos + A.width));
                output.add(B);
            //}
        }

        return output;
    }

    private static ArrayList<MovingInterval> findMaxBrute(ArrayList<MovingInterval> input) {
        return findMaxBruteRecur(input, new ArrayList<>());
    }

    private static ArrayList<MovingInterval> findMaxBruteRecur(ArrayList<MovingInterval> input, ArrayList<MovingInterval> output) {

        // Base case.
        if (input.size() == 0) {
            return output;
        }

        // Try both adding and discarding; no checks against previous entries.
        if (output.size() == 0) {
            ArrayList<MovingInterval> outputCopy = new ArrayList<>();
            ArrayList<MovingInterval> outputCopyPlusOne = new ArrayList<>();
            outputCopyPlusOne.add(input.get(0));

            ArrayList<MovingInterval> inputCopyLessOne = new ArrayList<>(input);
            inputCopyLessOne.remove(0);

            ArrayList<MovingInterval> takeVal = findMaxBruteRecur(inputCopyLessOne, outputCopyPlusOne);
            ArrayList<MovingInterval> discardVal = findMaxBruteRecur(inputCopyLessOne, outputCopy);

            return takeVal.size() > discardVal.size() ? takeVal : discardVal;
        }

        // Otherwise, we have at least one entry already in the output.
        // We only try adding it if it's possible to do so.

        ArrayList<MovingInterval> outputCopy = new ArrayList<>(output);
        ArrayList<MovingInterval> outputCopyPlusOne = new ArrayList<>(output);

        MovingInterval A = outputCopyPlusOne.get(outputCopyPlusOne.size() - 1);
        MovingInterval B = input.get(0);

        if (B.max - B.width < A.max) {
            // Discard the element and continue without it

            ArrayList<MovingInterval> inputCopyLessOne = new ArrayList<>(input);
            inputCopyLessOne.remove(0);

            return findMaxBruteRecur(inputCopyLessOne, outputCopy);
        } else {

            // Try by discarding it anyway here
            ArrayList<MovingInterval> inputCopyLessOne = new ArrayList<>(input);
            inputCopyLessOne.remove(0);
            ArrayList<MovingInterval> discardVal = findMaxBruteRecur(inputCopyLessOne, outputCopy);

            // Set B as far left as possible and try adding it
            B.setPosition(Math.min(B.min, A.pos + A.width));
            outputCopyPlusOne.add(B);
            ArrayList<MovingInterval> inputCopyLessOneAgain = new ArrayList<>(input);
            inputCopyLessOneAgain.remove(0);
            ArrayList<MovingInterval> takeVal = findMaxBruteRecur(inputCopyLessOneAgain, outputCopyPlusOne);

            return takeVal.size() > discardVal.size() ? takeVal : discardVal;
        }
    }

    private static ArrayList<MovingInterval> generateRandom(int amount) {
        ArrayList<MovingInterval> toReturn = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            float min = (float)(Math.random() * 100);
            float max = (float)(Math.random() * (100 - min)) + min;
            float width = (float)(Math.random() * (max - min));

            toReturn.add(new MovingInterval(min, max, width));
        }

        return toReturn;
    }
}
