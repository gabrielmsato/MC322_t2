package svoyikoziri.engine.exception;

import svoyikoziri.engine.PlayType;

/**
 * A classe <code>TakeAllCardsAsFirstPlayException</code> representa a exceção 
 * em tempo de execução que acontece quando um jogador faz uma jogada do tipo 
 * TAKEALLCARDS como a primera jogada de uma rodada.
 *
 * @author Luis H. P. Mendes
 */
@SuppressWarnings("serial")
public class TakeAllCardsAsFirstPlayException extends RuntimeException {
    /**
     * Inicializa um objeto de <code>TakeAllCardsAsFirstPlayException</code> 
     * recém-criado.
     *
     * @param isPlayer1 Flag que indica se a exceção foi provocada pelo 
     *                  Jogador1 ou pelo Jogador2.
     */
    public TakeAllCardsAsFirstPlayException(boolean isPlayer1) {
        super("A jogada do " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " e invalida! A primeira jogada da rodada nao pode ser do tipo " 
                + PlayType.TAKEALLCARDS + ".");
    }
}
