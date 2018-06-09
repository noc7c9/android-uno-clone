package com.athir.uno.ui;

import com.athir.uno.gamelogic.ICard;
import com.athir.uno.gamelogic.IMove;
import com.athir.uno.gamelogic.PlayCardMove;

/**
 * Move item for a playable a card in the hand.
 *
 * The button text and the text color is according to the card to be played.
 */
public class PlayCardMoveItem implements IMoveItem {

    private final PlayCardMove move;
    private final ICard card;

    /**
     * @param move the move this item represents
     */
    PlayCardMoveItem(PlayCardMove move) {
        this.move = move;
        this.card = move.getCard();
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
    public IMove getMove() {
        return move;
    }

}
