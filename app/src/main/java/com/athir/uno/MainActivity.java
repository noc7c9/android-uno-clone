package com.athir.uno;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.athir.uno.gamelogic.ICard;
import com.athir.uno.gamelogic.IMove;
import com.athir.uno.ui.GameOverDialogFragment;
import com.athir.uno.ui.IMoveItem;
import com.athir.uno.ui.MoveItemsCreator;
import com.athir.uno.ui.MoveViewAdaptor;
import com.athir.uno.ui.UIUtility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Main activity of the app.
 *
 * Responsible for starting and restarting the game as well as managing the user interface.
 */
public class MainActivity extends AppCompatActivity
        implements IPlayer, GameOverDialogFragment.GameOverDialogListener {

    // UI elements
    private TextView cpuHandText;
    private TextView playerHandText;
    private TextView drawPileText;
    private TextView discardPileText;

    private MoveViewAdaptor moveViewAdaptor;

    // The game related state variables
    private Referee referee;
    private List<ICard> currentHand = Collections.emptyList();
    private List<IMove> previousMoves = Collections.emptyList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cpuHandText = findViewById(R.id.cpuHand);
        playerHandText = findViewById(R.id.playerHand);
        drawPileText = findViewById(R.id.drawPile);
        discardPileText = findViewById(R.id.discardPile);

        // Setup the adaptor and listener for the move view
        GridView moveView = findViewById(R.id.moveView);
        moveViewAdaptor = new MoveViewAdaptor(this);
        moveView.setAdapter(moveViewAdaptor);

        moveView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                IMove move = moveViewAdaptor.getItem(position).getMove();
                if (move != null) {
                    referee.play(move);
                }
            }
        });

        // Finally start the game
        resetGame();
    }

    /**
     * (Re)starts the game.
     */
    private void resetGame() {
        List<IPlayer> players = new ArrayList<>();
        players.add(this);
        players.add(new RandomAIPlayer());
        players.add(new RandomAIPlayer());
        players.add(new RandomAIPlayer());

        referee = new Referee(players);
    }

    @Override
    public void updateState(int playerID, int currPlayerID,
                            @NonNull ICard topDiscardCard, int drawPileSize,
                            @NonNull List<Integer> handSizes, @NonNull List<ICard> hand) {
        // Update the hand view even when its not the player's turn.
        // Note: If it is the player's turn, then requestMove will update the view.
        if (playerID != currPlayerID) {
            List<IMoveItem> moveItems = MoveItemsCreator.createMoveItems(previousMoves, hand);
            moveViewAdaptor.updateMoveItems(moveItems);
            moveViewAdaptor.disableView();
        }

        // Keep a reference to the current hand contents, required to update the move view.
        this.currentHand = hand;

        // Update the user interface with new game state information.
        cpuHandText.setText(createCPUHandText(playerID, currPlayerID, handSizes));
        playerHandText.setText(String.format(Locale.getDefault(),
                "Your Hand: %d cards", handSizes.get(playerID)));

        drawPileText.setText(String.format(Locale.getDefault(),
                "%d cards\nremaining\nin deck", drawPileSize));

        discardPileText.setText(topDiscardCard.toString());
        discardPileText.setBackgroundColor(
                UIUtility.cardColorToColorValue(topDiscardCard.getColor(), this));
    }

    private String createCPUHandText(int playerID, int currPlayerID, List<Integer> handSizes) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("CPU Hand");
        if (handSizes.size() > 2) {
            stringBuilder.append('s');
        }
        stringBuilder.append(": ");

        int numPlayers = handSizes.size();
        for (int i = 0; i < numPlayers; i++) {
            if (i == playerID) {
                continue;
            }

            if (i == currPlayerID) {
                stringBuilder.append('[');
                stringBuilder.append(handSizes.get(i));
                stringBuilder.append(']');
            } else {
                stringBuilder.append(' ');
                stringBuilder.append(handSizes.get(i));
                stringBuilder.append(' ');
            }

            if (i != numPlayers - 1) {
                stringBuilder.append(" | ");
            }
        }

        return stringBuilder.toString();
    }

    @Override
    public void requestMove(Referee referee, List<IMove> moves) {
        // Update the move view based on the received moves.
        List<IMoveItem> moveItems = MoveItemsCreator.createMoveItems(moves, currentHand);
        moveViewAdaptor.updateMoveItems(moveItems);

        // Keep a reference to the moves,
        // required to update the move view when its not the player's turn.
        this.previousMoves = moves;
    }

    @Override
    public void notifyGameOver(boolean isWinner) {
        // Display a modal dialog when the game is over.
        DialogFragment dialog = new GameOverDialogFragment();

        Bundle args = new Bundle();
        args.putBoolean(GameOverDialogFragment.SHOW_WIN_MESSAGE_KEY, isWinner);
        dialog.setArguments(args);

        dialog.setCancelable(false);

        dialog.show(getSupportFragmentManager(), GameOverDialogFragment.TAG);
    }

    @Override
    public void onGameOverDialogOkClick() {
        // When the user dismisses the game over dialog, restart the game.
        resetGame();
    }

}
