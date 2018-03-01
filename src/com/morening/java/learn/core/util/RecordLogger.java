package com.morening.java.learn.core.util;

public class RecordLogger extends FileLogger{

    private static final String LOGGER_NAME = "record";
    private static RecordLogger sInstance = null;

    public static RecordLogger getInstance(){
        if (sInstance == null){
            sInstance = new RecordLogger();
        }

        return sInstance;
    }

    private RecordLogger(){
        init(LOGGER_NAME);
    }

    public static String getLoggingMsg(char[][] board, String playerName, int row, int col, float time) {
        StringBuffer sb = new StringBuffer();

        sb.append(String.format("%s 落子位置（%d, %d）耗时 %.3f 秒\n", playerName, row, col, time));

        sb.append(FileLogger.convertBoard2Msg(board));

        return sb.toString();
    }
}
