package blackjack;

import blackjack.game.main2.BlackjackGame;

import static blackjack.ui.Console.*;

/****************************************************************
 * ABOUT MAIN2
 * As an improvement on Main1, here we have extracted some of
 * the game logic into a separate class, Game.
 * We create a game object which stores the current state of the
 * game and provides methods to interact with the game.
 * We use those methods here in the main method to play the game.
 * This still leaves some of the logic regarding the game flow
 * in the main method (in the form of the sequence of conditionals
 * and loops that move the game through its phases).
 * We will tackle that in Main3.
 */

public class Main2 {

    public static void main(String[] args) {

        println("Welcome to Blackjack!");

        int chips = 100;

        println("""
                Your goal is to get closest to 21 without going over.
                Face cards are worth 10; number cards are worth their card value.
                Aces can be worth 1 or 11.
                The minimum bet is 1 chip.""");

        gameLoop: while ( true ) {

            showChipCount(chips);

            // Ask the user if they want to play a round
            var response = promptForOption("Would you like to play a round? ", "y", "n");

            if ( response.equals("y") ) {

                var betAmount = promptForInt("How much do you want to bet? ", 1, chips);

                var game = new BlackjackGame();


                if ( game.playerHasBlackjack() ) {
                    showHand("Your hand", game.getPlayerHand());
                    println("Blackjack! You win!");
                    chips += betAmount * 3 / 2;
                    continue;
                }

                if ( game.dealerHasBlackjack() ) {
                    showHand("Your hand", game.getPlayerHand());
                    showHand("Dealer's hand", game.getDealerHand());
                    println("Dealer has Blackjack! You lose!");
                    chips -= betAmount;
                    continue;
                }

                // Player's turn; they hit until they stand or bust
                while (true) {
                    showHand("Dealer's hand", game.getDealerHand(), false, true);
                    showHand("Your hand", game.getPlayerHand(), true);
                    response = promptForOption("Hit or stand? ", "h", "s");
                    if ( response.equalsIgnoreCase("h") ) {
                        game.playerHit();
                        if ( game.playerIsBust() ) {
                            showHand("Your hand", game.getPlayerHand(), true);
                            println("Bust! You lose!");
                            chips -= betAmount;
                            continue gameLoop;
                        } else if ( game.playerHasBlackjack() ) {
                            showHand("Your hand", game.getPlayerHand());
                            println("21! You win!");
                            chips += betAmount;
                            continue gameLoop;
                        }
                    } else {
                        break;
                    }
                }

                game.playDealerTurn();

                showHand("Dealer's hand", game.getDealerHand(), true);
                showHand("Your hand", game.getPlayerHand(), true);
                if ( game.dealerIsBust() ) {
                    println("Dealer busts! You win!");
                    chips += betAmount;
                } else if ( game.dealerWins() ) {
                    println("Dealer wins!");
                    chips -= betAmount;
                } else if ( game.playerWins() ) {
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
