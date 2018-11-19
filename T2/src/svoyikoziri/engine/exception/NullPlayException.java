package svoyikoziri.engine.exception;

/**
 * A classe <code>NullPlayException</code> representa a exceção em tempo de 
 * execução que acontece quando um jogador faz uma jogada nula.
 *
 * @author Luis H. P. Mendes
 */
@SuppressWarnings("serial")
public class NullPlayException extends RuntimeException {
    /**
     * Inicializa um objeto de <code>NullPlayException</code> recém-criado.
     *
     * @param isPlayer1 Flag que indica se a exceção foi provocada pelo 
     *                  Jogador1 ou pelo Jogador2.
     */
    public NullPlayException(boolean isPlayer1) {
        super("A jogada do " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " e invalida! A jogada e nula.");
    }
}
