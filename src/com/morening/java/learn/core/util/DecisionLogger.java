package com.morening.java.learn.core.util;

public class DecisionLogger extends FileLogger{

    private static final String LOGGER_NAME = "decision";
    private static DecisionLogger sInstance = null;

    public static DecisionLogger getInstance(){
        if (sInstance == null){
            sInstance = new DecisionLogger();
        }

        return sInstance;
    }

    private DecisionLogger(){
        init(LOGGER_NAME);
    }

    public static String getLoggingMsg(char[][] board, char mark, int score, char enemy_mark, int enemy_score) {
        StringBuffer sb = new StringBuffer();

        sb.append(String.format("%c: %d  %c: %d  Evaluate: %d\n", mark, score, enemy_mark, enemy_score, (score - enemy_score)));

        sb.append(FileLogger.convertBoard2Msg(board));

        return sb.toString();
    }
}
