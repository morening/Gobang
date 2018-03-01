package com.morening.java.learn.core.game;


import com.morening.java.learn.core.model.Move;
import com.morening.java.learn.core.util.DecisionLogger;
import com.morening.java.learn.core.util.GameUtil;

import java.io.IOException;
import java.util.Random;

public class Computer implements IPlayer {

    private int MAX_GAME_TREE_DEPTH = 1;
    private String NAME = "计算机";
    private char MARK = 'C';
    private char ENEMY_MARK = Game.MARK;

    private int turn = 0;

    public Computer(int depth) {
        if (depth <= 0){
            return;
        }
        MAX_GAME_TREE_DEPTH = depth;
    }

    @Override
    public boolean makeDecision(char[][] board, Move move, Move next) {
        turn++;
        try {
            DecisionLogger.getInstance().setFilePath(String.format("turn_%d.txt", turn));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(String.format("等待 %s 决定落子位置...", NAME));
        if (isHandFirst(board)){
            generateHandFirst(board, next);
            return true;
        }
        Node root = new Node(board, ENEMY_MARK, 0);
        createGameTree(root);
        populateGameTree(root);
        outputDecision(root, board, next);

        return true;
    }

    private void generateHandFirst(char[][] board, Move next) {
        int row = new Random().nextInt(Game.MAX_BOARD_SIZE);
        int col = new Random().nextInt(Game.MAX_BOARD_SIZE);
        board[row][col] = MARK;
        next.y = row;
        next.x = col;
    }

    private boolean isHandFirst(char[][] board) {
        for (int i=0; i<Game.MAX_BOARD_SIZE; i++){
            for (int j=0; j<Game.MAX_BOARD_SIZE; j++){
                if (board[i][j] != Game.MARK){
                    return false;
                }
            }
        }
        return true;
    }

    private void outputDecision(Node root, char[][] board, Move next) {
        Node child = root.child;
        Node nextNode = null;
        int maxValue = Integer.MIN_VALUE;
        while (child != null){
            if (maxValue < child.value){
                maxValue = child.value;
                nextNode = child;
            }
            child = child.brother;
        }
        if (next == null){
            return;
        }
        copyBoard(nextNode.board, board);
        next.y = nextNode.row;
        next.x = nextNode.col;
    }

    private void populateGameTree(Node root) {
        char mark = root.mark;
        char enemy_mark = mark == MARK ? ENEMY_MARK: MARK;
        Node child = root.child;
        if (child == null){
            int value = evaluate(root.board, mark, enemy_mark);
            if (root.depth % 2 == 1){
                root.value = value;
            } else {
                root.value = -value;
            }
            return;
        }
        Node parent = root.parent;
        root.value = root.mark == MARK ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        while (child != null){
            populateGameTree(child);
            if (mark == MARK){
                root.value = Math.min(root.value, child.value);
                if (parent != null && parent.mark == ENEMY_MARK){
                    if (parent.value >= root.value){
                        return;
                    }
                }
            } else if (mark == ENEMY_MARK){
                root.value = Math.max(root.value, child.value);
                if (parent != null && parent.mark == MARK){
                    if (parent.value <= root.value){
                        return;
                    }
                }
            }
            child = child.brother;
        }

        if (parent != null && parent.mark == MARK){
            parent.value = Math.min(parent.value, root.value);
        } else if (parent != null && parent.mark == ENEMY_MARK){
            parent.value = Math.max(parent.value, root.value);
        }
    }

    private int evaluate(char[][] board, char mark, char enemy_mark) {
        int score = calcScore(board, mark);
        int enemy_score = calcScore(board, enemy_mark);

        DecisionLogger.getInstance().doLogging(DecisionLogger.getLoggingMsg(board, mark, score, enemy_mark, enemy_score));

        return score - enemy_score;
    }

    private static final int SCORE_FIVE_IN_A_ROW = 100000;
    private static final int[] MULTI_SCORE = {0, 10, 100, 1000, 10000};
    private static final int[] SINGLE_SCORE = {0, 1, 10, 100, 1000};

    private int calcScore(char[][] board, char mark) {
        int score = 0;
        /*横向*/
        for (int i=0; i<Game.MAX_BOARD_SIZE; i++){
            int j = 0;
            while (j < Game.MAX_BOARD_SIZE){
                if (board[i][j] == mark){
                    int count = 1;
                    while (j+count < Game.MAX_BOARD_SIZE && board[i][j+count] == mark){
                        count++;
                    }
                    if (count >= 5){
                        score += SCORE_FIVE_IN_A_ROW;
                    } else if ((j-1 >= 0 && board[i][j-1] == Game.MARK)
                            && (j+count < Game.MAX_BOARD_SIZE && board[i][j+count] == Game.MARK)){
                        score += MULTI_SCORE[count];
                    } else if ((j-1 >= 0 && board[i][j-1] == Game.MARK)
                            || (j+count < Game.MAX_BOARD_SIZE && board[i][j+count] == Game.MARK)){
                        score += SINGLE_SCORE[count];
                    }

                    j = j + count - 1;
                }
                j++;
            }
        }
        /*纵向*/
        for (int j=0; j<Game.MAX_BOARD_SIZE; j++){
            int i = 0;
            while (i < Game.MAX_BOARD_SIZE){
                if (board[i][j] == mark){
                    int count = 1;
                    while (i+count < Game.MAX_BOARD_SIZE && board[i+count][j] == mark){
                        count++;
                    }
                    if (count >= 5){
                        score += SCORE_FIVE_IN_A_ROW;
                    } else if ((i-1 >= 0 && board[i-1][j] == Game.MARK)
                            && (i+count < Game.MAX_BOARD_SIZE && board[i+count][j] == Game.MARK)){
                        score += MULTI_SCORE[count];
                    } else if ((i-1 >= 0 && board[i-1][j] == Game.MARK)
                            || (i+count < Game.MAX_BOARD_SIZE && board[i+count][j] == Game.MARK)){
                        score += SINGLE_SCORE[count];
                    }

                    i = i + count - 1;
                }
                i++;
            }
        }
        /*左斜向*/
        for (int i=0; i<=10; i++){
            int j = 0;
            while (i+j < Game.MAX_BOARD_SIZE){
                if (board[i+j][j] == mark){
                    int count = 1;
                    while (i+j+count < Game.MAX_BOARD_SIZE && board[i+j+count][j+count] == mark){
                        count++;
                    }
                    if (count >= 5){
                        score += SCORE_FIVE_IN_A_ROW;
                    } else if ((j-1 >= 0 && board[i+j-1][j-1] == Game.MARK)
                            && (i+j+count < Game.MAX_BOARD_SIZE && board[i+j+count][j+count] == Game.MARK)){
                        score += MULTI_SCORE[count];
                    } else if ((j-1 >= 0 && board[i+j-1][j-1] == Game.MARK)
                            || (i+j+count < Game.MAX_BOARD_SIZE && board[i+j+count][j+count] == Game.MARK)){
                        score += SINGLE_SCORE[count];
                    }

                    j = j + count - 1;
                }
                j++;
            }
        }
        for (int j=1; j<=10; j++){
            int i = 0;
            while (i+j < Game.MAX_BOARD_SIZE){
                if (board[i][i+j] == mark){
                    int count = 1;
                    while (i+j+count < Game.MAX_BOARD_SIZE && board[i+count][i+j+count] == mark){
                        count++;
                    }
                    if (count >= 5){
                        score += SCORE_FIVE_IN_A_ROW;
                    } else if ((i-1 >= 0 && board[i-1][i+j-1] == Game.MARK)
                            && (i+j+count < Game.MAX_BOARD_SIZE && board[i+count][i+j+count] == Game.MARK)){
                        score += MULTI_SCORE[count];
                    } else if ((i-1 >= 0 && board[i-1][i+j-1] == Game.MARK)
                            || (i+j+count < Game.MAX_BOARD_SIZE && board[i+count][i+j+count] == Game.MARK)){
                        score += SINGLE_SCORE[count];
                    }

                    i = i + count - 1;
                }
                i++;
            }
        }

        /*右斜向*/
        for (int i=0; i<=10; i++){
            int j = 0;
            while (i+j < Game.MAX_BOARD_SIZE){
                if (board[i+j][Game.MAX_BOARD_SIZE-1-j] == mark){
                    int count = 1;
                    while (i+j+count < Game.MAX_BOARD_SIZE && Game.MAX_BOARD_SIZE-1-j-count >= 0 &&
                            board[i+j+count][Game.MAX_BOARD_SIZE-1-j-count] == mark){
                        count++;
                    }
                    if (count >= 5){
                        score += SCORE_FIVE_IN_A_ROW;
                    } else if ((i+j-1 < Game.MAX_BOARD_SIZE && Game.MAX_BOARD_SIZE-1-j+1 >= 0 && Game.MAX_BOARD_SIZE-1-j+1 < Game.MAX_BOARD_SIZE && board[i+j-1][Game.MAX_BOARD_SIZE-1-j+1] == Game.MARK)
                            && (i+j+count < Game.MAX_BOARD_SIZE && Game.MAX_BOARD_SIZE-1-j-count >= 0 && Game.MAX_BOARD_SIZE-1-j-count < Game.MAX_BOARD_SIZE && board[i+j+count][Game.MAX_BOARD_SIZE-1-j-count] == Game.MARK)){
                        score += MULTI_SCORE[count];
                    } else if ((i+j-1 < Game.MAX_BOARD_SIZE && Game.MAX_BOARD_SIZE-1-j+1 >= 0 && Game.MAX_BOARD_SIZE-1-j+1 < Game.MAX_BOARD_SIZE && board[i+j-1][Game.MAX_BOARD_SIZE-1-j+1] == Game.MARK)
                            && (i+j+count < Game.MAX_BOARD_SIZE && Game.MAX_BOARD_SIZE-1-j-count >= 0 && Game.MAX_BOARD_SIZE-1-j-count < Game.MAX_BOARD_SIZE && board[i+j+count][Game.MAX_BOARD_SIZE-1-j-count] == Game.MARK)){
                        score += SINGLE_SCORE[count];
                    }
                    j = j + count - 1;
                }
                j++;
            }
        }
        for (int j=4; j < Game.MAX_BOARD_SIZE-1; j++){
            int i = 0;
            while (i <= j){
                if (board[i][j-i] == mark){
                    int count = 1;
                    while (i+count < Game.MAX_BOARD_SIZE && j-i-count >= 0 && board[i+count][j-i-count] == mark){
                        count++;
                    }
                    if (count >= 5){
                        score += SCORE_FIVE_IN_A_ROW;
                    } else if ((i-1 >= 0 && j-i+1 < Game.MAX_BOARD_SIZE && board[i-1][j-i+1] == Game.MARK)
                            && (i+count < Game.MAX_BOARD_SIZE && j-i-count >= 0 && board[i+count][j-i-count] == Game.MARK)){
                        score += MULTI_SCORE[count];
                    } else if ((i-1 >= 0 && j-i+1 < Game.MAX_BOARD_SIZE && board[i-1][j-i+1] == Game.MARK)
                            && (i+count < Game.MAX_BOARD_SIZE && j-i-count >= 0 && board[i+count][j-i-count] == Game.MARK)){
                        score += SINGLE_SCORE[count];
                    }
                    i = i + count - 1;
                }
                i++;
            }
        }

        return score;
    }

    private void createGameTree(Node parent) {
        if (parent.depth >= MAX_GAME_TREE_DEPTH){
            return;
        }
        char enemy_mark = parent.mark;
        char mark = enemy_mark == MARK ? ENEMY_MARK: MARK;
        int[] ret = new int[2];
        if (willWin(parent.board, ret, mark)){
            Node temp = new Node(parent.board, ret[0], ret[1], mark, parent.depth+1);
            insertTreeNode(parent, temp);
            return;
        }
        for (int i=0; i<Game.MAX_BOARD_SIZE; i++){
            for (int j=0; j<Game.MAX_BOARD_SIZE; j++){
                if (parent.board[i][j] == Game.MARK
                        && (hasNeighbor(parent.board, i, j, mark) || hasNeighbor(parent.board, i, j, enemy_mark))){
                    Node temp = new Node(parent.board, i, j, mark, parent.depth+1);
                    insertTreeNode(parent, temp);
                    createGameTree(temp);
                }
            }
        }
    }

    private boolean willWin(char[][] board, int[] ret, char mark) {
        for (int i=0; i<Game.MAX_BOARD_SIZE; i++){
            for (int j=0; j<Game.MAX_BOARD_SIZE; j++){
                if (board[i][j] == Game.MARK && hasNeighbor(board, i, j, mark) && GameUtil.isFiveInARow(board, new Move(j, i), mark)){
                    ret[0] = i;
                    ret[1] = j;
                    return true;
                }
            }
        }

        return false;
    }

    private void insertTreeNode(Node parent, Node temp) {
        temp.brother = parent.child;
        parent.child = temp;

        temp.parent = parent;
    }

    private static int[][] offset = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
    private boolean hasNeighbor(char[][] board, int row, int col, char mark) {
        for (int k=0; k<8; k++){
            int offset_row = row + offset[k][0];
            int offset_col = col + offset[k][1];
            if (offset_row >= 0 && offset_row < Game.MAX_BOARD_SIZE
                    && offset_col >= 0 && offset_col < Game.MAX_BOARD_SIZE
                    && board[offset_row][offset_col] == mark){
                return true;
            }
        }
        return false;
    }

    @Override
    public String getPlayerName() {

        return NAME+"-"+MAX_GAME_TREE_DEPTH;
    }

    @Override
    public char getPlayerMark() {

        return MARK;
    }

    @Override
    public void setEnemyMark(char mark) {
        this.ENEMY_MARK = mark;
    }

    private void copyBoard(char[][] src, char[][] dst){
        for (int i=0; i<Game.MAX_BOARD_SIZE; i++){
            for (int j=0; j<Game.MAX_BOARD_SIZE; j++){
                dst[i][j] = src[i][j];
            }
        }
    }

    private class Node{
        char[][] board = null;
        int value = 0;
        char mark = Game.MARK;
        int depth = 0;

        int row = 0;
        int col = 0;

        Node child = null;
        Node brother = null;
        Node parent = null;

        public Node(char[][] board, char mark, int depth){
            this.board = new char[Game.MAX_BOARD_SIZE][Game.MAX_BOARD_SIZE];
            copyBoard(board, this.board);

            this.mark = mark;
            this.depth = depth;
        }

        public Node(char[][] board, int row, int col, char mark, int depth){
            this(board, mark, depth);

            this.row = row;
            this.col = col;
            this.board[row][col] = mark;
        }
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
