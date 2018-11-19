package svoyikoziri.engine.exception;

import svoyikoziri.deck.Card;

/**
 * A classe <code>PlayACardNotInHandException</code> representa a exceção em 
 * tempo de execução que acontece quando um jogador joga uma carta que 
 * não está em sua mão.
 *
 * @author Luis H. P. Mendes
 */
@SuppressWarnings("serial")
public class PlayACardNotInHandException extends RuntimeException {
    /**
     * Inicializa um objeto de <code>PlayACardNotInHandException</code> 
     * recém-criado.
     *
     * @param isPlayer1 Flag que indica se a exceção foi provocada pelo 
     *                  Jogador1 ou pelo Jogador2.
     * @param card      A carta que foi jogada.
     */
    public PlayACardNotInHandException(boolean isPlayer1, Card card) {
        super("A jogada do " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " e invalida! A carta " + card + " nao estava na mao dele.");
    }

    /**
     * Inicializa um objeto de <code>PlayACardNotInHandException</code> 
     * recém-criado.
     * 
     * @param isPlayer1 Flag que indica se a exceção foi provocada pelo 
     *                  Jogador1 ou pelo Jogador2.
     */
    public PlayACardNotInHandException(boolean isPlayer1) {
        this(isPlayer1, null);
    }
}
