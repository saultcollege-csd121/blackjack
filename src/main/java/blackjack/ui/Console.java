package blackjack.ui;

import blackjack.game.Card;
import blackjack.game.PointCounter;

import java.util.List;
import java.util.Scanner;

public class Console {

    public static void println(String message) {
        System.out.println(message);
    }

    /**
     * Prompt the user for one line of text input
     * @param message A message to display to the user
     * @return The line of text input by the user
     */
    public static String prompt(String message) {
        System.out.print(message);

        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    /**
     * Repeatedly prompt the user until they enter one of several valid options.
     * @param prompt The message to display to the user
     * @param options The set of valid options that the user may enter
     * @return The option entered by the user
     */
    public static String promptForOption(String prompt, String... options) {
        while (true) {
            String response = prompt(prompt + "(" + String.join("/", options) + "): ");
            for (String option : options) {
                if (response.equalsIgnoreCase(option)) {
                    return option;
                }
            }
            println("Please enter one of: " + String.join("/", options));
        }
    }

    /**
     * Repeatedly prompt the user for an integer between min and max (inclusive)
     * @param prompt The message to display to the user
     * @param min The minimum value that the user may enter
     * @param max The maximum value that the user may enter
     * @return A valid integer entered by the user
     */
    public static int promptForInt(String prompt, int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(prompt(prompt));
                if (value >= min && value <= max) {
                    return value;
                } else {
                    println("Please enter an integer between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                println("Please enter an integer between " + min + " and " + max);
            }
        }
    }

    public static void showHand(String prefix, List<Card> hand) {
        showHand(prefix, hand, false, false);
    }

    public static void showHand(String prefix, List<Card> hand, boolean showPoints) {
        showHand(prefix, hand, showPoints, false);
    }

    /**
     * Display a hand of cards to the console, including the point value of that hand
     * @param prefix A message to display before the hand
     * @param hand The list of cards in the hand to display
     * @param showPoints Whether to display the point value of the hand
     * @param hideFirstCard Whether to hide the first card in the hand (for the dealer's hand)
     */
    public static void showHand(String prefix, List<Card> hand, boolean showPoints, boolean hideFirstCard) {
        System.out.print(prefix + ": ");
        boolean isFirst = true;
        for (Card card : hand) {
            if ( isFirst ) {
                isFirst = false;
                if ( hideFirstCard ) {
                    System.out.print("?? ");
                    continue;
                }
            }
            System.out.print(card + " ");
        }
        if ( showPoints ) {
            System.out.print("(" + PointCounter.count(hand) + ")");
        }
        System.out.println();
    }

    public static void showChipCount(int chips) {
        System.out.println("You have " + chips + " chips.");
    }
}