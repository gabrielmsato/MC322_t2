package svoyikoziri.engine;

import java.util.List;
import java.util.Stack;

import svoyikoziri.deck.Card;
import svoyikoziri.deck.Suit;
import svoyikoziri.engine.exception.InvalidPlayException;
import svoyikoziri.engine.exception.MaxPlayTimeExceededException;
import svoyikoziri.engine.exception.NullPlayException;
import svoyikoziri.engine.exception.PlayACardNotInHandException;
import svoyikoziri.engine.exception.PlayANullCardException;
import svoyikoziri.engine.exception.PlayAWorseCardException;
import svoyikoziri.engine.exception.TakeAllCardsAsFirstPlayException;
import svoyikoziri.player.Player;

/**
 * A classe abstrata <code>Engine</code> representa um motor genérico do 
 * jogo de cartas Svoyi Koziri.
 *
 * @author Luis H. P. Mendes
 */
public abstract class Engine {
    /**
     * O número máximo padrão de jogadas do jogo.
     */
    public static final int DEFAULT_MAX_ROUNDS = 2707;

    /**
     * O tempo máximo padrão, em nanosegundos, que cada jogador tem para 
     * realizar sua jogada em cada rodada.
     */
    public static final long DEFAULT_MAX_PLAY_TIME = 100000000; // 100ms

    /**
     * Uma mensagem que informa que a pilha de carta da mesa está vazia.
     */
    protected static final String EMPTY_CARDS_ON_TABLE_MESSAGE = 
            "A pilha de cartas da mesa esta vazia.";

    /**
     * Uma mensagem que informa que os jogadores empataram.
     */
    protected static final String PLAYERS_DREW_MESSAGE = 
            "Os jogadores empataram!";

    /**
     * Retorna uma mensagem que informa uma jogada válida realizada por 
     * um jogador.
     *
     * @param isPlayer1 Flag que indica se a jogada foi realizada pelo 
     *                  Jogador1 ou pelo Jogador2.
     * @param play      A jogada realizada.
     *
     * @return Uma mensagem que informa uma jogada válida realizada por 
     *         um jogador.
     */
    protected static String getValidPlayMessage(boolean isPlayer1, Play play) {
        if (PlayType.PLAYACARD.equals(play.getType())) {
            return "O " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                    + " joga a carta " + play.getCard() + ".";
        } else {
            return "O " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                    + " pega todas as cartas da mesa.";
        }
    }

    /**
     * Retorna uma mensagem que informa o naipe trunfo de um jogador.
     *
     * @param isPlayer1 Flag que indica se o jogador em questão é o Jogador1 
     *                  ou o Jogador2.
     * @param trump     Naipe trunfo do jogador.
     *
     * @return Uma mensagem que informa o naipe trunfo de um jogador
     */
    protected static String getPlayerTrumpMessage(boolean isPlayer1, 
            Suit trump) {
        return "Naipe trunfo do " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + ": " + trump + ".";
    }

    /**
     * Retorna uma mensagem que informa o número da rodada atual 
     * e o número máximo de rodadas da partida.
     *
     * @param currentRound O número da rodada atual da partida.
     * @param maxRounds    O número máximo de rodadas da partida.
     *
     * @return Uma mensagem que informa o número da rodada atual 
     * e o número máximo de rodadas da partida.
     */
    protected static String getRoundNumberMessage(int currentRound, 
            int maxRounds) {
        return "Rodada " + currentRound + " de " + maxRounds + ".";
    }

    /**
     * Retorna uma mensagem que informa o número de cartas na mão de 
     * um jogador.
     *
     * @param isPlayer1 Flag que indica se o jogador em questão é o Jogador1 
     *                  ou o Jogador2.
     * @param handSize  O número de cartas na mão do jogador.
     *
     * @return Uma mensagem que informa o número de cartas na mão de 
     *         um jogador.
     */
    protected static String getNumberOfCardsOnPlayersHandMessage(
            boolean isPlayer1, int handSize) {
        return "O " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " tem a(s) seguinte(s) " + handSize + " carta(s) na mao:";
    }

    /**
     * Retorna uma mensagem que informa o número de cartas na pilha de cartas 
     * da mesa.
     *
     * @param cardsOnTableSize O número de cartas na pilha de cartas da mesa.
     *
     * @return Uma mensagem que informa o número de cartas na pilha de cartas 
     *         da mesa.
     */
    protected static String getNumberOfCardsOnCardsOnTableMessage(
            int cardsOnTableSize) {
        if (cardsOnTableSize > 0) {
            return "A pilha de cartas da mesa contem a(s) seguinte(s) " 
                    + cardsOnTableSize + " carta(s):";
        } else {
            return Engine.EMPTY_CARDS_ON_TABLE_MESSAGE;
        }
    }
    
    /**
     * Retorna uma mensagem que informa o jogador que começa a rodada.
     *
     * @param isPlayer1 Flag que indica se o jogador em questão é o Jogador1 
     *                  ou o Jogador2.
     *
     * @return Uma mensagem que informa o jogador que começa a rodada.
     */
    protected static String getPlayerStartsRoundMessage(boolean isPlayer1) {
        return "O " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " comeca a rodada.";
    }

    /**
     * Retorna uma mensagem que informa o jogador que ganhou a rodada.
     *
     * @param isPlayer1 Flag que indica se a rodada foi ganha pelo Jogador1 
     *                  ou pelo Jogador2.
     *
     * @return Uma mensagem que informa o jogador que ganhou a rodada.
     */
    protected static String getPlayerWinsRoundMessage(boolean isPlayer1) {
        return "O " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " ganha a rodada.";
    }

    /**
     * Retorna uma mensagem que informa o jogador vencedor da partida.
     *
     * @param isPlayer1 Flag que indica se o vencedor da partida foi o 
     *                  Jogador1 ou o Jogador2.
     *
     * @return Uma mensagem que informa o jogador vencedor da partida.
     */
    protected static String getWinnerPlayerMessage(boolean isPlayer1) {
        return "O " + (isPlayer1 ? "Jogador1" : "Jogador2") 
                + " venceu a partida!";
    }

    /**
     * Recupera o naipe trunfo do Jogador1.
     *
     * @return O naipe trunfo do Jogador1.
     */
    public abstract Suit getPlayer1Trump();

    /**
     * Recupera o naipe trunfo do Jogador2.
     *
     * @return O naipe trunfo do Jogador2.
     */
    public abstract Suit getPlayer2Trump();

    /**
     * Recupera uma cópia imutável das cartas da mão de um jogador.
     *
     * @param player O jogador em questão.
     *
     * @return Uma cópia imutável das cartas da mão de um jogador.
     */
    public abstract List<Card> getUnmodifiableHandOfPlayer(Player player);

    /**
     * Recupera uma cópia da pilha de cartas da mesa.
     *
     * @return Uma cópia da pilha de cartas da mesa.
     */
    public abstract Stack<Card> getCardsOnTable();

    /**
     * Recupera o número máximo de rodadas da partida.
     *
     * @return O número máximo de rodadas da partida.
     */
    public abstract int getMaxRounds();

    /**
     * Recupera o número da rodada atual da partida.
     *
     * @return O número da rodada atual da partida.
     */
    public abstract int getCurrentRound();

    /**
     * Recupera uma cópia imutável da lista de jogadas.
     *
     * @return Uma cópia imutável da lista de jogadas.
     */
    public abstract List<Play> getUnmodifiablePlays();

    /**
     * Joga uma partida do jogo de cartas Svoyi Koziri.
     * Retorna o jogador vencedor, ou null em caso de empate.
     *
     * @return O jogador vencedor, ou null em caso de empate.
     *
     * @throws NullPlayException                Lança uma <code>NullPlayException</code> caso um 
     *                                          jogador realize uma jogada nula.
     * @throws PlayANullCardException           Lança uma <code>PlayANullCardException</code> 
     *                                          caso um jogador jogue uma carta nula.
     * @throws PlayACardNotInHandException      Lança uma <code>PlayACardNotInHandException</code> 
     *                                          caso um jogador jogue uma carta que não esteja em 
     *                                          sua mão.
     * @throws TakeAllCardsAsFirstPlayException Lança uma <code>TakeAllCardsAsFirstPlayException</code> 
     *                                          caso um jogador faça uma jogada do tipo 
     *                                          TAKEALLCARDS como a primera jogada de uma rodada. 
     * @throws PlayAWorseCardException          Lança uma <code>PlayAWorseCardException</code> 
     *                                          caso um jogador jogue uma carta pior do que a 
     *                                          carta que está no topo da pilha de cartas da mesa.
     * @throws MaxPlayTimeExceededException     Lança uma <code>MaxPlayTimeExceededException</code> 
     *                                          caso um jogador exceda o tempo máximo para 
     *                                          realizar uma jogada.
     * @throws InvalidPlayException             Lança uma <code>InvalidPlayException</code> caso 
     *                                          um jogador realize uma jogada inválida, isto é, 
     *                                          quando um jogador lança uma exceção ao realizar 
     *                                          sua jogada (por exemplo, 
     *                                          ArrayIndexOutOfBoundsException ou 
     *                                          NullPointerException).
     */
    public abstract Player playMatch() 
            throws NullPlayException, PlayANullCardException, 
            PlayACardNotInHandException, TakeAllCardsAsFirstPlayException, 
            PlayAWorseCardException, MaxPlayTimeExceededException, 
            InvalidPlayException;

    /**
     * Caso a flag de verbosidade esteja ativa, imprime um objeto na saída 
     * padrão.
     *
     * @param obj Objeto a ser impresso.
     */
    protected abstract void println(Object obj);
}
