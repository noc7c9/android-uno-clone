package com.athir.uno.ui;

import com.athir.uno.gamelogic.Card;

public class DiscardMoveItem implements IMoveItem {

    private Card card;

    public DiscardMoveItem(Card card) {
        this.card = card;
    }

    @Override
    public String getLabel() {
        return card.toString();
    }

    @Override
    public int getColorID() {
        return UIUtility.cardColorToColorId(card.getColor());
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public void accept(IMoveItemVisitor visitor) {
        visitor.visit(this);
    }

    public Card getCard() {
        return card;
    }

}