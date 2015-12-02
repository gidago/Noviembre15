package lab.acme.noviembre15.utils;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import lab.acme.noviembre15.R;

/**
 * Utilities for creating dialogs.
 *
 * @author Jimmy Shih
 */
public class DialogUtils {

    private DialogUtils() {}

    /**
     * Creates a confirmation dialog.
     *
     * @param context the context
     * @param titleId the title
     * @param message the message
     * @param okListener the listener when OK is clicked
     */
    public static Dialog createConfirmationDialog(
            final Context context, int titleId, String message, DialogInterface.OnClickListener okListener) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context)
                .setCancelable(true)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage(message)
                .setNegativeButton(R.string.generic_no, null)
                .setPositiveButton(R.string.generic_yes, okListener)
                .setTitle(titleId).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                setDialogTitleDivider(context, alertDialog);
            }
        });
        return alertDialog;
    }

    /**
     * Creates a spinner progress dialog.
     *
     * @param context the context
     * @param messageId the progress message id
     * @param onCancelListener the cancel listener
     */
    public static ProgressDialog createSpinnerProgressDialog(
            Context context, int messageId, DialogInterface.OnCancelListener onCancelListener) {
        return createProgressDialog(true, context, messageId, onCancelListener);
    }

    /**
     * Creates a horizontal progress dialog.
     *
     * @param context the context
     * @param messageId the progress message id
     * @param onCancelListener the cancel listener
     * @param formatArgs the format arguments for the messageId
     */
    public static ProgressDialog createHorizontalProgressDialog(Context context, int messageId,
                                                                DialogInterface.OnCancelListener onCancelListener, Object... formatArgs) {
        return createProgressDialog(false, context, messageId, onCancelListener, formatArgs);
    }

    /**
     * Creates a progress dialog.
     *
     * @param spinner true to use the spinner style
     * @param context the context
     * @param messageId the progress message id
     * @param onCancelListener the cancel listener
     * @param formatArgs the format arguments for the message id
     */
    private static ProgressDialog createProgressDialog(boolean spinner, final Context context,
                                                       int messageId, DialogInterface.OnCancelListener onCancelListener, Object... formatArgs) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIcon(android.R.drawable.ic_dialog_info);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(context.getString(messageId, formatArgs));
        progressDialog.setOnCancelListener(onCancelListener);
        progressDialog.setProgressStyle(spinner ? ProgressDialog.STYLE_SPINNER
                : ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle(R.string.generic_progress_title);
        progressDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialog) {
                setDialogTitleDivider(context, progressDialog);
            }
        });
        return progressDialog;
    }

    /**
     * Sets the dialog title divider.
     *
     * @param context the context
     * @param dialog the dialog
     */
    public static void setDialogTitleDivider(Context context, Dialog dialog) {
        if (ApiAdapterFactory.getApiAdapter().hasDialogTitleDivider()) {
            try {
                ViewGroup decorView = (ViewGroup) dialog.getWindow().getDecorView();
                if (decorView == null) {
                    return;
                }
                FrameLayout windowContentView = (FrameLayout) decorView.getChildAt(0);
                if (windowContentView == null) {
                    return;
                }
                FrameLayout contentView = (FrameLayout) windowContentView.getChildAt(0);
                if (contentView == null) {
                    return;
                }
                LinearLayout parentPanel = (LinearLayout) contentView.getChildAt(0);
                if (parentPanel == null) {
                    return;
                }
                LinearLayout topPanel = (LinearLayout) parentPanel.getChildAt(0);
                if (topPanel == null) {
                    return;
                }
                View titleDivider = topPanel.getChildAt(2);
                if (titleDivider == null) {
                    return;
                }
                titleDivider.setBackgroundColor(context.getResources().getColor(R.color.holo_orange_dark));
            } catch (Exception e) {
                // Can safely ignore
            }
        }
    }
}