package lab.acme.noviembre15.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.List;
import lab.acme.noviembre15.R;
import lab.acme.noviembre15.models.FactItem;

// Refs:
// http://code.tutsplus.com/es/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465
// http://javatechig.com/android/android-recyclerview-example
// http://www.hermosaprogramacion.com/2015/02/android-recyclerview-cardview/
// https://github.com/tutsplus/Android-CardViewRecyclerView/blob/master/ListsAndCards/app/src/main/java/com/hathy/listsandcards/RVAdapter.java
// https://github.com/codepath/android_guides/wiki/Populating-a-ListView-with-a-CursorAdapter
// http://developer.android.com/intl/es/training/improving-layouts/smooth-scrolling.html
//
// https://www.buzzingandroid.com/tools/android-layout-finder/
// http://android-holo-colors.com/
// http://unitid.nl/androidpatterns/

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.ViewHolder>{
// public class ForecastAdapter extends CursorAdapter {
// public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
	
   private List<FactItem> mFacItems;
   private Context mContext;
   View rowView;

	/**
	* Cache of the children views for a fact list item.
	*/
	public static class ViewHolder extends RecyclerView.ViewHolder{
    //public static class ViewHolder {
    //public static class PersonViewHolder extends RecyclerView.ViewHolder {
       //ImageView factImage;
       public final ImageView factCardImage;
       public final TextView factDate;
       public final TextView factTitle;
       public final CardView cardView;

       public ViewHolder(View itemView) {
           super(itemView);
           cardView = (CardView)itemView.findViewById(R.id.facts_main_card_view);
		   //factImage = (ImageView) itemView.findViewById(R.id.image_view_fact_image);
           factCardImage = (ImageView) itemView.findViewById(R.id.image_view_fact_card);
           //factDate = (TextView)itemView.findViewById(R.id.text_view_fact_date);
           factDate = (TextView)itemView.findViewById(R.id.text_card_view_fact_date);
		   //factTitle = (TextView)itemView.findViewById(R.id.text_view_fact_title);
		   factTitle = (TextView)itemView.findViewById(R.id.text_card_view_fact_title);
       }
   }

	//public ForecastAdapter(Context context, Cursor c, int flags) {
	// RVAdapter(List<Person> persons){		
   public FactsAdapter(List<FactItem> factsList, Context context){
       mFacItems = factsList;
       mContext = context;
   }

	//TODO test
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

	// Create new views (invoked by the layout manager)
   	@Override
   	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {	   	
       	//rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.facts_list_row, parent, false);
		//TODO test
       	rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.facts_card_view, parent, false);
       	ViewHolder viewHolder = new ViewHolder(rowView);
       	return viewHolder;
	}

	 /*
		This is where we fill-in the views with the contents of the cursor.
	*/
	// Replace the contents of a view (invoked by the layout manager)
   	@Override
   	public void onBindViewHolder(ViewHolder holder, int position) {
   		// TODO - position coincide con el _ID de la bd ??
       	final FactItem selectedFact = mFacItems.get(position);

       	holder.factDate.setText(selectedFact.getDate());
 //      holder.factTitle.setText(selectedFact.getTitle());
 //      Picasso.with(mContext).load(selectedFact.getProfileImageId()).into(holder.factImage);
		Picasso.with(mContext).load(selectedFact.getProfileImageId()).into(holder.factCardImage );
      	/* if (position % 2 == 0){
           rowView.setBackgroundColor(mContext.getResources().getColor(R.color.activated_color));
       	}*/
       
/**       Handle RecyclerView Click Event

//Handle click event on both title and image click
customViewHolder.textView.setOnClickListener(clickListener);
customViewHolder.imageView.setOnClickListener(clickListener);

customViewHolder.textView.setTag(customViewHolder);
customViewHolder.imageView.setTag(customViewHolder);    

View.OnClickListener clickListener = new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        CustomViewHolder holder = (CustomViewHolder) view.getTag();
        int position = holder.getPosition();

        FeedItem feedItem = feedItemList.get(position);
        Toast.makeText(mContext, feedItem.getTitle(), Toast.LENGTH_SHORT).show();
    }
};   
    
        
http://stackoverflow.com/questions/28912253/recyclerview-onitemclick-listener
	
first store whole view in your viewHolder:

  public class FeedListRowHolder extends RecyclerView.ViewHolder {
     protected TextView title;
     protected View mRootView;

    public FeedListRowHolder(View view) {
        super(view);
        this.title = (TextView) view.findViewById(R.id.title);
        mRootView = view;
    }    
    
    
 then set click listener at onBindViewHolder:

@Override
public void onBindViewHolder(FeedListRowHolder feedListRowHolder, int i) {
    FeedItem feedItem = feedItemList.get(i);        
    feedListRowHolder.title.setText(Html.fromHtml(feedItem.getTitle()));
    feedListRowHolder.mRootView.setOnClickListener(new OnClickListener(){

        @Override
        public void onClick(View v) {

        }
   });
}       
*/       

/*       holder.checkInCheckOutButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Is the toggle on?
               boolean on = ((ToggleButton) v).isChecked();

               if (on){
                   selectedAttendant.setIsCheckedIn(true);
                   Toast.makeText(mContext, selectedAttendant.getName() + " checked in ", Toast.LENGTH_SHORT).show();
               } else {
                   selectedAttendant.setIsCheckedIn(false);
                   Toast.makeText(mContext, selectedAttendant.getName() + " checked out ", Toast.LENGTH_SHORT).show();
               }
           }
       }); */
   }

   @Override
   public int getItemCount() {
       return mFacItems.size();
   }

   public void Add(FactItem factItem){
       mFacItems.add(factItem);
       notifyDataSetChanged();
   }
   
   
	// TODO - ????
	/*
		Remember that these views are reused as needed.
	*/
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.facts_card_view, parent, false);
		View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
		ViewHolder viewHolder = new ViewHolder(view);
		view.setTag(viewHolder);
		return view;
	}
      
}

public class MyCursorAdapter extends CursorAdapter {

	/**
	 * ViewHolder class for layout.<br />
	 * <br />
	 * Auto-created on 2015-11-20 11:53:11 by Android Layout Finder
	 * (http://www.buzzingandroid.com/tools/android-layout-finder)
	 */
	private static class ViewHolder {
		public final CircleImageView imageViewFactCard;
		public final TextView textCardViewFactDate;
		public final TextView textCardViewFactTitle;
		public final View linea;
		public final ImageView compartir;
	
		private ViewHolder(CircleImageView imageViewFactCard, TextView textCardViewFactDate, TextView textCardViewFactTitle, View linea, ImageView compartir) {
			this.imageViewFactCard = imageViewFactCard;
			this.textCardViewFactDate = textCardViewFactDate;
			this.textCardViewFactTitle = textCardViewFactTitle;
			this.linea = linea;
			this.compartir = compartir;
		}
	
		public static ViewHolder create(android.support.v7.widget.CardView facts_main_card_view) {
			CircleImageView imageViewFactCard = (CircleImageView)facts_main_card_view.findViewById( R.id.image_view_fact_card );
			TextView textCardViewFactDate = (TextView)facts_main_card_view.findViewById( R.id.text_card_view_fact_date );
			TextView textCardViewFactTitle = (TextView)facts_main_card_view.findViewById( R.id.text_card_view_fact_title );
			View linea = (View)facts_main_card_view.findViewById( R.id.linea );
			ImageView compartir = (ImageView)facts_main_card_view.findViewById( R.id.compartir );
			return new ViewHolder( imageViewFactCard, textCardViewFactDate, textCardViewFactTitle, linea, compartir );
		}
	}
	
	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final ViewHolder vh = (ViewHolder)view.getTag();

		// TODO Bind your data to the views here
	}
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		View view = mInflater.inflate( R.layout.listitem, parent, false );
		view.setTag( ViewHolder.create( (android.support.v7.widget.CardView)view ) );
		return view;
	}

	private LayoutInflater mInflater;

	// Constructors
	public MyCursorAdapter(Context context) {
		super(context, null, true);
		this.mInflater = LayoutInflater.from( context );
	}
	public MyCursorAdapter(Context context, Cursor c) {
		super(context, c, true);
		this.mInflater = LayoutInflater.from( context );
	}
}




