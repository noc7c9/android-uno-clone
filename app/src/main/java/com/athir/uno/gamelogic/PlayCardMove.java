package com.athir.uno.gamelogic;

public class PlayCardMove implements IMove {

    private int player;
    private Card card;

    PlayCardMove(int player, Card card) {
        this.player = player;
        this.card = card;
    }

    @Override
    public void accept(IMoveVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public int getPlayer() {
        return player;
    }

    public Card getCard() {
        return card;
    }

}