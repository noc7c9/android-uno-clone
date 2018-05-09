package com.athir.uno.gamelogic;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomAIPlayer implements IPlayer {

    private Random rng;
    private List<Card> hand;
    private Card topDiscardCard;

    public RandomAIPlayer() {
        rng = new Random();
    }

    @Override
    public void updateState(int playerID, Card topDiscardCard, int drawPileSize, List<Integer> handSizes, List<Card> hand) {
        this.hand = hand;
        this.topDiscardCard = topDiscardCard;
    }

    @Override
    public void requestMove(Referee referee) {
        List<Card> validCards = new ArrayList<>(hand.size());
        for (Card card : hand) {
            if (GameState.isValidPlay(topDiscardCard, card)) {
                validCards.add(card);
            }
        }

        if (validCards.size() > 0) {
            int index = rng.nextInt(validCards.size());
            referee.playCard(validCards.get(index));
        } else {
            referee.drawCard();
        }
    }

    @Override
    public void notifyGameOver(boolean isWinner) { }

}