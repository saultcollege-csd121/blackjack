package blackjack.game;

import java.util.List;

public class PointCounter {

    /**
     * @param hand A list of cards in a Blackjack hand
     * @return The point value of the hand
     */
    public static int count(List<Card> hand) {
        var points = 0;
        var softAces = 0;

        for (var card : hand) {
            if ( card.rank() == Rank.Ace ) {
                softAces += 1;
            }

            points += switch ( card.rank() ) {
                case Ace -> 11;
                case Two -> 2;
                case Three -> 3;
                case Four -> 4;
                case Five -> 5;
                case Six -> 6;
                case Seven -> 7;
                case Eight -> 8;
                case Nine -> 9;
                case Ten, Jack, Queen, King -> 10;
            };

            if ( points > 21 && softAces > 0 ) {
                points -= 10;
                softAces -= 1;
            }
        }
        return points;
    }
}
