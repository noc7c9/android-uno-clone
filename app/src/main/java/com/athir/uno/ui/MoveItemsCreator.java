package com.athir.uno.ui;

import android.support.annotation.NonNull;

import com.athir.uno.gamelogic.DrawCardMove;
import com.athir.uno.gamelogic.ICard;
import com.athir.uno.gamelogic.IMove;
import com.athir.uno.gamelogic.IMoveVisitor;
import com.athir.uno.gamelogic.PlayCardMove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A IMoveVisitor implementation that will create the list of appropriate IMoveItems
 * for the given hand and list of moves.
 */
public class MoveItemsCreator implements IMoveVisitor {

    private final List<ICard> hand;
    private final Map<ICard, List<PlayCardMoveItem>> playCardMoveItems;
    private DrawCardMoveItem drawCardMoveItem = null;

    /**
     * Returns a list of move items for the given hand and moves.
     * Internally uses an instance of MoveItemsCreator.
     *
     * @param moves the given moves
     * @param hand the given hand
     * @return the list of move items
     */
    public static List<IMoveItem> createMoveItems(@NonNull List<IMove> moves, @NonNull List<ICard> hand) {
        MoveItemsCreator moveItemsCreator = new MoveItemsCreator(hand);

        for (IMove move : moves) {
            move.accept(moveItemsCreator);
        }

        return moveItemsCreator.getFinalMoveItemsList();
    }

    /**
     * Initialize an instance with the given hand.
     *
     * @param hand the hand to use for this instance
     */
    private MoveItemsCreator(@NonNull List<ICard> hand) {
        this.hand = hand;
        playCardMoveItems = new HashMap<>(hand.size());
    }

    /**
     * Create the final list of move items based on the visited moves.
     * Also creates the InvalidMoveItems for any cards that cannot be played.
     *
     * The move items will be ordered according the hand order.
     *
     * @return the list of move items
     */
    private List<IMoveItem> getFinalMoveItemsList() {
        List<IMoveItem> moveItems = new ArrayList<>(
                playCardMoveItems.size() + hand.size() + 1);

        for (ICard card : hand) {
            if (playCardMoveItems.containsKey(card)) {
                moveItems.addAll(playCardMoveItems.remove(card));
            } else {
                moveItems.add(new InvalidMoveItem(card));
            }
        }

        if (drawCardMoveItem != null) {
            moveItems.add(drawCardMoveItem);
        }

        return moveItems;
    }

    @Override
    public void visit(PlayCardMove move) {
        ICard card = move.getCard();
        if (!playCardMoveItems.containsKey(card)) {
            playCardMoveItems.put(card, new ArrayList<PlayCardMoveItem>(4));
        }
        playCardMoveItems.get(card).add(new PlayCardMoveItem(move));
    }

    @Override
    public void visit(DrawCardMove move) {
        drawCardMoveItem = new DrawCardMoveItem(move);
    }

}
