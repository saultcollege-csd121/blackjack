package blackjack;

import blackjack.game.main2.BlackjackGame;

import static blackjack.ui.Console.*;

/****************************************************************
 * ABOUT MAIN2
 * As an improvement on Main1, here we have extracted much of
 * the game logic into a separate class, Game.
 * We create a game object which stores the current state of the
 * game and provides methods to interact with and update the game.
 * We use those methods here in the main method to play the game.
 * There is still a bit of room for improvement, though:
 * Our main method still needs to 'know' how a Blackjack game
 * progresses through its phases. (This is modelled by the sequence
 * of conditionals and loops in the main method that call the
 * appropriate methods on the game object; if we are not careful
 * about the order of these calls, we could end up in an invalid
 * game state, e.g. settling the chips before the dealer has played.)
 * We will tackle this final improvement in Main3 by modelling the
 * flow of the game as a 'state machine' using a set of classes that
 * represent the different states of the game.
 */

public class Main2 {

    public static void main(String[] args) {

        println("Welcome to Blackjack!");

        var game = new BlackjackGame(100);

        println("""
                Your goal is to get closest to 21 without going over.
                Face cards are worth 10; number cards are worth their card value.
                Aces can be worth 1 or 11.
                The minimum bet is 1 chip.""");

        gameLoop: while ( true ) {

            showChipCount(game.getPlayerChips());

            if ( game.getPlayerChips() == 0) {
                println("You're out of chips! Goodbye!");
                System.exit(0);
            }

            // Ask the user if they want to play a round
            var response = promptForOption("Would you like to play a round? ", "y", "n");

            if ( response.equals("y") ) {

                var betAmount = promptForInt("How much do you want to bet? ", 1, game.getPlayerChips());

                game.newRound(betAmount);

                if ( game.playerHasBlackjack() ) {
                    showHand("Your hand", game.getPlayerHand());
                    println("Blackjack! You win!");
                    game.settleChips();
                    continue;
                }

                if ( game.dealerHasBlackjack() ) {
                    showHand("Your hand", game.getPlayerHand());
                    showHand("Dealer's hand", game.getDealerHand());
                    println("Dealer has Blackjack! You lose!");
                    game.settleChips();
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
                            game.settleChips();
                            continue gameLoop;
                        } else if ( game.playerHasBlackjack() ) {
                            showHand("Your hand", game.getPlayerHand());
                            println("21! You win!");
                            game.settleChips();
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
                } else if ( game.dealerWins() ) {
                    println("Dealer wins!");
                } else if ( game.playerWins() ) {
                    println("You win!");
                } else {
                    println("Push!");
                }

                game.settleChips();

            } else {
                println("Goodbye!");
                System.exit(0);
            }
        }

    }
}
