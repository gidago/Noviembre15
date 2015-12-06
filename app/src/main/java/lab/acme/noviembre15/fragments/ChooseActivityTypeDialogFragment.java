package lab.acme.noviembre15.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import lab.acme.noviembre15.R;
import lab.acme.noviembre15.utils.DialogUtils;
import lab.acme.noviembre15.utils.PreferencesUtils;
import lab.acme.noviembre15.utils.StringUtils;
import lab.acme.noviembre15.utils.TrackIconUtils;

/**
 * A DialogFragment to choose an activity type.
 *
 * @author apoorvn
 */
public class ChooseActivityTypeDialogFragment extends DialogFragment {

    /**
     * Interface for caller of this dialog fragment.
     *
     * @author apoorvn
     */
    public interface ChooseActivityTypeCaller {

        /**
         * Called when choose activity type is done.
         */
        void onChooseActivityTypeDone(String iconValue, boolean newWeight);
    }

    public static final String CHOOSE_ACTIVITY_TYPE_DIALOG_TAG = "chooseActivityType";

    private static final String KEY_CATEGORY = "category";

    private ChooseActivityTypeCaller caller;

    public static ChooseActivityTypeDialogFragment newInstance(String category) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CATEGORY, category);

        ChooseActivityTypeDialogFragment fragment = new ChooseActivityTypeDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            caller = (ChooseActivityTypeCaller) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement "
                    + ChooseActivityTypeCaller.class.getSimpleName());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return getDialog(getActivity(), getArguments().getString(KEY_CATEGORY), caller);
    }

    public static Dialog getDialog(
            final Activity activity, final String category, final ChooseActivityTypeCaller caller) {
        View view = activity.getLayoutInflater().inflate(R.layout.choose_activity_type, null);
        GridView gridView = (GridView) view.findViewById(R.id.choose_category_type_grid_view);
       // final View weightContainer = view.findViewById(R.id.choose_activity_type_weight_container);

      //  TextView weightLabel = (TextView) view.findViewById(R.id.choose_activity_type_weight_label);
        // todo - quitado imperial
       // weightLabel.setText(
         //        R.string.description_weight_metric);

       // final TextView weight = (TextView) view.findViewById(R.id.choose_activity_type_weight);

        List<Integer> imageIds = new ArrayList<Integer>();
        for (String iconValue : TrackIconUtils.getAllIconValues()) {
            imageIds.add(TrackIconUtils.getIconDrawable(iconValue));
        }

        Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_track_airplane, options);
        int padding = 32;
        int width = options.outWidth + 2 * padding;
        int height = options.outHeight + 2 * padding;
        gridView.setColumnWidth(width);

        final ChooseActivityTypeImageAdapter imageAdapter = new ChooseActivityTypeImageAdapter(
                activity, imageIds, width, height, padding);
        gridView.setAdapter(imageAdapter);

        final String weightValue = StringUtils.formatWeight(
                PreferencesUtils.getWeightDisplayValue(activity));
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).setNegativeButton(
                R.string.generic_cancel, null)
                .setPositiveButton(R.string.generic_ok, new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        boolean newWeight = false;
                    /*    if (weightContainer.getVisibility() == View.VISIBLE) {
                            String newValue = weight.getText().toString();
                            if (!newValue.equals(weightValue)) {
                                newWeight = true;
                                PreferencesUtils.storeWeightValue(activity, newValue);
                            }
                        }*/
                        int selected = imageAdapter.getSelected();
                        caller.onChooseActivityTypeDone(
                                TrackIconUtils.getAllIconValues().get(selected), newWeight);
                    }
                }).setTitle(R.string.track_edit_category_type_hint).setView(view).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                int position = getPosition(activity, category);
                alertDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(position != -1);
                if (position != -1) {
                    imageAdapter.setSelected(position);
                    imageAdapter.notifyDataSetChanged();
                }
               // updateWeightContainer(weightContainer, position);
               // weight.setText(weightValue);
                DialogUtils.setDialogTitleDivider(activity, alertDialog);
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                alertDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
                imageAdapter.setSelected(position);
                imageAdapter.notifyDataSetChanged();
               // updateWeightContainer(weightContainer, position);
            }
        });
        return alertDialog;
    }

    private static int getPosition(Activity activity, String category) {
        if (category == null) {
            return -1;
        }
        String iconValue = TrackIconUtils.getIconValue(activity, category);
        if (iconValue.equals("")) {
            return -1;
        }
        List<String> iconValues = TrackIconUtils.getAllIconValues();
        for (int i = 0; i < iconValues.size(); i++) {
            if (iconValues.get(i).equals(iconValue)) {
                return i;
            }
        }
        return -1;
    }

 /*   private static void updateWeightContainer(View weightContainer, int position) {
        boolean showWeight = position == 0 || position == 1 || position == 2;
        weightContainer.setVisibility(showWeight ? View.VISIBLE : View.GONE);
    }*/
}