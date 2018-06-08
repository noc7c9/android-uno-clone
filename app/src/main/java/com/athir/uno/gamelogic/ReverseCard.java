package com.athir.uno.gamelogic;

/**
 * Represents a reverse card.
 */
class ReverseCard implements ICard {

    private ICard.Color color;
    private ICard.Rank rank;

    /**
     * Create a reverse card of the given color
     *
     * @param color the color of the card
     */
    ReverseCard(ICard.Color color) {
        this.color = color;
        this.rank = Rank.REVERSE;
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
        if (gameState.getNumPlayers() == 2) {
            gameState.skipNextPlayer();
        } else {
            gameState.reverseTurnOrder();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof ReverseCard)) {
            return false;
        }
        ReverseCard card = (ReverseCard) o;
        return color == card.color;
    }

    @Override
    public int hashCode() {
        return color.ordinal() * 1000 + rank.ordinal();
    }

}