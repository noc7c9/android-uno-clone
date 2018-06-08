package com.athir.uno.gamelogic;

/**
 * Represents a draw two card.
 */
class DrawTwoCard implements ICard {

    private ICard.Color color;
    private ICard.Rank rank;

    /**
     * Create a draw two card of the given color
     *
     * @param color the color of the card
     */
    DrawTwoCard(ICard.Color color) {
        this.color = color;
        this.rank = Rank.DRAW_TWO;
    }

    public String toString() {
        String colorString = this.color.toString();
        String rankString = this.rank.toString();
        return colorString + " " + rankString;
    }

    @Override
    public ICard.Color getColor() {
        return color;
    }

    @Override
    public ICard.Rank getRank() {
        return rank;
    }

    @Override
    public void onPlay(GameState gameState) {
        gameState.forceDrawNextPlayer(2);
        gameState.skipNextPlayer();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof DrawTwoCard)) {
            return false;
        }
        DrawTwoCard card = (DrawTwoCard) o;
        return color == card.color;
    }

    @Override
    public int hashCode() {
        return color.ordinal() * 1000 + rank.ordinal();
    }

}