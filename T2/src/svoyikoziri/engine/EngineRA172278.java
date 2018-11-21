/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svoyikoziri.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import svoyikoziri.deck.Card;
import svoyikoziri.deck.Rank;
import svoyikoziri.deck.Suit;
import svoyikoziri.engine.exception.InvalidPlayException;
import svoyikoziri.engine.exception.MaxPlayTimeExceededException;
import svoyikoziri.engine.exception.NullPlayException;
import svoyikoziri.engine.exception.PlayACardNotInHandException;
import svoyikoziri.engine.exception.PlayANullCardException;
import svoyikoziri.engine.exception.PlayAWorseCardException;
import svoyikoziri.engine.exception.SameTrumpColorsException;
import svoyikoziri.engine.exception.TakeAllCardsAsFirstPlayException;
import svoyikoziri.player.Player;

/**
 *
 * @author gabrielmsato
 */
public class EngineRA172278 extends Engine{
    private final Player player1;
    private final Player player2;
    private final int MAX_ROUNDS;
    private final long MAX_PLAY_TIME;
    private int round;
    private List<Card> deck;
    private List<Card> player1Hand;
    private List<Card> player2Hand;
    private boolean verbose;
    private List<Play> jogadas;
    private int vitorias1;
    private int vitorias2;
    
    /**
     * 
     * @param player1
     * @param player2
     * @param minRank
     * @param seed
     * @param maxRounds
     * @param maxPlayTime
     * @param verbose
     * @throws SameTrumpColorsException 
     */
    public EngineRA172278 (Player player1, Player player2, Rank minRank, long seed, int maxRounds, long maxPlayTime, boolean verbose) throws SameTrumpColorsException{
        if (player1.getTrump().getColor().equals(player2.getTrump().getColor())) {
            throw new SameTrumpColorsException(player1.getTrump(), player2.getTrump());
        } 
        this.player1 = player1;
        this.player2 = player2;
        this.MAX_ROUNDS = maxRounds;
        this.MAX_PLAY_TIME = maxPlayTime;
        this.round = 0;
        this.verbose = verbose;
        this.deck = new ArrayList<>();
        this.player1Hand = new ArrayList<>();
        this.player2Hand = new ArrayList<>();
        this.jogadas = new ArrayList<>();
        
        for (Rank r : Rank.values()) {
            if (r.compareTo(minRank) >= 0) {
                this.deck.add(new Card(Suit.CLOVERS, r));
                this.deck.add(new Card(Suit.HEARTS, r));
                this.deck.add(new Card(Suit.PIKES, r));
                this.deck.add(new Card(Suit.TILES, r));
            }
        }
        
//        int i = 0;
//        for (Rank r : Rank.values()) {
//            if (r.equals(minRank))
//                break;
//            i++;
//        }
//        for (i = i; i < Rank.values().length; i++) {
//            this.deck.add(new Card(Suit.CLOVERS, Rank.values()[i]));
//            this.deck.add(new Card(Suit.HEARTS, Rank.values()[i]));
//            this.deck.add(new Card(Suit.PIKES, Rank.values()[i]));
//            this.deck.add(new Card(Suit.TILES, Rank.values()[i]));
//        }
        
        Collections.shuffle(deck);
    }
    
    /**
     *
     * @param player1
     * @param player2
     * @param deck
     * @param maxRounds
     * @param maxPlayTime
     * @param verbose
     * @throws SameTrumpColorsException
     */
    public EngineRA172278 (Player player1, Player player2 , List<Card> deck, int maxRounds, long maxPlayTime, boolean verbose) throws SameTrumpColorsException {
        if (player1.getTrump().getColor().equals(player2.getTrump().getColor())) {
            throw new SameTrumpColorsException(player1.getTrump(), player2.getTrump());
        }
        this.player1 = player1;
        this.player2 = player2;
        this.MAX_ROUNDS = maxRounds;
        this.MAX_PLAY_TIME = maxPlayTime;
        this.round = 0; 
        this.deck = new ArrayList<>();
        this.deck.addAll(deck);
        this.verbose = verbose;
        this.player1Hand = new ArrayList<>();
        this.player2Hand = new ArrayList<>();
        this.jogadas = new ArrayList<>();
        
//        Collections.shuffle(deck);
    }
    
    /**
     * Funcao que retorna o trunfo do Player 1
     * @return Naipe trunfo do player 1
     */
    @Override
    public Suit getPlayer1Trump() {
        return this.player1.getTrump();
    }
    
    /**
     * Funcao que retorna o trunfo do Player 2
     * @return Naipe trunfo do player 2
     */
    @Override
    public Suit getPlayer2Trump() {
        return this.player2.getTrump();
    }

    /**
     * Retorna a mao de um jogador
     * @param player O jogador que a mao sera retornada
     * @return A mao do jogador passada por parametro
     */
    @Override
    public List<Card> getUnmodifiableHandOfPlayer(Player player) {
        if (player.equals(this.player1))
            return Collections.unmodifiableList(this.player1Hand);
        return Collections.unmodifiableList(this.player2Hand);
        
    }

    /**
     * Funcao que retorna as cartas da mesa
     * @return Uma pilha com as cartas da mesa
     */
    @Override
    public Stack<Card> getCardsOnTable() {
        Stack<Card> s = new Stack<>();
        s.addAll(this.deck);
        return s;
    }
    
    /**
     * Funcao que retorna a quantidade maxima de rodadas que o jogo pode ter
     * @return O numero maximo de rodadas do jogo
     */
    @Override
    public int getMaxRounds() {
        return this.MAX_ROUNDS;
    }

    /**
     * Funcao que retorna a rodada atual
     * @return Rodada atual
     */
    @Override
    public int getCurrentRound() {
        return this.round;
    }
    
    /**
     * Funcao que retorna uma lista de jogadas realizadas no jogo
     * @return Lista com as jogadas realizadas durante o jogo
     */
    @Override
    public List<Play> getUnmodifiablePlays() {
        return Collections.unmodifiableList(this.jogadas);
    }

    @Override
    public Player playMatch() throws NullPlayException, PlayANullCardException, PlayACardNotInHandException, TakeAllCardsAsFirstPlayException, PlayAWorseCardException, MaxPlayTimeExceededException, InvalidPlayException {
        boolean  isPlayer1 = true;
        Play jogada = null;
        int i = 0;
        long startTime;

        
        this.println(Engine.getPlayerTrumpMessage(true, this.getPlayer1Trump()));
        this.println(Engine.getPlayerTrumpMessage(false, this.getPlayer2Trump()));
        
        // Trecho de codigo que distribui as cartas da cor do jogador 1
        for (Card c : this.deck.subList(0, this.deck.size() / 2)) {
            if (c.getSuit().getColor().equals(this.player1.getTrump().getColor())) {
                this.player1Hand.add(c);
            }
        }
        this.deck.removeAll(this.player1Hand);
        
        // Trecho de codigo que distribui as cartas do jogador 2 baseadas nas do jogador 1
        for (Card c : this.deck) {
            if (i == this.player1Hand.size())
                break;
            for (Card d : this.player1Hand) {
                if (c.getRank().equals(d.getRank())) {
                    if (d.getSuit().equals(this.getPlayer1Trump())) {
                        if (c.getSuit().equals(this.getPlayer2Trump())) {
                            this.player2Hand.add(c);
                            i++;
                        }    
                    } else if ((!c.getSuit().equals(this.getPlayer2Trump())) && (c.getSuit().getColor().equals(this.getPlayer2Trump().getColor()))) {
                        this.player2Hand.add(c);
                        i++;
                    }
                }    
            }
        }
        this.deck.removeAll(this.player2Hand);
        
        // Trecho de codigo que distribui as cartas restantes de cor do jogador 2 para o jogador 1
        for (Card c : this.deck) {
            if (!c.getSuit().getColor().equals(this.getPlayer1Trump().getColor())) {
                this.player1Hand.add(c);
            }
        }
        this.deck.removeAll(this.player1Hand);
        
        // Adiciona o restante das cartas para o jogador 2
        this.player2Hand.addAll(this.deck);
        this.deck.clear();
        
        
        // Aqui comeca as rodadas do jogo-----------------------------------
        while (true) {
            this.round++;
            if (this.round > this.MAX_ROUNDS) {
                if (this.player1Hand.size() < this.player2Hand.size()) {
                    // Imprime na saida padrao o vencedor do jogo
                    this.println(Engine.getWinnerPlayerMessage(true));
                    return player1;
                } else if (this.player1Hand.size() > this.player2Hand.size()) {
                    // Imprime na saida padrao o vencedor do jogo
                    this.println(Engine.getWinnerPlayerMessage(false));
                    return player2;
                } else {
                    if (this.vitorias1 > this.vitorias2) {
                        // Imprime na saida padrao o vencedor do jogo
                        this.println(Engine.getWinnerPlayerMessage(true));
                        return player1;
                    } else if (this.vitorias1 < this.vitorias2){
                        // Imprime na saida padrao o vencedor do jogo
                        this.println(Engine.getWinnerPlayerMessage(false));
                        return player2;
                    } else {
                        // Imprime na saida padrao o vencedor do jogo
                        this.println(Engine.PLAYERS_DREW_MESSAGE);
                        return player2;
                    }
                }
                    
            }
            
            // Imprime na saida padrao a rodada atual
            this.println(Engine.getRoundNumberMessage(this.round, this.MAX_ROUNDS));
            
            // Imprime na saida padrao o numero de cartas do jogador 1 e suas cartas
            this.println(Engine.getNumberOfCardsOnPlayersHandMessage(true, this.player1Hand.size()));
            Collections.sort(this.player1Hand);
            for (Card c : this.player1Hand) 
                this.println(c.toString());
            
            // Imprime na saida padrao o numero de cartas do jogador 2 e suas cartas
            this.println(Engine.getNumberOfCardsOnPlayersHandMessage(false, this.player2Hand.size()));
            Collections.sort(this.player2Hand);
            for (Card c : this.player2Hand) 
                this.println(c.toString());
            
            // Imprime na saida padrao o numero de cartas da mesa e as cartas
            this.println(Engine.getNumberOfCardsOnCardsOnTableMessage(this.deck.size()));
            for (Card c : this.deck)
                this.println(c.toString());
           
            
            if (isPlayer1) {
                // Imprime na saida padrao qual jogador comeca o round
                this.println(Engine.getPlayerStartsRoundMessage(true));
                
                // Jogada do jogador 1----------------------------
                // Bloco try catch para outras possiveis excessoes
                try {
                    startTime = System.nanoTime();
                    jogada = this.player1.playRound(true, this);
                } catch (Exception e) {
                    throw new InvalidPlayException(true);
                }
                
                // Verifica quanto tempo levou a jogada
                if (System.nanoTime() - startTime > this.MAX_PLAY_TIME) 
                    throw new MaxPlayTimeExceededException(true);
                
                // Verifica se a jogada eh nula
                if (jogada == null)
                    throw new NullPlayException(true);
                
                // Verifica se a carta jogada é nula
                if (jogada.getType().equals(PlayType.PLAYACARD) && jogada.getCard() == null)
                    throw new PlayANullCardException(true);
                    
                // Verifica se a carta jogada esta na mao do jogador
                if (jogada.getType().equals(PlayType.PLAYACARD) && !this.player1Hand.contains(jogada.getCard()))
                    throw new PlayACardNotInHandException(true, jogada.getCard());
                                
                // Verifica se esta tentando pegar as cartas da mesa na primeira jogada
                if (jogada.getType().equals(PlayType.TAKEALLCARDS))
                    throw new TakeAllCardsAsFirstPlayException(true);
                
                // A carta do jogador é jogada na mesa (deck)
                this.deck.add(jogada.getCard());
                this.player1Hand.remove(jogada.getCard());
                this.jogadas.add(jogada);
                // Imprime na saida padrao a jogada que o jogador executou
                this.println(Engine.getValidPlayMessage(true, jogada));
                
                // Caso a mao do jogador 1 esteja vazia, ele ganhou o jogo 
                if (this.player1Hand.isEmpty()){
                    // Imprime na saida padrao o vencedor do jogo
                    this.println(Engine.getWinnerPlayerMessage(true));
                    return player1;
                }
                
                
                // Jogada do jogador 2----------------------------
                // Bloco try catch para outras possiveis excessoes
                try {
                    startTime = System.nanoTime();
                    jogada = this.player2.playRound(false, this);
                } catch (Exception e) {
                    throw new InvalidPlayException(false);
                }
                
                // Verifica quanto tempo levou a jogada
                if (System.nanoTime() - startTime > this.MAX_PLAY_TIME) 
                    throw new MaxPlayTimeExceededException(false);
                
                // Verifica se a jogada eh nula
                if (jogada == null)
                    throw new NullPlayException(false);
                
                // Verifica se a carta jogada é nula
                if (jogada.getType().equals(PlayType.PLAYACARD) && jogada.getCard() == null)
                    throw new PlayANullCardException(false);
                    
                // Verifica de a carta jogada esta na mao do jogador
                if (jogada.getType().equals(PlayType.PLAYACARD) && !this.player2Hand.contains(jogada.getCard()))
                    throw new PlayACardNotInHandException(false, jogada.getCard());
                
                // Quando a jogada é pegar todas as cartas da mesa
                if (jogada.getType().equals(PlayType.TAKEALLCARDS)) {
                    this.player2Hand.addAll(this.deck);
                    this.deck.clear();
                    this.jogadas.add(jogada);
                    // Imprime na saida padrao o tipo de jogada do jogador
                    this.println(Engine.getValidPlayMessage(false, jogada));
                    this.println(Engine.getPlayerWinsRoundMessage(true));
                    this.vitorias1++;
                    isPlayer1 = true;
                    continue;
                }
                
                // Quando o jogador 2 vai jogar uma carta
                if (jogada.getCard().getSuit().equals(this.deck.get(this.deck.size() - 1).getSuit())) {
                    if (jogada.getCard().getRank().compareTo(this.deck.get(this.deck.size() - 1).getRank()) > 0) {
                        // A carta do jogador é jogada na mesa (deck)
                        this.deck.add(jogada.getCard());
                        this.player2Hand.remove(jogada.getCard());
                        this.jogadas.add(jogada);
                        // Imprime na saida padrao o tipo de jogada do jogador
                        this.println(Engine.getValidPlayMessage(false, jogada));
                        // Imprime na saida padrao o jogador que venceu o round
                        this.println(Engine.getPlayerWinsRoundMessage(false));
                        this.vitorias2++;
                    } else
                        throw new PlayAWorseCardException(false, jogada.getCard(), this.deck.get(this.deck.size() - 1));
                    
                } else if (jogada.getCard().getSuit().equals(this.getPlayer2Trump())) {
                    // A carta do jogador é jogada na mesa (deck)
                    this.deck.add(jogada.getCard());
                    this.player2Hand.remove(jogada.getCard());
                    this.jogadas.add(jogada);
                    // Imprime na saida padrao o tipo de jogada do jogador
                    this.println(Engine.getValidPlayMessage(false, jogada));
                    // Imprime na saida padrao o jogador que venceu o round
                    this.println(Engine.getPlayerWinsRoundMessage(false));
                    this.vitorias2++;
                } else
                    throw new PlayAWorseCardException(false, jogada.getCard(), this.deck.get(this.deck.size() - 1));
                
                isPlayer1 = false;

                // Caso a mao do jogador 2 esteja vazia, ele ganhou o jogo 
                if (this.player2Hand.isEmpty()) {
                    // Imprime na saida padrao o vencedor do jogo
                    this.println(Engine.getWinnerPlayerMessage(false));
                    return player2;
                }
                
            } else {
                // Quando o jogador 2 comeca a rodada
                
                // Imprime na saida padrao qual jogador comeca o round
                this.println(Engine.getPlayerStartsRoundMessage(false));
                
                // Jogada do jogador 2----------------------------
                // Bloco try catch para outras possiveis excessoes
                try {
                    startTime = System.nanoTime();
                    jogada = this.player2.playRound(true, this);
                } catch (Exception e) {
                    throw new InvalidPlayException(false);
                }
                
                // Verifica quanto tempo levou a jogada
                if (System.nanoTime() - startTime > this.MAX_PLAY_TIME) 
                    throw new MaxPlayTimeExceededException(false);
                
                // Verifica se a jogada eh nula
                if (jogada == null)
                    throw new NullPlayException(false);
                
                // Verifica se a carta jogada é nula
                if (jogada.getType().equals(PlayType.PLAYACARD) && jogada.getCard() == null)
                    throw new PlayANullCardException(false);
                    
                // Verifica de a carta jogada esta na mao do jogador
                if (jogada.getType().equals(PlayType.PLAYACARD) && !this.player2Hand.contains(jogada.getCard()))
                    throw new PlayACardNotInHandException(false, jogada.getCard());
                                
                // Verifica se esta tentando pegar as cartas da mesa na primeira jogada
                if (jogada.getType().equals(PlayType.TAKEALLCARDS))
                    throw new TakeAllCardsAsFirstPlayException(false);
                
                // A carta do jogador é jogada na mesa (deck)
                this.deck.add(jogada.getCard());
                this.player2Hand.remove(jogada.getCard());
                this.jogadas.add(jogada);
                // Imprime na saida padrao a jogada que o jogador executou
                this.println(Engine.getValidPlayMessage(false, jogada));
                
                // Caso a mao do jogador 2 esteja vazia, ele ganhou o jogo 
                if (this.player2Hand.isEmpty()){
                    // Imprime na saida padrao o vencedor do jogo
                    this.println(Engine.getWinnerPlayerMessage(false));
                    return player2;
                }
                
                
                // Jogada do jogador 1----------------------------
                // Bloco try catch para outras possiveis excessoes
                try {
                    startTime = System.nanoTime();
                    jogada = this.player1.playRound(false, this);
                } catch (Exception e) {
                    throw new InvalidPlayException(true);
                }
                
                // Verifica quanto tempo levou a jogada
                if (System.nanoTime() - startTime > this.MAX_PLAY_TIME) 
                    throw new MaxPlayTimeExceededException(true);
                
                // Verifica se a jogada eh nula
                if (jogada == null)
                    throw new NullPlayException(true);
                
                // Verifica se a carta jogada é nula
                if (jogada.getType().equals(PlayType.PLAYACARD) && jogada.getCard() == null)
                    throw new PlayANullCardException(true);
                    
                // Verifica de a carta jogada esta na mao do jogador
                if (jogada.getType().equals(PlayType.PLAYACARD) && !this.player1Hand.contains(jogada.getCard()))
                    throw new PlayACardNotInHandException(true, jogada.getCard());
                
                // Quando a jogada é pegar todas as cartas da mesa
                if (jogada.getType().equals(PlayType.TAKEALLCARDS)) {
                    this.player1Hand.addAll(this.deck);
                    this.deck.clear();
                    this.jogadas.add(jogada);
                    // Imprime na saida padrao o tipo de jogada do jogador
                    this.println(Engine.getValidPlayMessage(true, jogada));
                    this.println(Engine.getPlayerWinsRoundMessage(false));
                    this.vitorias2++;
                    isPlayer1 = false;
                    continue;
                }
                
                // Quando o jogador 1 vai jogar uma carta
                if (jogada.getCard().getSuit().equals(this.deck.get(this.deck.size() - 1).getSuit())) {
                    if (jogada.getCard().getRank().compareTo(this.deck.get(this.deck.size() - 1).getRank()) > 0) {
                        // A carta do jogador é jogada na mesa (deck)
                        this.deck.add(jogada.getCard());
                        this.player1Hand.remove(jogada.getCard());
                        this.jogadas.add(jogada);
                        // Imprime na saida padrao o tipo de jogada do jogador
                        this.println(Engine.getValidPlayMessage(true, jogada));
                        // Imprime na saida padrao o jogador que venceu o round
                        this.println(Engine.getPlayerWinsRoundMessage(true));
                        this.vitorias1++;
                    } else
                        throw new PlayAWorseCardException(true, jogada.getCard(), this.deck.get(this.deck.size() - 1));
                    
                } else if (jogada.getCard().getSuit().equals(this.getPlayer1Trump())) {
                    // A carta do jogador é jogada na mesa (deck)
                    this.deck.add(jogada.getCard());
                    this.player1Hand.remove(jogada.getCard());
                    this.jogadas.add(jogada);
                    // Imprime na saida padrao o tipo de jogada do jogador
                    this.println(Engine.getValidPlayMessage(true, jogada));
                    // Imprime na saida padrao o jogador que venceu o round
                    this.println(Engine.getPlayerWinsRoundMessage(true));
                    this.vitorias1++;
                } else
                    throw new PlayAWorseCardException(true, jogada.getCard(), this.deck.get(this.deck.size() - 1));
                
                isPlayer1 = true;

                // Caso a mao do jogador 2 esteja vazia, ele ganhou o jogo 
                if (this.player1Hand.isEmpty()) {
                    // Imprime na saida padrao o vencedor do jogo
                    this.println(Engine.getWinnerPlayerMessage(true));
                    return player1;
                }
            }
        }
    }

    /**
     * Funcao que escreve na saida padrao um objeto caso a flag verbose seja true
     * @param obj Objeto a ser escrito
     */
    @Override
    protected void println(Object obj) {
        if (verbose)
            System.out.println(obj);
    }
    
}
