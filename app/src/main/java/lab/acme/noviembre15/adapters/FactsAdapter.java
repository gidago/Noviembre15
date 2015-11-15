package lab.acme.noviembre15.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.squareup.picasso.Picasso;

import java.util.List;

import lab.acme.noviembre15.R;
import lab.acme.noviembre15.models.FactItem;

/**
class AdapterNICList extends CursorAdapter {

   public AdapterNICList(Context context, Cursor cursor) {
       super(context, cursor, 0);

   }

   // The newView method is used to inflate a new view and return it,
   // you don't bind any data to the view at this point.
   @Override
   public View newView(Context context, Cursor cursor, ViewGroup parent) {
       return LayoutInflater.from(context).inflate(R.layout.row, parent, false);
   }

   // The bindView method is used to bind all data to a given view
   // such as setting the text on a TextView.
   @Override
   public void bindView(View view, Context context, Cursor cursor) {
       // Find fields to populate in inflated template
       TextView tvMac = (TextView) view.findViewById(R.id.textName);
       TextView tvVendor = (TextView) view.findViewById(R.id.textValue);
       //Selección de icono de cobertura.
       ImageView imgPower = (ImageView) view.findViewById(R.id.item_image);

       Log.e(LOG_TAG, " id: " + cursor.getInt(cursor.getColumnIndexOrThrow("_id")));

       int pwr = cursor.getInt(cursor.getColumnIndexOrThrow("icon_type"));

       if (pwr == 1){	//"Generic"
           imgPower.setImageResource(R.drawable.ic_generic);
       }
       else if (pwr == 2) { //"Router"
           imgPower.setImageResource(R.drawable.ic_router);
       }
       else if (pwr == 3) { //"Usb"
           imgPower.setImageResource(R.drawable.ic_usb);
       }
       else if (pwr == 4) { //"Computer"
           imgPower.setImageResource(R.drawable.ic_computer);
       }
       else if (pwr == 5) { //"Laptop"
           imgPower.setImageResource(R.drawable.ic_laptop);
       }
       else if (pwr == 6) { //"iMac"
           imgPower.setImageResource(R.drawable.ic_imac);
       }
       else if (pwr == 7) { //"iPod"
           imgPower.setImageResource(R.drawable.ic_ipod);
       }
       else if (pwr == 8) { //"Mobile"
           imgPower.setImageResource(R.drawable.ic_mobile);
       }
       else if (pwr == 9) { //"Tablet"
           imgPower.setImageResource(R.drawable.ic_tablet);
       }
       else if (pwr == 10) { //"Printer"
           imgPower.setImageResource(R.drawable.ic_printer);
       }
       else if (pwr == 11) { //"DB"
           imgPower.setImageResource(R.drawable.ic_database);
       }
       else if (pwr == 12) { //"FireWall"
           imgPower.setImageResource(R.drawable.ic_firewall);
       }
       else if (pwr == 13) { //"Star"
           imgPower.setImageResource(R.drawable.star);
       }
       else if (pwr == 14) { //"Desktop"
           imgPower.setImageResource(R.drawable.ic_desktop);
       }
       else if (pwr == 15) { //"Access"
           imgPower.setImageResource(R.drawable.ic_accesspoint);
       }
       else if (pwr == 16) { //"User"
           imgPower.setImageResource(R.drawable.lock);
       }
       else if (pwr == 17) { //"xyz"
           imgPower.setImageResource(R.drawable.lock);
       }
       else{	//"Generic"
           imgPower.setImageResource(R.drawable.ic_generic);
       }
       // Extract properties from cursor
       // Populate fields with extracted properties
       tvMac.setText(cursor.getString(cursor.getColumnIndexOrThrow("mac")));
       tvVendor.setText(cursor.getString(cursor.getColumnIndexOrThrow("vendor")));   FactItem
   }
} */

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.ViewHolder>{

   private List<FactItem> mAttendants;
   private Context mContext;
   View rowView;

   public static class ViewHolder extends RecyclerView.ViewHolder{
       ImageView attendantHeadshot;
       TextView attendantName, attendantEmail;
       ToggleButton checkInCheckOutButton;

       public ViewHolder(View itemView) {
           super(itemView);
           attendantHeadshot = (ImageView) itemView.findViewById(R.id.image_view_attendant_head_shot);
           attendantName = (TextView)itemView.findViewById(R.id.text_view_attendants_name);
           attendantEmail = (TextView)itemView.findViewById(R.id.text_view_attendants_email);
           checkInCheckOutButton = (ToggleButton)itemView.findViewById(R.id.togglebutton);
       }
   }

   public FactsAdapter(List<FactItem> attendantsList, Context context){

       mAttendants = attendantsList;
       mContext = context;
   }

   /**
    public AdapterNICList(Context context, Cursor cursor) {
    super(context, cursor, 0);

    }
    * */


   // Create new views (invoked by the layout manager)
   @Override
   public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attendants_list_row, parent, false);

       ViewHolder viewHolder = new ViewHolder(rowView);
       return viewHolder;
   }

   // Replace the contents of a view (invoked by the layout manager)
   @Override
   public void onBindViewHolder(ViewHolder holder, int position) {
       final FactItem selectedAttendant = mAttendants.get(position);

       holder.attendantName.setText(selectedAttendant.getDate());
       holder.attendantEmail.setText(selectedAttendant.getTitle());
       Picasso.with(mContext).load(selectedAttendant.getProfileImageId()).into(holder.attendantHeadshot);

       if (position % 2 == 0){
           rowView.setBackgroundColor(mContext.getResources().getColor(R.color.activated_color));
       }

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
       return mAttendants.size();
   }

   public void Add(FactItem factItem){
       mAttendants.add(factItem);
       notifyDataSetChanged();
   }


}
