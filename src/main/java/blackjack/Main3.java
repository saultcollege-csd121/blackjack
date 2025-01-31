package blackjack;

import blackjack.game.main3.*;
import blackjack.ui.Console;

import static blackjack.ui.Console.*;

/****************************************************************
 * ABOUT MAIN3
 *
 * In this version of the game, we model ALL the game logic (both
 * the game state and the game rules) in a set of related records.
 * (See the game.main3 package for the records.)
 * Each record represents a different state of the game, and
 * stores only the data necessary to manage that state.
 * Some records represent states where the player has won or lost.
 * Others represent states where the game is still in progress, and
 * provide methods for transitioning to the next state.
 *
 * Modelling things in this way allows our main method to be a very
 * simple loop that simply transitions between game states.
 */

public class Main3 {

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

            // Ask the user if they want to play a round
            var response = promptForOption("Would you like to play a round? ", "y", "n");

            if ( response.equals("y") ) {

                var bet = promptForInt("How much do you want to bet? ", 1, chips);

                GameState state = new DealState();

                roundLoop: while (true) {

                    switch (state) {

                        case DealState s -> state = s.deal();

                        case PlayerBlackJack s -> {
                            Console.showHand("Your hand", s.playerHand());
                            Console.println("Blackjack! You win!");
                            chips += bet * 3 / 2;
                            break roundLoop;
                        }

                        case DealerBlackJack s -> {
                            Console.showHand("Your hand", s.playerHand());
                            Console.showHand("Dealer's hand", s.dealerHand());
                            Console.println("Dealer has Blackjack! You lose!");
                            chips -= bet;
                            break roundLoop;
                        }

                        case PlayerTurn s -> {
                            Console.showHand("Dealer's hand", s.dealerHand());
                            Console.showHand("Your hand", s.playerHand(), true);
                            response = Console.promptForOption("Hit or stand?", "h", "s");
                            if (response.equalsIgnoreCase("h")) {
                                state = s.hit();
                            } else if (response.equalsIgnoreCase("s")) {
                                state = s.stand();
                            }
                        }

                        case PlayerBust s -> {
                            Console.showHand("Dealer's hand", s.dealerHand(), false, true);
                            Console.showHand("Your hand", s.playerHand(), true);
                            Console.println("Bust! You loose!");
                            chips -= bet;
                            break roundLoop;
                        }

                        case DealerTurn nextRound -> state = nextRound.play();

                        case PlayerWin s -> {
                            Console.showHand("Dealer's hand", s.dealerHand(), true);
                            Console.showHand("Your hand", s.playerHand(), true);
                            Console.println("You win!");
                            chips += bet;
                            break roundLoop;
                        }

                        case DealerWin s -> {
                            Console.showHand("Dealer's hand", s.dealerHand(), true);
                            Console.showHand("Your hand", s.playerHand(), true);
                            Console.println("You loose!");
                            chips -= bet;
                            break roundLoop;
                        }

                        case Push s -> {
                            Console.showHand("Dealer's hand", s.dealerHand(), true);
                            Console.showHand("Your hand", s.playerHand(), true);
                            Console.println("Push!");
                            break roundLoop;
                        }
                    }
                }

            } else {
                println("Goodbye!");
                System.exit(0);
            }
        }

    }
}
