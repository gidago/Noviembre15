package lab.acme.noviembre15.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import lab.acme.noviembre15.R;
import lab.acme.noviembre15.provider.Provider;

/**
 * Created by skyfishjy on 10/31/14.
 */
//
// https://gist.github.com/skyfishjy/443b7448f59be978bc59#file-cursorrecyclerviewadapter-java
//

public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder>{

    private final String LOG_TAG = MyListCursorAdapter.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;

    public MyListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
        mContext = context;
        mCursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView factCardImage;
        public final TextView factDate;
        public final TextView factTitle;
        public final CardView cardView;
        public final TextView factID;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.facts_main_card_view);
            factCardImage = (ImageView) itemView.findViewById(R.id.image_view_fact_card);
            factDate = (TextView)itemView.findViewById(R.id.text_card_view_fact_date);
            factTitle = (TextView)itemView.findViewById(R.id.text_card_view_fact_title);
            factID = (TextView)itemView.findViewById(R.id.textView_DBID);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.facts_list_row, parent, false);
        //TODO test
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.facts_card_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        mCursor = cursor;

        viewHolder.factDate.setText(mCursor.getString(mCursor.getColumnIndex(Provider.COLUMN_DATE)));
        viewHolder.factTitle.setText(mCursor.getString(mCursor.getColumnIndex(Provider.COLUMN_TITLE)));
        viewHolder.factID.setText(mCursor.getString(mCursor.getColumnIndex(Provider.COLUMN_ID)));

        switch (mCursor.getInt(mCursor.getColumnIndex(Provider.COLUMN_CATEGORY_ID))) {
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

        Log.e(LOG_TAG, " ------> " + mCursor.getColumnIndex(Provider.COLUMN_CATEGORY_ID));
        Log.e(LOG_TAG, " --> " + mCursor.getString(mCursor.getColumnIndex(Provider.COLUMN_DATE)));
        Log.e(LOG_TAG, " --> " + mCursor.getString(mCursor.getColumnIndex(Provider.COLUMN_TITLE)));
        Log.e(LOG_TAG, " Category_ID --> " + mCursor.getString(mCursor.getColumnIndex(Provider.COLUMN_CATEGORY_ID)));
    }
}