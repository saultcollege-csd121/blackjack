package blackjack.game.main3.phase;

import blackjack.game.PointCounter;
import blackjack.game.main3.GameData;

public record DealerTurn(GameData game) implements GamePhase {

    public GamePhase play() {

        while (PointCounter.count(game.dealerHand()) < 17) {
            game.dealerHand().add(game.deck().takeOne());
        }

        var dealerPoints = PointCounter.count(game.dealerHand());
        var playerPoints = PointCounter.count(game.playerHand());

        if (dealerPoints > 21 || dealerPoints < playerPoints) {
            return new PlayerWin(game);
        } else if (playerPoints < dealerPoints) {
            return new DealerWin(game);
        } else {
            return new Push(game);
        }
    }
}
