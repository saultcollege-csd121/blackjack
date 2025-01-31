package blackjack.game.main3.phase;

public sealed interface GamePhase permits
        Start,
        Betting,
        Dealing,
        PlayerBlackJack,
        DealerBlackJack,
        PlayerTurn,
        DealerTurn,
        PlayerWin,
        DealerWin,
        Push,
        PlayerBust,
        ExitGame
{ }