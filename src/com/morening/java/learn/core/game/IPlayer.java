package com.morening.java.learn.core.game;


import com.morening.java.learn.core.model.Move;

public interface IPlayer {

    boolean makeDecision(char[][] board, Move move, Move next);

    String getPlayerName();

    void setPlayerName(String name);

    char getPlayerMark();

    void setPlayerMark(char mark);

    void setEnemyMark(char mark);
}
