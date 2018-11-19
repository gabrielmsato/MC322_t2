package svoyikoziri.deck;

/**
 * A enumeração <code>Suit</code> representa os naipes das cartas de baralho.
 * 
 * @author Luis H. P. Mendes
 */
public enum Suit {
    /**
     * O naipe Copas.
     */
    HEARTS (Color.RED),
    /**
     * O naipe Ouros.
     */
    TILES (Color.RED),
    /**
     * O naipe Paus.
     */
    CLOVERS (Color.BLACK),
    /**
     * O naipe Espadas.
     */
    PIKES (Color.BLACK);

    /**
     * A cor do naipe.
     */
    private final Color color;

    /**
     * Inicializa um <code>Suit</code> recém-criado.
     *
     * @param color A cor do naipe.
     */
    private Suit (Color color) {
        this.color = color;
    }

    /**
     * Recupera a cor deste naipe.
     *
     * @return A cor deste naipe.
     */
    public Color getColor() {
        return this.color;
    }
}
