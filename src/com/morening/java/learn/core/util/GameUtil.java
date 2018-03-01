package com.morening.java.learn.core.util;


import com.morening.java.learn.core.game.Game;
import com.morening.java.learn.core.model.Move;

import java.util.Arrays;

public class GameUtil {

    public static boolean isFiveInARow(char[][] board, Move next, char mark) {

        int[] count = new int[4];
        Arrays.fill(count, 1);
        count[0] += countRow(board, next.y, next.x, -1, 0, mark);
        count[1] += countRow(board, next.y, next.x, -1, 1, mark);
        count[2] += countRow(board, next.y, next.x, 0, 1, mark);
        count[3] += countRow(board, next.y, next.x, 1, 1, mark);
        count[0] += countRow(board, next.y, next.x, 1, 0, mark);
        count[1] += countRow(board, next.y, next.x, 1, -1, mark);
        count[2] += countRow(board, next.y, next.x, 0, -1, mark);
        count[3] += countRow(board, next.y, next.x, -1, -1, mark);

        for (int k=0; k<4; k++){
            if (count[k] >= 5){
                return true;
            }
        }

        return false;
    }

    private static int countRow(char[][] board, int row, int col, int offset_row, int offset_col, char mark){
        if (row+offset_row >= 0 && row+offset_row < Game.MAX_BOARD_SIZE
                && col+offset_col >= 0 && col+offset_col < Game.MAX_BOARD_SIZE
                && board[row+offset_row][col+offset_col] == mark){
            return countRow(board, row+offset_row, col+offset_col, offset_row, offset_col, mark) + 1;
        }
        return 0;
    }
}
