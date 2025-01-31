package blackjack.game.main3;

import blackjack.game.Card;

import java.util.List;

public record PlayerBlackJack(List<Card> playerHand) implements GameState {
}
