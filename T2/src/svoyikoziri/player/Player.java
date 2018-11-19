package svoyikoziri.player;

import svoyikoziri.deck.Suit;
import svoyikoziri.engine.Engine;
import svoyikoziri.engine.Play;

/**
 * A classe abstrata <code>Player</code> representa um jogador genérico do 
 * jogo de cartas Svoyi Koziri.
 *
 * @author Luis H. P. Mendes
 */
public abstract class Player {
    /**
     * O naipe trunfo do jogador.
     */
    protected final Suit trump;

    /**
     * Inicializa um objeto de <code>Player</code> recém-criado.
     *
     * @param trump O naipe trunfo do jogador.
     */
    public Player(Suit trump) {
        this.trump = trump;
    }

    /**
     * Recupera o naipe trunfo do jogador.
     *
     * @return O naipe trunfo do jogador.
     */
    public abstract Suit getTrump();

    /**
     * Indica se algum outro objeto é igual a este objeto.
     *
     * @param obj O objeto de referência com o qual comparar.
     *
     * @return <code>true</code> se este objeto é o mesmo que o parâmetro obj, 
     *         <code>false</code> caso contrário.
     */
    public abstract boolean equals(Object obj);

    /**
     * Retorna a jogada do jogador para a rodada atual.
     *
     * @param firstToPlay Um valor booleano que indica se o jogador iniciou a 
     *                    rodada atual.
     * @param engine      O motor do jogo.
     *
     * @return Uma jogada.
     */
    public abstract Play playRound(boolean firstToPlay, Engine engine);
}
