package svoyikoziri.player;

import java.util.TreeSet;

import svoyikoziri.deck.Card;
import svoyikoziri.deck.Suit;
import svoyikoziri.engine.Engine;
import svoyikoziri.engine.Play;
import svoyikoziri.engine.PlayType;
import svoyikoziri.engine.exception.InvalidPlayException;
import svoyikoziri.engine.exception.MaxPlayTimeExceededException;
import svoyikoziri.engine.exception.NullPlayException;
import svoyikoziri.engine.exception.PlayACardNotInHandException;
import svoyikoziri.engine.exception.PlayANullCardException;
import svoyikoziri.engine.exception.PlayAWorseCardException;
import svoyikoziri.engine.exception.TakeAllCardsAsFirstPlayException;

/**
 * A classe <code>InvalidPlayer</code> representa um jogador inválido.
 * 
 * @author Luis H. P. Mendes
 */
public class InvalidPlayer extends Player {
    /**
     * A exceção desejada.
     */
    private final RuntimeException wantedException;

    /**
     * Inicializa um objeto de <code>InvalidPlayer</code> recém-criado.
     * 
     * @param trump           O naipe trunfo do jogador.
     * @param wantedException A exceção desejada.
     */
    public InvalidPlayer(Suit trump, RuntimeException wantedException) {
        super(trump);
        this.wantedException = wantedException;
    }

    /**
     * Recupera o naipe trunfo do jogador.
     * 
     * @return O naipe trunfo do jogador.
     */
    @Override
    public Suit getTrump() {
        return this.trump;
    }

    /**
     * Indica se algum outro objeto é igual a este objeto.
     * 
     * @param obj O objeto de referência com o qual comparar.
     * 
     * @return <code>true</code> se este objeto é o mesmo que o parâmetro obj, 
     *         <code>false</code> caso contrário.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj instanceof Player) {
            Player player = (Player) obj;

            return ((this.trump == null && player.trump == null) 
                    || this.trump.equals(player.trump));
        }

        return false;
    }

    /**
     * Retorna uma jogada inválida do jogador para a rodada atual.
     * 
     * @param firstToPlay Um valor booleano que indica se o jogador iniciou a 
     *                    rodada atual.
     * @param engine      O motor do jogo.
     * 
     * @return Uma jogada inválida.
     */
    @SuppressWarnings("null")
	@Override
    public Play playRound(boolean firstToPlay, Engine engine) {
        // Se a exceção desejada não for nula
        if (this.wantedException != null) {
            // Se a exceção desejada for NullPlayException
            if (this.wantedException instanceof NullPlayException) {
                // Retorna uma jogada nula
                return null;
            // Se a exceção desejada for PlayANullCardException
            } else if (this.wantedException instanceof PlayANullCardException) {
                // Retorna uma jogada do tipo PLAYACARD sem uma carta
                return new Play(PlayType.PLAYACARD);
            // Se a exceção desejada for PlayACardNotInHandException
            } else if (this.wantedException  instanceof PlayACardNotInHandException) {
                TreeSet<Card> hand = new TreeSet<Card>(engine.getUnmodifiableHandOfPlayer(this));
                Card invalidCardToPlay = null;
                // Procura por uma carta que não tem na mão do jogador e a joga
                for (Card card : hand) {
                    Card possibleInvalidCardToPlay = new Card(Suit.HEARTS, 
                            card.getRank());

                    if (!hand.contains(possibleInvalidCardToPlay)) {
                        invalidCardToPlay = possibleInvalidCardToPlay;
                        break;
                    }

                    possibleInvalidCardToPlay = new Card(Suit.TILES, 
                            card.getRank());
    
                    if (!hand.contains(possibleInvalidCardToPlay)) {
                        invalidCardToPlay = possibleInvalidCardToPlay;
                        break;
                    }

                    possibleInvalidCardToPlay = new Card(Suit.CLOVERS, 
                            card.getRank());

                    if (!hand.contains(possibleInvalidCardToPlay)) {
                        invalidCardToPlay = possibleInvalidCardToPlay;
                        break;
                    }

                    possibleInvalidCardToPlay = new Card(Suit.PIKES, 
                            card.getRank());

                    if (!hand.contains(possibleInvalidCardToPlay)) {
                        invalidCardToPlay = possibleInvalidCardToPlay;
                        break;
                    }
                }

                return new Play(PlayType.PLAYACARD, invalidCardToPlay);
            // Se a exceção desejada for TakeAllCardsAsFirstPlayException
            } else if (this.wantedException instanceof TakeAllCardsAsFirstPlayException) {
                // Retorna uma jogada do tipo TAKEALLCARDS
                return new Play(PlayType.TAKEALLCARDS);
            // Se a exceção desejada for do tipo PlayAWorseCardException
            } else if (this.wantedException instanceof PlayAWorseCardException) {
                // Cartas na mão do jogador
                TreeSet<Card> hand = new TreeSet<Card>(engine.getUnmodifiableHandOfPlayer(this));
                Card worseCardToPlay = null;

                // Se for o primeiro a jogar na rodada atual
                if (firstToPlay) {
                    // Escolhe a primeira carta da mão
                    worseCardToPlay = hand.first();
                } else { // Senão
                    // É o segundo a jogar na rodada atual
                    // Para cada carta na mão
                    for (Card card : hand) {
                        // Se a pilha de cartas da mesa estiver vazia 
                        if (engine.getCardsOnTable().isEmpty()) {
                            // Escolhe a primeira carta da mão
                            worseCardToPlay = hand.first();
                        } else { // Senão
                            // A pilha de cartas da mesa não está vazia
                            // Se a carta for do mesmo naipe que a carta no 
                            // topo da pilha de cartas da mesa
                            if (card.getSuit().equals(engine.getCardsOnTable().peek().getSuit())) {
                                // Se a carta jogada for menor do que 
                                // a carta que está no topo da pilha de cartas da mesa
                                if (card.compareTo(engine.getCardsOnTable().peek()) < 0) {
                                    // Escolhe a carta
                                    worseCardToPlay = card;
                                    // e para de procurar
                                    break;
                                }
                            } else { // Senão
                                // A carta não é do mesmo naipe que 
                                // a carta no topo da pilha de cartas da mesa
                                // Se a carta não for do naipe trunfo
                                if (!card.getSuit().equals(this.trump)) {
                                    // Escolhe a carta
                                    worseCardToPlay = card;
                                    // e para de procurar
                                    break;
                                }
                            }
                        }
                    }
                }

                return new Play(PlayType.PLAYACARD, worseCardToPlay);
            // Se a exceção desejada for do tipo MaxPlayTimeExceededException
            } else if (this.wantedException instanceof MaxPlayTimeExceededException) {
                // "Dorme" por 2 * Engine.DEFAULT_MAX_PLAY_TIME ns
                try {
                    Thread.sleep(2 * (Engine.DEFAULT_MAX_PLAY_TIME / 1000000));
                } catch (InterruptedException e) {
                }
            // Se a exceção desejada for do tipo InvalidPlayException
            } else if (this.wantedException instanceof InvalidPlayException) {
                // Lança uma NullPointerException
                Object obj = null;
                obj.getClass();
            }
        }

        return null;
    }
}
