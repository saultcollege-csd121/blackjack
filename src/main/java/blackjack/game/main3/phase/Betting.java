package blackjack.game.main3.phase;

public record Betting(int playerChips) implements GamePhase {

    public GamePhase placeBet(int betAmount) {
        if (betAmount > playerChips) {
            throw new IllegalArgumentException("Bet amount exceeds player chips");
        }
        return new Dealing(playerChips, betAmount);
    }
}
