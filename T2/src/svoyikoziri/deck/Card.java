package svoyikoziri.deck;

/**
 * A classe <code>Card</code> representa uma carta de baralho.
 *
 * @author Luis H. P. Mendes
 */
public class Card implements Comparable<Card> {
    /**
     * O naipe da carta.
     */
    private final Suit suit;

    /**
     * O rank da carta.
     */
    private final Rank rank;

    /**
     * Inicializa um objeto de <code>Card</code> recém-criado.
     *
     * @param suit O naipe da carta.
     * @param rank O rank da carta.
     */
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Recupera o naipe desta carta.
     *
     * @return O naipe desta carta.
     */
    public Suit getSuit() {
        return this.suit;
    }

    /**
     * Recupera o rank desta carta.
     *
     * @return O rank desta carta.
     */
    public Rank getRank() {
        return this.rank;
    }

    /**
     * Compara a ordem entre esta carta com a carta passada por parâmetro.
     * Retorna um inteiro negativo, zero, ou um inteiro positivo se esta carta 
     * for, respectivamente, menor que, igual à, ou mais que a carta passada 
     * por parâmetro.
     *
     * @param card A carta que será comparada com esta carta.
     *
     * @return Um inteiro negativo, zero, ou um inteiro positivo se esta carta 
     * for, respectivamente, menor que, igual à, ou mais que a carta passada 
     * por parâmetro.
     */
    @Override
    public int compareTo(Card card) {
        if (this.suit.compareTo(card.suit) == 0) {
            return this.rank.compareTo(card.rank);
        }

        return this.suit.compareTo(card.suit);
    }

    /**
     * Indica se algum outro objeto é igual a este objeto.
     *
     * @param obj O objeto de referência com o qual comparar.
     *
     * @return <code>true</code> se este objeto é o mesmo que o parâmetro obj, 
     *         <code>false</code> caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Card) {
            Card card = (Card) obj;

            return (((this.suit == null && card.suit == null) 
                    || this.suit.equals(card.suit)) 
                    && ((this.rank == null && card.rank == null) 
                            || this.rank.equals(card.rank)));
        }

        return false;
    }

    /**
     * Retorna uma representação em String desta carta.
     *
     * @return Uma representação em String desta carta.
     */
    @Override
    public String toString() {
        return "(" + this.rank + ", " + this.suit + ")";
    }
}
