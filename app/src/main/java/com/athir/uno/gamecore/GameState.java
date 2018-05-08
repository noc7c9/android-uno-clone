package com.athir.uno.gamecore;

import android.util.Log;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GameState {

    private static final int INITIAL_HAND_SIZE = 7;

    private final int numPlayers;
    private int currentTurn;

    private ArrayList<ArrayList<Card>> hands;
    private LinkedList<Card> drawPile;
    private LinkedList<Card> discardPile;

    public GameState(int numPlayers) {
        this.numPlayers = numPlayers;
        this.currentTurn = 0;

        // Initialize the deck
        drawPile = new LinkedList<>();
        for (CardColor color : CardColor.values()) {
            for (int value = 0; value < 10; value++) {
                drawPile.add(new Card(color, value));
                drawPile.add(new Card(color, value));
            }
        }
        Collections.shuffle(drawPile);

        // Initialize player hands
        hands = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            ArrayList<Card> hand = new ArrayList<>(INITIAL_HAND_SIZE * 2);
            for (int j = 0; j < INITIAL_HAND_SIZE; j++) {
                hand.add(drawPile.pop());
            }

            hands.add(i, hand);
        }

        // Initialize discard pile
        discardPile = new LinkedList<>();
        discardPile.add(drawPile.pop());
    }

    public List<Card> getHand(int playerId) {
        validatePlayerIDParameter(playerId);
        return Collections.unmodifiableList(hands.get(playerId));
    }

    public int getHandSize(int playerId) {
        validatePlayerIDParameter(playerId);
        return hands.get(playerId).size();
    }

    public int getDrawPileSize() {
        return drawPile.size();
    }

    public Card getTopCard() {
        return discardPile.peek();
    }

    public boolean isValidPlay(Card cardToPlay) {
        Card topCard = getTopCard();

        boolean isSameColor = topCard.getColor() == cardToPlay.getColor();
        boolean isSameValue = topCard.getValue() == cardToPlay.getValue();

        return isSameColor || isSameValue;
    }

    public boolean playCard(Card cardToPlay) {
        if (!isValidPlay(cardToPlay)) {
            return false;
        }

        ArrayList<Card> hand = hands.get(currentTurn);

        hand.remove(cardToPlay);
        discardPile.push(cardToPlay);

        endTurn();
        Log.i("PLAY CARD", cardToPlay.toString());
        return true;
    }

    public boolean drawCard() {
        hands.get(currentTurn).add(drawPile.pop());

        endTurn();
        Log.i("DRAW CARD", "-");
        return true;
    }

    public void cpuMove() {
        for (Card card : hands.get(currentTurn)) {
            if (isValidPlay(card)) {
                Log.i("CPU AI", card.toString());
                playCard(card);
                return;
            }
        }
        Log.i("CPU AI", "Draw");
        drawCard();
    }

    private void endTurn() {
        currentTurn = (currentTurn + 1) % numPlayers;
    }

    private void validatePlayerIDParameter(int playerId) {
        if (playerId < 0 || playerId >= numPlayers) {
            throw new InvalidParameterException(
                    String.format("player id must be within the range 0 to %d", numPlayers - 1));
        }
    }

}