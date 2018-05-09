package com.athir.uno.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.athir.uno.R;

public class GameOverDialogFragment extends DialogFragment {

    public static final String TAG = "GameOver";
    public static final String SHOW_WIN_MESSAGE_KEY = "showWinMessage";

    private GameOverDialogListener listener;

    public interface GameOverDialogListener {
        void onGameOverDialogOkClick();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        int dialogMessage = getArguments().getBoolean(SHOW_WIN_MESSAGE_KEY)
                ? R.string.gameover_win_msg
                : R.string.gameover_lose_msg;

        builder.setMessage(dialogMessage)
            .setPositiveButton(R.string.gameover_ok, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (listener != null) {
                        listener.onGameOverDialogOkClick();
                    }
                }
            });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (GameOverDialogListener) context;
        } catch (ClassCastException e) {}
    }

}