package com.athir.uno.gamelogic;

import android.util.Log;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameState {

    private static final int INITIAL_HAND_SIZE = 7;

    private final Random rng;

    private final int numPlayers;
    private int currentTurn;

    private ArrayList<ArrayList<Card>> hands;
    private LinkedList<Card> drawPile;
    private LinkedList<Card> discardPile;

    private boolean isGameOver = false;
    private int winner = -1;

    public GameState(int numPlayers) {
        this.rng = new Random();

        this.numPlayers = numPlayers;
        this.currentTurn = rng.nextInt(numPlayers);

        // Initialize the deck
        drawPile = new LinkedList<>();
        for (CardColor color : CardColor.values()) {
            for (int value = 0; value < 10; value++) {
                drawPile.add(new Card(color, value));
                drawPile.add(new Card(color, value));
            }
        }
        Collections.shuffle(drawPile, rng);

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

    private void endTurn() {
        currentTurn = (currentTurn + 1) % numPlayers;
    }

    private void reshuffleDiscardPile() {
        drawPile.add(discardPile.pop());

        LinkedList<Card> tmp = drawPile;
        drawPile = discardPile;
        discardPile = tmp;

        Collections.shuffle(drawPile, rng);
    }

    private void validatePlayerIDParameter(int playerId) {
        if (playerId < 0 || playerId >= numPlayers) {
            throw new InvalidParameterException(
                    String.format("player id must be within the range 0 to %d", numPlayers - 1));
        }
    }

    public int getCurrentTurn() {
        return currentTurn;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public int getWinner() {
        return winner;
    }

    public List<Card> getHand(int playerId) {
        validatePlayerIDParameter(playerId);
        return Collections.unmodifiableList(hands.get(playerId));
    }

    public List<Integer> getHandSizes() {
        List<Integer> handSizes = new ArrayList<>(hands.size());
        for (List<Card> hand : hands) {
            handSizes.add(hand.size());
        }
        return handSizes;
    }

    public int getDrawPileSize() {
        return drawPile.size();
    }

    public Card getTopCard() {
        return discardPile.peek();
    }

    public boolean playCard(Card cardToPlay) {
        if (isGameOver) {
            return false;
        }

        if (!GameState.isValidPlay(getTopCard(), cardToPlay)) {
            return false;
        }

        ArrayList<Card> hand = hands.get(currentTurn);

        hand.remove(cardToPlay);
        discardPile.push(cardToPlay);

        // detect game over
        if (hand.size() == 0) {
            isGameOver = true;
            winner = currentTurn;
        }

        endTurn();
        return true;
    }

    public boolean drawCard() {
        if (isGameOver) {
            return false;
        }

        // It is possible for draw pile to still be empty.
        if (drawPile.size() > 0) {
            hands.get(currentTurn).add(drawPile.pop());
        }
        if (drawPile.size() == 0) {
            reshuffleDiscardPile();
        }

        endTurn();
        return true;
    }

    public static boolean isValidPlay(Card topCard, Card cardToPlay) {
        boolean isSameColor = topCard.getColor() == cardToPlay.getColor();
        boolean isSameValue = topCard.getValue() == cardToPlay.getValue();

        return isSameColor || isSameValue;
    }

}