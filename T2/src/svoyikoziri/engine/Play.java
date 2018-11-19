package svoyikoziri.engine;

import svoyikoziri.deck.Card;

/**
 * A classe <code>Play</code> representa uma jogada do jogo de cartas 
 * Svoyi Koziri.
 *
 * @author Luis H. P. Mendes
 */
public class Play {
    /**
     * O tipo da jogada.
     */
    private final PlayType type;

    /**
     * A carta da jogada.
     */
    private final Card card;

    /**
     * Inicializa um objeto de <code>Play</code> recem-criado.
     *
     * @param type O tipo da jogada.
     * @param card A carta da jogada.
     */
    public Play(PlayType type, Card card) {
        this.type = type;
        this.card = card;
    }

    /**
     * Inicializa um objeto de <code>Play</code> recem-criado.
     *
     * @param type O tipo da jogada.
     */
    public Play(PlayType type) {
        this.type = type;
        this.card = null;
    }

    /**
     * Recupera o tipo da jogada.
     *
     * @return O tipo da jogada.
     */
    public PlayType getType() {
        return this.type;
    }

    /**
     * Recupera a carta da jogada.
     *
     * @return A carta da jogada.
     */
    public Card getCard() {
        return this.card;
    }

    /**
     * Retorna uma representação em String desta jogada.
     *
     * @return Uma representação em String desta jogada.
     */
    @Override
    public String toString() {
        if (PlayType.PLAYACARD.equals(this.type)) {
            return "(" + this.type + ", " + this.card + ")";
        }
        return "(" + this.type + ")";
    }
}
