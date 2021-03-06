package com.athir.uno;

import android.os.Handler;
import android.support.annotation.NonNull;

import com.athir.uno.gamelogic.DrawCardMove;
import com.athir.uno.gamelogic.ICard;
import com.athir.uno.gamelogic.IMove;
import com.athir.uno.gamelogic.IMoveVisitor;
import com.athir.uno.gamelogic.PlayCardMove;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * An AI player that will randomly play a card if there any playable cards
 * and draw a card if there are no playable cards
 */
public class RandomAIPlayer implements IPlayer {

    private static final int MOVE_DELAY = 750;

    private final Random rng;

    /**
     * Initialize a new random ai player
     */
    RandomAIPlayer() {
        rng = new Random();
    }

    @Override
    public void updateState(int playerID, int currPlayerID, @NonNull ICard topDiscardCard, int drawPileSize,
                            @NonNull List<Integer> handSizes, @NonNull List<ICard> hand) { }

    @Override
    public void requestMove(final Referee referee, final List<IMove> moves) {
        Handler pause = new Handler();
        Runnable playMove = new Runnable() {
            @Override
            public void run() {
                RandomSelectMoveVisitor visitor = new RandomSelectMoveVisitor(moves.size());
                for (IMove move : moves) {
                    move.accept(visitor);
                }
                referee.play(visitor.getSelection());
            }
        };
        pause.postDelayed(playMove, MOVE_DELAY);
    }

    @Override
    public void notifyGameOver(boolean isWinner) { }

    /**
     * A move visitor that will collect and randomly select a playable card
     * or return the draw card move if there are none.
     */
    private class RandomSelectMoveVisitor implements IMoveVisitor {

        private final List<PlayCardMove> playMoves;
        private DrawCardMove drawMove;

        /**
         * Initialize a new instance.
         *
         * @param capacity the number of playable moves to expect
         */
        private RandomSelectMoveVisitor(int capacity) {
            playMoves = new ArrayList<>(capacity);
        }

        /**
         * Make the actual random selection or return the draw move.
         *
         * @return the selected move
         */
        private IMove getSelection() {
            if (playMoves.size() > 0) {
                return playMoves.get(rng.nextInt(playMoves.size()));
            } else {
                return drawMove;
            }
        }

        @Override
        public void visit(PlayCardMove move) {
            playMoves.add(move);
        }

        @Override
        public void visit(DrawCardMove move) {
            drawMove = move;
        }

    }

}
