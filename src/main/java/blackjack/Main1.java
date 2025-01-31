package blackjack;

import blackjack.game.CardStack;
import blackjack.game.PointCounter;

import java.util.ArrayList;
import java.util.List;

import static blackjack.ui.Console.*;


/****************************************************************
 * ABOUT MAIN1
 * This is the first version of the main class that we made in class
 * It's a good start. We have tried to separate our game data and
 * operations (in the game package) from the presentation of that data
 * (in the ui package)
 * All the logic for the game is in one place here in the main method.
 * It would be nice if we could model that logic (e.g. the game rules)
 * in a separate class and then just refer to that class from here.
 * That's what is done in Main2.
 */

public class Main1 {

    public static void main(String[] args) {

        println("Welcome to Blackjack!");

        int chips = 100;

        println("""
                Your goal is to get closest to 21 without going over.
                Face cards are worth 10; number cards are worth their card value.
                Aces can be worth 1 or 11.
                The minimum bet is 1 chip.""");

        while ( true ) {

            var gameOver = false;

            showChipCount(chips);

            if ( chips == 0 ) {
                println("You're out of chips! Goodbye!");
                System.exit(0);
            }

            // Ask the user if they want to play a round
            var response = promptForOption("Would you like to play a round? ", "y", "n");

            if ( response.equals("y") ) {

                var betAmount = promptForInt("How much do you want to bet? ", 1, chips);

                var deck = CardStack.createShuffled52CardDeck();

                var playerHand = new ArrayList<>(List.of(deck.takeOne()));
                var dealerHand = new ArrayList<>(List.of(deck.takeOne()));
                playerHand.add(deck.takeOne());

                if ( PointCounter.count(playerHand) == 21 ) {
                    showHand("Your hand", playerHand);
                    println("Blackjack! You win!");
                    chips += betAmount * 3 / 2;
                    continue;
                }

                dealerHand.add(deck.takeOne());

                if ( PointCounter.count(dealerHand) == 21 ) {
                    showHand("Your hand", playerHand);
                    showHand("Dealer's hand", dealerHand);
                    println("Dealer has Blackjack! You lose!");
                    chips -= betAmount;
                    continue;
                }

                // Player's turn; they hit until they stand or bust
                while (true) {
                    showHand("Dealer's hand", dealerHand, false, true);
                    showHand("Your hand", playerHand, true);
                    response = promptForOption("Hit or stand? ", "h", "s");
                    if ( response.equalsIgnoreCase("h") ) {
                        playerHand.add(deck.takeOne());
                        if ( PointCounter.count(playerHand) > 21 ) {
                            showHand("Your hand", playerHand, true);
                            println("Bust! You lose!");
                            chips -= betAmount;
                            gameOver = true;
                            break;
                        } else if ( PointCounter.count(playerHand) == 21 ) {
                            showHand("Your hand", playerHand);
                            println("21! You win!");
                            chips += betAmount;
                            gameOver = true;
                            break;
                        }
                    } else {
                        break;
                    }
                }

                if ( gameOver ) {
                    continue;
                }

                // Dealer's turn
                while ( PointCounter.count(dealerHand) < 17 ) {
                    dealerHand.add(deck.takeOne());
                }

                var playerPoints = PointCounter.count(playerHand);
                var dealerPoints = PointCounter.count(dealerHand);

                showHand("Dealer's hand", dealerHand, true);
                showHand("Your hand", playerHand, true);
                if ( dealerPoints > 21 ) {
                    println("Dealer busts! You win!");
                    chips += betAmount;
                } else if ( playerPoints < dealerPoints ) {
                    println("Dealer wins!");
                    chips -= betAmount;
                } else if ( playerPoints > dealerPoints ) {
                    println("You win!");
                    chips += betAmount;
                } else {
                    println("Push!");
                }

            } else {
                println("Goodbye!");
                System.exit(0);
            }
        }

    }
}
