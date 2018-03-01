package com.morening.java.learn.core.game;


import com.morening.java.learn.core.model.Message;
import com.morening.java.learn.core.model.Move;
import com.morening.java.learn.core.model.OnOutputListener;
import com.morening.java.learn.core.util.GameUtil;
import com.morening.java.learn.core.util.RecordLogger;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Game {
    public static final int MAX_BOARD_SIZE = 15;
    public static final char MARK = '+';

    private IPlayer player1 = null;
    private IPlayer player2 = null;

    private char[][] board = null;
    private Queue<Message> messageQ = null;
    private OnOutputListener listener = null;

    public void setPlayer1(IPlayer player) {
        this.player1 = player;
    }

    public void setPlayer2(IPlayer player) {
        this.player2 = player;
    }

    public void start() {
        if (player1 == null || player2 == null){
            return;
        }
        player1.setEnemyMark(player2.getPlayerMark());
        player2.setEnemyMark(player1.getPlayerMark());
        try {
            RecordLogger.getInstance().setFilePath("record.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Thread thread = new Thread(() -> run());
        thread.start();
    }

    private void run() {
        int turn = 0;
        int count = 0;
        while (true){
            try {
                turn++;
                RecordLogger.getInstance().doLogging(String.format("===  第 %d 回合  ===\n", turn));
                Move next = new Move();
                long time = System.currentTimeMillis();
                if (player1 instanceof Human){
                    Message message = nextMessage();
                    if (!player1.makeDecision(board, message.move, next)){
                        continue;
                    }
                } else if (player1 instanceof Computer){
                    if (!player1.makeDecision(board, null, next)){
                        continue;
                    }
                    if (listener != null){
                        listener.onOutput(player1, next);
                    }
                }
                RecordLogger.getInstance().doLogging(RecordLogger.getLoggingMsg(board, player1.getPlayerName(), next.y, next.x, (System.currentTimeMillis() - time)/1000.0f));
                count++;
                printBoard(board, next);
                if (count >= MAX_BOARD_SIZE*MAX_BOARD_SIZE){
                    System.out.println("平局");
                    break;
                }
                if (GameUtil.isFiveInARow(board, next, board[next.y][next.x])){
                    System.out.println(String.format("%s 先手获胜", player1.getPlayerName()));
                    break;
                }
                time = System.currentTimeMillis();
                if (player2 instanceof Human){
                    Message message = nextMessage();
                    if (!player2.makeDecision(board, message.move, next)){
                        continue;
                    }
                } else if (player2 instanceof Computer){
                    if (!player2.makeDecision(board, null, next)){
                        continue;
                    }
                    if (listener != null){
                        listener.onOutput(player2, next);
                    }
                }
                RecordLogger.getInstance().doLogging(RecordLogger.getLoggingMsg(board, player2.getPlayerName(), next.y, next.x, (System.currentTimeMillis() - time)/1000.0f));
                count++;
                printBoard(board, next);
                if (GameUtil.isFiveInARow(board, next, board[next.y][next.x])){
                    System.out.println(String.format("%s 后手获胜", player2.getPlayerName()));
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printBoard(char[][] board, Move next){
        System.out.println(String.format("落子位置 (%d, %d)", next.y, next.x));
        System.out.print("     ");
        for (int j=0; j<MAX_BOARD_SIZE; j++){
            if (j < 10){
                System.out.print(" "+j+" ");
            } else {
                System.out.print(j+" ");
            }
        }
        System.out.println();
        System.out.print("     ");
        System.out.println("---------------------------------------------");
        for (int i=0; i<MAX_BOARD_SIZE; i++){
            if (i < 10){
                System.out.print(i+"  | ");
            } else {
                System.out.print(i+" | ");
            }
            for (int j=0; j<MAX_BOARD_SIZE; j++){
                System.out.print(" "+board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void init() {
        board = new char[MAX_BOARD_SIZE][MAX_BOARD_SIZE];
        for (int i=0; i<MAX_BOARD_SIZE; i++){
            for (int j=0; j<MAX_BOARD_SIZE; j++){
                board[i][j] = MARK;
            }
        }

        messageQ = new LinkedList<>();
    }

    public void inputMove(IPlayer player, Move move){
        sendMessage(player, move);
    }

    public void setOnOutputListener(OnOutputListener listener){
        this.listener = listener;
    }

    private Message nextMessage() throws InterruptedException {
        synchronized (messageQ){
            while (messageQ.isEmpty()){
                messageQ.wait();
            }
            return messageQ.poll();
        }
    }

    private void sendMessage(IPlayer player, Move move){
        synchronized (messageQ){
            messageQ.add(new Message(player, move));
            messageQ.notifyAll();
        }
    }

}
