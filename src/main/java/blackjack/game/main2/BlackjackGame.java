package blackjack.game.main2;

import blackjack.game.Card;
import blackjack.game.CardStack;
import blackjack.game.PointCounter;

import java.util.ArrayList;
import java.util.List;

public class BlackjackGame {

    private final CardStack deck;
    private final ArrayList<Card> playerHand;
    private final ArrayList<Card> dealerHand;

    public BlackjackGame() {
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

    public void playerHit() {
        playerHand.add(deck.takeOne());
    }

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
}
