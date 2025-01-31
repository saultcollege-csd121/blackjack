package blackjack.game.main3.phase;

import blackjack.game.PointCounter;
import blackjack.game.main3.GameData;

public record Dealing(int playerChips, int betAmount) implements GamePhase {

    /**
     * Deal two cards to the player and two cards to the dealer.
     * @return The next phase of the game, depending on the point value of the dealt cards.
     */
    public GamePhase deal() {

        var newRound = GameData.newRound(playerChips, betAmount);

        if (PointCounter.count(newRound.playerHand()) == 21) {
            return new PlayerBlackJack(newRound);
        }

        if (PointCounter.count(newRound.dealerHand()) == 21) {
            return new DealerWin(newRound);
        }

        return new PlayerTurn(newRound);
    }
}
