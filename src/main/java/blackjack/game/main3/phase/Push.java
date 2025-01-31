package blackjack.game.main3.phase;

import blackjack.game.main3.GameData;

public record Push(GameData game) implements GamePhase {
    public GamePhase nextRound() {
        return new Start(game.playerChips());
    }
}
