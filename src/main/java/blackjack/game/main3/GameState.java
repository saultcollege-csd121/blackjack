package blackjack.game.main3;

public sealed interface GameState permits
        DealState,
        PlayerBlackJack,
        DealerBlackJack,
        PlayerTurn,
        PlayerBust,
        DealerTurn,
        PlayerWin,
        DealerWin,
        Push
{ }