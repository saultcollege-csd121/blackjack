package blackjack.game;

/**
 * Represents a stack of playing cards.
 */
public class CardStack {

    /**
     * The cards in the stack.
     */
    private final Card[] cards;

    /**
     * The index of the top card in the stack (starting at 0 and increasing as cards are taken).
     */
    private int topCardIndex = 0;

    /**
     * Creates a new card stack with the given cards.
     *
     * @param cards the cards in the stack
     */
    private CardStack(Card[] cards) {
        this.cards = cards;
    }

    /**
     * @return a new shuffled 52-card deck
     */
    public static CardStack createShuffled52CardDeck() {
        var deckCards = new Card[52];
        int index = 0;
        for (var suit : Suit.values()) {
            for (var rank : Rank.values()) {
                deckCards[index] = new Card(rank, suit);
                index += 1;
            }
        }
        var deck = new CardStack(deckCards);
        deck.shuffle();
        return deck;
    }

    /**
     * Shuffles the cards in the stack.
     */
    private void shuffle() {
        for (int i = 0; i < cards.length; i++) {
            var randomIndex = (int) (Math.random() * cards.length);
            var temp = cards[i];
            cards[i] = cards[randomIndex];
            cards[randomIndex] = temp;
        }
    }

    /**
     * @return The top card from the stack. (The next call to this method will return the next card.)
     */
    public Card takeOne() {
        return take(1)[0];
    }

    /**
     * Takes n cards from the stack. (The next call to this method will return the next n cards.)
     * @param n The number of cards to take
     * @return The n cards taken from the stack
     */
    public Card[] take(int n) {
        if ( topCardIndex + n > cards.length ) {
            throw new IllegalStateException("Not enough cards left in the deck");
        }

        var newCards = new Card[n];
        for (int i = 0; i < n; i += 1 ) {
            newCards[i] = cards[topCardIndex];
            topCardIndex += 1;
        }
        return newCards;
    }

}
