package svoyikoziri.engine.exception;

import svoyikoziri.deck.Suit;

/**
 * A classe <code>SameTrumpColorsException</code> representa a exceção em 
 * tempo de execução que acontece quando tenta-se inicializar uma partida 
 * com dois jogadores que têm o naipe trunfo da mesma cor. 
 *
 * @author Luis H. P. Mendes
 */
@SuppressWarnings("serial")
public class SameTrumpColorsException extends RuntimeException {
    /**
     * Inicializa um objeto de <code>SameTrumpColorsException</code> 
     * recém-criado.
     * @param trump1 O naipe trunfo do Jogador1.
     * @param trump2 O naipe trunfo do Jogador2.
     */
    public SameTrumpColorsException(Suit trump1, Suit trump2) {
        super("Os naipes trunfos dos jogadores sao invalidos! Os naipes " 
                + trump1 + " e " + trump2 + " tem a mesma cor.");
    }
}
