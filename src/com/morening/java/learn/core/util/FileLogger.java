package com.morening.java.learn.core.util;


import com.morening.java.learn.core.game.Game;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public abstract class FileLogger {

    protected static final String PREFIX_PATH = "log";

    private boolean isEnabled = true;
    private FileHandler handler = null;
    private Formatter formatter = null;
    private Logger logger = null;
    private String loggerName = null;

    protected void init(String name){
        loggerName = name;
        logger = Logger.getLogger(name);
        formatter = new Formatter() {
            @Override
            public String format(LogRecord record) {
                return record.getMessage();
            }
        };
        File dir = new File(PREFIX_PATH+File.separator+loggerName);
        if (dir.exists()){
            deleteDir(dir);
        }
        dir.mkdirs();
    }

    private void deleteDir(File dir) {
        if (dir.isDirectory()){
            File[] files = dir.listFiles();
            for (File file: files){
                deleteDir(file);
            }
        }

        dir.delete();
    }

    public void setLoggerEnable(boolean enable){
        isEnabled = enable;
    }

    public void setFilePath(String fileName) throws IOException {
        if (logger == null || formatter == null
                || fileName == null || fileName.trim().isEmpty()
                || !isEnabled){
            return;
        }
        logger.removeHandler(handler);
        handler = new FileHandler(PREFIX_PATH+File.separator+loggerName+File.separator+fileName);
        handler.setFormatter(formatter);
        logger.setUseParentHandlers(false);
        logger.addHandler(handler);
    }

    public void doLogging(String msg){
        if (logger == null || !isEnabled){
            return;
        }
        logger.info(msg);
    }

    public static String convertBoard2Msg(char[][] board) {
        StringBuffer sb = new StringBuffer();
        sb.append("     ");
        for (int j = 0; j< Game.MAX_BOARD_SIZE; j++){
            if (j < 10){
                sb.append(" "+j+" ");
            } else {
                sb.append(j+" ");
            }
        }
        sb.append("\n");
        sb.append("     ");
        sb.append("---------------------------------------------\n");
        for (int i = 0; i< Game.MAX_BOARD_SIZE; i++){
            if (i < 10){
                sb.append(i+"  | ");
            } else {
                sb.append(i+" | ");
            }
            for (int j=0; j<Game.MAX_BOARD_SIZE; j++){
                sb.append(" "+board[i][j]+" ");
            }
            sb.append("\n");
        }
        sb.append("\n");

        return sb.toString();
    }
}
