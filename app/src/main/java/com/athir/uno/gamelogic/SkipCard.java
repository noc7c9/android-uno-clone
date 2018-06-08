package com.athir.uno.gamelogic;

/**
 * Represents a skip card.
 */
class SkipCard implements ICard {

    private ICard.Color color;
    private ICard.Rank rank;

    /**
     * Create a skip card of the given color
     *
     * @param color the color of the card
     */
    SkipCard(ICard.Color color) {
        this.color = color;
        this.rank = Rank.SKIP;
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
        gameState.endTurn();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof SkipCard)) {
            return false;
        }
        SkipCard card = (SkipCard) o;
        return color == card.color;
    }

    @Override
    public int hashCode() {
        return color.ordinal() * 1000 + rank.ordinal();
    }

}