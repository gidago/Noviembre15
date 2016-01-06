package lab.acme.noviembre15.utils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.util.Pair;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import lab.acme.noviembre15.R;


/**
 * Utilities for track icon.
 *
 * @author Jimmy Shih
 */
public class CategoryIconUtils {

    public static final String VIAJE = "VIAJE";
    public static final String HOGAR = "HOGAR";
    public static final String TRABAJO = "TRABAJO";
    public static final String OTRO = "OTRO";
    public static final String COMPRAS = "COMPRAS";
    public static final String AFICION = "AFICION";

    private static final int[] VIAJE_LIST = new int[] { R.string.category_type_1,
            R.string.category_type_2, R.string.category_type_3};
    private static final int[] HOGAR_LIST = new int[] { R.string.category_type_1,
            R.string.category_type_2, R.string.category_type_3,
            R.string.category_type_4};

    private static final LinkedHashMap<String, Pair<Integer, Integer>> MAP = new LinkedHashMap<String, Pair<Integer, Integer>>();

    static {
        MAP.put(VIAJE, new Pair<Integer, Integer>(R.string.category_type_1, R.drawable.ic_category_1));
        MAP.put(HOGAR, new Pair<Integer, Integer>(R.string.category_type_2, R.drawable.ic_category_2));
        MAP.put(TRABAJO, new Pair<Integer, Integer>(R.string.category_type_3, R.drawable.ic_category_3));
        MAP.put(COMPRAS, new Pair<Integer, Integer>(R.string.category_type_4, R.drawable.ic_category_4));
        MAP.put(AFICION, new Pair<Integer, Integer>(R.string.category_type_5, R.drawable.ic_category_5));
        MAP.put(OTRO,new Pair<Integer, Integer>(R.string.category_type_6, R.drawable.ic_no_category));
    }

    private static final float[] REVERT_COLOR_MATRIX = { -1.0f, 0, 0, 0, 255, // red
            0, -1.0f, 0, 0, 255, // green
            0, 0, -1.0f, 0, 255, // blue
            0, 0, 0, 1.0f, 0 // alpha
    };

    private CategoryIconUtils() {}

    /**
     * Gets the icon drawable.
     *
     * @param iconValue the icon value
     */
    public static int getIconDrawable(String iconValue) {
        if (iconValue == null || iconValue.equals("")) {
            return R.drawable.ic_track_generic;
        }
        Pair<Integer, Integer> pair = MAP.get(iconValue);
        return pair == null ? R.drawable.ic_track_generic : pair.second;
    }

    /**
     * Gets the icon activity type.
     *
     * @param iconValue the icon value
     */
    public static int getIconActivityType(String iconValue) {
        if (iconValue == null || iconValue.equals("")) {
            return R.string.category_type_1;
        }
        Pair<Integer, Integer> pair = MAP.get(iconValue);
        return pair == null ? R.string.category_type_1 : pair.first;
    }

    /**
     * Gets all icon values.
     */
    public static List<String> getAllIconValues() {
        List<String> values = new ArrayList<String>();
        for (String value : MAP.keySet()) {
            values.add(value);
        }
        return values;
    }

    /**
     * Gets the icon value.
     *
     * @param context the context
     * @param activityType the activity type
     */
    public static String getIconValue(Context context, String activityType) {
        if (activityType == null || activityType.equals("")) {
            return "";
        }
        if (inList(context, activityType, VIAJE_LIST)) {
            return VIAJE;
        }
       if (inList(context, activityType, HOGAR_LIST)) {
            return HOGAR;
        }
        return "";
    }

    public static void setIconSpinner(Spinner spinner, String iconValue) {
        @SuppressWarnings("unchecked")
        ArrayAdapter<StringBuilder> adapter = (ArrayAdapter<StringBuilder>) spinner.getAdapter();
        StringBuilder stringBuilder = adapter.getItem(0);
        stringBuilder.delete(0, stringBuilder.length());
        stringBuilder.append(iconValue);
        adapter.notifyDataSetChanged();
    }

    public static ArrayAdapter<StringBuilder> getIconSpinnerAdapter(
            final Context context, String iconValue) {
        return new ArrayAdapter<StringBuilder>(context, android.R.layout.simple_spinner_item,
                new StringBuilder[] { new StringBuilder(iconValue) }) {
            @Override
            public View getView(int position, View convertView, android.view.ViewGroup parent) {
                ImageView imageView = convertView != null ? (ImageView) convertView
                        : new ImageView(getContext());
                Bitmap source = BitmapFactory.decodeResource(
                        context.getResources(), CategoryIconUtils.getIconDrawable(getItem(position).toString()));
                imageView.setImageBitmap(source);
                //// Default Spinner Image
                imageView.setColorFilter(0xFF303F9F, PorterDuff.Mode.MULTIPLY);
                imageView.setPadding(4, 4, -4, -4);
                return imageView;
            }
        };
    }

    /**
     * Returns true if the activity type is in the list.
     *
     * @param context the context
     * @param activityType the activity type
     * @param list the list
     */
    private static boolean inList(Context context, String activityType, int[] list) {
        for (int i : list) {
            if (context.getString(i).equals(activityType)) {
                return true;
            }
        }
        return false;
    }

}