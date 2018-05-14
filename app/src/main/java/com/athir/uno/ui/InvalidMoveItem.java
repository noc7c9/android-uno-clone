package com.athir.uno.ui;

import com.athir.uno.gamelogic.ICard;
import com.athir.uno.gamelogic.IMove;

/**
 * Move item for an unplayable a card in the hand.
 *
 * The button is disabled.
 * The text is according to the card.
 */
public class InvalidMoveItem implements IMoveItem {

    private ICard card;

    /**
     * @param card the card this item represents
     */
    InvalidMoveItem(ICard card) {
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