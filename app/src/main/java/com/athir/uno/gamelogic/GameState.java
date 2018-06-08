package com.athir.uno.gamelogic;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represents one game of Uno.
 * Keeps track of all game state and implements all the game logic.
 *
 * Also implements the IMoveVisitor interface to play moves when visiting.
 */
public class GameState implements IMoveVisitor {

    private static final int INITIAL_HAND_SIZE = 7;

    // Used when shuffling and when deciding which player plays first.
    private final Random rng;

    // Game state variables
    private final int numPlayers;
    private int currentTurn;

    private final ArrayList<ArrayList<ICard>> hands;
    private final LinkedList<ICard> drawPile;
    private final LinkedList<ICard> discardPile;

    private boolean isGameOver = false;
    private int winner = -1;

    /**
     * Initialize a new game with a random player starting.
     *
     * @param numPlayers the number of players in the game
     */
    public GameState(int numPlayers) {
        this(numPlayers, -1);
    }

    /**
     * Initialize a new game with a specified number of players and a specific player starting.
     *
     * @param numPlayers     the number of players in the game
     * @param startingPlayer the player that will start the game, can be negative to let a random player start
     */
    public GameState(int numPlayers, int startingPlayer) {
        this.rng = new Random();

        this.numPlayers = numPlayers;
        this.currentTurn = startingPlayer < 0
                ? rng.nextInt(numPlayers)
                : startingPlayer % numPlayers;

        // Initialize the deck
        drawPile = new LinkedList<>();
        initializeDeck();
        Collections.shuffle(drawPile, rng);

        // Initialize player hands
        hands = new ArrayList<>(numPlayers);
        for (int i = 0; i < numPlayers; i++) {
            ArrayList<ICard> hand = new ArrayList<>(INITIAL_HAND_SIZE * 2);
            for (int j = 0; j < INITIAL_HAND_SIZE; j++) {
                hand.add(drawPile.pop());
            }

            hands.add(i, hand);
        }

        // Initialize discard pile
        discardPile = new LinkedList<>();
        discardPile.add(drawPile.pop());
    }

    /**
     * Initializes the draw pile with all the cards in a deck.
     */
    private void initializeDeck() {
        drawPile.clear();

        for (ICard.Color color : ICard.Color.values()) {
            // Numbered cards
            drawPile.add(new NumberedCard(color, 0));
            for (int value = 1; value < 10; value++) {
                drawPile.add(new NumberedCard(color, value));
                drawPile.add(new NumberedCard(color, value));
            }

            // Special cards
            drawPile.add(new SkipCard(color));
            drawPile.add(new SkipCard(color));
            drawPile.add(new SkipCard(color));
            drawPile.add(new SkipCard(color));
            drawPile.add(new SkipCard(color));
            drawPile.add(new SkipCard(color));
            drawPile.add(new SkipCard(color));
            drawPile.add(new SkipCard(color));
        }
    }

    /**
     * Handles bookkeeping necessary when the turn ends. Specifically updating whose turn it is.
     */
    void endTurn() {
        currentTurn = (currentTurn + 1) % numPlayers;
    }

    /**
     * Shuffles the discard pile (excluding the top card) into the draw pile.
     */
    private void reshuffleDiscardPile() {
        ICard topCard = discardPile.pop();

        drawPile.addAll(discardPile);
        Collections.shuffle(drawPile, rng);

        discardPile.clear();
        discardPile.add(topCard);
    }

    /**
     * Checks if the given card can be played.
     *
     * @param ICardToPlay the card to play
     * @return true if the card can be played
     */
    private boolean isValidPlay(ICard ICardToPlay) {
        ICard topICard = getTopCard();

        boolean isSameColor = topICard.getColor() == ICardToPlay.getColor();
        boolean isSameValue = topICard.getRank() == ICardToPlay.getRank();

        return isSameColor || isSameValue;
    }

    /**
     * Returns the ID of the player whose turn it is currently.
     *
     * @return current player's ID
     */
    public int getCurrentTurn() {
        return currentTurn;
    }

    /**
     * Check if the game is over or not.
     *
     * @return true if the game is over
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Check who won the game if any.
     *
     * @return the winning player's ID, negative if there is no winner yet
     */
    public int getWinner() {
        return winner;
    }

    /**
     * Retrieves the hand of the requested player
     *
     * @param playerId ID of the requested player
     * @return the cards in the hand as a readonly list
     */
    public List<ICard> getHand(int playerId) {
        if (playerId < 0 || playerId >= numPlayers) {
            throw new InvalidParameterException(
                    String.format("player id must be within the range 0 to %d", numPlayers - 1));
        }
        return Collections.unmodifiableList(hands.get(playerId));
    }

    /**
     * Returns a list containing the number of cards each player has.
     * The index is the playerID and the value is the size of the hand.
     *
     * @return the list of hand sizes
     */
    public List<Integer> getHandSizes() {
        List<Integer> handSizes = new ArrayList<>(hands.size());
        for (List<ICard> hand : hands) {
            handSizes.add(hand.size());
        }
        return handSizes;
    }

    /**
     * @return the number of cards in the draw pile
     */
    public int getDrawPileSize() {
        return drawPile.size();
    }

    /**
     * @return the card at the top of the discard pile
     */
    public ICard getTopCard() {
        return discardPile.peek();
    }

    /**
     * Return all possible valid moves that a player can take.
     *
     * @return the list of moves
     */
    public List<IMove> getMoves() {
        List<ICard> hand = hands.get(currentTurn);

        List<IMove> moves = new ArrayList<>(hand.size() + 1);
        for (ICard card : hand) {
            if (isValidPlay(card)) {
                moves.add(new PlayCardMove(currentTurn, card));
            }
        }

        moves.add(new DrawCardMove(currentTurn));

        return moves;
    }

    /**
     * Play the given move and end the turn.
     * Will silently fail if an attempt is made to play out of turn or after the game is over.
     *
     * @param move the move to play
     */
    public void play(IMove move) {
        if (isGameOver || move.getPlayer() != currentTurn) {
            return;
        }

        move.accept(this);

        endTurn();
    }

    // Visitor methods to play each move.

    @Override
    public void visit(PlayCardMove move) {
        ICard cardToPlay = move.getCard();

        if (!isValidPlay(cardToPlay)) {
            return;
        }

        ArrayList<ICard> hand = hands.get(currentTurn);

        hand.remove(cardToPlay);
        discardPile.push(cardToPlay);

        // detect game over
        if (hand.size() == 0) {
            isGameOver = true;
            winner = currentTurn;
        }

        if (!isGameOver) {
            cardToPlay.onPlay(this);
        }
    }

    @Override
    public void visit(DrawCardMove move) {
        // It is possible for draw pile to still be empty.
        if (drawPile.size() > 0) {
            hands.get(currentTurn).add(drawPile.pop());
        }
        if (drawPile.size() == 0) {
            reshuffleDiscardPile();
        }
    }
}