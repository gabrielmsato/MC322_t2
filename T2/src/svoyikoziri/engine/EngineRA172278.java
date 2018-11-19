/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package svoyikoziri.engine;

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
    
    public EngineRA172278 (Player player1, Player player2, Rank minRank, long seed, int maxRounds, long maxPlayTime, boolean verbose) {
        this.player1 = player1;
        this.player2 = player2;
        this.MAX_ROUNDS = maxRounds;
        this.MAX_PLAY_TIME = maxPlayTime;
        this.round = 0;
    }
    
    public EngineRA172278 (Player player1, Player player2 , List<Card> deck, int maxRounds, long maxPlayTime, boolean verbose) {
        this.player1 = player1;
        this.player2 = player2;
        this.MAX_ROUNDS = maxRounds;
        this.MAX_PLAY_TIME = maxPlayTime;
        this.round = 0;
    }

    @Override
    public Suit getPlayer1Trump() {
        return this.player1.getTrump();
    }

    @Override
    public Suit getPlayer2Trump() {
        return this.player2.getTrump();
    }

    @Override
    public List<Card> getUnmodifiableHandOfPlayer(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Stack<Card> getCardsOnTable() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMaxRounds() {
        return this.MAX_ROUNDS;
    }

    @Override
    public int getCurrentRound() {
        return this.round;
    }

    @Override
    public List<Play> getUnmodifiablePlays() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player playMatch() throws NullPlayException, PlayANullCardException, PlayACardNotInHandException, TakeAllCardsAsFirstPlayException, PlayAWorseCardException, MaxPlayTimeExceededException, InvalidPlayException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void println(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
