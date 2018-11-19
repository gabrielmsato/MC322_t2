package svoyikoziri.engine.exception;

import svoyikoziri.engine.PlayType;

/**
 * A classe <code>PlayANullCardException</code> representa a exceção em tempo 
 * de execução que acontece quando um jogador joga uma carta nula.
 *
 * @author Luis H. P. Mendes
 */
@SuppressWarnings("serial")
public class PlayANullCardException extends RuntimeException {
    /**
     * Inicializa um objeto de <code>PlayANullCardException</code> 
     * recém-criado.
     *
     * @param isPlayer1 Flag que indica se a exceção foi provocada pelo 
     *                  Jogador1 ou pelo Jogador2.
     */
    public PlayANullCardException(boolean isPlayer1) {
        super("A jogada do " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " e invalida! Uma jogada do tipo " + PlayType.PLAYACARD 
                + " deve conter uma carta.");
    }
}
