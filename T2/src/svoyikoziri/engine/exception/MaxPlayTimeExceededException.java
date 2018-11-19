package svoyikoziri.engine.exception;

/**
 * A classe <code>MaxPlayTimeExceededException</code> representa a exceção em 
 * tempo de execução que acontece quando um jogador excede o tempo máximo para 
 * realizar uma jogada.
 *
 * @author Luis H. P. Mendes
 */
@SuppressWarnings("serial")
public class MaxPlayTimeExceededException extends RuntimeException {
    /**
     * Inicializa um objeto de <code>MaxPlayTimeExceededException</code> 
     * recém-criado.
     *
     * @param isPlayer1 Flag que indica se a exceção foi provocada pelo 
     *                  Jogador1 ou pelo Jogador2.
     */
    public MaxPlayTimeExceededException(boolean isPlayer1) {
        super("O " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " demorou mais do que o permitido para fazer sua jogada.");
    }
}
