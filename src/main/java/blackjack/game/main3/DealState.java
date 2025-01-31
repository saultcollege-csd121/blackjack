package blackjack.game.main3;

import blackjack.game.CardStack;
import blackjack.game.PointCounter;

import java.util.ArrayList;
import java.util.List;

public record DealState() implements GameState {

    public GameState deal() {
        var deck = CardStack.createShuffled52CardDeck();

        var playerHand = new ArrayList<>(List.of(deck.take(2)));

        if (PointCounter.count(playerHand) == 21) {
            return new PlayerBlackJack(playerHand);
        }

        var dealerHand = new ArrayList<>(List.of(deck.take(2)));

        if (PointCounter.count(dealerHand) == 21) {
            return new DealerBlackJack(playerHand, dealerHand);
        }

        return new PlayerTurn(deck, playerHand, dealerHand);
    }
}
