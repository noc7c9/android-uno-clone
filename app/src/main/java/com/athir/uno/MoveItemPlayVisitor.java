package com.athir.uno;

import android.util.Log;

import com.athir.uno.gamecore.GameState;
import com.athir.uno.ui.DiscardMoveItem;
import com.athir.uno.ui.DrawMoveItem;
import com.athir.uno.ui.IMoveItemVisitor;
import com.athir.uno.ui.InvalidMoveItem;

class MoveItemPlayVisitor implements IMoveItemVisitor {

    private GameState gameState;

    public MoveItemPlayVisitor(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void visit(DrawMoveItem moveItem) {
        gameState.drawCard();
    }

    @Override
    public void visit(DiscardMoveItem moveItem) {
        Log.i("PLAYER", "Attempting to play: " + moveItem.getCard());
        gameState.playCard(moveItem.getCard());
    }

    @Override
    public void visit(InvalidMoveItem moveItem) {
        return;
    }

}
