package com.athir.uno.ui;

import com.athir.uno.gamelogic.DrawCardMove;
import com.athir.uno.gamelogic.IMove;

public class DrawCardMoveItem implements IMoveItem {

    private DrawCardMove move;

    DrawCardMoveItem(DrawCardMove move) {
        this.move = move;
    }

    @Override
    public String getLabel() {
        return "Draw a card";
    }

    @Override
    public int getColorID() {
        return -1;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public IMove getMove() {
        return move;
    }

}