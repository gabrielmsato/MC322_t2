package svoyikoziri.player;

import java.util.SortedSet;
import java.util.TreeSet;

import svoyikoziri.deck.Card;
import svoyikoziri.deck.Suit;
import svoyikoziri.engine.Engine;
import svoyikoziri.engine.Play;
import svoyikoziri.engine.PlayType;

/**
 * A classe <code>DeterministicDummyPlayer</code> representa um jogador 
 * "ingênuo" deterministico.
 *
 * @author Luis H. P. Mendes
 */
public class DeterministicDummyPlayer extends Player {
    /**
     * Inicializa um objeto de <code>DeterministicDummyPlayer</code> 
     * recém-criado.
     *
     * @param trump O naipe trunfo do jogador.
     */
    public DeterministicDummyPlayer(Suit trump) {
        super(trump);
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
     * Retorna a jogada do jogador para a rodada atual.
     *
     * @param firstToPlay Um valor booleano que indica se o jogador iniciou a 
     *                    rodada atual.
     * @param engine      O motor do jogo.
     *
     * @return Uma jogada.
     */
    @Override
    public Play playRound(boolean firstToPlay, Engine engine) {
        // Jogada a ser retornada
        Play play;

        // Cartas na mão do jogador
        SortedSet<Card> hand = 
                new TreeSet<Card>(engine.getUnmodifiableHandOfPlayer(this));
        // Carta escolhida para jogar
        Card chosenCard = null;

        // Se for o primeiro a jogar na rodada atual
        if (firstToPlay) {
            // Escolhe a primeira carta da mão
            chosenCard = hand.first();
        } else { // Senão
            // É o segundo a jogar na rodada atual
            // Para cada carta na mão
            for (Card card : hand) {
                // Se a pilha de cartas da mesa estiver vazia 
                if (engine.getCardsOnTable().isEmpty()) {
                    // Escolhe a carta para jogar
                    chosenCard = card;
                    // e para de procurar
                    break;
                } else { // Senão
                    // A pilha de cartas da mesa não está vazia
                    // Se a carta for do mesmo naipe que a carta no topo da 
                    // pilha de cartas da mesa
                    if (card.getSuit().equals(engine.getCardsOnTable().peek().getSuit())) {
                        // Se a carta jogada for maior do que 
                        // a carta que está no topo da pilha de cartas da mesa
                        if (card.compareTo(engine.getCardsOnTable().peek()) > 0) {
                            // Escolhe a carta para jogar 
                            chosenCard = card;
                            // e para de procurar
                            break;
                        }
                    } else { // Senão
                        // A carta não é do mesmo naipe que 
                        // a carta no topo da pilha de cartas da mesa
                        // Se a carta for do naipe trunfo
                        if (card.getSuit().equals(this.trump)) {
                            // Escolhe a carta para jogar
                            chosenCard = card;
                            // e para de procurar
                            break;
                        }
                    }
                }
            }
        }

        // Se conseguiu escolher uma carta para jogar
        if (chosenCard != null) {
            // Joga a carta escolhida
            play = new Play(PlayType.PLAYACARD, chosenCard);
        } else { // Senão
            // Pega todas as cartas da pilha de cartas da mesa
            play = new Play(PlayType.TAKEALLCARDS);
        }

        // Retorna a jogada
        return play;
    }
}
