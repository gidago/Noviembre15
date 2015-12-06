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
import lab.acme.noviembre15.utils.CategoryIconUtils;


/**
 * A DialogFragment to choose an activity type.
 *
 * @author apoorvn
 */
public class ChooseCategoryTypeDialogFragment extends DialogFragment {

    /**
     * Interface for caller of this dialog fragment.
     *
     * @author apoorvn
     */
    public interface ChooseCategoryTypeCaller {

        /**
         * Called when choose activity type is done.
         */
        void onChooseCategoryTypeDone(String iconValue);
    }

    public static final String CHOOSE_CATEGORY_TYPE_DIALOG_TAG = "chooseCategoryType";

    private static final String KEY_CATEGORY = "category";

    private ChooseCategoryTypeCaller caller;

    public static ChooseCategoryTypeDialogFragment newInstance(String category) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_CATEGORY, category);

        ChooseCategoryTypeDialogFragment fragment = new ChooseCategoryTypeDialogFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            caller = (ChooseCategoryTypeCaller) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement "
                    + ChooseCategoryTypeCaller.class.getSimpleName());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return getDialog(getActivity(), getArguments().getString(KEY_CATEGORY), caller);
    }

    public static Dialog getDialog(
            final Activity activity, final String category, final ChooseCategoryTypeCaller caller) {
        View view = activity.getLayoutInflater().inflate(R.layout.choose_category_type_grid, null);
        GridView gridView = (GridView) view.findViewById(R.id.choose_activity_type_grid_view);
        List<Integer> imageIds = new ArrayList<Integer>();
        for (String iconValue : CategoryIconUtils.getAllIconValues()) {
            imageIds.add(CategoryIconUtils.getIconDrawable(iconValue));
        }
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_track_generic, options);
        int padding = 32;
        int width = options.outWidth + 2 * padding;
        int height = options.outHeight + 2 * padding;
        gridView.setColumnWidth(width);
        final ChooseCategoryTypeImageAdapter imageAdapter = new ChooseCategoryTypeImageAdapter(
                activity, imageIds, width, height, padding);
        gridView.setAdapter(imageAdapter);
        final AlertDialog alertDialog = new AlertDialog.Builder(activity).setNegativeButton(
                R.string.generic_cancel, null)
                .setPositiveButton(R.string.generic_ok, new Dialog.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int selected = imageAdapter.getSelected();
                        caller.onChooseCategoryTypeDone(
                                CategoryIconUtils.getAllIconValues().get(selected));
                    }
                }).setTitle(R.string.track_edit_activity_type_hint).setView(view).create();
        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                int position = getPosition(activity, category);
                alertDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(position != -1);
                if (position != -1) {
                    imageAdapter.setSelected(position);
                    imageAdapter.notifyDataSetChanged();
                }
            }
        });

        gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                alertDialog.getButton(Dialog.BUTTON_POSITIVE).setEnabled(true);
                imageAdapter.setSelected(position);
                imageAdapter.notifyDataSetChanged();
            }
        });
        return alertDialog;
    }

    private static int getPosition(Activity activity, String category) {
        if (category == null) {
            return -1;
        }
        String iconValue = CategoryIconUtils.getIconValue(activity, category);
        if (iconValue.equals("")) {
            return -1;
        }
        List<String> iconValues = CategoryIconUtils.getAllIconValues();
        for (int i = 0; i < iconValues.size(); i++) {
            if (iconValues.get(i).equals(iconValue)) {
                return i;
            }
        }
        return -1;
    }

}