package blackjack.game.main2;

import blackjack.game.Card;
import blackjack.game.CardStack;
import blackjack.game.PointCounter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a game of blackjack.
 */
public class BlackjackGame {

    private CardStack deck;
    private ArrayList<Card> playerHand;
    private ArrayList<Card> dealerHand;
    private int playerChips;
    private int betAmount;

    public BlackjackGame(int playerChips) {
        this.playerChips = playerChips;
    }

    /**
     * Starts a new round of blackjack. A new deck of cards is shuffled, and the
     * player and dealer are both dealt two cards.
     * IMPORTANT: This method should only be called at the beginning of a round.
     * @param betAmount The amount the player is betting for this round
     */
    public void newRound(int betAmount) {
        this.betAmount = betAmount;
        deck = CardStack.createShuffled52CardDeck();

        playerHand = new ArrayList<>(List.of(deck.takeOne()));
        dealerHand = new ArrayList<>(List.of(deck.takeOne()));
        playerHand.add(deck.takeOne());
        dealerHand.add(deck.takeOne());
    }

    public boolean playerHasBlackjack() {
        return PointCounter.count(playerHand) == 21;
    }

    public boolean dealerHasBlackjack() {
        return PointCounter.count(dealerHand) == 21;
    }

    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }
    public ArrayList<Card> getDealerHand() {
        return dealerHand;
    }
    public int getPlayerChips() {
        return playerChips;
    }

    /**
     * Player receives one more card.
     * IMPORTANT: This method should only be called during the player's turn.
     */
    public void playerHit() {
        playerHand.add(deck.takeOne());
    }

    /**
     * Plays the dealer's turn. The dealer will hit until their hand has a value of 17 or more.
     * IMPORTANT: This method should only be called after the player has finished their turn.
     */
    public void playDealerTurn() {
        while ( PointCounter.count(dealerHand) < 17 ) {
            dealerHand.add(deck.takeOne());
        }
    }

    public boolean playerIsBust() {
        return PointCounter.count(playerHand) > 21;
    }

    public boolean dealerIsBust() {
        return PointCounter.count(dealerHand) > 21;
    }

    public boolean dealerWins() {
        return PointCounter.count(dealerHand) > PointCounter.count(playerHand);
    }

    public boolean playerWins() {
        return PointCounter.count(playerHand) > PointCounter.count(dealerHand);
    }

    /**
     * Updates the player's chip count based on the current state of the game.
     * IMPORTANT: This method should only be called after the round is over.
     */
    public void settleChips() {
        if (playerHasBlackjack()) {
            playerChips += betAmount * 3 / 2;
        } else if (dealerHasBlackjack()) {
            playerChips -= betAmount;
        } else if (playerIsBust()) {
            playerChips -= betAmount;
        } else if (dealerIsBust()) {
            playerChips += betAmount;
        } else if (dealerWins()) {
            playerChips -= betAmount;
        } else if (playerWins()) {
            playerChips += betAmount;
        }
    }
}
