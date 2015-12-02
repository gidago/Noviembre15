package lab.acme.noviembre15.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import lab.acme.noviembre15.utils.DialogUtils;

/**
 * Abstract My Tracks DialogFragment.
 *
 * @author Jimmy Shih
 */
public abstract class AbstractMyTracksDialogFragment extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = createDialog();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {
                DialogUtils.setDialogTitleDivider(getActivity(), dialog);
            }
        });
        return dialog;
    }

    protected abstract Dialog createDialog();
}