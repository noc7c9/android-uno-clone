package com.athir.uno.ui;

import com.athir.uno.gamelogic.Card;
import com.athir.uno.gamelogic.IMove;
import com.athir.uno.gamelogic.PlayCardMove;

public class PlayCardMoveItem implements IMoveItem {

    private PlayCardMove move;
    private Card card;

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