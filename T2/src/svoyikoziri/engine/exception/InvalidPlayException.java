package svoyikoziri.engine.exception;

/**
 * A classe <code>InvalidPlayException</code> representa a exceção em tempo 
 * de execução que acontece quando um jogador lança uma exceção ao realizar 
 * sua jogada (por exemplo, ArrayIndexOutOfBoundsException ou 
 * NullPointerException).
 *
 * @author Luis H. P. Mendes
 */
@SuppressWarnings("serial")
public class InvalidPlayException extends RuntimeException {
    /**
     * Inicializa um objeto de <code>InvalidPlayException</code> recém-criado.
     *
     * @param isPlayer1 Flag que indica se a exceção foi provocada pelo 
     *                  Jogador1 ou pelo Jogador2.
     */
    public InvalidPlayException(boolean isPlayer1) {
        super("A jogada do " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " e invalida!");
    }
}
