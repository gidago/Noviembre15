package lab.acme.noviembre15.fragments;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import lab.acme.noviembre15.R;


/**
 * Image adapter for choosing an activity type.
 *
 * @author apoorvn
 */
public class ChooseCategoryTypeImageAdapter extends BaseAdapter {

    private final Context context;
    private final List<Integer> imageIds;
    private final int width;
    private final int height;
    private final int padding;
    private int selected = -1;

    public ChooseCategoryTypeImageAdapter(
            Context context, List<Integer> imageIds, int width, int height, int padding) {
        this.context = context;
        this.imageIds = imageIds;
        this.width = width;
        this.height = height;
        this.padding = padding;
    }

    @Override
    public int getCount() {
        return imageIds.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int position) {
        selected = position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
        } else {
            imageView = (ImageView) convertView;
        }
        if (position == selected) {
            imageView.setBackgroundResource(R.drawable.list_selector_background_transition_holo_dark);
        } else {
            imageView.setBackgroundColor(Color.TRANSPARENT);
            //imageView.setColorFilter(android.R.color.black);

        }
        imageView.setImageResource(imageIds.get(position));
        imageView.setMinimumHeight(height);
        imageView.setMinimumWidth(width);
        imageView.setPadding(padding, padding, padding, padding);
        return imageView;
    }
}