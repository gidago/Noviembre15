package lab.acme.noviembre15.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import lab.acme.noviembre15.R;
import lab.acme.noviembre15.provider.FactsContract;
import lab.acme.noviembre15.provider.Provider;

// Refs:
// http://code.tutsplus.com/es/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465
// http://javatechig.com/android/android-recyclerview-example
// http://www.hermosaprogramacion.com/2015/02/android-recyclerview-cardview/
// https://github.com/tutsplus/Android-CardViewRecyclerView/blob/master/ListsAndCards/app/src/main/java/com/hathy/listsandcards/RVAdapter.java
// https://github.com/codepath/android_guides/wiki/Populating-a-ListView-with-a-CursorAdapter
// http://developer.android.com/intl/es/training/improving-layouts/smooth-scrolling.html
// https://github.com/hdodenhof/CircleImageView
// http://square.github.io/picasso/
// https://www.buzzingandroid.com/tools/android-layout-finder/
// http://android-holo-colors.com/
// http://unitid.nl/androidpatterns/
// https://android-arsenal.com/tag/53//
// https://gist.github.com/skyfishjy/443b7448f59be978bc59#file-cursorrecyclerviewadapter-java
// http://www.sgoliver.net/blog/controles-de-seleccion-v-recyclerview/
// http://prontoandroid.com/create-and-publish-your-first-android-app-part-2/


public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder> {

    private final String LOG_TAG = MyListCursorAdapter.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;

    public MyListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
        mContext = context;
        mCursor = cursor;
    }

    /**
     * Cache of the children views for a fact list item.
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView factCardImage;
        private final TextView  factDate;
        private final TextView  factTitle;
        private final TextView  factID;

        public ViewHolder(View itemView) {
            super(itemView);
            factCardImage = (ImageView) itemView.findViewById(R.id.image_view_fact_card);
            factDate = (TextView)itemView.findViewById(R.id.text_card_view_fact_date);
            factTitle = (TextView)itemView.findViewById(R.id.text_card_view_fact_title);
            factID = (TextView)itemView.findViewById(R.id.textView_DBID);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.facts_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        mCursor = cursor;
        viewHolder.factDate.setText(mCursor.getString(mCursor.getColumnIndex(FactsContract.FactsEntry.COLUMN_DATE)));
        viewHolder.factTitle.setText(mCursor.getString(mCursor.getColumnIndex(FactsContract.FactsEntry.COLUMN_TITLE)));
        viewHolder.factID.setText(mCursor.getString(mCursor.getColumnIndex(FactsContract.FactsEntry.COLUMN_ID)));
        switch (mCursor.getInt(mCursor.getColumnIndex(FactsContract.FactsEntry.COLUMN_CATEGORY_ID))) {
            case 1:
                Picasso.with(mContext).load(R.drawable.category_1).into(viewHolder.factCardImage);
                break;
            case 2:
                Picasso.with(mContext).load(R.drawable.category_2).into(viewHolder.factCardImage);
                break;
            case 3:
                Picasso.with(mContext).load(R.drawable.category_3).into(viewHolder.factCardImage);
                break;
            default:
                Picasso.with(mContext).load(R.drawable.no_category).into(viewHolder.factCardImage);
        }
    }
}