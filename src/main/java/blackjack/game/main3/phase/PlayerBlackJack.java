package blackjack.game.main3.phase;

import blackjack.game.main3.GameData;

public record PlayerBlackJack(GameData game) implements GamePhase {
    public GamePhase nextRound() {
        return new Start(game.playerChips() + game.betAmount() * 3 / 2);
    }
}
