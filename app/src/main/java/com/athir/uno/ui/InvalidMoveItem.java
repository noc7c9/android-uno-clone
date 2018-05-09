package com.athir.uno.ui;

import com.athir.uno.gamelogic.Card;
import com.athir.uno.gamelogic.IMove;

public class InvalidMoveItem implements IMoveItem {

    private Card card;

    InvalidMoveItem(Card card) {
        this.card = card;
    }

    @Override
    public String getLabel() {
        return card.toString();
    }

    @Override
    public int getColorID() {
        return -1;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public IMove getMove() {
        return null;
    }

}