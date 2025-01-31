package blackjack.game.main3.phase;

public record Start(int playerChips) implements GamePhase {

    // From the Start phase, the player may choose to play a round, end the game,
    // or they may be out of chips and the game must end.
    // Having separate methods for 'play' and 'end' allows us to separate the
    // interaction with the user (getting console input) from the core game logic.
    // The main method can determine which of these methods to call base on the user's input.

    public GamePhase play() {
        return new Betting(playerChips);
    }

    public GamePhase endGame() {
        return new ExitGame();
    }
}
