package svoyikoziri.engine.exception;

import svoyikoziri.deck.Card;

/**
 * A classe <code>PlayAWorseCardException</code> representa a exceção em tempo 
 * de execução que acontece quando um jogador joga uma carta pior do que a 
 * carta que está no topo da pilha de cartas da mesa.
 *
 * @author Luis H. P. Mendes
 */
@SuppressWarnings("serial")
public class PlayAWorseCardException extends RuntimeException {
    /**
     * Inicializa um objeto de <code>PlayAWorseCardException</code> 
     * recém-criado.
     *
     * @param isPlayer1        Flag que indica se a exceção foi provocada 
     *                         pelo Jogador1 ou pelo Jogador2.
     * @param playedCard       A carta que foi jogada.
     * @param cardOnTopOfStack A carta que está no topo da pilha de cartas 
     *                         da mesa.
     */
    public PlayAWorseCardException(boolean isPlayer1, Card playedCard, 
            Card cardOnTopOfStack) {
        super("A jogada do " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " e invalida! A carta jogada " + playedCard 
                + " nao e melhor que a carta " + cardOnTopOfStack 
                + " que esta no topo da pilha de cartas da mesa.");
    }

    /**
     * Inicializa um objeto de <code>PlayAWorseCardException</code> 
     * recém-criado.
     *
     * @param isPlayer1        Flag que indica se a exceção foi provocada 
     *                         pelo Jogador1 ou pelo Jogador2.
     */
    public PlayAWorseCardException(boolean isPlayer1) {
        this(isPlayer1, null, null);
    }
}
