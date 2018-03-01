package com.morening.java.learn.core.game;


import com.morening.java.learn.core.model.Move;

public class Human implements IPlayer {

    private String NAME = "人类";
    private char MARK = 'H';
    private char ENEMY_MARK = Game.MARK;

    @Override
    public boolean makeDecision(char[][] board, Move move, Move next) {
        System.out.println(String.format("请 %s 决定落子位置：", NAME));
        int row = move.y;
        int col = move.x;
        if (row >= 0 && row < Game.MAX_BOARD_SIZE
                && col >= 0 && col < Game.MAX_BOARD_SIZE
                && board[row][col] == Game.MARK){
            board[row][col] = MARK;

            next.x = col;
            next.y = row;
            return true;
        }

        return false;
    }

    @Override
    public String getPlayerName() {

        return NAME;
    }

    @Override
    public char getPlayerMark() {

        return MARK;
    }

    @Override
    public void setEnemyMark(char mark) {
        this.ENEMY_MARK = mark;
    }

    @Override
    public void setPlayerName(String name) {
        NAME = name;
    }

    @Override
    public void setPlayerMark(char mark) {
        MARK = mark;
    }
}
