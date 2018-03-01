package com.morening.java.learn.core.model;


import com.morening.java.learn.core.game.IPlayer;

public interface OnOutputListener {

    void onOutput(IPlayer player, Move move);
}
