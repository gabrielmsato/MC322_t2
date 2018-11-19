package svoyikoziri.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import svoyikoziri.deck.Card;
import svoyikoziri.deck.Suit;
import svoyikoziri.engine.Engine;
import svoyikoziri.engine.Play;
import svoyikoziri.engine.PlayType;

/**
 * A classe <code>StochasticDummyPlayer</code> representa um jogador 
 * "ingênuo" aleatório.
 *
 * @author Luis H. P. Mendes
 */
public class StochasticDummyPlayer extends Player {
    /**
     * O gerador de números aleatórios.
     */
    private Random rnd;

    /**
     * Inicializa um objeto de <code>StochasticDummyPlayer</code> recém-criado.
     *
     * @param trump O naipe trunfo do jogador.
     * @param seed  A semente do gerador de números aleatórios.
     */
    public StochasticDummyPlayer(Suit trump, long seed) {
        super(trump);
        this.rnd = new Random(seed);
    }

    /**
     * Inicializa um objeto de <code>StochasticDummyPlayer</code> recém-criado.
     *
     * @param trump O naipe trunfo do jogador.
     */
    public StochasticDummyPlayer(Suit trump) {
        super(trump);
        this.rnd = new Random();
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

        List<Card> hand = engine.getUnmodifiableHandOfPlayer(this);
        // Lista de possíveis cartas a se jogar
        List<Card> possibleCardsToPlay = new ArrayList<Card>();

        // Se for o primeiro a jogar na rodada atual
        if (firstToPlay) {
            // Então pode jogar qualquer carta da mão
            possibleCardsToPlay.addAll(hand);
        } else { // Senão
            // É o segundo a jogar na rodada atual
            // Para cada carta na mão
            for (Card card : hand) {
                // Se a pilha de cartas da mesa estiver vazia 
                if (engine.getCardsOnTable().isEmpty()) {
                    // Adiciona a carta na lista de possíveis cartas a se jogar
                    possibleCardsToPlay.add(card);
                } else { // Senão
                    // A pilha de cartas da mesa não está vazia
                    // Se a carta for do mesmo naipe que a carta no topo da 
                    // pilha de cartas da mesa
                    if (card.getSuit().equals(engine.getCardsOnTable().peek().getSuit())) {
                        // Se a carta jogada for maior do que 
                        // a carta que está no topo da pilha de cartas da mesa
                        if (card.compareTo(engine.getCardsOnTable().peek()) > 0) {
                            // Adiciona a carta na lista de possíveis 
                            // cartas a se jogar 
                            possibleCardsToPlay.add(card);
                        }
                    } else { // Senão
                        // A carta não é do mesmo naipe que 
                        // a carta no topo da pilha de cartas da mesa
                        // Se a carta for do naipe trunfo
                        if (card.getSuit().equals(this.trump)) {
                            // Adiciona a carta na lista de possíveis 
                            // cartas a se jogar
                            possibleCardsToPlay.add(card);
                        }
                    }
                }
            }
        }

        // Se houver alguma carta na lista de possíveis cartas a se jogar
        if (!possibleCardsToPlay.isEmpty()) {
            // Escolhe uma carta aleatória da lista e a joga
            play = new Play(PlayType.PLAYACARD, 
                    possibleCardsToPlay.get(
                            this.rnd.nextInt(possibleCardsToPlay.size())));
        } else { // Senão
            // Pega todas as cartas da pilha de cartas da mesa
            play = new Play(PlayType.TAKEALLCARDS);
        }

        // Retorna a jogada
        return play;
    }
}
