package com.athir.uno;

import com.athir.uno.gamelogic.Referee;
import com.athir.uno.ui.DiscardMoveItem;
import com.athir.uno.ui.DrawMoveItem;
import com.athir.uno.ui.IMoveItemVisitor;
import com.athir.uno.ui.InvalidMoveItem;

class MoveItemPlayVisitor implements IMoveItemVisitor {

    private Referee referee;

    public MoveItemPlayVisitor(Referee referee) {
        this.referee = referee;
    }

    @Override
    public void visit(DrawMoveItem moveItem) {
        referee.drawCard();
    }

    @Override
    public void visit(DiscardMoveItem moveItem) {
        referee.playCard(moveItem.getCard());
    }

    @Override
    public void visit(InvalidMoveItem moveItem) {
        return;
    }

}