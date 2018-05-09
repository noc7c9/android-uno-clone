package com.athir.uno.ui;

import com.athir.uno.gamelogic.Card;
import com.athir.uno.gamelogic.DrawCardMove;
import com.athir.uno.gamelogic.IMoveVisitor;
import com.athir.uno.gamelogic.PlayCardMove;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoveItemFactoryMoveVisitor implements IMoveVisitor {

    private Map<Card, PlayCardMoveItem> playCardMoveItems;
    private DrawCardMoveItem drawCardMoveItem;
    private List<Card> hand;

    public MoveItemFactoryMoveVisitor(List<Card> hand) {
        this.hand = hand;
        playCardMoveItems = new HashMap<>(hand.size());
    }

    @Override
    public void visit(PlayCardMove move) {
        playCardMoveItems.put(move.getCard(), new PlayCardMoveItem(move));
    }

    @Override
    public void visit(DrawCardMove move) {
        drawCardMoveItem = new DrawCardMoveItem(move);
    }

    public List<IMoveItem> getMoveItems() {
        List<IMoveItem> moveItems = new ArrayList<>(hand.size() + 1);

        for (Card card : hand) {
            if (playCardMoveItems.containsKey(card)) {
                moveItems.add(playCardMoveItems.get(card));
            } else {
                moveItems.add(new InvalidMoveItem(card));
            }
        }

        moveItems.add(drawCardMoveItem);

        return moveItems;
    }

}