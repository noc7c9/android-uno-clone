package com.athir.uno.gamelogic;

public class DrawCardMove implements IMove {

    private int player;

    DrawCardMove(int player) {
        this.player = player;
    }

    @Override
    public void accept(IMoveVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getPlayer() {
        return player;
    }

}