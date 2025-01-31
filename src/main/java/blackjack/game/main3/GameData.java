package blackjack.game.main3;

import blackjack.game.Card;
import blackjack.game.CardStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the current state of a game of blackjack.
 */
public record GameData(
        CardStack deck,
        ArrayList<Card> playerHand,
        ArrayList<Card> dealerHand,
        int playerChips,
        int betAmount
) {
    /**
     * Creates a new GameData object representing the start of a new round of Blackjack.
     * A new deck of cards is shuffled, and the player and dealer are both dealt 2 cards.
     * @param playerChips The number of chips the player has.
     * @param betAmount The amount the player is betting on this round.
     * @return A new GameData object representing the start of a new round.
     */
    public static GameData newRound(int playerChips, int betAmount) {
        var deck = CardStack.createShuffled52CardDeck();
        var playerHand = new ArrayList<>(List.of(deck.take(2)));
        var dealerHand = new ArrayList<>(List.of(deck.take(2)));
        return new GameData(deck, playerHand, dealerHand, playerChips, betAmount);
    }
}
