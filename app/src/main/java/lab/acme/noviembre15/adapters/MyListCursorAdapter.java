package lab.acme.noviembre15.adapters;

import android.content.Context;
import android.database.Cursor;
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


//TODO - añadido onClickListener - probar
public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder> implements View.OnClickListener{

    private final String LOG_TAG = MyListCursorAdapter.class.getSimpleName();
    private Context mContext;
    private Cursor mCursor;

    private View.OnClickListener listener;

    public MyListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
        mContext = context;
        mCursor = cursor;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
		//  TODO cambiados de public a private
        private final ImageView factCardImage;
        private final TextView  factDate;
        private final TextView  factTitle;
        private final TextView  factID;
        
        //public final CardView  cardView;
        

        public ViewHolder(View itemView) {
            super(itemView);
            // TODO ¿Es necesario cardView?
            //cardView = (CardView)itemView.findViewById(R.id.facts_main_card_view);
            factCardImage = (ImageView) itemView.findViewById(R.id.image_view_fact_card);
            factDate = (TextView)itemView.findViewById(R.id.text_card_view_fact_date);
            factTitle = (TextView)itemView.findViewById(R.id.text_card_view_fact_title);
            factID = (TextView)itemView.findViewById(R.id.textView_DBID);
        }


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.facts_card_view, parent, false);
        //TODO añadido listener
        rowView.setOnClickListener(this);
        ViewHolder viewHolder;
        viewHolder = new ViewHolder(rowView);
        return viewHolder;
    }

	//TODO añadido listener
	public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

	//TODO añadido listener 
    @Override
    public void onClick(View view) {
        if(listener != null)
            listener.onClick(view);
    }

	// TODO - ¿necesario definir metodo bindFact? Ver http://www.sgoliver.net/blog/controles-de-seleccion-v-recyclerview/
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

    /**
     * Swap in a new Cursor, returning the old Cursor.  Unlike
     * {@link #changeCursor(Cursor)}, the returned old Cursor is <em>not</em>
     * closed.
     *
     * @param newCursor
     */
    @Override
    public Cursor swapCursor(Cursor newCursor) {
        return super.swapCursor(newCursor);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }



 /*

    int positionToRemove = mLayoutManager.findFirstCompletelyVisibleItemPosition();
                sampleRecyclerAdapter.removeData(positionToRemove);


    public void removeData (int position) {

        sampleData.remove(position);
        notifyItemRemoved(position);
    }
*/

}