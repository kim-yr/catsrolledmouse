/**
 * Input for program must be numbers. Spare is represented by number of knocked
 * down pins. Strike is represented by number 10. 
 * Strike: []   10 
 * Spare: 6 /  6 4
 *
 * Examples of input: 
 * 10 10 10 10 10 10 10 10 10 10 10 10 
 * 5 0 7 3 3 3 9 0 1 7 7 2 3 0 10 10 9 1 10
 */
package test;

public class Bowling {

    private static final int ZERO = 0;
    private static final int STRIKE = 10;

    private static final String INPUT = "Input: ";
    private static final String OUTPUT = "Output: ";
    private static final String ERR_WRONG_VALUE = "Value input is too big or low";
    private static final String ERR_SUM = "Sum of frame is bigger then ten";
    private static final String ERR_STRIKES = "Wrong numbers of strikes";
    private static final String ERR_LAST_FRAME = "Wrong number of throws in the last frame";

    public static void main(String... args) {
        System.out.print(INPUT);
        for (String s : args) {
            System.out.print(" " + s);
        }
        System.out.println();

        int[] result = doCount(args);

        System.out.print(OUTPUT);
        for (int i : result) {
            System.out.print(" " + i);
        }
    }

    public static int[] doCount(String... args) {
        try {
            int[] ballThrows = stringToInt(args);
            int[] result = new int[10];

            int i = 0;
            int frame = 0;
            int score;
            int previousScore = 0;

            for (int j = 0; j < args.length; j++) {
                if (ballThrows[j] < ZERO || ballThrows[j] > STRIKE) {
                    throw new IllegalArgumentException(ERR_WRONG_VALUE);
                }
            }

            while (frame < 10) {
                if (ballThrows[i] == STRIKE) {
                    score = previousScore + STRIKE + ballThrows[i + 1] + ballThrows[i + 2];
                    result[frame] = score;
                    if (frame == 9) {
                        if (ballThrows[i + 1] < STRIKE && ((ballThrows[i + 1] + ballThrows[i + 2]) > STRIKE)) {
                            throw new IllegalArgumentException(ERR_LAST_FRAME);
                        }
                        i += 2;
                    }
                    i++;
                } else {
                    if (ballThrows[i] + ballThrows[i + 1] == STRIKE) {
                        score = previousScore + STRIKE + ballThrows[i + 2];
                        result[frame] = score;
                        if (frame == 9) {
                            i++;
                        }
                    } else if (ballThrows[i] + ballThrows[i + 1] < STRIKE) {
                        score = previousScore + ballThrows[i] + ballThrows[i + 1];
                        result[frame] = score;
                    } else {
                        throw new IllegalArgumentException(ERR_SUM);
                    }
                    i += 2;
                }
                previousScore = score;
                frame++;
            }
            if (i != args.length) {
                throw new IllegalArgumentException(ERR_STRIKES);
            }
            return result;
        } catch (ArrayIndexOutOfBoundsException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    private static int[] stringToInt(String... args) {
        int[] integers = new int[args.length];
        try {
            for (int i = 0; i < args.length; i++) {
                integers[i] = Integer.valueOf(args[i]);
            }
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(ex);
        }
        return integers;
    }
}