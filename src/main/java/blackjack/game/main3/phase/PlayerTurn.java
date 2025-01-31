package blackjack.game.main3.phase;

import blackjack.game.PointCounter;
import blackjack.game.main3.GameData;

public record PlayerTurn(GameData game) implements GamePhase {

    // Here, as in the Start phase, we need to accommodate a range of possible user interactions.
    // The two methods here allow the main method to get the user input and decide what to do next.

    public GamePhase hit() {
        game.playerHand().add(game.deck().takeOne());

        if (PointCounter.count(game.playerHand()) > 21) {
            return new PlayerBust(game);
        }

        return new PlayerTurn(game);
    }

    public DealerTurn stand() {
        return new DealerTurn(game);
    }
}
