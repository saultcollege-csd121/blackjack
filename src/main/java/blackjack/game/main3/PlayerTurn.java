package blackjack.game.main3;

import blackjack.game.Card;
import blackjack.game.CardStack;
import blackjack.game.PointCounter;

import java.util.List;

public record PlayerTurn(CardStack deck, List<Card> playerHand, List<Card> dealerHand) implements GameState {

    public GameState hit() {
        playerHand.add(deck.takeOne());

        if (PointCounter.count(playerHand) > 21) {
            return new PlayerBust(playerHand, dealerHand);
        }

        return new PlayerTurn(deck, playerHand, dealerHand);
    }

    public DealerTurn stand() {
        return new DealerTurn(deck, playerHand, dealerHand);
    }
}
