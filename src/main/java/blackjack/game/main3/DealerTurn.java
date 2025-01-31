package blackjack.game.main3;

import blackjack.game.Card;
import blackjack.game.CardStack;
import blackjack.game.PointCounter;

import java.util.List;

public record DealerTurn(CardStack deck, List<Card> playerHand, List<Card> dealerHand) implements GameState {

    public GameState play() {

        while (PointCounter.count(dealerHand) < 17) {
            dealerHand.add(deck.takeOne());
        }

        var dealerPoints = PointCounter.count(dealerHand);
        var playerPoints = PointCounter.count(playerHand);

        if (dealerPoints > 21 || dealerPoints < playerPoints) {
            return new PlayerWin(playerHand, dealerHand);
        } else if (playerPoints < dealerPoints) {
            return new DealerWin(playerHand, dealerHand);
        } else {
            return new Push(playerHand, dealerHand);
        }
    }
}
