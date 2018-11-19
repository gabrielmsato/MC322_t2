package svoyikoziri.driver;

import java.util.Arrays;
import java.util.List;
import java.util.TreeSet;

import svoyikoziri.deck.Card;
import svoyikoziri.deck.Rank;
import svoyikoziri.deck.Suit;
import svoyikoziri.engine.DefaultEngine;
import svoyikoziri.engine.Engine;
import svoyikoziri.engine.Play;
import svoyikoziri.engine.exception.InvalidPlayException;
import svoyikoziri.engine.exception.MaxPlayTimeExceededException;
import svoyikoziri.engine.exception.NullPlayException;
import svoyikoziri.engine.exception.PlayACardNotInHandException;
import svoyikoziri.engine.exception.PlayANullCardException;
import svoyikoziri.engine.exception.PlayAWorseCardException;
import svoyikoziri.engine.exception.SameTrumpColorsException;
import svoyikoziri.engine.exception.TakeAllCardsAsFirstPlayException;
import svoyikoziri.player.DeterministicDummyPlayer;
import svoyikoziri.player.InvalidPlayer;
import svoyikoziri.player.Player;

/**
 * A classe <code>Main</code> representa uma aplicação em Java.
 *
 * @author Luis H. P. Mendes
 */
public class Main {
    /**
     * O método main é o ponto de partida para esta aplicação Java.
     *
     * @param args argumentos passados ao método main.
     */
    public static void main(String[] args) {
        List<Card> deck;
        Player player1, player2;
        Engine engine;

        // Cria um baralho de 32 cartas já embaralhado
        deck = Arrays.asList(
                new Card(Suit.TILES, Rank.ACE),
                new Card(Suit.CLOVERS, Rank.ACE),
                new Card(Suit.CLOVERS, Rank.EIGHT),
                new Card(Suit.HEARTS, Rank.EIGHT),
                new Card(Suit.PIKES, Rank.EIGHT),
                new Card(Suit.HEARTS, Rank.ACE),
                new Card(Suit.HEARTS, Rank.QUEEN),
                new Card(Suit.TILES, Rank.QUEEN),
                new Card(Suit.HEARTS, Rank.NINE),
                new Card(Suit.TILES, Rank.NINE),
                new Card(Suit.PIKES, Rank.ACE),
                new Card(Suit.HEARTS, Rank.JACK),
                new Card(Suit.PIKES, Rank.SEVEN),
                new Card(Suit.TILES, Rank.JACK),
                new Card(Suit.HEARTS, Rank.SEVEN),
                new Card(Suit.HEARTS, Rank.TEN),
                new Card(Suit.CLOVERS, Rank.JACK),
                new Card(Suit.PIKES, Rank.JACK),
                new Card(Suit.PIKES, Rank.TEN),
                new Card(Suit.PIKES, Rank.QUEEN),
                new Card(Suit.CLOVERS, Rank.TEN),
                new Card(Suit.HEARTS, Rank.KING),
                new Card(Suit.TILES, Rank.KING),
                new Card(Suit.CLOVERS, Rank.QUEEN),
                new Card(Suit.CLOVERS, Rank.KING),
                new Card(Suit.PIKES, Rank.KING),
                new Card(Suit.TILES, Rank.TEN),
                new Card(Suit.PIKES, Rank.NINE),
                new Card(Suit.CLOVERS, Rank.NINE),
                new Card(Suit.TILES, Rank.SEVEN),
                new Card(Suit.TILES, Rank.EIGHT),
                new Card(Suit.CLOVERS, Rank.SEVEN));

        // Tenta instanciar um motor entre dois jogadores de naipes trunfos 
        // de mesma cor
        player1 = new DeterministicDummyPlayer(Suit.HEARTS);
        player2 = new DeterministicDummyPlayer(Suit.TILES);

        try {
            engine = new DefaultEngine(player1, player2, deck, 
                    Engine.DEFAULT_MAX_ROUNDS, Engine.DEFAULT_MAX_PLAY_TIME, 
                    false);
        } catch (SameTrumpColorsException stce) {
            System.out.println(stce.getMessage());
        }

        // Tenta jogar uma partida com um jogador que faz jogadas nulas
        player1 = new InvalidPlayer(Suit.HEARTS, new NullPlayException(true));
        player2 = new DeterministicDummyPlayer(Suit.CLOVERS);

        try {
            engine = new DefaultEngine(player1, player2, deck, 
                    Engine.DEFAULT_MAX_ROUNDS, Engine.DEFAULT_MAX_PLAY_TIME, 
                    true);
            
            engine.playMatch();
        } catch (NullPlayException npe) {
            System.out.println(npe.getMessage());
        }

        // Tenta jogar uma partida com um jogador que joga cartas nulas
        player1 = new InvalidPlayer(Suit.HEARTS, 
                new PlayANullCardException(true));
        player2 = new DeterministicDummyPlayer(Suit.PIKES);

        try {
            engine = new DefaultEngine(player1, player2, deck, 
                    Engine.DEFAULT_MAX_ROUNDS, Engine.DEFAULT_MAX_PLAY_TIME, 
                    false);
            
            engine.playMatch();
        } catch (PlayANullCardException pance) {
            System.out.println(pance.getMessage());
        }

        // Tenta jogar uma partida com um jogador que joga cartas que não 
        // estão em sua mão
        player1 = new InvalidPlayer(Suit.TILES, 
        		new PlayACardNotInHandException(true));
        player2 = new DeterministicDummyPlayer(Suit.CLOVERS);

        try {
            engine = new DefaultEngine(player1, player2, deck, 
                    Engine.DEFAULT_MAX_ROUNDS, Engine.DEFAULT_MAX_PLAY_TIME, 
                    true);
            
            engine.playMatch();
        } catch (PlayACardNotInHandException pacnihe) {
            System.out.println(pacnihe.getMessage());
        }

        // Tenta jogar uma partida com um jogador que tenta pegar todas as 
        // cartas da mesa sendo o primeiro a jogar na rodada
        player1 = new InvalidPlayer(Suit.TILES, 
        		new TakeAllCardsAsFirstPlayException(true));
        player2 = new DeterministicDummyPlayer(Suit.PIKES);

        try {
            engine = new DefaultEngine(player1, player2, deck, 
                    Engine.DEFAULT_MAX_ROUNDS, Engine.DEFAULT_MAX_PLAY_TIME, 
                    false);
            
            engine.playMatch();
        } catch (TakeAllCardsAsFirstPlayException tacafpe) {
            System.out.println(tacafpe.getMessage());
        }

        // Tenta jogar uma partida com um jogador que jogar uma carta que é 
        // pior do que a carta no topo da pilha de cartas da mesa
        player1 = new DeterministicDummyPlayer(Suit.CLOVERS);
        player2 = new InvalidPlayer(Suit.HEARTS, 
        		new PlayAWorseCardException(false));

        try {
            engine = new DefaultEngine(player1, player2, deck, 
                    Engine.DEFAULT_MAX_ROUNDS, Engine.DEFAULT_MAX_PLAY_TIME, 
                    true);
            
            engine.playMatch();
        } catch (PlayAWorseCardException pawce) {
            System.out.println(pawce.getMessage());
        }

        // Tenta jogar uma partida com um jogador que lança exceção ao tentar 
        // fazer sua jogada
        player1 = new InvalidPlayer(Suit.CLOVERS, 
        		new InvalidPlayException(true));
        player2 = new DeterministicDummyPlayer(Suit.TILES);

        try {
            engine = new DefaultEngine(player1, player2, deck, 
                    Engine.DEFAULT_MAX_ROUNDS, Engine.DEFAULT_MAX_PLAY_TIME, 
                    false);
            
            engine.playMatch();
        } catch (InvalidPlayException ipe) {
            System.out.println(ipe.getMessage());
        }

        // Tenta jogar uma partida com um jogador que demora mais do que o 
        // permitido para fazer a sua jogada
        player1 = new InvalidPlayer(Suit.PIKES, 
        		new MaxPlayTimeExceededException(true));
        player2 = new DeterministicDummyPlayer(Suit.HEARTS);

        try {
            engine = new DefaultEngine(player1, player2, deck, 
                    Engine.DEFAULT_MAX_ROUNDS, Engine.DEFAULT_MAX_PLAY_TIME, 
                    true);
            
            engine.playMatch();
        } catch (MaxPlayTimeExceededException mptee) {
            System.out.println(mptee.getMessage());
        }

        // Joga uma partida entre dois jogadores "ingênuos" determinísticos
        player1 = new DeterministicDummyPlayer(Suit.PIKES);
        player2 = new DeterministicDummyPlayer(Suit.TILES);

        engine = new DefaultEngine(player1, player2, deck, 
                Engine.DEFAULT_MAX_ROUNDS, Engine.DEFAULT_MAX_PLAY_TIME, 
                false);

        engine.playMatch();

        System.out.println("Naipe trunfo do Jogador1: " 
                + engine.getPlayer1Trump() + ".");
        System.out.println("Naipe trunfo do Jogador2: " 
                + engine.getPlayer2Trump() + ".");
        if (engine.getUnmodifiableHandOfPlayer(player1).isEmpty()) {
            System.out.println("O Jogador1 nao tem nenhuma carta na mao.");
            try {
                engine.getUnmodifiableHandOfPlayer(player1).add(
                        new Card(Suit.HEARTS, Rank.TWO));
            } catch (UnsupportedOperationException upe) {
                System.out.println("Nao foi possivel modificar as cartas na mao do Jogador1.");
            }
        } else {
            System.out.println("Cartas na mao do Jogador1:");
            TreeSet<Card> sortedHand1 = 
                    new TreeSet<Card>(engine.getUnmodifiableHandOfPlayer(player1));
            for (Card card : sortedHand1) {
                System.out.println(card);
            }
            try {
                engine.getUnmodifiableHandOfPlayer(player1).clear();
            } catch (UnsupportedOperationException upe) {
                System.out.println("Nao foi possivel modificar as cartas na mao do Jogador1.");
            }
        }
        if (engine.getUnmodifiableHandOfPlayer(player2).isEmpty()) {
            System.out.println("O Jogador2 nao tem nenhuma carta na mao.");
            try {
                engine.getUnmodifiableHandOfPlayer(player2).add(
                        new Card(Suit.HEARTS, Rank.TWO));
            } catch (UnsupportedOperationException upe) {
                System.out.println("Nao foi possivel modificar as cartas na mao do Jogador2.");
            }
        } else {
            System.out.println("Cartas na mao do Jogador2:");
            TreeSet<Card> sortedHand2 = 
                    new TreeSet<Card>(engine.getUnmodifiableHandOfPlayer(player2));
            for (Card card : sortedHand2) {
                System.out.println(card);
            }
            try {
                engine.getUnmodifiableHandOfPlayer(player2).clear();
            } catch (UnsupportedOperationException upe) {
                System.out.println("Nao foi possivel modificar as cartas na mao do Jogador2.");
            }
        }
        if (engine.getCardsOnTable().isEmpty()) {
            System.out.println("A pilha de cartas da mesa nao tem nenhuma carta.");
        } else {
            System.out.println("Cartas na pilha de cartas da mesa:");
            for (Card card : engine.getCardsOnTable()) {
                System.out.println(card);
            }
        }
        System.out.println("Numero maximo de rodadas da partida: " 
                + engine.getMaxRounds() + ".");
        System.out.println("Numero da rodada atual da partida: " 
                + engine.getCurrentRound() + ".");
        System.out.println("Lista de jogadas:");
        for (Play play : engine.getUnmodifiablePlays()) {
            System.out.println(play);
        }
        try {
            engine.getUnmodifiablePlays().clear();
        } catch (UnsupportedOperationException upe) {
            System.out.println("Nao foi possivel modificar a lista de jogadas.");
        }

        // Joga uma partida entre dois jogadores "ingênuos" determinísticos
        player1 = new DeterministicDummyPlayer(Suit.HEARTS);
        player2 = new DeterministicDummyPlayer(Suit.CLOVERS);

        engine = new DefaultEngine(player1, player2, deck, 
                Engine.DEFAULT_MAX_ROUNDS, Engine.DEFAULT_MAX_PLAY_TIME, 
                true);

        engine.playMatch();

        System.out.println("Naipe trunfo do Jogador1: " 
                + engine.getPlayer1Trump() + ".");
        System.out.println("Naipe trunfo do Jogador2: " 
                + engine.getPlayer2Trump() + ".");
        if (engine.getUnmodifiableHandOfPlayer(player1).isEmpty()) {
            System.out.println("O Jogador1 nao tem nenhuma carta na mao.");
            try {
                engine.getUnmodifiableHandOfPlayer(player1).add(
                        new Card(Suit.HEARTS, Rank.TWO));
            } catch (UnsupportedOperationException upe) {
                System.out.println("Nao foi possivel modificar as cartas na mao do Jogador1.");
            }
        } else {
            System.out.println("Cartas na mao do Jogador1:");
            TreeSet<Card> sortedHand1 = 
                    new TreeSet<Card>(engine.getUnmodifiableHandOfPlayer(player1));
            for (Card card : sortedHand1) {
                System.out.println(card);
            }
            try {
                engine.getUnmodifiableHandOfPlayer(player1).clear();
            } catch (UnsupportedOperationException upe) {
                System.out.println("Nao foi possivel modificar as cartas na mao do Jogador1.");
            }
        }
        if (engine.getUnmodifiableHandOfPlayer(player2).isEmpty()) {
            System.out.println("O Jogador2 nao tem nenhuma carta na mao.");
            try {
                engine.getUnmodifiableHandOfPlayer(player2).add(
                        new Card(Suit.HEARTS, Rank.TWO));
            } catch (UnsupportedOperationException upe) {
                System.out.println("Nao foi possivel modificar as cartas na mao do Jogador2.");
            }
        } else {
            System.out.println("Cartas na mao do Jogador2:");
            TreeSet<Card> sortedHand2 = 
                    new TreeSet<Card>(engine.getUnmodifiableHandOfPlayer(player2));
            for (Card card : sortedHand2) {
                System.out.println(card);
            }
            try {
                engine.getUnmodifiableHandOfPlayer(player2).clear();
            } catch (UnsupportedOperationException upe) {
                System.out.println("Nao foi possivel modificar as cartas na mao do Jogador2.");
            }
        }
        if (engine.getCardsOnTable().isEmpty()) {
            System.out.println("A pilha de cartas da mesa nao tem nenhuma carta.");
        } else {
            System.out.println("Cartas na pilha de cartas da mesa:");
            for (Card card : engine.getCardsOnTable()) {
                System.out.println(card);
            }
        }
        System.out.println("Numero maximo de rodadas da partida: " 
                + engine.getMaxRounds() + ".");
        System.out.println("Numero da rodada atual da partida: " 
                + engine.getCurrentRound() + ".");
        System.out.println("Lista de jogadas:");
        for (Play play : engine.getUnmodifiablePlays()) {
            System.out.println(play);
        }
        try {
            engine.getUnmodifiablePlays().clear();
        } catch (UnsupportedOperationException upe) {
            System.out.println("Nao foi possivel modificar a lista de jogadas.");
        }
    }
}
