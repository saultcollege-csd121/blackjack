package blackjack;

import blackjack.game.main3.phase.*;

import static blackjack.ui.Console.*;

/****************************************************************
 * ABOUT MAIN3
 * In this version of the game, we model ALL the game logic (both
 * the game rules and the progression of game phases) in a set of
 * related records. (See the game.main3.phase package.)
 * Note that we still model the data for the game as we did in
 * Main2, but this time using a simple record, (game.main3.GameData);
 * most of the logic is now implemented in the phase records.
 * Each record represents a different phase of the game, and exposes
 * methods for transitioning to the next phase from that phase.
 * Some records represent phases where the player has won or lost,
 * and the chip count must be updated (PlayerBlackjack, PlayerLose,
 * PlayerWin, Push).
 * Others represent states where the game is still in progress, and
 * provide methods for transitioning to the next state (Start, Betting,
 * Dealing, PlayerTurn, DealerTurn).
 * Modelling things in this way allows our main method to be a very
 * simple loop that simply calls one of the available methods on the
 * current phase object, possibly depending on the user's input.
 * The logic of which phase to transition to is encapsulated in the
 * record methods themselves. In this way, there is very little that
 * can go wrong in the main method, and it is very easy to follow.
 * This implementation is more complex than Main2 in terms of the
 * number of classes and files, but it is much simpler in terms of
 * the logic in the main method, and is less error-prone because all
 * the game logic is encapsulated in the records, with each record,
 * encapsulating only the logic for its own phase of the game.
 */

public class Main3 {

    public static void main(String[] args) {

        println("Welcome to Blackjack!");

        println("""
                Your goal is to get closest to 21 without going over.
                Face cards are worth 10; number cards are worth their card value.
                Aces can be worth 1 or 11.
                The minimum bet is 1 chip.""");

        GamePhase phase = new Start(100);

        while ( true ) {

            switch (phase) {

                case Start _phase -> {
                    showChipCount(_phase.playerChips());

                    if ( _phase.playerChips() == 0 ) {
                        println("You're out of chips!");
                        phase = _phase.endGame();
                    } else {

                        var response = promptForOption("Would you like to play a round? ", "y", "n");

                        if (response.equals("y")) {
                            phase = _phase.play();
                        } else {
                            phase = _phase.endGame();
                        }
                    }
                }

                case ExitGame _phase -> {
                    println("Thanks for playing!");
                    return;
                }

                case Betting _phase -> {
                    var bet = promptForInt("How much do you want to bet? ", 1, _phase.playerChips());
                    phase = _phase.placeBet(bet);
                }

                case Dealing _phase -> phase = _phase.deal();

                case PlayerBlackJack _phase -> {
                    showHand("Your hand", _phase.game().playerHand());
                    println("Blackjack! You win!");
                    phase = _phase.nextRound();
                }

                case DealerBlackJack _phase -> {
                    showHand("Your hand", _phase.game().playerHand());
                    showHand("Dealer's hand", _phase.game().dealerHand());
                    println("Dealer has Blackjack! You lose!");
                    phase = _phase.nextRound();
                }

                case PlayerTurn _phase -> {
                    showHand("Dealer's hand", _phase.game().dealerHand(), false, true);
                    showHand("Your hand", _phase.game().playerHand(), true);
                    switch (promptForOption("Hit or stand?", "h", "s")) {
                        case "h" -> phase = _phase.hit();
                        case "s" -> phase = _phase.stand();
                    }
                }

                case PlayerBust _phase -> {
                    showHand("Dealer's hand", _phase.game().dealerHand(), false, true);
                    showHand("Your hand", _phase.game().playerHand(), true);
                    println("Bust! You lose!");
                    phase = _phase.nextRound();
                }

                case DealerTurn _phase -> phase = _phase.play();

                case PlayerWin _phase -> {
                    showHand("Dealer's hand", _phase.game().dealerHand(), true);
                    showHand("Your hand", _phase.game().playerHand(), true);
                    println("You win!");
                    phase = _phase.nextRound();
                }

                case DealerWin _phase -> {
                    showHand("Dealer's hand", _phase.game().dealerHand(), true);
                    showHand("Your hand", _phase.game().playerHand(), true);
                    println("You lose!");
                    phase = _phase.nextRound();
                }

                case Push _phase -> {
                    showHand("Dealer's hand", _phase.game().dealerHand(), true);
                    showHand("Your hand", _phase.game().playerHand(), true);
                    println("Push!");
                    phase = _phase.nextRound();
                }

            }
        }

    }
}
