package blackjack.game;

public record Card(Rank rank, Suit suit) {

    /**
     * @return A string representation of the card, e.g. "A♥" for the Ace of Hearts
     */
    @Override
    public String toString() {
        var suit = switch ( this.suit ) {
            case Hearts -> "♥";
            case Diamonds -> "♦";
            case Clubs -> "♣";
            case Spades -> "♠";
        };
        var rank = switch ( this.rank ) {
            case Ace -> "A";
            case Two -> "2";
            case Three -> "3";
            case Four -> "4";
            case Five -> "5";
            case Six -> "6";
            case Seven -> "7";
            case Eight -> "8";
            case Nine -> "9";
            case Ten -> "10";
            case Jack -> "J";
            case Queen -> "Q";
            case King -> "K";
        };
        return rank + suit;
    }
}
