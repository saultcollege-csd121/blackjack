package blackjack.game.main3;

import blackjack.game.Card;

import java.util.List;

public record PlayerBust(List<Card> playerHand, List<Card> dealerHand) implements GameState {
}
