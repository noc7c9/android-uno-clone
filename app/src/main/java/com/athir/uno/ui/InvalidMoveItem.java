package com.athir.uno.ui;

import com.athir.uno.gamecore.Card;

public class InvalidMoveItem implements IMoveItem {

    private Card card;

    public InvalidMoveItem(Card card) {
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
    public void accept(IMoveItemVisitor visitor) {
        visitor.visit(this);
    }
}